package com.Project_Management.Repository;

import com.Project_Management.Dto.UserDto;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Long> {
    List<Project> findByOwner(User user);

    List<Project> findByNameContainingAndTeamContaining(String partialName,User user);

    @Query("SELECT p From  Project p join p.team t where t=:user")
    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user,User owner);
}
