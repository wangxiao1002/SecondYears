package com.sy.auth.facde.spring;

import com.sy.auth.facde.service.LogService;
import com.sy.basis.log.LogEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author wangxiao
 * @since 1.1
 */
@Service
public class LogServiceImpl implements LogService {

    @Override
    public int saveLogs(List<LogEntity> logEntities) {
        return 0;
    }
}
