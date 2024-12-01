package com.Project_Management.Repository;

import com.Project_Management.Entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription,Long> {
    Subscription findByUserId(Long userId);
}
