package com.Project_Management.Repository;

import com.Project_Management.Entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription,Long> {
    @Query("SELECT s FROM Subscription s WHERE s.user.userId = :userId")
    Subscription findByUserId(@Param("userId") Long userId);

}
