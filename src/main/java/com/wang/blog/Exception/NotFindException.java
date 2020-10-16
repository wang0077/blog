package com.wang.blog.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**为这个异常设置状态码
 * @author wangsiyuan
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFindException extends RuntimeException {
    public NotFindException() {
    }

    public NotFindException(String message) {
        super(message);
    }

    public NotFindException(String message, Throwable cause) {
        super(message, cause);
    }
}
