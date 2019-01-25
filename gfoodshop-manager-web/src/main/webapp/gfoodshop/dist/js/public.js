/**
 * 判空
 *
 * @param obj
 * @returns {boolean}
 */
function isNull(obj) {
    if (obj == null || obj == undefined || obj.trim() == "") {
        return true;
    }
    return false;
}

/**
 * 参数长度验证
 *
 * @param obj
 * @param length
 * @returns {boolean}
 */
function validLength(obj, length) {
    if (obj.trim().length < length) {
        return true;
    }
    return false;
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
 * 登录
 */
function login() {
    var phone = $("#phone").val();
    var password = $("#password").val();
    var remenber = $("#remenber").prop("checked") === true;
    if (isNull(phone)) {
        swal("","请输入手机号码!", "error"); 
        return;
    }
    if (!validPhone(phone)) {
        swal("","请输入正确的手机号!", "error"); 
        return;
    }
    if (isNull(password)) {
        swal("","请输入密码!", "error"); 
        return;
    }
    if (!validPassword(password)) {
        swal("","请输入正确的密码!", "error"); 
        return;
    }
    var data = { "phone": phone, "password": password, "remember": remenber }
    console.log(data)
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/user/login",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code === 200) {
                $(location).prop('href', '/gfoodshop/pages/index.html');
            } else {
                swal("登录错误",result.message, "error");
            }
        },
        error: function (result) {
            swal("登录错误",result.message, "error");
        }
    });
}

function logout() {
    swal({
        title: "确认弹框",
        text: "你将退出登录!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((flag) => {
            if (flag) {
                delCookie("user");
                swal("你已退出此次登录", {
                    icon: "success",
                });
                window.location.href = "login.html";
            }
        });
}

/**
 * 修改密码
 */
function editPassword() {
    swal("温馨提示", "暂时还没做哦！");
}

/**
 * 写入cookie
 *
 * @param name
 * @param value
 */
function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";

}

/**
 * 读取cookie
 * @param name
 * @returns {null}
 */
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

/**
 * 删除cookie
 * @param name
 */
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

/**
 * 检查cookie
 */
function checkCookie() {
    if (getCookie("user") == null) {
        swal("未登录！");
        window.location.href = "login.html";
    }
}

function showErrorInfo(info) {
    $('.alert-danger').css("display", "block");
    $('.alert-danger').html(info);
}