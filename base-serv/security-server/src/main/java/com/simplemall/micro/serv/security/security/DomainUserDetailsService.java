package com.simplemall.micro.serv.security.security;

import com.simplemall.micro.serv.security.domain.SysUser;
import com.simplemall.micro.serv.security.repository.SysRoleRepository;
import com.simplemall.micro.serv.security.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

/**
 * Created by fanyoucai on 2018/5/26.
 */
//@Service("userDetailsService")
@Slf4j
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowcaseUsername = username.toLowerCase();
        Optional<SysUser> realUser = sysUserRepository.findOneWithRolesByUsername(lowcaseUsername);
        return realUser.map(user -> {
            Set<GrantedAuthority> grantedAuthorities = user.getAuthorities();
            return new User(user.getUsername(),user.getPassword(),grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("用户" + lowcaseUsername + "不存在!"));
    }
}
