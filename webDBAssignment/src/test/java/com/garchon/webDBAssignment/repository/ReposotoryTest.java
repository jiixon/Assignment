package com.garchon.webDBAssignment.repository;

import com.garchon.webDBAssignment.entity.TableEntity;
import com.garchon.webDBAssignment.entity.type.Type;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class ReposotoryTest {

    @Autowired TableRepository tableRepository;
    @Test
    void UpdateTest() {
        TableEntity tableEntity;
        tableEntity = new TableEntity();
        tableEntity.setCheerCnt(12L);
        tableEntity.setUpdateDate(LocalDateTime.now());
        tableEntity.setDeleteYn(false);
        tableEntity.setTitle("TEST_TITLE");
        tableEntity.setType(Type.Before.toString());
        tableEntity.setWriter("Jacob");

        tableRepository.save(tableEntity);
    }
}
