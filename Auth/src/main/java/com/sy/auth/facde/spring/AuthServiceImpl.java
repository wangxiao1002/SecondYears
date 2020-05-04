package com.sy.auth.facde.spring;

import com.sy.auth.facde.service.AuthService;
import com.sy.auth.mapper.AuthMapper;
import com.sy.basis.domain.AuthorityDO;
import com.sy.basis.domain.LoginDO;
import com.sy.basis.domain.MenuDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 * @author wangxiao
 * @since
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthMapper authMapper;

    @Override
    public LoginDO queryUserByInfo(LoginDO param) {
        return authMapper.selectUser(param);
    }

    @Override
    public List<MenuDTO> queryMenus(String userCode) {
        return authMapper.selectMenusByUser(userCode);
    }

    @Override
    public List<AuthorityDO> queryAuthorities(String userCode) {
        return authMapper.selectAuthorityByUser(userCode);
    }
}
