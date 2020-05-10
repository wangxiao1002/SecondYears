package com.sy.auth.facde.spring;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.auth.facde.service.LogService;
import com.sy.auth.mapper.LogMapper;
import com.sy.auth.support.LogDTO;
import com.sy.basis.log.LogEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志
 * @author wangxiao
 * @since 1.1
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;


    @Override
    public int saveLogs(List<LogEntity> logEntities) {
        return logMapper.saveLogs(logEntities);
    }

    @Override
    public PageInfo<LogEntity> queryLog(LogDTO logDTO) {
        PageHelper.startPage(logDTO.getPageNum(),logDTO.getPageSize());
        List<LogEntity> logEntities = logMapper.selectLogs(logDTO);
        return PageInfo.of(logEntities);
    }
}
