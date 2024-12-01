package com.Project_Management.Service;

import com.Project_Management.Entity.PlanType;
import com.Project_Management.Entity.Subscription;
import com.Project_Management.Entity.User;

public interface SubscriptionPlanService {
        Subscription createSubscription(User user);
        Subscription getUserSubscription(Long userId);
        Subscription upgradeSubscription(Long userId, PlanType planType);

        boolean isValid(Subscription subscription);

}
