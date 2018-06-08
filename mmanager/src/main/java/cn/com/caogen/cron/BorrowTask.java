package cn.com.caogen.cron;


import cn.com.caogen.entity.Borrow;
import cn.com.caogen.externIsystem.service.MessageService;
import cn.com.caogen.externIsystem.service.RateService;
import cn.com.caogen.service.BorrowServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DataMonitor;
import cn.com.caogen.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/6/8
 */
@Component
public class BorrowTask {

    private static Logger logger = LoggerFactory.getLogger(BorrowTask.class);
    @Autowired
    private BorrowServiceImpl borrowService;


    /**
     * //1 0 12 * * ?  每天中午12点查看借款期限
     *
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void checkBorrow(){


        logger.info("checkBorrow start:" + DateUtil.getTime());
        String todaydate=DateUtil.getDate().split("-")[2];
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("status",0);
        List<Borrow> borrowList=borrowService.queryAll(parmMap);

        String temp="";
        for(Borrow borrow:borrowList){
            String date=borrow.getCreatetime().split(" ")[0].split("-")[2];
            int num=Integer.parseInt(todaydate)-Integer.parseInt(date);
            if(borrow.getTerm()-num==1){
                temp=borrow.getSnumber()+"即将到期";
            }else if(borrow.getTerm()-num<1){
                temp=borrow.getSnumber()+"已经到期";
            }
            String msg="尊敬的客户您好，您的预支订单"+temp+",请您尽快前往南方国际钱庄的任何一个网点进行处理。【友情提示】";
            String telphone=borrow.getBorrowerphone();
            logger.info(telphone+"================");
            int c=MessageService.sendMessage(telphone,msg);
            logger.info("=================="+c);
        }
    }
}
