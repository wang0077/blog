package com.wang.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**拦截带有Controller注释的请求
 * @author wangsiyuan
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 标识这个方法可以做异常处理
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception{
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);

        //如果这个异常有状态码就放行到相应状态的HTML
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        //设置视图放讲没有设置状态码的异常返回到error的HTML
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURI());
        mv.addObject("Exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
