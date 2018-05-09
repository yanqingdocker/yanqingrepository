document.writeln(" <div class=\"navbar navbar-default navbar-fixed-top top-main \" style=\"background:rgba(0,0,0,0.0) ;\" >");
document.writeln("  <div class=\"container-fluid top-bar p0 car_bg f12 bl3\">");
document.writeln(" <div class=\"container row m p0\">");
document.writeln("  <div class=\"col-lg-6\"></div>");
document.writeln("  <div class=\"col-lg-6 tr\">");
document.writeln("  <div  class=\"log_bar_link r gray\" id=\"unlogin\"  >");
document.writeln("     <a class=\" pr10\" href=\"/login\">登录</a><span class=\"white\">|</span>");
document.writeln("      <a class=\" pl10\" href=\"/register\" >注册</a>");
document.writeln("      </div>");
document.writeln("      <div class=\"nav r user_info\" id=\"login\"  style=\"display:none;\">");
document.writeln(" <div class=\" dropdown \">");
document.writeln("   <a class=\"nav-link dropdown-toggle\" id=\"username\"><i class=\"icon_down\"></i></a>");
document.writeln("<div class=\"dropdown-menu \" style=\"display: none\" >");
document.writeln(" <a class=\"dropdown-item\" href=\"/my/my_account\">我的账户</a>");
document.writeln(" <a class=\"dropdown-item\" href=\"/my/my_money\">我的余额</a>");
document.writeln("<a class=\"dropdown-item\" href=\"/my/authentication\">我的资料</a>");
document.writeln("<a class=\"dropdown-item\" href=\"javascript:;\" onclick=\"logout()\">退出</a>");
document.writeln(" </div>");
document.writeln(" </div>");
document.writeln(" </div>");
document.writeln("  </div>");
document.writeln("</div>");
document.writeln(" </div>");
document.writeln(" <div class=\"navbar_block\">");
document.writeln("  <div class=\"container row p0 m \">");
document.writeln(" <div class=\"col-lg-2 \"><a href=\"/index\"><img class=\"logo\" src=\"/images/logo.png\" alt=\"\" ></a></div>");
document.writeln(" <div class=\"col-lg-10 mt20\">\n ");
document.writeln(" <!--nav--><div class=\"row\">");
document.writeln(" <div class=\"col-lg-12 nav \" >\n ");
document.writeln("   <ul class=\"nav-con navbar-nav\">\n");
document.writeln("  <li ><a href=\"/index\" id=\"nav_index\" >首页</a></li>\n");
document.writeln("  <li class=\" dropdown \">\n");
document.writeln(" <a class=\"nav-link dropdown-toggle\" id=\"nav_my\">我的<i class=\"icon_down\"></i></a>\n ");
document.writeln(" <div class=\"dropdown-menu \" style=\"display: none\" >\n");
document.writeln(" <a class=\"dropdown-item\" href=\"/my/my_account\">我的账户</a>\n");
document.writeln("<a class=\"dropdown-item\" href=\"/my/my_money\">我的余额</a>\n ");
document.writeln("<a class=\"dropdown-item\" href=\"/my/authentication\">我的资料</a>\n ");
document.writeln("<a class=\"dropdown-item\" href=\"/my/inbox\">我的消息</a>\n ");
document.writeln(" </div>\n ");
document.writeln("   </li>\n");
document.writeln("   <li ><a href=\"/main/recharge\" id=\"nav_recharge\">充值</a></li>\n");
document.writeln(" <li ><a href=\"/main/trans_cash\" id=\"nav_trans\">提现</a></li>\n ");
document.writeln(" <li ><a href=\"/main/transfer_accounts\"  id=\"nav_transfer\">转账</a></li>\n");
document.writeln("<li><a href=\"/currency_exchange/exchange\" id=\"nav_exchange\">货币兑换</a></li>\n ");
document.writeln("     <li ><a href=\"/nonCardCash/cash_in\" id=\"nav_nocard\">无卡取现</a></li>\n ");
document.writeln("    <li class=\" dropdown \">\n ");
document.writeln("  <a class=\"nav-link dropdown-toggle\" id=\"nav_more\">更多<i class=\"icon_down\"></i></a>\n");
document.writeln("  <div class=\"dropdown-menu \" style=\"display: none\" >\n ");
document.writeln(" <a class=\"dropdown-item\" href=\"/more/financial\">智慧理财</a>\n ");
document.writeln("  <a class=\"dropdown-item\" href=\"/more/life_payment\">生活缴费</a>\n");
document.writeln(" <a class=\"dropdown-item\" href=\"/more/trade_list\">交易记录</a>\n");
document.writeln("<a class=\"dropdown-item\" href=\"/more/active_list\">活动记录</a>\n ");
document.writeln(" </div>\n ");
document.writeln(" </li>\n ");
document.writeln(" </ul>\n");
document.writeln(" </div>");
document.writeln(" </div> <!--nav-->");
document.writeln("</div>\n ");
document.writeln(" </div>\n ");
document.writeln("  </div>\n");
document.writeln("</div>\n ");
document.writeln(" </div>\n");
document.writeln(" <input type='hidden' id='usertel'>\n");
document.writeln(" <input type='hidden' id='userid'>\n");
document.writeln(" <input type='hidden' id='isauth'>\n");
$(function(){
    $.ajax({
        url: "/user/getuser",
        type: "get",
        data: null,
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            $("#unlogin").hide();
            $("#login").show();
            if (data!= null && data != undefined && data != "") {
                var html = "";
                if (data.username != "") {
                    html += "" + data.username + "";
                }
                else{    html += "" + data.phone + "";}
                $("#username").html(html+"<i class=\"icon_down\"></i>");
                $("#usertel").val(data.phone);
                $("#userid").val(data.userid);
                $("#isauth").val(data.isauthentication);
            }
            if(data.isauthentication==1){
                $("#changetext").text("基本信息");
            }
            else{
                $("#changetext").text("会员认证");
            }
        },
        error: function (data) {
            $("#unlogin").show();
            $("#login").hide();
            window.location.href = "/login";
        },
    });
});
function logout() {
    $.ajax({
        url: "/user/logout",
        type: "post",
        data: null,
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            if (data.code=="success") {
                sweetAlert("", "退出成功！", "success");
                setTimeout(function(){ window.location.href = "/login";},1500);
            }
            else {
            }
        },
        error: function () {
            window.location.href = "/login";
        },
    });

}





