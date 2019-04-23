package users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

 class UserValidate {

     static BiFunction<List<User>,String,Boolean> checkUserName=(users, username) -> users.stream()
             .reduce(null,(accum, user)-> user.getUserName().equals(username) ? user : accum) != null;

     static BiFunction<List<User>,String[],User> createUser=(users,userInfo)-> new User(userInfo[0],userInfo[1],users.size()+1);

     static BiFunction<List<User>,String[],User> getUser=(users,userInfo)-> users.stream()
             .reduce(null, (accum, user) -> (user.getUserName().equals(userInfo[0]) && user.getPassword()
                     .equals(userInfo[1])) ? user : accum);

     static BiFunction<List<User>, User, List<User>> extractedUserList=(users, user) -> users.stream()
             .filter(user1 -> !user.equals(user1)&&!user.getFollowings().contains(user1))
             .distinct().collect(Collectors.toList());

     static BiFunction<List<User>, Integer, User> findByUserId=(users, id) -> users.stream()
             .reduce(null, (accum, user) -> user.getUserId() == id ? user : accum);

     static BiConsumer<List<User>, User> showRecommendation =(users, user) -> {
         System.out.println("Some Recommendations for you to follow:");
         UiFormat.drawProfileTable.draw();
         users.stream()
                 .filter(user1 -> !user.equals(user1) && !user.getFollowings().contains(user1))
                 .distinct()
                 .limit(5)
                 .forEach(User::print);
         UiFormat.drawLine.draw();
     };

     static Consumer<User> showFollowers= user -> {
         System.out.println("List of Followers:");
         UiFormat.drawProfileTable.draw();
         user.getFollowers().forEach(User::print);
         UiFormat.drawLine.draw();
     };

     static Consumer<User> showFollowings= user -> {
         System.out.println("List of Followings:");
         UiFormat.drawProfileTable.draw();
         user.getFollowings().forEach(User::print);
         UiFormat.drawLine.draw();
     };

}
