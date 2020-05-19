package com.sy.basis.util;

import com.sy.basis.common.BaseResult;
import com.sy.basis.common.ResultStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author wangxiao
 * @since
 */
public final class ResultUtil {

    private ResultUtil() {
    }


    private final static String EMPTY_MESSAGE = "数据内容为空";

    private final static List EMPTY_LIST =  new ArrayList(0);

    public static BaseResult<String> success(String message) {
        return new BaseResult<>(ResultStatus.SUCCESS, message, null);
    }

    public static <T> BaseResult<T> success(T result) {
        return new BaseResult<>(ResultStatus.SUCCESS, null, result);
    }

    public static <T> BaseResult<T> success(String message, T result) {
        return new BaseResult<>(ResultStatus.SUCCESS, message, result);
    }

    public static BaseResult<? extends Collection> empty() {
        return new BaseResult<>(ResultStatus.SUCCESS, EMPTY_MESSAGE, EMPTY_LIST);
    }

    public static BaseResult<String> isnull(String message) {
        return new BaseResult<>(ResultStatus.SUCCESS, message, null);
    }

    public static BaseResult<String> fail(String message) {
        return new BaseResult<>(ResultStatus.FAIL, message, null);
    }

    public static <T> BaseResult<T> fail(String message, T result) {
        return new BaseResult<>(ResultStatus.FAIL, message, result);
    }

    public static BaseResult fail(Throwable e) {
        return fail(e.getMessage());
    }
}
