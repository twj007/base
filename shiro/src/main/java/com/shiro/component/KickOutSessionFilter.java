package com.shiro.component;

import com.shiro.model.ShiroUser;
import com.shiro.utils.KickOutException;
import net.minidev.json.JSONObject;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 * 由于集成了spring-session，通过shiroredismanager直接去获取spring-session的session，不然会获取不到session
 **/
public class KickOutSessionFilter extends AccessControlFilter {
    private int maxSession;
    private boolean kickBefore;
    private String kickoutUrl;
    private ShiroRedisManager sessionManager;
    private Map<String, Deque<Serializable>> cache;


    public void setSessionManager(ShiroRedisManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public KickOutSessionFilter() {
    }

    public KickOutSessionFilter(int maxSession, boolean kickBefore, String kickoutUrl, ShiroRedisManager sessionManager) {
        this.maxSession = maxSession;
        this.kickBefore = kickBefore;
        this.kickoutUrl = kickoutUrl;
        this.sessionManager = sessionManager;
        this.cache = new HashMap<>();
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }


    public void setKickBefore(boolean kickBefore) {
        this.kickBefore = kickBefore;
    }


    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        String username = request.getParameter("username");
        Serializable sessionId = "spring:session:sessions:"+session.getId();

        //TODO 同步控制
        Deque<Serializable> deque = cache.get(username);
        if(deque == null) {
            deque = new LinkedList<Serializable>();
            cache.put(username, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }
        Serializable kickoutSessionId = null;
        //如果队列里的sessionId数超出最大会话数，开始踢人
        if (deque.size() > maxSession) {

            if(kickBefore) { //如果踢出后者
                kickoutSessionId = deque.removeLast();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeFirst();
            }
            try {
                ShiroRedisManager.ShiroRedisCache cache = (ShiroRedisManager.ShiroRedisCache) sessionManager.getCache(kickoutSessionId.toString());
                if(cache != null) {
                    //设置会话的kickout属性表示踢出了
                    cache.put("kickout", true);
                }
            } catch (Exception e) {//ignore exception
                e.printStackTrace();
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (((ShiroRedisManager.ShiroRedisCache) sessionManager.getCache(sessionId.toString())).get("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
                e.printStackTrace();
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            Map<String, String> res = new HashMap<>();
            res.put("msg", "你已被顶出，请重新登陆");
            res.put("code", "500");
            String result = JSONObject.toJSONString(res);
            out.append(result);
            return false;
        }
        return true;
    }

}
