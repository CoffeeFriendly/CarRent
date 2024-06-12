package com.example.CarRent.DAO;

import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAOimpl implements UserDAO {
    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
    public void setSessionFactory(SessionFactory sessionFactory) {
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    public List<UserEntity> getUsers() {
        List<UserEntity> users = sessionFactory.openSession().createQuery("SELECT * FROM users").list();
        return users;
    }
}
