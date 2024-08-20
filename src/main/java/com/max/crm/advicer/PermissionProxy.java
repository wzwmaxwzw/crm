package com.max.crm.advicer;

import com.max.crm.annotation.RequirePermission;
import com.max.crm.exceptions.NoPermissionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {

    @Resource
    private HttpSession session;

    @Around(value = "@annotation(com.max.crm.annotation.RequirePermission)")
    public Object around(ProceedingJoinPoint p) throws Throwable {
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if(permissions==null || permissions.size()==0){
            throw new NoPermissionException();
        }
        Object result=null;
        MethodSignature methodSignature = (MethodSignature) p.getSignature();
        RequirePermission requirePermission = methodSignature.getMethod().getDeclaredAnnotation(RequirePermission.class);
        if(!(permissions.contains(requirePermission.code()))){
            throw new NoPermissionException();
        }
        result=p.proceed();

        return result;
    }

}
