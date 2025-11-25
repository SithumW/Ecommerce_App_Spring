package com.app.ecom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
    public ResponseEntity<List<User>> getAllUsers(){

        return new ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllUsers());


    }


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user){
        System.out.println(user);
        userService.addUser(user);

        return ResponseEntity.ok("User Added Successfully!");
    }

    //Update user
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id , @RequestBody User user){

        return userService.updateUser(id, user)
                .map(u -> ResponseEntity.ok("User Updated Successfully"))
                .orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<User> getUser(@PathVariable Long id){ //taking id query parameter
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
