package in.sanket.ExpenseTrackerApi.service;

import in.sanket.ExpenseTrackerApi.entity.User;
import in.sanket.ExpenseTrackerApi.entity.UserModel;
import org.springframework.stereotype.Service;


public interface UserService {

    User createUser(UserModel user);

    User readUser();

    User updateUser(UserModel user);

    void deleteUser();

    User getLoggedInUser();

}
