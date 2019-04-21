package users;

import java.util.List;
import java.util.function.Predicate;

public class UserValidate implements Predicate<String> {
    private List<User> users;
    UserValidate(List<User> users){
        this.users = users;
    }

    public boolean test(String username){
        return this.users.stream()
                .reduce(null,(accum, user)-> user.getUserName().equals(username) ? user : accum) != null;
    }
    public boolean test(String username,String Password){
        return this.users.stream()
                .reduce(null,(accum, user)-> user.getUserName().equals(username) ? user : accum) != null;
    }
}
