package com.wb.dao;

import com.wb.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wb on 2020/2/18 0018 13:07
 *
 * JpaRepository<User, Integer> ---> JpaRepository<对象, 主键类型>
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);
}
