package com.zhengbing.cloud.provideruser.service;

import com.zhengbing.cloud.provideruser.entity.AuthUser;

public interface UserService {

    /**
     * 创建用户，根据用户名和密码
     *
     * @param username
     * @param password
     * @return
     */
    AuthUser create(String username, String password);

}
