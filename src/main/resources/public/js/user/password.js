layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    form.on("submit(saveBtn)",function (data){

        var filedData=data.field;

        $.ajax({
            type:"post",
            url:ctx+"/user/updatePassword",
            data:{
                oldPassword:filedData.old_password,
                newPassword:filedData.new_password,
                confirmPassword:filedData.again_password
            },
            dataType:"json",
            success:function (data){
                if(data.code==200){
                    layer.msg("用户密码修改成功，系统将在3秒钟后退出",{icon:6},function (){
                        $.removeCookie("userIdStr",{domain:"localhost",path:"/"});
                        $.removeCookie("userName",{domain:"localhost",path:"/"});
                        $.removeCookie("trueName",{domain:"localhost",path:"/"});

                        window.parent.location.href=ctx+"/index";

                    });
                }else {
                    layer.msg(data.msg);
                }
            }

        });

    });



});