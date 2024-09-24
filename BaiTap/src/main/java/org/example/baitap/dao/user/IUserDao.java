package org.example.baitap.dao.user;

import org.example.baitap.models.User;

public interface IUserDao {
    void register(User user);
    User login(String username, String password);
}
