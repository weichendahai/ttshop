/**
 * Created by Woody on 2017/4/29.
 */
$(function(){
    $("body").append("<div id='selectItemLayer'></div>");
    $("#selectItemLayer").hide();
    var content = "<input type='text' class='form-control' value='' placeholder='请输入商品名称关键字' name='searchItemKeyword' id='searchItemKeyword' style='margin:10px;width:70%;display:inline-block;'>"
                 + "<button id = 'searchItemBtn' class='btn btn-primary' style='display:inline-block;margin:4px;'>搜索</button>"
                 + "<div  style='margin:10px;border: 1px solid #e4eaec;'><div id='selectTips'></div><table id='resultTable'class='table table-hover table-striped' style='margin-bottom:0;'><table></div>";
    $("#selectItemLayer").html(content);
$("#searchItemBtn").click(searchKeyword);
});
var currentUpdateId;
var currentUpdateName;
var selectLayerIndex;
function openItemSearch(updateId,updateName){
    currentUpdateId = updateId;
    currentUpdateName = updateName;
    selectLayerIndex = layer.open({
        type: 1,
        title: "选择商品",
        closeBtn: 1,
        area: ['400px', '400px'],
        skin: 'layui-layer-rim',
        shadeClose: true,
        content:$('#selectItemLayer')
    });
}

function searchKeyword() {
    var para =  $("#searchItemKeyword").val();
    if (para=="") {
        layer.msg('请输入搜索的商品名称关键字');
        return;
    }
    para = encodeURI(para);
    //var searchUrl = $("#searchItemUrl").val();
    //var searchUrl = $("#selectItemLayer").attr("name");
    var searchUrl = "/item/commonSearchItem";
    $.ajax({
        url: searchUrl,
        data: {keyword:para},
        type: "POST",
        success: function(data){
            var d = JSON.parse(data);
            if(d.success && d.data){
                var content = "<tr><td style='display: none'>序号</td><td>商品名称</td><td>商品厂家</td></tr>";
                var flag = d.data.length > 0;
                $(d.data).each(function(index,item){
                    content+= "<tr class ='resultTd'><td class='selectId'  style='display: none'>"+item.id+"</td><td class='selectName' >"+item.itemName+"</td><td>"+item.itemCompose+"</td></tr>"
                });
                if (flag){
                    $("#selectTips").html("鼠标左键点击商品名称选择");
                }else{
                    $("#selectTips").html("没有找到商品，建议更换关键字尝试");
                }
                $("#resultTable").html(content);
                $(".resultTd").click(resultTdClick);
            }
        }
    });
}

function resultTdClick(){
    var id = $(this).children(".selectId").html();
    var name =  $(this).children(".selectName").html();
    $("#"+currentUpdateId).val(id);
    $("#"+currentUpdateName).val(name);
    layer.close(selectLayerIndex);
    $("#resultTable").html("");
    $("#selectTips").html("");
    $("#searchItemKeyword").val("");
}