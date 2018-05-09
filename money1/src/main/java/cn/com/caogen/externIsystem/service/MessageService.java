package cn.com.caogen.externIsystem.service;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;


public class MessageService {
	
	private static String Url = "http://106.wx96.com/webservice/sms.php?method=Submit";

	public  static int checkPhone(String phone) {
		
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);
		client.getParams().setContentCharset("GBK");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");
		int mobile_code = (int)((Math.random()*9+1)*100000);

	    String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
		NameValuePair[] data = {
			    new NameValuePair("account", "C01409210"),
			    new NameValuePair("password", "b3add47d33a7368bfc546f51961a5173"),
			    new NameValuePair("mobile", phone),
			    new NameValuePair("content", content),
		};
		method.setRequestBody(data);
		try {
			client.executeMethod(method);
			String SubmitResult =method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			String code = root.elementText("code");
			 if("2".equals(code)){
				return mobile_code;
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
}