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

    /* This is not needed (@RequiredArgsConstructor)
    public userService(UserRepository userRepository) { //Automatically inject userRepository when object created
        this.userRepository = userRepository;
    }
*/

    public List<UserResponse> fetchAllUsers(){
    //Returns UserResponse

        return userRepository.findAll().stream() //convert to stream
                .map(this::mapToUserResponse)//for each User comes from userRepository(), apply the function mapToUserResponse()
                .collect(Collectors.toList());//Convert to a List again

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


    }



    public Optional<UserResponse> getOneUser(Long id){

        return userRepository.findById(id)
                .map(this::mapToUserResponse);

    }



    public boolean updateUser(Long id, UserRequest UpdateduserRequest){
            //get the id, userUpdated

        return userRepository.findById(id) //returns an optional
                .map(existinguser -> {
                    updateUserFromRequest(existinguser,UpdateduserRequest);
                    userRepository.save(existinguser);
                    return true;
                        }).orElse(false);

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
