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
function Fobj(id) { return $(id).length };
function returnObj(obj, isJquery) {
    if (isJquery == undefined) { isJquery = true; }
    var _obj; if (obj == null || obj == undefined) return; if (typeof (obj) == 'object') { _obj = obj; } else if (obj.substring(0, 1) != '#' && obj.substring(0, 1) != '.') { _obj = (isJquery) ? '#' + obj : obj; } else { _obj = (isJquery) ? obj : obj.substring(1, len(obj)); }
    return _obj;
}; var Class = { create: function () { return function () { this.initialize.apply(this, arguments); } } };
function getBoxVal (objname, areaTag)
{ var str = "";
    var _areaTag = returnObj(areaTag);
    var _areaObj = (Fobj(_areaTag)) ? _areaTag + ' ' : '';
    $(_areaObj + "input:checkbox[name='" + objname + "'][checked]").each(function () { str += $(this).val() + ","; });
    if (str != "") {
        return str.substr(0, str.length - 1);
    } else { return "" }
};

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
    }
}






