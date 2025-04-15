package com.team1.team1project.repository.productProcessManagementSearch;

import com.team1.team1project.domain.MachineHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MachineHistorySearch {
    Page<MachineHistory> searchAll(String[] types, String keyword, Pageable pageable);
}
