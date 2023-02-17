package com.garchon.webDBAssignment.controller;

import com.garchon.webDBAssignment.dto.CommentDto;
import com.garchon.webDBAssignment.dto.DetailFormDto;
import com.garchon.webDBAssignment.entity.CheerEntity;
import com.garchon.webDBAssignment.entity.CommentEntity;
import com.garchon.webDBAssignment.entity.DetailEntity;
import com.garchon.webDBAssignment.entity.TableEntity;
import com.garchon.webDBAssignment.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final CommentService commentService;
    private final CheerService cheerService;
    private final TableService tableService;
    private final DetailService detailService;
    private final PagingService pagingService;

    @GetMapping
    String home() {
        return "index";
    } //로그인 페이지

    @ResponseBody
    @PostMapping("/comment") //댓글 추가
    Map comment(CommentDto commentDto) {

        CommentEntity commentEntity = commentService.getCommentEntity(commentDto); //댓글 저장

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        stringObjectHashMap.put("writer", commentEntity.getName());
        stringObjectHashMap.put("time", commentEntity.getLocalDateTime());
        stringObjectHashMap.put("comment", commentEntity.getComment());
        stringObjectHashMap.put("usertoken", commentEntity.getUserToken());
        stringObjectHashMap.put("autopk", commentEntity.getId());

        return stringObjectHashMap;
    }



    @GetMapping("delete/comment") //댓글 삭제
    String deleteComment(@RequestParam Long pk, @RequestParam Long autopk, @RequestParam Long usertoken) { //작성자의 토큰 값과 삭제 요청한 사람의 토큰 값 비교

        Optional<CommentEntity> byId = commentService.getCommentRepositoryById(autopk); //commentRepository검색

        if (byId.get().getUserToken().equals(usertoken)) { //작성자가 삭제 요청
            commentService.deleteCommentEntity(byId); //삭제
            return "redirect:/view?usertoken=" + usertoken + "&pk=" + pk;
        } else { //작성자가 아닌 사람이 삭제 요청

            return "redirect:/view?usertoken=" + usertoken + "&pk=" + pk;
        }
    }




    @GetMapping("/view")
    String view(Model model, @RequestParam Long usertoken, @RequestParam Long pk) { //글 자세히 보여주는 URI

        Optional<TableEntity> tableById = tableService.getTableById(pk); //TableEntity 검색
        Optional<DetailEntity> detailById = detailService.getDetailRepositoryById(pk); //DetailEntity 검색
        Optional<List<CommentEntity>> allByDetailEntity = commentService.getAllByDetailEntity(detailById); //모든 DetailEntity 검색

        String[] time = detailById.get().getScheduleTime().split(",");
        String[] results = detailById.get().getScheduleResult().split(",");
        String[] scheduleContents = detailById.get().getScheduleContents().split(",");

        model.addAttribute("userName", tableById.get().getWriter());
        model.addAttribute("contents", detailById.get().getMainContents());
        model.addAttribute("title", tableById.get().getTitle());
        model.addAttribute("pk", pk);
        model.addAttribute("usertoken", usertoken);
        model.addAttribute("commentlist", allByDetailEntity.get());
        model.addAttribute("cheerCnt", tableById.get().getCheerCnt());
        model.addAttribute("time", time);
        model.addAttribute("results", results);
        model.addAttribute("scheduleContents", scheduleContents);

        return "view";
    }




    @GetMapping("/detail") // 글 수정 또는 생성 URI
    String detail(@RequestParam(required = false) Long pk, @RequestParam Long usertoken, Model model) {
        if (pk != null) { //글 수정 페이지
            Optional<DetailEntity> detailById = detailService.getDetailRepositoryById(pk); //DetailEntity 검색
            Optional<TableEntity> tableById = tableService.getTableById(pk); //TableEntity 검색

            String[] time = detailById.get().getScheduleTime().split(",");
            String[] results = detailById.get().getScheduleResult().split(",");
            String[] contents = detailById.get().getScheduleContents().split(",");

            model.addAttribute("detailEntity", detailById.get());
            model.addAttribute("tableEntity", tableById.get());
            model.addAttribute("usertoken", usertoken);
            model.addAttribute("time", time);
            model.addAttribute("results", results);
            model.addAttribute("contents", contents);

            return "detail"; //글 수정하는 페이지
        }
        return "createDetail"; //글 쓰는 페이지
    }

    @PostMapping("/upload/detail")
    String uploadDetail(DetailFormDto detailFormDto) { //작성된 글 저장 uri

        detailService.uploadDetailExtracted(detailFormDto); //작성된 글 저장

        String username = URLEncoder.encode(detailFormDto.getUserName(), StandardCharsets.UTF_8); //한글 깨져서 ???로 리다이렉트되기 때문에 사용
        String usertoken = URLEncoder.encode(detailFormDto.getUserToken().toString(), StandardCharsets.UTF_8);

        return "redirect:/list?nickname=" + username + "&usertoken=" + usertoken;
    }



    @GetMapping("/switch/modify") //수정을 요청한 사용자가 글 작성자와 일치하는지 확인하는 uri
    String switchModify(@RequestParam Long pk, @RequestParam Long usertoken) {

        Optional<TableEntity> byId = tableService.getTableById(pk); //TableEntity 검색
        Long userToken = byId.get().getUserToken();

        if (userToken.equals(usertoken)) { //작성자가 수정 버튼을 누름

            return "redirect:/detail?pk=" + pk + "&usertoken=" + usertoken;
        } else { //작성자가 아닌 사람이 수정을 누름

            return "redirect:/view?usertoken=" + usertoken + "&pk=" + pk;
        }
    }

    @PostMapping("/modify/detail") //수정된 글을 저장하는 uri
    String modifyDetail(DetailFormDto detailFormDto) {

        detailService.detailModifiedExtracted(detailFormDto); //전달받은 수정내용 디비에 반영
        return "redirect:/list?nickname=" + detailFormDto.getUserName() + "&usertoken=" + detailFormDto.getUserToken();
    }



    @GetMapping("/delete/detail") //글 삭제하는 uri
    String deleteDetail(@RequestParam Long pk,@RequestParam String nickname, @RequestParam Long usertoken) {
        detailService.deleteDetailExtracted(pk); //글 삭제
        return "redirect:/list?nickname=" + nickname + "&usertoken=" + usertoken;
    }


    @ResponseBody
    @GetMapping("/update/cheerCnt") //응원하기 uri
    Map updateCheerCnt(@RequestParam Long pk, @RequestParam Long usertoken) {

        long cnt;
        Optional<TableEntity> byId = tableService.getTableById(pk); //TableEntity 검색
        Optional<List<CheerEntity>> cheerById = cheerService.getAllByTableEntity(byId); //byId를 가진 모든 TableEntity 검색
        List<CheerEntity> collect = cheerById.get().stream().filter(obj -> obj.getUsertoken().equals(usertoken)).collect(Collectors.toList());

        if (collect.isEmpty()) { //해당 글에 응원을 하지 않았었음
            log.info("+1");
            cnt = byId.get().getCheerCnt() + 1;//응원수  + 1
            byId.get().setCheerCnt(cnt);
            tableService.getSaveTableEntity(byId);

            CheerEntity cheerEntity = new CheerEntity();
            cheerEntity.setTableEntity(byId.get());
            cheerEntity.setUsertoken(usertoken);
            cheerService.getSaveCheerEntity(cheerEntity);
        } else { //해당 글을 응원했었음 -> 응원 취소
            log.info("-1");
            cnt = byId.get().getCheerCnt() - 1;//응원수  - 1
            byId.get().setCheerCnt(cnt);
            tableService.getSaveTableEntity(byId);
            cheerService.getDeleteCheerEntity(collect);
        }

        HashMap<String, Long> stringLongHashMap = new HashMap<>();
        stringLongHashMap.put("cheerCnt", cnt);
        return stringLongHashMap;
    }




    @GetMapping("/list") //메인 페이지
    String list(@RequestParam String nickname, @RequestParam Long usertoken, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {


        List<TableEntity> allContents = tableService.getTableRepositoryAllByDeleteYnLike();
        List<TableEntity> content = pagingService.paging(allContents, 10, page);
        model.addAttribute("pageList", content);
        model.addAttribute("usertoken", usertoken);
        model.addAttribute("nickname", nickname);

        int totalPage = pagingService.getTotalPage(allContents); // 전체 페이지 개수
        int pageSize = 5; // 하단 페이지 번호 개수
        int tempStartPage = pagingService.getTempStartPage((double) page, pageSize); // 특정 페이지 리스트 목록의 시작 번호
        int tempEndPage = tempStartPage + pageSize - 1; // 특정 페이지 리스트 목록의 마지막 번호

        if (tempEndPage>totalPage){
            tempEndPage = totalPage-1;
        }

        int right = pagingService.getRight(page, pageSize); // 오른쪽 버튼 눌렀을 때 이동 페이지 번호
        int left = pagingService.getLeft(page, pageSize); // 왼쪽 버튼 눌렀을 때 이동 페이지 번호

        model.addAttribute("sw", "list");
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("tempStartPage", tempStartPage);
        model.addAttribute("tempEndPage", tempEndPage);
        model.addAttribute("right", right);
        model.addAttribute("left", left);

        return "list";
    }

    @GetMapping("/search/title") //검색했을 때 uri
    String searchTitle(@RequestParam String nickname, @RequestParam Long usertoken1, @RequestParam String search,@RequestParam(required = false, defaultValue = "0") Integer page ,Model model) {

        List<TableEntity> allContents = tableService.getTableRepositoryAllByTitleContaining(search);
        List<TableEntity> content = pagingService.paging(allContents, 10, page);
        model.addAttribute("pageList", content);
        model.addAttribute("usertoken", usertoken1);
        model.addAttribute("nickname", nickname);

        int totalPage = pagingService.getTotalPage(allContents); // 전체 페이지 개수
        int pageSize = 5; // 하단 페이지 번호 개수
        int tempStartPage = pagingService.getTempStartPage((double) page, pageSize); // 특정 페이지 리스트 목록의 시작 번호
        int tempEndPage = tempStartPage + pageSize - 1; // 특정 페이지 리스트 목록의 마지막 번호

        if (tempEndPage>totalPage){
            tempEndPage = totalPage-1;
        }

        int right = pagingService.getRight(page, pageSize); // 오른쪽 버튼 눌렀을 때 이동 페이지 번호
        int left = pagingService.getLeft(page, pageSize); // 왼쪽 버튼 눌렀을 때 이동 페이지 번호

        model.addAttribute("search", search);
        model.addAttribute("sw", "search");
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("tempStartPage", tempStartPage);
        model.addAttribute("tempEndPage", tempEndPage);
        model.addAttribute("right", right);
        model.addAttribute("left", left);
        return "list";
    }


}
