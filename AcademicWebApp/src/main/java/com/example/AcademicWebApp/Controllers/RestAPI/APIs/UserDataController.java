package com.example.AcademicWebApp.Controllers.RestAPI.APIs;

import com.example.AcademicWebApp.Controllers.RestAPI.Entities.Message;
import com.example.AcademicWebApp.Models.UserData;
import com.example.AcademicWebApp.Services.UserDataService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class UserDataController {


    @Autowired
    UserDataService userDataService;

    @GetMapping("/userdata/{username}/{name}")
    public String getFullName(@PathVariable(name = "username") String username, @PathVariable(name="name") String name) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("fullname", userDataService.getNameByUsername(name));
        return obj.toString();
    }

    @GetMapping("/userdata/{username}")
    public UserData getUserData(@PathVariable(name = "username") String username){
        return userDataService.getUserDataByUsername(username);
    }

    @PostMapping("/userdata/{username}")
    public Message getUserData(@PathVariable(name = "username") String username, @RequestBody UserData userData){
        //ignora lockul asta improvizat
        Object lock = new Object();
        Map<String, AtomicInteger> executingOrdersMap = new HashMap<>();
        // try-catch ul tau
        while (!executingOrdersMap.isEmpty()) {
            executingOrdersMap.entrySet().stream()
                    .filter(e -> e.getValue().get() == 0)
                    .forEach(e -> {
                        //codul de printare in fisier cu tot cu synchronized
                        synchronized (lock) {
                            try (FileWriter fileWriter = new FileWriter("abc", true)) {
                                fileWriter.write(e.getKey() + ",shipped");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
            executingOrdersMap.entrySet().removeIf(e -> e.getValue().get() == 0);
        }
        return userDataService.saveUserData(userData);
    }




}
