package me.guoxin.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.dto.OnlineUserDTO;
import me.guoxin.dto.PasswordDTO;
import me.guoxin.dto.RegisterUserDTO;
import me.guoxin.manager.mapper.RoleMapper;
import me.guoxin.manager.utils.SessionUtils;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsRole;
import me.guoxin.pojo.IException;
import me.guoxin.manager.mapper.UserMapper;

import me.guoxin.manager.service.UserService;
import me.guoxin.pojo.GfsUser;
import me.guoxin.manager.utils.PasswordHelper;
import me.guoxin.utils.DataTableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PasswordHelper passwordHelper;

    @Resource
    private RedisSessionDAO redisSessionDAO;

    @Resource
    private SessionManager sessionManager;

    @Override
    public GfsUser getUserByPhone(String userPhone) {
        List<GfsUser> list = new ArrayList<>();
        try {
            list = userMapper.selectByPhone(userPhone);
        } catch (Exception e) {
            throw new IException("服务器错误");
        }
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean hasUserByPhone(String userPhone) {
        List<GfsUser> list = new ArrayList<>();
        list = userMapper.selectByPhone(userPhone);
        return list.size() != 0;
    }

    public boolean hasUserByPhone(String userPhone, Long id) {
        List<GfsUser> list = new ArrayList<>();
        list = userMapper.selectByPhone(userPhone);
        for (GfsUser user : list) {
            if (!user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUserByid(Long id) {
        List<GfsUser> list = new ArrayList<>();
        list = userMapper.selectById(id);
        for (GfsUser user : list) {
            if (!user.getId().equals(id)) {
                return true;
            }
        }
        return false;
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
    public DataTableViewPageDTO<GfsUser> listUsers(DataTableDTO dataTableDTO) {
        DataTableViewPageDTO<GfsUser> dtoDataTableViewPageDTO = new DataTableViewPageDTO<>();
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        startIndex = startIndex / length + 1;
        String OrderBy = DataTableUtil.getUserDataTableOrderBy(dataTableDTO.getOrder());
        PageHelper.startPage(startIndex, length, OrderBy);

        List<GfsUser> list = userMapper.listUsers(dataTableDTO.getSearch());

        for (GfsUser gfsUser : list) {
            gfsUser.maskPasswordInfo();
        }

        PageInfo<GfsUser> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        dtoDataTableViewPageDTO.setData(list);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(total);
        return dtoDataTableViewPageDTO;
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
            OnlineUserDTO onlineUser = SessionUtils.getSessionBo(session);
            if (onlineUser != null) {
                onlineUserList.add(onlineUser);
            }
        }
        // 再将List<UserOnlineBo>转换成mybatisPlus封装的page对象
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        int endIndex = (startIndex * length) + length;
        long size = onlineUserList.size();
        if (endIndex > size) {
            endIndex = (int) size;
        }

        List<OnlineUserDTO> list = onlineUserList.subList(startIndex, endIndex);
        dtoDataTableViewPageDTO.setData(list);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(size);
        return dtoDataTableViewPageDTO;
    }

    @Override
    public void kickOutOnlineUser(List<String> sessionIds) {
        try {
            for (String sessionId : sessionIds) {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(sessionId));
                kickoutSession.setAttribute("kickout", true);
            }
        } catch (Exception e) {
            throw new IException("踢出在线用户时发生未知错误！");
        }
    }

    @Transactional
    @Override
    public void deleteUser(List<Long> ids) {
        List<GfsUser> list = userMapper.selectAdmin();
        if (list == null || list.isEmpty()) {
            throw new IException("服务器错误");
        }
        GfsUser admin = list.get(0);
        // 移除admin
        if (ids.contains(admin.getId())) {
            ids.remove(admin.getId());
        }
        userMapper.deleteUser(ids);
    }

    @Override
    public void insertUser(GfsUser gfsUser) {
        if (hasUserByPhone(gfsUser.getPhone())) {
            throw new IException("手机号已存在，请重新输入");
        }

        if (!gfsUser.RightPhone()) {
            throw new IException("手机号格式错误，请重新输入");
        }
        // 密码加密与盐
        passwordHelper.encryptPassword(gfsUser);
        // 设置创建时间
        gfsUser.setCreateTime(new Date());
        // 设置状态正常
        gfsUser.setStatus(1);
        if (userMapper.insert(gfsUser) != 1) {
            throw new IException("添加用户失败！");
        }
    }

    @Override
    public void updateUser(GfsUser gfsUser) {
        if (hasUserByPhone(gfsUser.getPhone(), gfsUser.getId())) {
            throw new IException("手机号已存在，请重新输入");
        }

        if (!gfsUser.RightPhone()) {
            throw new IException("手机号格式错误，请重新输入");
        }
        List<GfsUser> list = userMapper.selectById(gfsUser.getId());
        if (list == null || list.isEmpty()) {
            throw new IException("更新用户发生错误，可能用户被删除，请刷新重试！");
        }
        GfsUser user = list.get(0);
        if (gfsUser.getUsername() == null) {
            gfsUser.setUsername(user.getUsername());
        }
        if (gfsUser.getPhone() == null) {
            gfsUser.setPassword(user.getPhone());
        }
        if (gfsUser.getPassword() == null) {
            gfsUser.setPassword(user.getPassword());
        }
        if (gfsUser.getSalt() == null) {
            gfsUser.setSalt(user.getSalt());
        }
        if (gfsUser.getCreateTime() == null) {
            gfsUser.setCreateTime(user.getCreateTime());
        }
        if (gfsUser.getLastLoginTime() == null) {
            gfsUser.setLastLoginTime(user.getLastLoginTime());
        }
        if (gfsUser.getStatus() == null) {
            gfsUser.setStatus(user.getStatus());
        }
        if (gfsUser.getRole() == null) {
            gfsUser.setRole(user.getRole());
        }
        if (gfsUser.getSex() == null) {
            gfsUser.setSex(user.getSex());
        }
        if (gfsUser.getDescription() == null) {
            gfsUser.setDescription(user.getDescription());
        }
        if (userMapper.updateUser(gfsUser) != 1) {
            throw new IException("修改用户失败！");
        }
    }

    @Override
    public void restoreUser(Long id) {
        List<GfsUser> list = userMapper.selectById(id);
        if (list == null || list.isEmpty()) {
            throw new IException("更新用户发生错误，可能用户被删除，请刷新重试！");
        }
        GfsUser gfsUser = list.get(0);
        if (gfsUser.getRole().getId() == GfsRole.ADMIN) {
            throw new IException("不可修改管理员");
        }
        gfsUser.setStatus(GfsUser._1);
        if (userMapper.updateUser(gfsUser) != 1) {
            throw new IException("修改用户失败！");
        }
    }

    @Override
    public void disableUser(Long id) {
        List<GfsUser> list = userMapper.selectById(id);
        if (list == null || list.isEmpty()) {
            throw new IException("更新用户发生错误，可能用户被删除，请刷新重试！");
        }
        GfsUser gfsUser = list.get(0);
        if (gfsUser.getRole().getId() == GfsRole.ADMIN) {
            throw new IException("不可修改管理员");
        }
        gfsUser.setStatus(GfsUser._0);
        if (userMapper.updateUser(gfsUser) != 1) {
            throw new IException("修改用户失败！");
        }
    }

    @Override
    public void register(RegisterUserDTO account) {
        GfsUser gfsUser = account;
        List<GfsRole> list = roleMapper.getUserRole(GfsRole.USER);
        if (!list.isEmpty()) {
            gfsUser.setRole(list.get(0));
        } else {
            throw new IException("注册发生错误，不允许普通用户注册");
        }
        insertUser(gfsUser);
    }

    @Override
    @Transactional
    public void changePassword(Long id, PasswordDTO passwordDTO) {
        if (passwordDTO == null) {
            throw new IException("服务器错误");
        }
        if (passwordDTO.getOldPassword() == null) {
            throw new IException("旧密码不为空");
        }
        if (!passwordDTO.newPasswordIsSure()) {
            throw new IException("两次输入密码不相同，请重新输入");
        }
        // 获取当前用户
        List<GfsUser> list = new ArrayList<>();
        try {
            list = userMapper.selectById(id);
        } catch (Exception e) {
            throw new IException("服务器错误");
        }
        if (list.isEmpty()) {
            throw new IException("当前用户不存在，请刷新页面重试");
        }
        GfsUser gfsUser = list.get(0);
        // 判断旧密码是否正确
        if (!passwordHelper.usreOldPassword(gfsUser, passwordDTO.getOldPassword())) {
            throw new IException("旧密码输入错误！");
        }
        gfsUser.setPassword(passwordDTO.getNewPassword());
        // 密码加密与盐
        passwordHelper.encryptPassword(gfsUser);
        if (userMapper.updateUser(gfsUser) != 1) {
            throw new IException("修改密码失败，请重试！");
        }
    }
}
