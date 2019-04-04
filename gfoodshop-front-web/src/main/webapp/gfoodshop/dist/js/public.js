/**
 * 当前用户获取信息
 */
function loadUserInfo() {
    var userInfo;
    $.ajax({
        type: "GET",
        async: false,
        url: "/user/myInfo",
        contentType: "application/json",
        success: function (result) {
            if (result.code === 200) {
                userInfo = result.data;
                return userInfo;
            } else {
                if (isEmptyResult(result)) {

                } else {
                    alert(result.message);
                }
            }
        },
        error: function (result) {
            var resultMsg = result.responseJSON;
            if (isEmptyResult(resultMsg)) {

            } else {
                alert(resultMsg.message);
            }
        }
    });
    return userInfo;
}

/**
 * 展示当前用户信息
 * @param userInfo
 */
function showUserInfo(userInfo) {
    if (userInfo === null) {
        $("#userNameA").text("{获取错误}");
        $("#userNameB").text("{获取错误}");
        $("#userPhone").text("{获取错误}");
        $("#userRole").text("{获取错误}");
        $("#userRoleName").text("{获取错误}");
        return;
    }
    if (isNull(userInfo.username)) {
        $("#userNameA").text("{获取错误}");
        $("#userNameB").text("{获取错误}");
    } else {
        $("#userNameA").text(userInfo.username);
        $("#userNameB").text(userInfo.username);
    }
    if (isNull(userInfo.phone)) {
        $("#userPhone").text("{获取错误}");
    } else {
        $("#userPhone").text(userInfo.phone);
    }
    if (isNull(userInfo.role.name)) {
        $("#userRole").text("{获取错误}");
        $("#userRoleName").text("{获取错误}");
    } else {
        $("#userRole").text(userInfo.role.name);
        $("#userRoleName").text(userInfo.role.description);
    }
}

/**
 * 密码重复是否正确
 * @param password
 * @param passwordSure
 * @returns {boolean}
 */
function passwordIsSured(password, passwordSure) {
    return password === passwordSure;
}

/**
 * 判空
 *
 * @param obj
 * @returns {boolean}
 */
function isNull(obj) {
    return obj == null || obj === undefined || obj.trim() === "";

}

/**
 * 参数长度验证
 *
 * @param obj
 * @param length
 * @returns {boolean}
 */
function validLength(obj, length) {
    return obj.trim().length < length;

}

/**
 * 验证手机号
 * 移动号段：
 * 134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 198
 * 联通号段：
 * 130 131 132 145 146 155 156 166 171 175 176 185 186
 * 电信号段：
 * 133 149 153 173 174 177 180 181 189 199
 * 虚拟运营商:
 * 170
 *
 * @param phone
 * @returns {boolean}
 */
function validPhone(phone) {
    var pattern = /^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[8-9])[0-9]{8}$/;
    if (pattern.test(phone.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 用户密码验证 最少6位，最多20位字母或数字的组合
 *
 * @param password
 * @returns {boolean}
 */
function validPassword(password) {
    var pattern = /^[a-zA-Z0-9]{4,20}$/;
    if (pattern.test(password.trim())) {
        return (true);
    } else {
        return (false);
    }
}


/**
 * 毫秒转换
 * @param msd
 * @returns {number}
 * @constructor
 */
function MillisecondToDate(msd) {
    var time = parseFloat(msd) / 1000;
    if (null != time && "" !== time) {
        if (time > 60 && time < 60 * 60) {
            time = parseInt(time / 60.0) + "分钟" + parseInt((parseFloat(time / 60.0) -
                parseInt(time / 60.0)) * 60) + "秒";
        } else if (time >= 60 * 60 && time < 60 * 60 * 24) {
            time = parseInt(time / 3600.0) + "小时" + parseInt((parseFloat(time / 3600.0) -
                parseInt(time / 3600.0)) * 60) + "分钟" +
                parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) -
                    parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) + "秒";
        } else {
            time = parseInt(time) + "秒";
        }
    }
    return time;
}

/**
 * 判断返回结果是否为空
 * @param resultMsg
 * @returns {boolean}
 */
function isEmptyResult(resultMsg) {
    if (jQuery.isEmptyObject(resultMsg)) {
        return true;
    }
    if (resultMsg.message == null) {
        return true;
    }
    if (resultMsg.message.length === 0) {
        return true;
    }
    return false;
}

function makeAddress(province, city, district) {
    return province + city + district;
}

function findInListByName(list, data) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].name === data) {
            return list[i];
        }
    }
    return null;
}


