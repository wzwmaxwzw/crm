$(function () {
    loadModuleInfo(); 
});


var zTreeObj;
function loadModuleInfo() {
    $.ajax({
        type:"post",
        url:ctx+"/module/queryAllModules",
        dataType:"json",
        data:{
            roleId:$("input[name='roleId']").val()
        },
        success:function (data) {
            // console.log(data);

            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                data:{
                    simpleData: {
                        enable:true,
                        pIdKey: "pid"
                    }
                },
                view:{
                    showLine:false
                },
                check: {
                    enable:true,
                    chkboxType:{"Y":"ps","N":"ps"}
                },
                callback: {
                    onCheck:zTreeOnCheck
                }
            };
            zTreeObj = $.fn.zTree.init($("#test1"), setting, data);
        }
    })
}


function zTreeOnCheck(event, treeId, treeNode) {
    // alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
    var nodes= zTreeObj.getCheckedNodes(true);
    // console.log(nodes);
    var mids="mids=";
    for(var i=0;i<nodes.length;i++){
        if(i<nodes.length-1){
            mids=mids+nodes[i].id+"&mids=";
        }else{
            mids=mids+nodes[i].id;
        }
    }

    $.ajax({
        type:"post",
        url:ctx+"/role/addGrant",
        data:mids+"&roleId="+$("input[name='roleId']").val(),
        dataType:"json",
        success:function (data) {
            console.log(data);
        }
    })

}