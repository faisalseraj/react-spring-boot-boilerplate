package com.kitchensaver.backend.Repo;

import com.kitchensaver.backend.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JobRepo extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j WHERE " +
            "(:status IS NULL OR j.status = :status) AND " +
            "(:installerId IS NULL OR j.installer.id = :installerId) AND " +
            "(:materialStatus IS NULL OR j.materialStatus = :materialStatus) AND " +
            "(:office IS NULL OR j.office = :office)")
    List<Job> findByFilters(@Param("status") String status,
                           @Param("installerId") Long installerId,
                           @Param("materialStatus") String materialStatus,
                           @Param("office") String office);
}