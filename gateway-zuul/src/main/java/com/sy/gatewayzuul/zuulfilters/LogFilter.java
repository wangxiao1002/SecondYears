package com.sy.gatewayzuul.zuulfilters;

import com.netflix.zuul.context.RequestContext;
import com.sy.gatewayzuul.utils.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author wangxiao
 * @description: //日志
 * @date 2020/4/4
 */
public class LogFilter extends BaseApiFilter {


    private Logger logger = LoggerFactory.getLogger(LogFilter.class);


    public LogFilter(List<String> patternList, JwtTokenProvider jwtTokenProvider) {
        super(patternList, jwtTokenProvider);
    }

    @Override
    protected Object doFilter() throws Exception {
        StringBuffer buffer = new StringBuffer();
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String remoteAddr = request.getRemoteAddr();
        String host = request.getRemoteHost();
        String url = request.getRequestURI();
        Map<String,String[]> params = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            buffer.append(entry.getKey()).append(":{").append(Arrays.toString(entry.getValue())).append("},");

        }
        logger.info("request is from {}:{} to {},params is {}",remoteAddr,host,url,buffer);
        return null;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}
