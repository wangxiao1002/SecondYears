package com.sy.gatewayzuul.zuulfilters;


import com.sy.gatewayzuul.utils.JwtTokenProvider;

import java.util.List;

/**
 * @author wangxiao
 * @description: //TODO
 * @date 2020/3/30
 */
public class AuthFilter extends BaseApiFilter {

    public AuthFilter(List<String> patternList, JwtTokenProvider jwtTokenProvider) {
        super(patternList, jwtTokenProvider);
    }

    @Override
    protected Object doFilter() {
        return null;
    }

    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
