/**
 * 日期格式化
 * @param time
 * @returns {*}
 */
function formatDate(time) {
    if (time == '' || time == null || time == undefined) return '';
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "年" + month + "月" + date + "日 " + hour + ":" + minute + ":" + second;
}
function formatDateSimple(time) {
    if (time == '' || time == null || time == undefined) return '';
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    /*return year + "年" + month + "月" + date + "日 " + hour + ":" + minute + ":" + second;*/
    return year + "年" + month + "月" + date + "日 " ;
}
var Request = {
    QueryString : function(val) {
        var uri = window.location.search;
        var re = new RegExp("" +val+ "=([^&?]*)", "ig");
        return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):"");
    }
}

function dataQueryParams(params) {
    return {
        _size: params.limit,  //页面大小
        _index: params.offset //页码
    }
}

function showPoint(num){
    var result =0;
    result = num/100;
    return result+".00";
}

function propertyKey(num){
    if(num==0){
        return "色号";
    }
    if(num==1){
        return "容量";
    }
    if(num==2){
        return "数量";
    }
}

function propertyValue(num){
    if(num==0){
        return "红色";
    }
    if(num==1){
        return "黄色";
    }
    if(num==2){
        return "蓝色";
    }
}

function showPropertyKey(key){
    if(key==0){
        return "色号";
    }else if(key==1){
        return "容量";
    }else if(key==2){
        return "数量";
    }else if(key==3){
        return "重量";
    }
}
function showOrderState(value){
    if(value==0){
        return "待付款";
    }
    if(value==1){
        return "待发货";
    }
    if(value==2){
        return "待收货";
    }
    if(value==3){
        return "待评价";
    }
    if(value==4){
        return "已完成";
    }
}