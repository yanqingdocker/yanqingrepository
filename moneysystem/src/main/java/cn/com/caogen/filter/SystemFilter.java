package cn.com.caogen.filter;

import cn.com.caogen.controller.AppliyController;
import cn.com.caogen.entity.User;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.JedisUtil;
import cn.com.caogen.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@WebFilter(urlPatterns = "/*")
public class SystemFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SystemFilter.class);
    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();
    private  Pattern pattern1=Pattern.compile("index");
    private  Pattern pattern2=Pattern.compile("login");
    private  Pattern pattern3=Pattern.compile("register");
    private  Pattern pattern4=Pattern.compile("user/checkPhone");
    private  Pattern pattern5=Pattern.compile("user/register");
    private  Pattern pattern6=Pattern.compile("user/login");
    private  Pattern pattern7=Pattern.compile("user/findpsw");
    private  Pattern pattern8=Pattern.compile("user/resetpwd");
    private  Pattern pattern9=Pattern.compile(".*[(\\.js)||(\\.css)||(\\.png)||(\\.jpg)]");
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        patterns.add(pattern1);
        patterns.add(pattern2);
        patterns.add(pattern3);
        patterns.add(pattern4);
        patterns.add(pattern5);
        patterns.add(pattern6);
        patterns.add(pattern7);
        patterns.add(pattern8);
        patterns.add(pattern9);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest=(HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
            if (url.startsWith("/") && url.length() > 1) {
                url = url.substring(1);
            }
            if(isInclude(url)){
                chain.doFilter(request, response);
                return;
            }else{
                logger.info("sessionId=========="+httpServletRequest.getSession().getId());
                User user=JedisUtil.getUser(httpServletRequest);
                if(user!=null){
                    chain.doFilter(request,response);
                    return;
                }else{
                    /*Map<String,Object> sessionMap=JedisUtil.getSessionMap();
                    sessionMap.remove(httpServletRequest.getSession().getId());
                    logger.info("remove user");
                    JedisUtil.getJedis().set(ConstantUtil.SESSIONCOLLCTION.getBytes(),SerializeUtil.serialize(sessionMap));*/
                    httpServletResponse.sendRedirect("/login");
                }
            }

    }

    @Override
    public void destroy() {

    }

    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
