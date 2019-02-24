/**
 * 全选
 */
$(".checkall").click(function () {
    var check = $(this).prop("checked");
    $(".checkchild").prop("checked", check);
    var trs = $(".datatables").find("tr");
    if (check) {
        for (var i = 0; i < trs.length; i++) {
            trs[i].setAttribute("class", "selected");
        }
    } else {
        for (var i = 0; i < trs.length; i++) {
            trs[i].setAttribute("class", "");
        }
    }
});

/**
 * 点击选中
 */
$(".datatables").on('click', 'tr', function () {
    $(this).toggleClass('selected');
    var i = $(".datatables").DataTable().row(this).index();
    var all = $(".checkchild");
    for (var j = 0; j < all.length; j++) {
        if (j === i) {
            var thisClass = $(this)[0].className;
            if (thisClass.indexOf('selected') >= 0) {
                all[j].checked = true;
            } else {
                all[j].checked = false;
                $(".checkall")[0].checked = false;
            }
        }
    }
});