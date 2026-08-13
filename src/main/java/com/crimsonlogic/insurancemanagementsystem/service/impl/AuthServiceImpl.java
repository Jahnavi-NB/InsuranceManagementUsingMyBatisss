package com.crimsonlogic.insurancemanagementsystem.service.impl;

import com.crimsonlogic.insurancemanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.insurancemanagementsystem.mapper.UserMapper;
import com.crimsonlogic.insurancemanagementsystem.model.User;
import com.crimsonlogic.insurancemanagementsystem.service.AuthService;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class AuthServiceImpl implements AuthService {
    public Optional<User> login(String u, String p) {
        try (SqlSession s = MyBatisUtil.openSession()) {
            return Optional.ofNullable(s.getMapper(UserMapper.class).findByUsername(u, p));
        }
    }
}
