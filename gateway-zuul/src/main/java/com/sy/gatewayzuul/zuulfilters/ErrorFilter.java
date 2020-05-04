package com.sy.gatewayzuul.zuulfilters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import com.sy.basis.util.ResultUtil;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wangxiao
 * @description: //错误处理
 * @date 2020/4/4
 */
public class ErrorFilter extends SendErrorFilter {

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(HttpStatus.SC_OK);
        HttpServletResponse response = context.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        String json = "{\"status:\"fail\"}";
        try  {
            json = objectMapper.writeValueAsString(ResultUtil.fail("网关服务系统异常"));
        }catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        context.setResponseBody(json);
        return null;



    }


}
