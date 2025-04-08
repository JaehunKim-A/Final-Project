package com.team1.team1project.repository.codeManagementSearch;

//@Service
//@RequiredArgsConstructor
//public class CodeManagementSearchImpl implements CodeManagementSearch {
//    private final EntityManager entityManager;
//
//    @Override
//    public Page<CodeManagement> searchAll(String[] types, String keyword, Pageable pageable, LocalDateTime from, LocalDateTime to) {
//        // JPQL로 작성
//        StringBuilder jpql = new StringBuilder("SELECT c FROM CodeManagement c WHERE c.codeId > 0");
//
//        if (from != null && to != null) {
//            jpql.append(" AND c.regDate BETWEEN :from AND :to");
//        } else if (from != null) {
//            jpql.append(" AND c.regDate >= :from");
//        } else if (to != null) {
//            jpql.append(" AND c.regDate <= :to");
//        }
//
//        // 동적 검색 조건 추가
//        if ((types != null && types.length > 0) && keyword != null) {
//            jpql.append(" AND (");
//
//            for (int i = 0; i < types.length; i++) {
//                String type = types[i];
//                switch (type) {
//                    case "a":
//                        jpql.append("c.codeValue LIKE :keyword");
//                        break;
//                    case "b":
//                        jpql.append("c.codeType LIKE :keyword");
//                        break;
//                    case "c":
//                        jpql.append("c.codeName LIKE :keyword");
//                        break;
//                    case "d":
//                        jpql.append("i.category LIKE :keyword");
//                        break;
//                    case "e":
//                        jpql.append("c.codeDescription Like :keyword");
//                }
//
//                // 각 조건 사이에 "OR" 추가
//                if (i < types.length - 1) {
//                    jpql.append(" OR ");
//                }
//            }
//            jpql.append(")");
//        }
//        jpql.append(" ORDER BY codeId DESC");
//
//        // JPQL로 쿼리 생성
//        TypedQuery<CodeManagement> query = entityManager.createQuery(jpql.toString(), CodeManagement.class);
//        TypedQuery<Long> countQuery = entityManager.createQuery(
//                jpql.toString().replace("SELECT c", "SELECT COUNT(c)"), Long.class
//        );
//
//        // 파라미터 바인딩
//        if ((types != null && types.length > 0) && keyword != null) {
//            query.setParameter("keyword", "%" + keyword + "%");
//            countQuery.setParameter("keyword", "%" + keyword + "%");
//        }
//        if (from != null) {
//            query.setParameter("from", from);
//            countQuery.setParameter("from", from);
//        }
//        if (to != null) {
//            query.setParameter("to", to);
//            countQuery.setParameter("to", to);
//        }
//
//        // 페이징 처리
//        query.setFirstResult((int) pageable.getOffset());
//        query.setMaxResults(pageable.getPageSize());
//
//        // 결과 조회
//        List<CodeManagement> list = query.getResultList();
//        long count = countQuery.getSingleResult();
//
//        return new PageImpl<>(list, pageable, count);
//    }
//}