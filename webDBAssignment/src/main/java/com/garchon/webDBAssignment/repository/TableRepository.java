package com.garchon.webDBAssignment.repository;

import com.garchon.webDBAssignment.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<TableEntity, Long> {
    List<TableEntity> findAllByTitleContaining(String s);

    List<TableEntity> findAllByDeleteYnLike(Boolean tf);
}
