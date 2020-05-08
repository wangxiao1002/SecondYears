package com.sy.auth.facde.spring;

import com.sy.auth.facde.service.AuthorityService;
import com.sy.auth.mapper.AuthorityMapper;
import com.sy.basis.domain.AuthorityDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  权限
 * @author wangxiao
 * @since 1.1
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public AuthorityDO queryAuthorityByCode(String code) {
        return authorityMapper.selectByCode(code);
    }

    @Override
    public List<AuthorityDO> queryAuthorityByOther(String other) {
        return authorityMapper.selectByNameOrCode(other);
    }

    @Override
    public List<AuthorityDO> queryAuthorities() {
        return authorityMapper.selectAll();
    }

    @Override
    public int updateAuthorityDO(AuthorityDO authorityDO) {
        return authorityMapper.updateAuthorityDO(authorityDO);
    }

    @Override
    public int addAuthorityDO(AuthorityDO authorityDO) {
        return authorityMapper.addAuthorityDO(authorityDO);
    }

    @Override
    public int delAuthorityDO(String code) {
        return authorityMapper.delAuthorityDO(code);
    }
}
