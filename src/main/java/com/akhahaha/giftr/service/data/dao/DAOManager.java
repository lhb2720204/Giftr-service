package com.akhahaha.giftr.service.data.dao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * DAOManager factory
 * Created by Alan on 4/30/2016.
 */
public class DAOManager {
    private static final String BEAN_CONFIGURATION_FILE = "spring.xml";

    public enum DAOType {
        USER
    }

    private final ClassPathXmlApplicationContext context;

    private DAOManager() {
        context = new ClassPathXmlApplicationContext(BEAN_CONFIGURATION_FILE);
    }

    public static DAOManager getInstance() {
        return DAOManagerSingleton.INSTANCE;
    }

    public DAO getDAO(DAOType t) {
        switch (t) {
            case USER:
                return (DAO) context.getBean("userDAO");
            default:
                return null;
        }
    }

    private static class DAOManagerSingleton {
        static final DAOManager INSTANCE = new DAOManager();
    }
}
