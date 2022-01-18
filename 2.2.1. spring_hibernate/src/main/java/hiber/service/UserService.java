package hiber.service;

import hiber.model.User;

import java.util.List;

public interface UserService {
    User carOwner(String model, int series);

    User add(User user);

    List<User> listUsers();

}
