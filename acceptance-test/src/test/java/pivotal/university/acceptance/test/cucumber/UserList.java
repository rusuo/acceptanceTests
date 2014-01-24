package pivotal.university.acceptance.test.cucumber;

import pivotal.university.acceptance.test.testData.User;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    List<User> userList = new ArrayList<User>() ;

    public void addUser(){
        User newUser = new User();
        userList.add(newUser);
    }

    public User getUser(int index){
        return userList.get(index);
    }

    public List<User> getUserList() {
        return userList;
    }
}
