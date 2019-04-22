package users;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserValidate {

    public static BiFunction<List<User>,String,Boolean> checkUserName=(users, username) -> {
        return users.stream()
                .reduce(null,(accum, user)-> user.getUserName().equals(username) ? user : accum) != null;
    };

    public static BiFunction<List<User>,String[],User> createUser=(users,userInfo)->{
        return new User(userInfo[0],userInfo[1],users.size()+1);

    };

    public static BiFunction<List<User>,String[],User> getUser=(users,userInfo)-> {
        return users.stream()
                .reduce(null, (accum, user) -> (user.getUserName().equals(userInfo[0]) && user.getPassword()
                        .equals(userInfo[1])) ? user : accum);
    };

}
