package com.garchon.webDBAssignment.repository;

import com.garchon.webDBAssignment.entity.CheerEntity;
import com.garchon.webDBAssignment.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheerRepository extends JpaRepository<CheerEntity, Long> {
    Optional<List<CheerEntity>> findAllByTableEntity(TableEntity tableEntity);
}
