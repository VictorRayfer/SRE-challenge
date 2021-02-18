package com.sre.monitorauditpoc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class Home {

    @GetMapping("/hello")
    ResponseEntity<String> age() {
        System.out.println("Hello World!!!");
        return new ResponseEntity<>("Hello World!!", HttpStatus.OK);
    }

}