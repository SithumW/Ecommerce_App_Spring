package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service //Service annotation, allows to inject to others
public class userService {

    private int userID = 1; //Increments based on the number of users

    public List<User> userList = new ArrayList<>();


    public List<User> fetchAllUsers(){
        return userList;
    }

    public List<User> addUser(User user){
        user.setId((long) userID);
        userID ++;

        //Or we can use post increment
        //user.setId((long) userID++);

        userList.add(user);
        return userList;
    }




    public Optional<User> getOneUser(Long id){


        return userList.stream() //convert userlist into a stream
                .filter(user -> user.getId().equals(id)) //for every user, check userId == id
                .findFirst(); //Get the first
                //This will return an optional

        /*
        //filter out one user
        for (int a = 1; a < userList.size(); a ++){
            User user = userList.get(a);
            if (Objects.equals(user.getId(), id)){
                return user;
            }

        }
        return null;
*/

    }


    public Optional<User> updateUser(Long id, User userIn){
            //get the id, userUpdated

        int idInt = Math.toIntExact(id);


        //find user
        Optional<User> user =  userList.stream()
                .filter(user1 -> id.equals(user1.getId()))
                .findFirst(); //find the user

        user.ifPresent(foundUser->{ //if user present
            int index = userList.indexOf(foundUser); //find its index
            userList.set(index,userIn); //update

        });

        System.out.println(user);
        return user; //returns user (Optional type)


        /*
            //Use the following to update each one
            return userList.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .map(existingUser -> {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            return true;
            })orElse(false);

          */

    }
}
