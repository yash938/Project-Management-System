package com.Project_Management.ServiceImpl;

import com.Project_Management.Entity.PlanType;
import com.Project_Management.Entity.Subscription;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.SubscriptionRepo;
import com.Project_Management.Service.SubscriptionPlanService;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionPlanService {

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private UserService userService;
    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        Subscription save = subscriptionRepo.save(subscription);
        return save;
    }

    @Override
    public Subscription getUserSubscription(Long userId) {
        Subscription subscription = subscriptionRepo.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }
        return subscription;
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepo.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }else{
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }
        return subscriptionRepo.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlanType() == PlanType.FREE){
            return true;
        }
        LocalDate subscriptionEndDate = subscription.getSubscriptionEndDate();
//        LocalDate subscriptionStartDate = subscription.getSubscriptionStartDate();
        LocalDate now = LocalDate.now();

        return subscriptionEndDate.isAfter(now) || subscriptionEndDate.equals(now);
    }
}
