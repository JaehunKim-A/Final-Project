package com.team1.team1project.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;

public class PageController {

    // 공통 페이지네이션 처리 로직
    public <T> void addPagination(Model model, Page<T> page, int currentPage, List<String> columnNames) {
        model.addAttribute("page", page);
        model.addAttribute("pageNumber", currentPage);  // 현재 페이지 (1부터 시작)
        model.addAttribute("items", page.getContent());
        model.addAttribute("columns", columnNames);
    }

    // 페이지 번호가 1부터 시작하도록 설정
    protected Pageable getPageable(int page, int size, Pageable pageableRaw) {
        int pageIndex = page - 1;  // 페이지 번호를 1부터 시작하므로 1을 빼주어야 함
        return PageRequest.of(pageIndex, size, pageableRaw.getSort());
    }
}