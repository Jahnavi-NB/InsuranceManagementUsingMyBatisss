package com.crimsonlogic.insurancemanagementsystem.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import java.io.InputStream;

public final class MyBatisUtil {
    private static SqlSessionFactory factory;
    private MyBatisUtil() {
    }
    static {
        try {
            InputStream input = Resources.getResourceAsStream("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(input);
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize MyBatis: "+e.getMessage(), e);
        }
    }
    public static SqlSession openSession() {
        return factory.openSession();
    }
    public static void close() {
    }
}
