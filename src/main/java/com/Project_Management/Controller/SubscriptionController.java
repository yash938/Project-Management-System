package com.Project_Management.Controller;


import com.Project_Management.Entity.PlanType;
import com.Project_Management.Entity.Subscription;
import com.Project_Management.Entity.User;
import com.Project_Management.Service.SubscriptionPlanService;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionPlanService subscriptionPlanService;

    @Autowired
    private UserService userService;

    @GetMapping("/userSubscription")
    public ResponseEntity<Subscription> getUserSubscriptions(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Subscription userSubscription = subscriptionPlanService.getUserSubscription(user.getUserId());
        return new ResponseEntity<>(userSubscription, HttpStatus.OK);
    }

    @PutMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(@RequestParam PlanType planType){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Subscription subscription = subscriptionPlanService.upgradeSubscription(user.getUserId(), planType);
        return new ResponseEntity<>(subscription,HttpStatus.OK);
    }
}
