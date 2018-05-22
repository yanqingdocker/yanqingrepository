package cn.com.caogen.controller;

import cn.com.caogen.util.JedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@Controller
public class PageController {
    /**
     * 访问index界面
     *
     * @returnmy/my_account
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 访问注册页面
     *
     * @return
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 访问登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest httpServletRequest) {
        if(JedisUtil.getUser(httpServletRequest) !=null){
            return "index";
        }
        return "login";
    }

    /**
     * 访问忘记密码界面
     *
     * @return
     */
    @RequestMapping("/find_psw")
    public String find_psw() {
        return "find_psw";
    }

    /**
     * 账户充值
     *
     * @return
     */
    @RequestMapping("/main/recharge_form")
    public String recharge_form() {
        return "main/recharge_form";
    }
    /**
     * 充值
     *
     * @return
     */
    @RequestMapping("/main/recharge")
    public String mainrecharge() {
        return "main/recharge";
    }
    /**
     * 我的资料
     *
     * @return
     */
    @RequestMapping("/my/my_account")
    public String myaccount() {
        return "my/my_account";
    }

    /**
     * 账户认证/基本资料
     *
     * @return
     */
    @RequestMapping("/my/authentication")
    public String authentication() {
        return "my/authentication";
    }
    /**
     * 我的余额
     *
     * @return
     */
    @RequestMapping("/my/my_money")
    public String my_money() {
        return "my/my_money";
    }
    /**
     * 登录密码
     *
     * @return
     */
    @RequestMapping("/my/login_psw")
    public String login_psw() {
        return "my/login_psw";
    }
    /**
     * 资金密码
     *
     * @return
     */
    @RequestMapping("/my/pay_psw")
    public String pay_psw() {
        return "my/pay_psw";
    }
    /**
     * 银行卡绑定
     *
     * @return
     */
    @RequestMapping("/my/bank_card")
    public String bank_card() {
        return "my/bank_card";
    }
    /**
     * 支付宝微信/绑定
     *
     * @return
     */
    @RequestMapping("/my/bind_account")
    public String bind_account() {
        return "my/bind_account";
    }
    /**
     * 更多--生活缴费
     *
     * @return
     */
    @RequestMapping("/more/life_payment")
    public String life_payment() {
        return "more/life_payment";
    }
    /**
     * 生活缴费--话费充值
     *
     * @return
     */
    @RequestMapping("/more/phone_charge")
    public String phone_charge() {
        return "more/phone_charge";
    }
    /**
     * 生活缴费--水费代缴
     *
     * @return
     */
    @RequestMapping("/more/water_charge")
    public String water_charge() {
        return "more/water_charge";
    }
    /**
     * 生活缴费--电费代缴
     *
     * @return
     */
    @RequestMapping("/more/ele_charge")
    public String ele_charge() {
        return "more/ele_charge";
    }
    /**
     * 我的消息--写信
     *
     * @return
     */
    @RequestMapping("/my/message")
    public String message() {
        return "my/message";
    }
    /**
     * 我的消息--收件箱
     *
     * @return
     */
    @RequestMapping("/my/inbox")
    public String inbox() {
        return "my/inbox";
    }
    /**
     * 我的消息--发件箱
     *
     * @return
     */
    @RequestMapping("/my/outbox")
    public String outbox() {
        return "my/outbox";
    }

    /**
     * 货币兑换
     *
     * @return
     */
    @RequestMapping("/currency_exchange/exchange")
    public String exchange() {
        return "currency_exchange/exchange";
    }
    /**
     * 交易明细列表
     * @return
     */
    @RequestMapping("/currency_exchange/exchange_list")
    public String exchange_list() {
        return "currency_exchange/exchange_list"; }
    /**
     * 汇率列表
     * @return
     */
    @RequestMapping("/currency_exchange/exchange_rate_list")
    public String exchange_rate_list() {
        return "currency_exchange/exchange_rate_list"; }
    /**
     * 转账
     * @return
     */
    @RequestMapping("/main/transfer_accounts")
    public String transfer_accounts() {
        return "main/transfer_accounts"; }
    /**
     * 提现
     * @return
     */
    @RequestMapping("/main/trans_cash")
    public String trans_cash() {
        return "main/trans_cash"; }
    /**
     * 提现---从银行提现
     * @return
     */
    @RequestMapping("/main/pos_Bank")
    public String pos_Bank() {
        return "main/pos_Bank"; }
    /**
     * 提现---从微信提现
     * @return
     */
    @RequestMapping("/main/pos_WeChat")
    public String pos_WeChat() {
        return "main/pos_WeChat"; }

    /**
     * 提现---从支付宝提现
     * @return
     */
    @RequestMapping("/main/pos_Alipay")
    public String pos_Alipay() {
        return "main/pos_Alipay"; }
    /**
     * 提现---现金提现
     * @return
     */
    @RequestMapping("/main/pos_cash")
    public String pos_cash() {
        return "main/pos_cash"; }
    /**
     * 提现---比特币提现
     * @return
     */
    @RequestMapping("/main/pos_bitcoin")
    public String pos_bitcoin() {
        return "main/pos_bitcoin"; }

    /**
     * 无卡取现
     * @return
     */
    @RequestMapping("/nonCardCash/cash_in")
    public String nonCardCash() {
        return "nonCardCash/cash_in"; }

    /**
     * 智慧理财
     * @return
     */
    @RequestMapping("/more/financial")
    public String financial() {
        return "more/financial"; }

    /**
     * 交易记录
     * @return
     */
    @RequestMapping("/more/trade_list")
    public String trade_list() {
        return "more/trade_list"; }
    /**
     * 历史交易记录
     * @return
     */
    @RequestMapping("/more/trade_history")
    public String trade_history() {
        return "more/trade_history"; }

    /**
     * 活动记录
     * @return
     */
    @RequestMapping("/more/active_list")
    public String active_list() {
        return "more/active_list"; }

}
