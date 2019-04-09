document.write("<script type='text/javascript' src='../dist/js/public.js'></script>");

var userInfo;

$(function () {
    userInfo = loadUserInfo();
    if (userInfo == null) {
        $(".user-login-before").css("display", "block");
        $(".user-login").css("display", "none");
    } else {
        $(".user-login-before").css("display", "none");
        $(".user-login").css("display", "block");
        $("#username").text(userInfo.username);
    }
});
