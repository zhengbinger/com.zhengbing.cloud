package com.zhengbing.cloud.auth.repository;


import com.zhengbing.cloud.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findByUsername(String username);
}
