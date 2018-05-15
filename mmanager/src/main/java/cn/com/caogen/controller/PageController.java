package cn.com.caogen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@Controller
public class PageController {
    /**
     * 访问index界面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 主页框架
     *
     * @return
     */
    @RequestMapping("/mainframe")
    public String mainframe() {
        return "mainframe";
    }

    /**
     * 访问登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 会员管理--会员列表
     *
     * @return
     */
    @RequestMapping("/member/member_list")
    public String member_list() {
        return "member/member_list";
    }
    /**
     *会员管理--账户管控
     *
     * @return
     */
    @RequestMapping("/member/account_list")
    public String account_list() {
        return "member/account_list";
    }
    /**
     *会员管理--会员支付宝
     *
     * @return
     */
    @RequestMapping("/member/alipay_list")
    public String alipay_list() {
        return "member/alipay_list";
    }
    /**
     *会员管理--银行卡绑定
     *
     * @return
     */
    @RequestMapping("/member/bankcard_list")
    public String bankcard_list() {
        return "member/bankcard_list";
    }

    /**
     *会员管理--账户交易记录
     *
     * @return
     */
    @RequestMapping("/member/opera_log")
    public String opera_log() {
        return "member/opera_log";
    }
    /**
     *消息通知--会员认证审批
     *
     * @return
     */
    @RequestMapping("/msgNotify/member_certificate")
    public String member_certificate() {
        return "msgNotify/member_certificate";
    }
    /**
     *消息通知--转账申请通知
     *
     * @return
     */
    @RequestMapping("/msgNotify/transfer_apply")
    public String transfer_apply() {
        return "msgNotify/transfer_apply";
    }
    /**
     *消息通知--提现申请通知
     *
     * @return
     */
    @RequestMapping("/msgNotify/cash_apply")
    public String cash_apply() {
        return "msgNotify/cash_apply";
    }
    /**
     *消息通知--充值通知
     *
     * @return
     */
    @RequestMapping("/msgNotify/recharge_record")
    public String recharge_record() {
        return "msgNotify/recharge_record";
    }

    /**
     *汇率查询
     *
     * @return
     */
    @RequestMapping("/exchangeRate/rate")
    public String rate() {
        return "exchangeRate/rate";
    }
    /**
     *平台资金库
     *
     * @return
     */
    @RequestMapping("/capitalbank/capital_bank")
    public String capital_bank() {
        return "capitalbank/capital_bank";
    }
    /**
     *现金管理--现金存入
     *
     * @return
     */
    @RequestMapping("/cashmanage/cash_in")
    public String cash_in() {
        return "cashmanage/cash_in";
    }
    /**
     *现金管理--现金转出
     *
     * @return
     */
    @RequestMapping("/cashmanage/cash_out")
    public String cash_out() {
        return "cashmanage/cash_out";
    }
    /**
     *后台管理员--管理员角色
     *
     * @return
     */
    @RequestMapping("/manage/manage_role")
    public String manage_role() {
        return "manage/manage_role";
    }
    /**
     *后台管理员--管理员列表
     *
     * @return
     */
    @RequestMapping("/manage/manage_list")
    public String manage_list() {
        return "manage/manage_list";
    }
    /**
     *后台管理员--修改密码
     *
     * @return
     */
    @RequestMapping("/manage/changepsw")
    public String changepsw() {
        return "manage/changepsw";
    }
    /**
     *后台管理员--修改信息
     *
     * @return
     */
    @RequestMapping("/manage/changeinfo")
    public String changeinfo() {
        return "manage/changeinfo";
    }
    /**
     *后台管理员--管理员权限
     *
     * @return
     */
    @RequestMapping("/manage/manage_power")
    public String manage_power() {
        return "manage/manage_power";
    }
    /**
     *日志管理
     *
     * @return
     */
    @RequestMapping("/oplog/log")
    public String log() {
        return "oplog/log";
    }

    /**
     *收入分析--月分析
     *
     * @return
     */
    @RequestMapping("/incomeAnalysis/month")
    public String month() {
        return "incomeAnalysis/month";
    }

    /**
     *收入分析--周分析
     *
     * @return
     */
    @RequestMapping("/incomeAnalysis/week")
    public String week() {
        return "incomeAnalysis/week";
    }
    /**
     *收入分析--季度分析
     *
     * @return
     */
    @RequestMapping("/incomeAnalysis/quarter")
    public String quarter() {
        return "incomeAnalysis/quarter";
    }

    /**
     *支出分析--月分析
     *
     * @return
     */
    @RequestMapping("/spentAnalysis/month")
    public String spentmonth() {
        return "spentAnalysis/month";
    }

    /**
     *支出分析--周分析
     *
     * @return
     */
    @RequestMapping("/spentAnalysis/week")
    public String spentweek() {
        return "spentAnalysis/week";
    }

    /**
     *支出分析--季度分析
     *
     * @return
     */
    @RequestMapping("/spentAnalysis/quarter")
    public String spentquarter() {
        return "spentAnalysis/quarter";
    }
}
