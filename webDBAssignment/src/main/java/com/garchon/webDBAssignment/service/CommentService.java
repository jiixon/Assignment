package com.garchon.webDBAssignment.service;

import com.garchon.webDBAssignment.dto.CommentDto;
import com.garchon.webDBAssignment.entity.CommentEntity;
import com.garchon.webDBAssignment.entity.DetailEntity;
import com.garchon.webDBAssignment.repository.CommentRepository;
import com.garchon.webDBAssignment.repository.DetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final DetailRepository detailRepository;
    public CommentEntity getCommentEntity(CommentDto commentDto) { //댓글 저장하는 매서드
        Optional<DetailEntity> detailById = detailRepository.findById(commentDto.getPk());

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setDetailEntity(detailById.get());
        commentEntity.setComment(commentDto.getInputLine()); //댓글 내용 저장
        commentEntity.setLocalDateTime(LocalDateTime.now()); //댓글 달린 시간 저장
        commentEntity.setUserToken(commentDto.getUsertoken()); //댓글 작성자 토큰 저장
        commentEntity.setName(commentDto.getUsername()); //댓글 작성자 이름 저장

        commentRepository.save(commentEntity);
        return commentEntity;
    }

    public Optional<CommentEntity> getCommentRepositoryById(Long autopk) { //매개변수로 전달받은 값 기준으로 Comment repository 검색
        return commentRepository.findById(autopk);
    }

    public void deleteCommentEntity(Optional<CommentEntity> byId) { //매개변수로 전달받은 CommentEntity 삭제
        commentRepository.delete(byId.get());
    }

    public Optional<List<CommentEntity>> getAllByDetailEntity(Optional<DetailEntity> detailById) { //매개변수로 전달받은 객체 기준으로 모든 DetailEntity 검색
        return commentRepository.findAllByDetailEntity(detailById.get());
    }
}
