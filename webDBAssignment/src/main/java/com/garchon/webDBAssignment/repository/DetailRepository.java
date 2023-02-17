package com.garchon.webDBAssignment.repository;

import com.garchon.webDBAssignment.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<DetailEntity, Long> {
}
