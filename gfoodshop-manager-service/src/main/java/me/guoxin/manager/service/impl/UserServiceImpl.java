package me.guoxin.manager.service.impl;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.dto.OnlineUserDTO;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.IException;
import me.guoxin.manager.mapper.UserMapper;

import me.guoxin.manager.service.UserService;
import me.guoxin.pojo.GfsUser;
import me.guoxin.manager.utils.PasswordHelper;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordHelper passwordHelper;

    @Resource
    private RedisSessionDAO redisSessionDAO;

    @Override
    public GfsUser getUserByPhone(String userPhone) {
        List<GfsUser> list = new ArrayList<>();
        try {
            list = userMapper.selectByPhone(userPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int addUser(GfsUser gfsUser) {

        if (hasUserByPhone(gfsUser.getPhone())) {
            throw new IException("手机号已存在，请重新输入");
        }

        if (!gfsUser.RightPhone()) {
            throw new IException("手机号格式错误，请重新输入");
        }

        /*密码加密与盐*/
        passwordHelper.encryptPassword(gfsUser);
        /*设置创建时间*/
        gfsUser.setCreatTime(new Date());
        /*设置状态正常*/
        gfsUser.setStatus(1);
        if (userMapper.insert(gfsUser) != 1) {
            throw new IException("添加用户失败！");
        }
        return 1;
    }


    @Override
    public boolean hasUserByPhone(String userPhone) {
        List<GfsUser> list = new ArrayList<>();
        list = userMapper.selectByPhone(userPhone);
        return list.size() != 0;
    }

    @Override
    public Set<String> getRolesByUserPhone(String userPhone) {
        return userMapper.getRolesByUserPhone(userPhone);
    }

    @Override
    public Set<String> getPermissionsByUserPhone(String userPhone) {
        return userMapper.getPermissionsByUserPhone(userPhone);
    }

    @Override
    public List<GfsUser> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public DataTableViewPageDTO<OnlineUserDTO> getOnlineUser(DataTableDTO dataTableDTO) {
        DataTableViewPageDTO<OnlineUserDTO> dtoDataTableViewPageDTO = new DataTableViewPageDTO<>();
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        Iterator<Session> it = sessions.iterator();
        List<OnlineUserDTO> onlineUserList = new ArrayList<OnlineUserDTO>();
        // 遍历session
        while (it.hasNext()) {
            // 这是shiro已经存入session的
            Session session = it.next();
            //标记为已提出的不加入在线列表
            if (session.getAttribute("kickout") != null)
                continue;
            OnlineUserDTO onlineUser = getSessionBo(session);
            if (onlineUser != null) {
                onlineUserList.add(onlineUser);
            }
        }
        // 再将List<UserOnlineBo>转换成mybatisPlus封装的page对象
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        int endIndex = (startIndex * length) + length;
        int size = onlineUserList.size();
        if (endIndex > size) {
            endIndex = size;
        }

        List<OnlineUserDTO> list = onlineUserList.subList(startIndex, endIndex);
        dtoDataTableViewPageDTO.setData(list);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(size);
        return dtoDataTableViewPageDTO;
    }

    //从session中获取UserOnline对象
    private OnlineUserDTO getSessionBo(Session session) {
        //获取session登录信息。
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (null == obj) {
            return null;
        }
        //确保是 SimplePrincipalCollection对象。
        if (obj instanceof SimplePrincipalCollection) {
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            /**
             * 获取用户登录的，@link SampleRealm.doGetAuthenticationInfo(...)方法中
             * return new SimpleAuthenticationInfo(user,user.getPswd(), getName());的user 对象。
             */
            obj = spc.getPrimaryPrincipal();
            if (null != obj && obj instanceof GfsUser) {
                // 存储session + user 综合信息
                OnlineUserDTO userDTO = new OnlineUserDTO((GfsUser) obj);
                // 最后一次和系统交互的时间
                userDTO.setLastAccess(session.getLastAccessTime());
                // 主机的ip地址
                userDTO.setHost(session.getHost());
                // session ID
                userDTO.setSessionId(session.getId().toString());
                // session最后一次与系统交互的时间
                userDTO.setLastLoginTime(session.getLastAccessTime());
                // 回话到期 ttl(ms)
                userDTO.setTimeout(session.getTimeout());
                // session创建时间
                userDTO.setStartTime(session.getStartTimestamp());
                // 是否踢出
                userDTO.setSessionStatus(false);
                // 隐藏密码和盐
                userDTO.maskPasswordInfo();
                return userDTO;
            }
        }
        return null;
    }
}
