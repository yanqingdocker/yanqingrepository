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





