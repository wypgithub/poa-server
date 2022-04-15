package com.poa.server.aspect;


import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.exception.PoaException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Component
@Aspect
public class RoleAccessAspect {


    @Pointcut("@annotation(com.poa.server.annotation.AccessAuthorize)")
    public void roleAccess() {
    }



    /**
     * Around
     *
     * @param joinPoint join point for advice
     */
    @Around("roleAccess()")
    public Object roleAccessAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // get role name
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String userRole = getUserRole(request);

        // check role
        if (StringUtils.isNotBlank(userRole)) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            AccessAuthorize auth = method.getAnnotation(AccessAuthorize.class);

            if (auth != null) {
                String value = auth.value();
                String[] authorities = value.split(",");
                for (String authority : authorities) {
                    if (authority.equalsIgnoreCase(userRole)) {
                        // action
                        return joinPoint.proceed();
                    }
                }
            }
        }

        throw new PoaException(HttpStatus.UNAUTHORIZED.value(), "Insufficient permissions!");
    }

    public String getUsername(HttpServletRequest request) {
        try {
            return request.getHeader("User-ID");
        } catch (Exception e) {
            return "";
        }
    }

    public String getUserRole(HttpServletRequest request) {
        try {
            return request.getHeader("User-Role");
        } catch (Exception e) {
            return "";
        }
    }
}
