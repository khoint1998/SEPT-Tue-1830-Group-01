package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/bookeroo/registrations")
public class RegistrationController {

    @Autowired
    private ShopService shopService;

    @PatchMapping("/update/status/sold/{id}")
    public void ChangeStatusToSold(@PathVariable(value="id") Long registrationId){
        shopService.changeStatusToSold(registrationId);
    }
}
