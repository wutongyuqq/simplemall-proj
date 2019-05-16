package com.simplemall.micro.serv.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by fanyoucai on 2018/5/23
 */

@Entity(name = "com.simplemall.micro.serv.security.domain.SysAuthority")
@Table(name = "sys_authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public  class SysAuthority  implements Serializable {
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
}
