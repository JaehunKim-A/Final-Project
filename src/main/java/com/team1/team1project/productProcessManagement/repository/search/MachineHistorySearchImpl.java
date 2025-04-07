package com.team1.team1project.productProcessManagement.repository.search;

import com.team1.team1project.domain.MachineHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineHistorySearchImpl implements MachineHistorySearch {
    private final EntityManager entityManager;

    @Override
    public Page<MachineHistory> searchAll(String[] types, String keyword, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT mh FROM MachineHistory mh WHERE mh.historyId > 0");

        // 동적 검색 조건 추가
        if ((types != null && types.length > 0) && keyword != null && !keyword.trim().isEmpty()) {
            jpql.append(" AND (");

            for (int i = 0; i < types.length; i++) {
                String type = types[i];
                switch (type) {
                    case "h":
                        // 정수형 필드는 = 연산자 사용
                        try {
                            int historyId = Integer.parseInt(keyword);
                            jpql.append("mh.historyId = :historyIdParam");
                        } catch (NumberFormatException e) {
                            // 숫자로 변환할 수 없는 경우 항상 false인 조건 추가
                            jpql.append("1 = 0");
                        }
                        break;
                    case "p":
                        // 정수형 필드는 = 연산자 사용
                        try {
                            int productionAmount = Integer.parseInt(keyword);
                            jpql.append("mh.productionAmount = :productionAmountParam");
                        } catch (NumberFormatException e) {
                            // 숫자로 변환할 수 없는 경우 항상 false인 조건 추가
                            jpql.append("1 = 0");
                        }
                        break;
                    case "d":
                        // 정수형 필드는 = 연산자 사용
                        try {
                            int defectiveAmount = Integer.parseInt(keyword);
                            jpql.append("mh.defectiveAmount = :defectiveAmountParam");
                        } catch (NumberFormatException e) {
                            // 숫자로 변환할 수 없는 경우 항상 false인 조건 추가
                            jpql.append("1 = 0");
                        }
                        break;
                    case "m":
                        // 문자열 필드는 LIKE 연산자 사용
                        jpql.append("mh.machineId LIKE :keywordParam");
                        break;
                    case "r":
                        // 날짜 필드는 LIKE 연산자 사용 (부분 일치 검색은 제한됨)
                        jpql.append("FUNCTION('DATE_FORMAT', mh.productionDate, '%Y-%m-%d') LIKE :keywordParam");
                        break;
                }

                // 각 조건 사이에 "OR" 추가
                if (i < types.length - 1) {
                    jpql.append(" OR ");
                }
            }
            jpql.append(")");
        }
        Sort sort = pageable.getSort();

        if (sort.isSorted()) {
            jpql.append(" ORDER BY ");
            List<String> orders = new ArrayList<>();
            for (Sort.Order order : sort) {
                orders.add("mh." + order.getProperty() + " " + order.getDirection().name());
            }
            jpql.append(String.join(", ", orders));
        }

        TypedQuery<MachineHistory> query = entityManager.createQuery(jpql.toString(), MachineHistory.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(
                jpql.toString().replace("SELECT mh", "SELECT COUNT(mh)"), Long.class
        );

        if ((types != null && types.length > 0) && keyword != null && !keyword.trim().isEmpty()) {
            // 정수형 파라미터 설정
            for (String type : types) {
                switch (type) {
                    case "h":
                        try {
                            int historyId = Integer.parseInt(keyword);
                            query.setParameter("historyIdParam", historyId);
                            countQuery.setParameter("historyIdParam", historyId);
                        } catch (NumberFormatException e) {
                            // 처리 완료
                        }
                        break;
                    case "p":
                        try {
                            int productionAmount = Integer.parseInt(keyword);
                            query.setParameter("productionAmountParam", productionAmount);
                            countQuery.setParameter("productionAmountParam", productionAmount);
                        } catch (NumberFormatException e) {
                            // 처리 완료
                        }
                        break;
                    case "d":
                        try {
                            int defectiveAmount = Integer.parseInt(keyword);
                            query.setParameter("defectiveAmountParam", defectiveAmount);
                            countQuery.setParameter("defectiveAmountParam", defectiveAmount);
                        } catch (NumberFormatException e) {
                            // 처리 완료
                        }
                        break;
                    case "m":
                    case "r":
                        query.setParameter("keywordParam", "%" + keyword + "%");
                        countQuery.setParameter("keywordParam", "%" + keyword + "%");
                        break;
                }
            }
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<MachineHistory> list = query.getResultList();
        long count = countQuery.getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }
}
