package com.sy.gatewayzuul.zuulfilters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sy.gatewayzuul.support.BaseResponse;
import com.sy.gatewayzuul.utils.JwtTokenProvider;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;


import java.util.List;


/**
 * @author wangxiao
 * @description: //TODO
 * @date 2020/3/26
 */
public abstract class BaseApiFilter extends ZuulFilter {


    protected  Logger log = LoggerFactory.getLogger("zuul");


    private ObjectMapper objectMapper = new ObjectMapper();
    private PathMatcher pathMatcher = new AntPathMatcher();

    private List<String> patternList;

    protected JwtTokenProvider jwtTokenProvider;


    public BaseApiFilter(List<String> patternList, JwtTokenProvider jwtTokenProvider) {
        this.patternList = patternList;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        String uri = getUri(currentContext);
        return isPatternList(uri);
    }

    @Override
    public Object run() throws ZuulException {
        try {
            return doFilter();
        }catch (Exception e){
            serverError(e);
            return null;
        }

    }

    /**
     *  拦截器具体实现功能
     * @return
     */
    protected abstract Object doFilter();


    /**
     * 判断是否需要鉴权
     * @param uri
     * @return
     */
    private boolean isPatternList (String uri) {
        for (String patternUrl : patternList){
            if (pathMatcher.match(patternUrl,uri)) {
                return true;
            }
        }
        return false;
    }

    private String getUri(RequestContext context) {
        return context.getRequest().getRequestURI();
    }


    protected void serverError(Exception e){
        log.info("gateWay-zuul is error ,the message is :{}",e.getMessage());
        RequestContext context = RequestContext.getCurrentContext();
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(HttpStatus.SC_OK);
        context.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
        String json = "{\"status:\"fail}";
        try  {
            objectMapper.writeValueAsString(BaseResponse.build().status("fail").message("网关解析系统异常"));
        }catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        context.setResponseBody(json);
    }





}
