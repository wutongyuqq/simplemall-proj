package com.simplemall.micro.serv.security.repository;


import com.simplemall.micro.serv.security.domain.SysUser;
import com.simplemall.micro.serv.security.repository.support.WiselyRepository;

import java.util.Optional;

/**
 * Created by fanyoucai on 2018/5/26.
 */
public interface SysUserRepository extends WiselyRepository<SysUser,Long> {
    //通过jpa实现
    Optional<SysUser> findOneWithRolesByUsername(String username);
}
