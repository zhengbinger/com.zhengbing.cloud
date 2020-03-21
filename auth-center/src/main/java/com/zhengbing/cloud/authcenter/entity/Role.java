package com.zhengbing.cloud.authcenter.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhengbing
 */
@Data
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = -2432805731609094372L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
