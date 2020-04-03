package com.sy.gatewayzuul.zuulfilters;

import com.netflix.zuul.context.RequestContext;
import com.sy.gatewayzuul.utils.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


/**
 * @author wangxiao
 * @description: // 请求头设置
 * @date 2020/4/3
 */
public class RequestFilter extends BaseApiFilter {

    public RequestFilter(List<String> patternList, JwtTokenProvider jwtTokenProvider) {
        super(patternList, jwtTokenProvider);
    }

    @Override
    protected Object doFilter() throws Exception {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request  = context.getRequest();
        String headToken = request.getHeader("sy-auth-token");
        headToken = jwtTokenProvider.refreshToken(headToken);
        request.setAttribute("sy-auth-token",headToken);
        String userCode = jwtTokenProvider.getUserCode(headToken);
        request.setAttribute("sy-auth-user",userCode);
        jwtTokenProvider.setCookie(context.getResponse(),headToken);
        return null;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
