package com.zhengbing.cloud.provideruser.repository;


import com.zhengbing.cloud.provideruser.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {
}
