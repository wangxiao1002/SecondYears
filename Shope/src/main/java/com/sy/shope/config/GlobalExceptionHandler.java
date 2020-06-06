package com.sy.shope.config;



import com.sy.shope.support.JsonResult;
import com.sy.shope.support.OrderingException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangxiao
 * @description: //全局异常
 * @date 2019/10/18
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Bean 校验异常
     * @param ex
     * @return ResponseEntity<JsonResultVO>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonResult> validationErrorHandler(MethodArgumentNotValidException ex) {
        List<String> errorInformation = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.ok(JsonResult.fail(errorInformation.toString()));
    }



    /**
     * 统一大异常
     * @param e
     * @return ResponseEntity<JsonResultVO>
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<JsonResult<String>> handleResourceNotFoundException(RuntimeException e) {
        return ResponseEntity.ok(JsonResult.fail(e.getMessage()));
    }

    @ExceptionHandler(value = {OrderingException.class})
    public ResponseEntity<JsonResult<String>> handleOrderingException(OrderingException e) {
        return ResponseEntity.ok(JsonResult.fail(e.getCode(),e.getMessage()));
    }
}
