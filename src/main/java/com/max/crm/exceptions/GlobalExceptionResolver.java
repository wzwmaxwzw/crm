package com.max.crm.exceptions;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.max.crm.base.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex) {

        if(ex instanceof NoLoginException){
            ModelAndView modelAndView = new ModelAndView("redirect:/index");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/error");
        modelAndView.addObject("code",400);
        modelAndView.addObject("msg","系统异常，请稍后重试...");

        if(handle instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handle;
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if(responseBody==null){

                if(ex instanceof ParamsException){
                    ParamsException p= (ParamsException) ex;
                    modelAndView.addObject("code",p.getCode());
                    modelAndView.addObject("msg",p.getMsg());
                }else if(ex instanceof NoPermissionException){
                    NoPermissionException a = (NoPermissionException) ex;
                    modelAndView.addObject("code",a.getCode());
                    modelAndView.addObject("msg",a.getMsg());
                }
                return modelAndView;

            }else {

                ResultInfo resultInfo=new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("系统异常，请重试！");
                if(ex instanceof ParamsException){
                    ParamsException p = (ParamsException) ex;
                    resultInfo.setCode(p.getCode());
                    resultInfo.setMsg(p.getMsg());
                }else if(ex instanceof NoPermissionException){
                    NoPermissionException a = (NoPermissionException) ex;
                    resultInfo.setCode(a.getCode());
                    resultInfo.setMsg(a.getMsg());
                }
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out=null;
                try{
                    out=response.getWriter();
                    out.write(JSON.toJSONString(resultInfo));
                    out.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(out!=null){
                        out.close();
                    }
                }
                return null;
            }
        }
        return modelAndView;
    }
}
