package storage;


import model.User;
import util.StorageSerializeUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserStorage implements Serializable {

    private Map<String, User> users = new HashMap<>();

    public void add(User user) {
        users.put(user.getId(), user);
        StorageSerializeUtil.serializeUserStorage(this);
    }

    public void print() {
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    public User getByEmailAndPassword(String userEmail, String password) {
        for (User user : users.values()) {
            if (user.getEmail().equals(userEmail) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getByEmail(String userEmail) {
        for (User user : users.values()) {
            if (user.getEmail().equals(userEmail)) {
                return user;
            }
        }
        return null;
    }
}
