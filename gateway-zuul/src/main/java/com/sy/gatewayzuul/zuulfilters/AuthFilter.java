package com.sy.gatewayzuul.zuulfilters;


import com.netflix.zuul.context.RequestContext;
import com.sy.basis.util.ResultUtil;
import com.sy.gatewayzuul.service.facade.UserFeignService;
import com.sy.gatewayzuul.utils.JwtTokenProvider;
import org.apache.commons.lang.StringUtils;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author wangxiao
 * @description: //TODO
 * @date 2020/3/30
 */
public class AuthFilter extends BaseApiFilter {


    private final String spliteStr = "*";
    private List<String> whiteList;

    private UserFeignService userFeignService;

    public AuthFilter(List<String> patternList, JwtTokenProvider jwtTokenProvider) {
        super(patternList, jwtTokenProvider);
    }

    public AuthFilter(List<String> whiteList, UserFeignService userFeignService,List<String> patternList, JwtTokenProvider jwtTokenProvider){
        super(patternList, jwtTokenProvider);
        this.whiteList = whiteList;
        this.userFeignService = userFeignService;

    }

    @Override
    protected Object doFilter() {
        RequestContext context  = RequestContext.getCurrentContext();
        String uri = getUri(context);

        if (whiteList.stream().anyMatch(s -> s.equals(uri))){
            return null;
        }
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();

        String headToken = request.getHeader("sy-auth-token");
        if (StringUtils.isEmpty(headToken) && jwtTokenProvider.isTokenExpired(headToken)) {
            nopermission(request.getRequestURL().toString());
        }

        String userCode = jwtTokenProvider.getUserCode(headToken);
        if (!checkUser(userCode)){
            sendBaseResponse(ResultUtil.fail("您好！该用户不存在"));
        }

        if (!checkPermission(userCode,method+spliteStr+uri)) {
            nopermission(request.getRequestURL().toString());
        }
        return null;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }


    private boolean checkUser(String userCode) {
        return Optional.ofNullable(userFeignService.queryUserByUserCode(userCode)).isPresent();
    }

    private boolean checkPermission(String userCode,String uri) {
        List<String> permissions = userFeignService.queryUserPermissions(userCode);
        return permissions.stream().anyMatch(s -> s.equals(uri));
    }
}
