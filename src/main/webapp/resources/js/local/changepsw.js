$(function () {
    //修改平台密码的controller url
    var url = '/o2o/local/changelocalpwd';
    //从地址栏的URL获取usertype
    //usertype==1则为customer,其他则为shopmanager
    var usertype = getQueryString("usertype");
    $('#submit').click(function () {
        //获取用户名
        var userName = $('#userName').val();
        //获取原密码
        var password = $('#password').val();
        //获取新密码
        var newPassword = $('#newPassword').val();
        //获取确认密码
        var confirmPassword = $('#confirmPassword').val();
        if (newPassword != confirmPassword) {
            $.toast('两次输入的新密码不一致');
            return;
        }

        //添加表单数据
        var formData = new FormData();
        formData.append('userName', userName);
        formData.append('password', password);
        formData.append('newPassword', newPassword);

        //获取验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    //前端展示系统
                    if (usertype == 1) {
                        window.location.href = '/o2o/fronted/index';
                    } else {
                        //店家管理系统的店铺列表页
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
                } else {
                    $.toast('提交失败！' + data.errMsg);
                    //刷新验证码
                    $('#captcha_img').click();
                }
            }
        });
    });

    $('#back').click(function () {
        window.location.href = '/o2o/shopadmin/shoplist';
    });
});
