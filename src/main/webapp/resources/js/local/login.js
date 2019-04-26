$(function () {
    //登录验证的controller url
    var loginUrl = '/o2o/local/logincheck';
    //从地址栏URL获取usertype
    var usertype = getQueryString("usertype");
    //登录次数，累计登录三次失败后，自动弹出验证码要求验证
    var loginCount = 0;

    $('#submit').click(function () {
        //账号
        var userName = $('#username').val();
        //密码
        var password = $('#psw').val();
        //验证码
        var verifyCodeActual = $('#j_captcha').val();
        //是否需要验证码验证，默认不需要
        var needVerify = false;
        if (loginCount >= 3) {
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            } else {
                needVerify = true;
            }
        }
        //访问后台进行登录验证
        $.ajax({
            url: loginUrl,
            async: false,
            cache: false,
            type: "post",
            dataType: 'json',
            data: {
                userName: userName,
                password: password,
                verifyCodeActual: verifyCodeActual,
                needVerify: needVerify
            },
            success: function (data) {
                if (data.success) {
                    $.toast('登录成功！');
                    if (usertype == 1) {
                        //前端展示系统首页
                        window.location.href = '/o2o/fronted/index';
                    } else {
                        //店铺列表首页
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }

                } else {
                    $.toast('登录失败！' + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        //登录失败三次，进行验证码验证
                        $('#verifyPart').show();
                    }
                }
            }
        });
    });
    
});