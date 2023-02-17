package com.garchon.webDBAssignment.service;

import com.garchon.webDBAssignment.entity.TableEntity;
import com.garchon.webDBAssignment.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TableService {
    private final TableRepository tableRepository;

    public Optional<TableEntity> getTableById(Long pk) { //매개변수로 전달받은 값 기준으로 TableEntity 검색
        return tableRepository.findById(pk);
    }

    public TableEntity getSaveTableEntity(Optional<TableEntity> byId) { //매개변수로 전달된 TableEntity 저장
        return tableRepository.save(byId.get());
    }

    public List<TableEntity> getTableRepositoryAllByDeleteYnLike() { // 삭제여부가 false로  된 모든 TableEntity 검색
        return tableRepository.findAllByDeleteYnLike(false);
    }

    public List<TableEntity> getTableRepositoryAllByTitleContaining(String search) { //매개변수로 전달된 내용이 제목에 포함되는 것 중 삭제 여부가 false인 모든 tableEntity 검색
        return tableRepository.findAllByTitleContaining(search).stream()
                .filter(obj->obj.getDeleteYn()==false).collect(Collectors.toList());
    }

}
