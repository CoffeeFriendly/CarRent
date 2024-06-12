package com.example.CarRent.DAO;

import com.example.CarRent.Entity.UserEntity;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;

public interface UserDAO {
    void setSessionFactory(SessionFactory sessionFactory);
    public List<UserEntity> getUsers();
}