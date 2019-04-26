$(function () {
    $('#log-out').click(function () {
        //清除session
        $.ajax({
            url: '/o2o/local/logout',
            type: 'post',
            success: function (data) {
                if (data.success) {
                    var usertype = $('#log-out').attr('usertype');
                    //清除成功之后退出到登录界面
                    window.location.href = '/o2o/local/login?usertype=' + usertype;
                } else {
                    $.toast("退出失败！" + data.errMsg);
                }
            },
        });
    })
});