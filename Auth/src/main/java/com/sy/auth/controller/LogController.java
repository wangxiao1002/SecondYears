package com.sy.auth.controller;

import com.github.pagehelper.PageInfo;
import com.sy.auth.facde.service.LogService;
import com.sy.auth.support.LogDTO;
import com.sy.basis.common.BaseResult;
import com.sy.basis.log.LogEntity;
import com.sy.basis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
@RestController
@RequestMapping("/log")
public class LogController {

    private final List<LogEntity> data = new ArrayList<>(20);

    @Autowired
    private LogService logService;


    @PostMapping("/search")
    public BaseResult<PageInfo<LogEntity>> search (@RequestBody LogDTO logDTO) {
        return ResultUtil.success(logService.queryLog(logDTO));
    }

    @PostMapping
    public BaseResult<Boolean> addLog(@RequestBody LogEntity logEntity) {
        data.add(logEntity);
        saveLogs();
        return ResultUtil.success("保存成功",true);
    }

    private void saveLogs () {
        int mid = 10;
        if (!data.isEmpty() && data.size() > mid) {
            synchronized (data) {
                logService.saveLogs(data);
                data.clear();
            }
        }
    }





}
