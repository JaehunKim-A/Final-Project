package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.domain.FinishedProductOutbound;
import com.team1.team1project.dto.*;
import com.team1.team1project.repository.FinishedProductOutboundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FinishedProductOutboundServiceImpl implements FinishedProductOutboundService {

    private final FinishedProductOutboundRepository repository;
    private final ModelMapper modelMapper;

    /** ✅ 1. 페이징 + 검색 목록 */
    @Override
    public PageResponseDTO<FinishedProductOutboundDTO> getPagedFinishedProductOutbounds(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("outboundId").descending());

        // 검색 조건 적용 예시
        Page<FinishedProductOutbound> result = repository.searchWithPaging(pageRequestDTO.getKeyword(), pageable);

        List<FinishedProductOutboundDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, FinishedProductOutboundDTO.class))
                .collect(Collectors.toList());

        // ✅ 빌더에 pageRequestDTO, dtoList, total만 전달
        return PageResponseDTO.<FinishedProductOutboundDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    /** ✅ 2. 상세 조회 */
    @Override
    public FinishedProductOutboundDTO getFinishedProductOutbound(Long outboundId) {
        FinishedProductOutbound entity = repository.findById(outboundId).orElseThrow();
        return modelMapper.map(entity, FinishedProductOutboundDTO.class);
    }

    /** ✅ 3. 등록 */
    @Override
    public void createFinishedProductOutbound(FinishedProductOutboundDTO dto) {
        FinishedProductOutbound entity = modelMapper.map(dto, FinishedProductOutbound.class);
        repository.save(entity);
    }

    /** ✅ 4. 수정 (상태 또는 전체) */
    @Override
    public void modifyOutboundStatus(Long outboundId, FinishedProductOutboundDTO dto) {
        FinishedProductOutbound entity = repository.findById(outboundId).orElseThrow();

        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }

        if (dto.getOutboundDate() != null) {
            entity.setOutboundDate(dto.getOutboundDate());
        }

        if (dto.getQuantity() != null) {
            entity.setQuantity(dto.getQuantity());
        }

        if (dto.getOutboundCode() != null) {
            entity.setOutboundCode(dto.getOutboundCode());
        }

        if (dto.getProductId() != null) {
            entity.setProductId(dto.getProductId());
        }

        repository.save(entity);
    }

    /** ✅ 5. 삭제 */
    @Override
    public void deleteFinishedProductOutbound(Long outboundId) {
        repository.deleteById(outboundId);
    }

    /** ✅ 6. 히스토리 조회 */
    @Override
    public PageResponseDTO<FinishedProductOutboundDTO> getFinishedProductOutboundHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by(direction, sorter));

        Page<FinishedProductOutbound> result = repository.searchWithPaging(pageRequestDTO.getKeyword(), pageable);

        List<FinishedProductOutboundDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, FinishedProductOutboundDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<FinishedProductOutboundDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
