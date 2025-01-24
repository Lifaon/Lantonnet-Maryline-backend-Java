package com.safetynet.alerts.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger();

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
//        System.out.println("preHandle");
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model) {
//        System.out.println("postHandle");
//    }

    static public void logRequestResponse(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        String s_info = "" + status;
        s_info += ": " + request.getMethod();
        s_info += " " + request.getRequestURI();
        if (request.getQueryString() != null) {
            s_info += "?" + request.getQueryString();
        }

        if (status < 400)
            LOGGER.info(s_info);
        else
            LOGGER.error(s_info);
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object object, Exception exception) {

        int status = response.getStatus();
        if (exception != null) {
            status = 500;
            response.setStatus(status);
            LOGGER.error(exception);
        }

        logRequestResponse(request, response);

        if (LOGGER.isDebugEnabled() && object instanceof HandlerMethod) {
            final Class<?> controllerClass = ((HandlerMethod) object).getBeanType();
            final String methodName = ((HandlerMethod) object).getMethod().getName();
            LOGGER.debug("{} -> {}()", controllerClass.getName(), methodName);
        }

    }
}