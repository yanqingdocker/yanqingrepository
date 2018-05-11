package cn.com.caogen.controller;

import cn.com.caogen.externIsystem.service.RateService;
import cn.com.caogen.util.ConstantUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * author:huyanqing
 * Date:2018/4/23
 */

@Component
@Order(value = 1)
public class Starup implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(Starup.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 系统启动时首先调用汇率接口存入redis
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        String result = RateService.getRequest2();

      /* if(stringRedisTemplate.opsForValue().get(ConstantUtil.ONE)==null){
            stringRedisTemplate.opsForValue().set(ConstantUtil.ONE, result);
        }
        if(stringRedisTemplate.opsForValue().get(ConstantUtil.TWO)==null){
            stringRedisTemplate.opsForValue().set(ConstantUtil.TWO, result);
        }
        if(stringRedisTemplate.opsForValue().get(ConstantUtil.THREE)==null){
            stringRedisTemplate.opsForValue().set(ConstantUtil.THREE, result);
        }
        if(stringRedisTemplate.opsForValue().get(ConstantUtil.FOURE)==null){
            stringRedisTemplate.opsForValue().set(ConstantUtil.FOURE, result);
        }
        if(stringRedisTemplate.opsForValue().get(ConstantUtil.FIVE)==null){
            stringRedisTemplate.opsForValue().set(ConstantUtil.FIVE, result);
        }
        if(stringRedisTemplate.opsForValue().get(ConstantUtil.SIX)==null){
            stringRedisTemplate.opsForValue().set(ConstantUtil.SIX, result);
        }
        if(stringRedisTemplate.opsForValue().get(ConstantUtil.SENVEN)==null){
            stringRedisTemplate.opsForValue().set(ConstantUtil.SENVEN, result);
        }*/

    }


    public static boolean GenerateImage(String imgStr){
        ////图像数据为空
        if (imgStr == null){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "G:\\svn1\\new.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static void main(String args[]){
        String str="iVBORw0KGgoAAAANSUhEUgAAAIwAAACMCAIAAAAhotZpAAADSklEQVR42u3aS5LjMAwEUd//0jPr2UyIqCxIspPL9oc0n6IBAvz8cTx+fNwCkRwiieQQySGSSI5HIH2ycWnukzcffWG4sN7ss4WJJJJIO0izhf5n6bMffPTSbD1X3nz0cepJFUkkkZaRwoVeeSn8Zio0Hs3V2zGRRBLpXUhH0SWMCniijMdjkUQS6YuRZnk2FdKOvvnKp45eEkkkkV6KhKezVBFhxo/XF76hLCSSSL+NFHZHfvAv72v6iSTSbyMttJnDgmb4nvBTqzsmkkgiNZHCCNSrAizk6+GTgefrIokk0gORsGWNHoiwDNqrJoSRTCSRRKoiURvXK0/g6lTmTa1ZJJFE2kGiWtp4s4f61z/bSupiE1wFF0kkkYhWRdiBDlvIm+WA3rWhehVcJJFEQisOVIsobD6F6f7sCmP4gO4VWEUSSSQiJuH/zXs72NvccNLWbSGRRBKpc5ilmitUwWJWusWrutQhQSSRRLodidoLqhDJZ7p3XBRopeAiiSTSVsUBT3Bn1xzxnzMLRXtXukQSSaROTFro34RndapPhkOezSWSSCLtIlENmM3bQtR2984PIokk0u1I925K2P7pdbMWKtEiiSTSMtLC4T/MhqmrmSHJLFzBKbhIIom01T5fqKLOAPDn6Z4pRBJJpCZSeGGRyrPxjk4v3oSFGJFEEuk5SKvLgmq4VE5PHQCGZwORRBKpidQrB8wS7rDSGs5FRcRWgVUkkURCkfBcfCHezLpHlB9W+RVJJJF2kaieE367kQoqYbAs1mdFEkmkXaTw/hCVyveep4WKww0puEgiiXS5wErVH6leEZ7phpFjtlFnKxRJJJFuQsJz3/AmJV6o7dUXWk0/kUQSqYxEFQjC2Tf78b0hkkgiPRmJ6lvf8nE83RdJJJFeioQVB7PeDB7Awn5SGAhb/SSRRBKJiEnhVuJn/l7XPNzc4lJFEkmkFSQ8AlGlUjwmUUcLPraJJJJIu0hU+2ez1dSboldfEEkkkd6ONGsIUaERr0HgwUkkkUT6GqQwMcWPBHidF29ZiSSSSHchhcFgIXXG02K8v/XQspBIIomE9pPCIiz/y+n2GBVHRRJJpGUkx3OGSCI5RBLJIZJDJJEcIjn+HX8BoTW7A5ikfjQAAAAASUVORK5CYII=";
        GenerateImage(str);
    }
}
