package cn.com.caogen.util;

import com.sun.org.apache.regexp.internal.RE;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * author:huyanqing
 * Date:2018/5/7
 */
public class ImageUtil {
    public static boolean GenerateImage(String filename,String imgStr){
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
            OutputStream out = new FileOutputStream("./src/main/resources/static/imgfile/"+filename);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static void delete(int userid){
        File file=new File("./src/main/resources/static/imgfile/"+userid+"login.jpg");
        if(file.exists()){
            file.delete();
        }
        file=new File("./src/main/resources/static/imgfile/"+userid+"allow.jpg");
        file.delete();
        if(file.exists()){
            file.delete();
        }

    }
}
