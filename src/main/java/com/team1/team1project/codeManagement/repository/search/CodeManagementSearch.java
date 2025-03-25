package com.team1.team1project.codeManagement.repository.search;

import com.team1.team1project.domain.CodeManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CodeManagementSearch {
    Page<CodeManagement> searchAll(String[] types, String keyword, Pageable pageable, LocalDateTime from, LocalDateTime to);
}
