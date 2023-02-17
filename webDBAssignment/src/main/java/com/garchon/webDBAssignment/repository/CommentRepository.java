package com.garchon.webDBAssignment.repository;

import com.garchon.webDBAssignment.entity.CommentEntity;
import com.garchon.webDBAssignment.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<List<CommentEntity>> findAllByDetailEntity(DetailEntity detailEntity);
}
