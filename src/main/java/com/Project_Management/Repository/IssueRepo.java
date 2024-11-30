package com.Project_Management.Repository;


import com.Project_Management.Entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<IssueEntity,Long> {
    List<IssueEntity> findByProjectId(Long projectId);


}
