package com.sy.auth.facde.spring;

import com.sy.auth.facde.service.AuthService;
import com.sy.auth.mapper.AuthMapper;
import com.sy.auth.mapper.AuthorityMapper;
import com.sy.auth.mapper.MenuMapper;
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

    @Resource
    private AuthorityMapper authorityMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public LoginDO queryUserByInfo(LoginDO param) {
        return authMapper.selectUser(param);
    }

    @Override
    public LoginDO queryUserByCode(String userCode) {
        return authMapper.selectUserByCode(userCode);
    }

    @Override
    public List<MenuDTO> queryMenus(String userCode) {
        return authMapper.selectMenusByUser(userCode);
    }

    @Override
    public List<AuthorityDO> queryAuthorities(String userCode) {
        return authMapper.selectAuthorityByUser(userCode);
    }

    @Override
    public int addUserAuthority(String userCode, List<AuthorityDO> authorityDOList) {
        String [] authorityCodes = authorityDOList.stream().map(AuthorityDO::getCode).toArray(String [] :: new);
        return authorityMapper.insertUserAuthority(userCode,authorityCodes);
    }

    @Override
    public int addUserMenu(String userCode, List<MenuDTO> menuDTOList) {
        String [] authorityCodes = menuDTOList.stream().map(MenuDTO::getCode).toArray(String [] :: new);
        return menuMapper.insertUserMenu(userCode,authorityCodes);
    }
}
