package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
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

    public List<UserResponse> fetchAllUsers(){
    //Returns UserResponse

        //return userRepository.findAll(); //use Repository operations (return all the users)
        //This will return List of User
        //But we want a list of UserResponse
        //We can define a separate method for that

        /*
        List<User> users = userRepository.findAll(); //get all the Users to a List

        List<UserResponse>  responseList = new ArrayList<>();

        for (User u : users){
            responseList.add(mapToUserResponse(u));

        }

        return responseList;
        */


        //Do it using single line:

        return userRepository.findAll().stream() //convert to stream
                .map(this::mapToUserResponse)//for each User comes from userRepository(), apply the function mapToUserResponse()
                .collect(Collectors.toList());//Convert to a List again





    }

    //Convert User => UserResponse
    private UserResponse mapToUserResponse(User user){
        //will take the values from the object passed (User)
        //and assign those values to the UserResponse

        UserResponse response = new UserResponse(); //create UserResponse Object
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setPhone(user.getPhone());


        if (user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setStreet(user.getAddress().getCity());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }

        return response;
    }





    public boolean addUser(UserRequest userRequest){

        try {
            User user = new User();
            updateUserFromRequest(user, userRequest);
            userRepository.save(user);
            return true;

        }
        catch (Exception  e){
            log.error("e: ", e);
            return false;

        }

        /*
        user.setId((long) userID);
        userID ++;


        //Or we can use post increment
        //user.setId((long) userID++);

        userList.add(user);
        return userList;
           */

    }



    public Optional<UserResponse> getOneUser(Long id){

        return userRepository.findById(id)
                .map(this::mapToUserResponse);



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


    public boolean updateUser(Long id, UserRequest UpdateduserRequest){
            //get the id, userUpdated


        return userRepository.findById(id) //returns an optional
                .map(existinguser -> {
                    updateUserFromRequest(existinguser,UpdateduserRequest);
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


    //UserRequest => User
    //Instead of using mapToUserRequest, this way help to update
    private  void updateUserFromRequest(User user, UserRequest userRequest){

        //user.setId(Long.valueOf(userRequest.getId())); no need to set the id, will automatically added in the repository.
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null){

            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);

        }


    }



}
