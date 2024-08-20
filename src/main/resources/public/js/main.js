layui.use(['element', 'layer', 'layuimini','jquery','jquery_cookie'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        $ = layui.jquery_cookie($);

    // 菜单初始化
    $('#layuiminiHomeTabIframe').html('<iframe width="100%" height="100%" frameborder="0"  src="welcome"></iframe>')
    layuimini.initTab();

    $(".login-out").click(function (){

        layer.confirm('确定要退出吗?', {icon: 3, title:'提示'}, function(index){
            $.removeCookie("userIdStr",{domain:"localhost",path:"/"});
            $.removeCookie("userName",{domain:"localhost",path:"/"});
            $.removeCookie("trueName",{domain:"localhost",path:"/"});

            window.parent.location.href=ctx+"/index";
            layer.close(index);
        });

    });

});