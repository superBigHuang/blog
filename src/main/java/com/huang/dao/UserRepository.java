package com.huang.dao;

import com.huang.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author huangneng
 * @create 2020-04-17 10:26
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
}
