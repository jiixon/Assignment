package com.garchon.webDBAssignment.service;

import com.garchon.webDBAssignment.dto.DetailFormDto;
import com.garchon.webDBAssignment.entity.DetailEntity;
import com.garchon.webDBAssignment.entity.TableEntity;
import com.garchon.webDBAssignment.repository.DetailRepository;
import com.garchon.webDBAssignment.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DetailService {
    private final DetailRepository detailRepository;
    private final TableRepository tableRepository;

    public void uploadDetailExtracted(DetailFormDto detailFormDto) { //작성된 글 저장

        TableEntity tableEntity = new TableEntity();
        tableEntity.setTitle(detailFormDto.getScheduleName());
        tableEntity.setWriter(detailFormDto.getUserName());
        tableEntity.setType(detailFormDto.getFlexRadioDefault());
        tableEntity.setDeleteYn(false);
        tableEntity.setCheerCnt(0L);
        tableEntity.setUserToken(detailFormDto.getUserToken());
        tableRepository.save(tableEntity);

        DetailEntity detailEntity = new DetailEntity();
        detailEntity.setTableEntity(tableEntity);
        detailEntity.setMainContents(detailFormDto.getScheduleContents());
        detailEntity.setScheduleTime(detailFormDto.getTime());
        detailEntity.setScheduleContents(detailFormDto.getSchedule());
        detailEntity.setScheduleResult(detailFormDto.getResult());
        detailRepository.save(detailEntity);
    }

    public void detailModifiedExtracted(DetailFormDto detailFormDto) { //전달받은 DTO내용으로 디비에 반영
        Optional<TableEntity> tableById = tableRepository.findById(detailFormDto.getIspk());
        Optional<DetailEntity> detailById = detailRepository.findById(detailFormDto.getIspk());

        tableById.get().setTitle(detailFormDto.getScheduleName()); //글 제목 수정
        tableById.get().setUpdateDate(LocalDateTime.now()); //글 작성 시간 수정
        tableById.get().setType(detailFormDto.getType()); //글 타입 수정

        detailById.get().setTableEntity(tableById.get());
        detailById.get().setMainContents(detailFormDto.getScheduleContents()); //일정 내용 수정
        detailById.get().setScheduleTime(detailFormDto.getTime()); //일정 시간 수정
        detailById.get().setScheduleContents(detailFormDto.getSchedule()); //세부 일정 내용 수정
        detailById.get().setScheduleResult(detailFormDto.getResult()); //일정 만료여부 수정

        tableRepository.save(tableById.get());
        detailRepository.save(detailById.get());
    }

    public void deleteDetailExtracted(Long pk) { //글 삭제 여부를 true로 바꾸는 메서드
        Optional<TableEntity> tableById = tableRepository.findById(pk); //전달받은 매개변수로 TableEntity 검색
        tableById.get().setDeleteYn(true); //삭제여부를 true로
        tableRepository.save(tableById.get()); //디비에 반영
    }

    public Optional<DetailEntity> getDetailRepositoryById(Long pk) { //매개변수로 전달받은 값 기준으로 DetailEntity 검색
        return detailRepository.findById(pk);
    }
}
