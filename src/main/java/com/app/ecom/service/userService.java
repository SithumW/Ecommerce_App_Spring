package com.app.ecom.service;

import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service //Service annotation, allows to inject to others
@RequiredArgsConstructor
public class userService {

    private final UserRepository userRepository; //Create an instance of user Repository
    //public List<User> userList = new ArrayList<>();
    private int userID = 1; //Increments based on the number of users

    /* This is not needed (@RequiredArgsConstructor)
    public userService(UserRepository userRepository) { //Automatically inject userRepository when object created
        this.userRepository = userRepository;
    }
*/

    public List<User> fetchAllUsers(){

        //return userList;
        return userRepository.findAll(); //use Repository operations (return all the users)
    }

    public String addUser(User user){

        userRepository.save(user);
        return "User created successfully";

        /*
        user.setId((long) userID);
        userID ++;


        //Or we can use post increment
        //user.setId((long) userID++);

        userList.add(user);
        return userList;
           */

    }




    public Optional<User> getOneUser(Long id){

        return userRepository.findById(id);



/*
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


    public boolean updateUser(Long id, User userIn){
            //get the id, userUpdated


        return userRepository.findById(id) //returns an optional
                .map(existinguser -> { //use map to update
                    existinguser.setFirstName(userIn.getFirstName());
                    existinguser.setLastName(userIn.getLastName());
                    existinguser.setAddress(userIn.getAddress());
                    existinguser.setEmail(userIn.getEmail());
                    existinguser.setPhone(userIn.getPhone());
                    userRepository.save(existinguser);
                    return true;
                        }).orElse(false);


       /* int idInt = Math.toIntExact(id);


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
