package me.guoxin.manager.utils;


import me.guoxin.pojo.GfsUser;
import me.guoxin.pojo.IException;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

@Component
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithName = "MD5";
    private int hashInterations = 2;

    //加密算法
    public void encryptPassword(GfsUser user) {
        if (user.getPassword() != null) {
            //对user对象设置盐：salt；这个盐值是randomNumberGenerator生成的随机数
            user.setSalt(randomNumberGenerator.nextBytes().toHex());

            //调用SimpleHash指定散列算法参数：1、算法名称；2、用户输入的密码；3、盐值（随机生成的）；4、迭代次数
            String newPassword = new SimpleHash(
                    algorithName,
                    user.getPassword(),
                    ByteSource.Util.bytes(user.CredentialsSalt()),
                    hashInterations).toHex();
            user.setPassword(newPassword);
        }
    }

    //加密算法
    public boolean usreOldPassword(GfsUser user, String oldPassword) {
        if (user.getPassword() != null && user.getSalt() != null) {
            String salt = user.CredentialsSalt();

            //调用SimpleHash指定散列算法参数：1、算法名称；2、用户输入的密码；3、盐值（随机生成的）；4、迭代次数
            String passwordSure = new SimpleHash(
                    algorithName,
                    oldPassword,
                    salt,
                    hashInterations).toHex();
            if (passwordSure.equals(user.getPassword())) {
                return true;
            } else
                return false;
        } else {
            throw new IException("用户未曾设置密码");
        }
    }
}
