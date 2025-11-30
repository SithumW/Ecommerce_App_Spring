package com.app.ecom.controller;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import com.app.ecom.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.ecom.service.userService;

import java.util.List;
import java.util.ResourceBundle;


//@RequiredArgsConstructor eliminates the need for separate constructor to instantiate userService.
//Come from lombok
@RestController
@RequiredArgsConstructor

@RequestMapping("/api/users")
public class UserController {
    //Creates an instance of userService
    //final -> add userService to the constructor,
    // Each time UserController is instantiated, userService also will be instantiated.
    //If not final, the @RequiredArgsConstructor will not create them.
    private final userService userService;

    /*
    //Constructor -> this needs if the @RequiredArgsConstructor is not above.
    public UserController(userService userService){
        this.userService = userService;
    }
     */

    @GetMapping
   //@RequestMapping(value = "api/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getAllUsers(){
    //UserResponse will be returned

        return new ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllUsers());


    }


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        //Request is not User type
        //Its now on, UserRequest


        if (userService.addUser(userRequest)){

            return ResponseEntity.ok("User Added Successfully!");
        }
        else{
            return ResponseEntity.notFound().build();
        }


    }

    //Update user
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id , @RequestBody UserRequest UpdateduserRequest) {


        boolean res = userService.updateUser(id, UpdateduserRequest);
        if (res) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
            //error occurred;
        }
        /*
        return userService.updateUser(id, user)
                .map(u -> ResponseEntity.ok("User Updated Successfully"))
                .orElseGet(() -> ResponseEntity.notFound().build());
        */

        //if you are using second method

        /*

        boolean updated = userService.updateUser(id, user);
        if (updated){
        return ResponseEntity.ok("User Added Successfully")
        }
        else{
        return ResponseEntity.notFound().build();
        }

        */

    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){ //taking id query parameter
    /*
        User user =  userService.getOneUser(id);
        if (user == null){

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);

     */

        //Handle the optional
        return userService.getOneUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());

    }
}
