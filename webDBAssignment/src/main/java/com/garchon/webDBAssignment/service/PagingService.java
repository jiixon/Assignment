package com.garchon.webDBAssignment.service;

import com.garchon.webDBAssignment.entity.TableEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagingService {

    public int getLeft(Integer page, int pageSize) {
        return (int) Math.floor((double) (page - pageSize) / (double) pageSize) * pageSize;
    }

    public int getRight(Integer page, int pageSize) {
        return (int) Math.floor((double) (page + pageSize) / (double) pageSize) * pageSize;
    }

    public int getTempStartPage(double page, int pageSize) {
        return (int) Math.floor(page / (double) pageSize) * pageSize;
    }

    public int getTotalPage(List<TableEntity> content) {
        return (int) Math.ceil((double) content.size() / (double) 10);
    }
    public List<TableEntity> paging(List<TableEntity> tables, int fixCount, int page) { // 페이징 처리
        int numberOfSize = tables.size();
        int startRow = page * fixCount; // 시작
        int endRow =  startRow + fixCount;// 끝
        int remainder = numberOfSize % fixCount; // 나머지
        int totalPage = (int) Math.ceil(((double) numberOfSize)/ (double) fixCount); // 전체 페이지 개수

        List<TableEntity> sensingList = new ArrayList<>();

        if (totalPage == 0){
            startRow = 0;
            endRow = 0;
            List<TableEntity> sensings1 = tables.subList(startRow, endRow);

            sensingList.addAll(sensings1);
        }
        else if (page + 1 != totalPage){ // 맨 마지막 이전 페이지까지의 계산
            List<TableEntity> sensings1 = tables.subList(startRow, endRow);

            sensingList.addAll(sensings1);
        }
        else { // 맨 마지막 페이지 일 때의 계산
            startRow = fixCount * page;
            endRow = startRow + remainder;

            List<TableEntity> sensings1 = tables.subList(startRow, endRow);

            sensingList.addAll(sensings1);
        }

        return sensingList;
    }
}
