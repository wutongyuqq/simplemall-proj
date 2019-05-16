package com.simplemall.micro.serv.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fanyoucai on 2018/5/24.
 */
@Entity(name = "SysRole")
@Table(name = "sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false,name="name")
    private String name;

    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false,name="value")
    private String value;

    @JsonIgnore
    @ManyToMany(targetEntity =SysAuthority.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @BatchSize(size = 20)
    private Set<SysAuthority> authorities = new HashSet<>();
}
