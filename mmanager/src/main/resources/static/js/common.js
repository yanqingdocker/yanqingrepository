//字符串json转数组json
function strToJson(str){
    return JSON.parse(str);
}

// 此处调用的就是我们写好的引入的方法，会将带有name的input，全部转为JSON提交表单
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


// 获取地址栏参数
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}


function currency_type(types) {
    switch(types)
    {
        case "CNY":
            return "人民币"
            break;
        case "USD":
            return "美元"
            break;
        default:
            break;
    }
}

//接口返回异常，超时登录
function handleAjaxError() {
    sweetAlert({
            title: "",
            text: "登录超时，点击确定返回登录页面！",
            type: "warning",
            showCancelButton: false,
            cancelButtonText: "取消",
            confirmButtonText: "确定",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm) {
                window.location.href = "/login";
            }
        });

}
//对多位小数进行四舍五入
//num是要处理的数字  v为要保留的小数位数
function decimal(num,v){
    var vv = Math.pow(10,v);
    return Math.round(num*vv)/vv;
}

// 获取当前年月日
function showdate(){
    var mydate = new Date();
    var str = "" + mydate.getFullYear() + "-";
    str += (mydate.getMonth()+1) + "-";
    str += mydate.getDate() ;
    return str;
}

// 实时更新未处理任务
setInterval("task_nodeal()",10000);

// 显示未处理任务条数
function task_nodeal() {
    $.ajax({
        url: "/task/queryUndo",
        type: "get",
        data: null,
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            if(data.length>0){
                $("#nodeal_num").html(data.length);
                $("#msg_status").addClass("danger");
                $("#tast_num").html(data.length);
                $("#indexAlert").show();
            }
            else if (data.code=="403") {
                $("#msg_status").hide();
                $("#indexAlert").hide();
            }
            else {
                $("#msg_status").removeClass("danger");
                $("#indexAlert").hide();
            }

        },
        error: function () {
            window.location.href = "/login";
        },
    });

}
