package com.simplemall.micro.serv.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fanyoucai on 2017/6/9.
 */
@Entity
@Table(name = "sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false,name="username")
    private String username;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    @Size(max = 50)
    @Column(length = 50,name="firstName")
    private String firstName;

    @Size(max = 50)
    @Column(length = 50,name="lastName")
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true,name="email")
    private String email;


    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;


    @JsonIgnore
    @ManyToMany(targetEntity =com.simplemall.micro.serv.security.domain.SysRole.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @BatchSize(size = 20)
    private Set<SysRole> roles = new HashSet<>();


    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> userAuthotities = new HashSet<>();
        for(SysRole role : this.roles){
            for(SysAuthority authority : role.getAuthorities()){
                userAuthotities.add(new SimpleGrantedAuthority(authority.getValue()));
            }
        }

        return userAuthotities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
