package com.garchon.webDBAssignment.service;

import com.garchon.webDBAssignment.entity.CheerEntity;
import com.garchon.webDBAssignment.entity.TableEntity;
import com.garchon.webDBAssignment.repository.CheerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CheerService {
    private final CheerRepository cheerRepository;

    public Optional<List<CheerEntity>> getAllByTableEntity(Optional<TableEntity> byId) { //매개변수로 전달받은 객체를 기준으로 모든 TableEntity 검색
        return cheerRepository.findAllByTableEntity(byId.get());
    }

    public void getSaveCheerEntity(CheerEntity cheerEntity) {
        cheerRepository.save(cheerEntity);
    } //CheerEntity 저장

    public void getDeleteCheerEntity(List<CheerEntity> collect) { //CheerEntity 삭제
        cheerRepository.delete(collect.get(0));
    }
}
