package org.homo.core.executor;

import org.homo.authority.model.User;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wujianchuan 2018/12/29
 */
public class HomoRequest {

    private HttpServletRequest httpServletRequest;

    private User user;

    private ApplicationContext applicationContext;

    private HomoRequest() {
    }

    public String getParameter(String name) {
        return this.httpServletRequest.getParameter(name);
    }

    public Map<String, String[]> getParameterMap() {
        return this.httpServletRequest.getParameterMap();
    }

    public User getUser() {
        return user;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    static HomoRequest newInstance(HttpServletRequest request, User user, ApplicationContext applicationContext) {
        HomoRequest homoRequest = new HomoRequest();
        homoRequest.setHttpServletRequest(request);
        homoRequest.setUser(user);
        homoRequest.setApplicationContext(applicationContext);
        return homoRequest;
    }

    private HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    private void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    private void setUser(User user) {
        this.user = user;
    }

    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
