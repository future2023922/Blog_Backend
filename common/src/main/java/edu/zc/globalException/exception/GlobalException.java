package edu.zc.globalException.exception;

import edu.zc.globalException.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: keeplooking
 * @since: 2022/01/01 - 14:49
 */

@Slf4j//异常写入日志文件中
@ControllerAdvice
public class GlobalException {
    //出现任何异常都能出现这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Results error(Exception e){
        e.printStackTrace();
        return  Results.fail().msg("全局异常处理");
    }

    //特殊异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Results error(ArithmeticException e){
        e.printStackTrace();
        return Results.fail().msg("特殊异常处理");
    }

    //自定义异常
    @ExceptionHandler(DiyException.class)
    @ResponseBody
    public Results error(DiyException e){
//        log.error(e.getMsg());
        e.printStackTrace();
        return Results.fail().code(e.getCode()).msg(e.getMsg());
    }

}
