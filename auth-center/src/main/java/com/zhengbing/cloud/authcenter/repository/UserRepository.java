package com.zhengbing.cloud.authcenter.repository;


import com.zhengbing.cloud.authcenter.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findByUsername(String username);
}
