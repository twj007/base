
$(function(){
    $('#loginForm').bootstrapValidator({
        message: '未验证',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        trigger: 'blur',

        fields: {
            username: {
                message: '用户名不为空',
                validators: {
                    notEmpty: {
                        message: '用户名不为空'
                    },
                    remote : {
                        delay: 500,
                        name : 'username',
                        url : 'user/checkUserExists',
                        message : "该用户不存在",
                        type : 'get'
                    }
                }
            },
            password: {
                message: '密码不为空不为空',
                validators: {
                    notEmpty: {
                        message: '密码不为空'
                    },
                    callback: {
                        callback: function(value, validator) {
                            // Check the password strength
                            if (value.length < 6) {
                                return {
                                    valid: false,
                                    message: '密码大于6个字符'
                                }
                            }

                            if (value === value.toLowerCase()) {
                                return {
                                    valid: false,
                                    message: '包含大写字符'
                                }
                            }
                            if (value === value.toUpperCase()) {
                                return {
                                    valid: false,
                                    message: '包含小写字符'
                                }
                            }
                            if (value.search(/[0-9]/) < 0) {
                                return {
                                    valid: false,
                                    message: '至少包含一个数字'
                                }
                            }

                            return true;
                        }
                    }

                }
            }
        }
    }).on('error.form.bv', function(e){
        console.log(e);
    }).on('success.form.bv', function(e){
        e.preventDefault();
        $.post({
            url: "/user/login",
            data: {
                username: $("#username").val(),
                password: $("#password").val()
            },
            success: function(data){
                console.log(ctx);
                if(data.code == 500){
                    console.log("登陆失败");
                    return;
                }else if(data.code == 200){
                    console.log(data.message);
                    window.location.href = ctx;
                }else if(data.retCode == 200){
                    console.info("登陆成功");
                    window.location.href = ctx;
                }else{
                    console.log(data.message);
                    return;
                }
            }
        });
    });
});