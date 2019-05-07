document.write("<script type='text/javascript' src='../dist/js/public.js'></script>");

/**
 * 初始化加载函数
 */
$(function () {
    var userInfo = loadUserInfo();
    showUserInfo(userInfo);
});

/**
 * 删除请求
 * @param data
 */
function sendDELETE(url, data) {
    $.ajax({
        type: "DELETE",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code === 200) {
                alert(result.message);
                window.location.reload();
            } else {
                if (isEmptyResult(result)) {
                    alert("未知错误");
                } else {
                    alert(result.message);
                }
                window.location.reload();
            }
        },
        error: function (result) {
            var resultMsg = result.responseJSON;
            if (isEmptyResult(resultMsg)) {
                alert("未知错误");
            } else {
                alert(resultMsg.message);
            }
            window.location.reload();
        }
    });
}

/**
 * 添加请求
 */
function sendPOST(url, data) {
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code === 200) {
                alert(result.message);
                window.location.reload();
            } else {
                if (isEmptyResult(result)) {
                    alert("发生未知错误");
                } else {
                    alert(result.message);
                }
            }
        },
        error: function (result) {
            var resultMsg = result.responseJSON;
            if (isEmptyResult(resultMsg)) {
                alert("发生未知错误");
            } else {
                alert(resultMsg.message);
            }
        }
    })
}

/**
 * 更新请求
 */
function sendPUT(url,data) {
    $.ajax({
        type: "PUT",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code === 200) {
                alert(result.message);
                window.location.reload();
            } else {
                if (isEmptyResult(result)) {
                    alert("发生未知错误");
                } else {
                    alert(result.message);
                }
            }
        },
        error: function (result) {
            var resultMsg = result.responseJSON;
            if (isEmptyResult(resultMsg)) {
                alert("发生未知错误");
            } else {
                alert(resultMsg.message);
            }
        }
    })
}
function sendPUTNoData(url) {
    $.ajax({
        type: "PUT",
        url: url,
        success: function (result) {
            if (result.code === 200) {
                alert(result.message);
                window.location.reload();
            } else {
                if (isEmptyResult(result)) {
                    alert("发生未知错误");
                } else {
                    alert(result.message);
                }
            }
        },
        error: function (result) {
            var resultMsg = result.responseJSON;
            if (isEmptyResult(resultMsg)) {
                alert("发生未知错误");
            } else {
                alert(resultMsg.message);
            }
        }
    })
}