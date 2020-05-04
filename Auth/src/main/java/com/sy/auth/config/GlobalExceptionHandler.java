package com.sy.auth.config;


import com.sy.basis.common.BaseResult;
import com.sy.basis.util.ResultUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
    public ResponseEntity<BaseResult<String>> validationErrorHandler(MethodArgumentNotValidException ex) {
        List<String> errorInformation = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResultUtil.fail(errorInformation.toString()));
    }

    /**
     * method param 校验异常
     * @param ex
     * @return ResponseEntity<JsonResultVO>
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<BaseResult<String>> handleResourceNotFoundException(ConstraintViolationException ex) {

        List<String> errorInformation = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResultUtil.fail(errorInformation.toString()));
    }

    /**
     * 统一大异常
     * @param e
     * @return ResponseEntity<JsonResultVO>
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<BaseResult<String>> handleResourceNotFoundException(RuntimeException e) {
        return ResponseEntity.ok(ResultUtil.fail(e.getMessage()));
    }
}
