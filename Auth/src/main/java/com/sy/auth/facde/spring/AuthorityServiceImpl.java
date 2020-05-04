package com.sy.auth.facde.spring;

import com.sy.auth.facde.service.AuthorityService;
import com.sy.basis.domain.AuthorityDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  权限
 * @author wangxiao
 * @since 1.1
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Override
    public AuthorityDO queryAuthorityById(String id) {
        return null;
    }

    @Override
    public List<AuthorityDO> queryAuthorityByOther(String other) {
        return null;
    }

    @Override
    public List<AuthorityDO> queryAuthorities() {
        return null;
    }

    @Override
    public int updateAuthorityDO(AuthorityDO authorityDO) {
        return 0;
    }

    @Override
    public int addAuthorityDO(AuthorityDO authorityDO) {
        return 0;
    }


}
