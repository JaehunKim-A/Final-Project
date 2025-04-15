package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.domain.FinishedProductInbound;
import com.team1.team1project.dto.*;
import com.team1.team1project.repository.FinishedProductInboundRepository;
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
public class FinishedProductInboundServiceImpl implements FinishedProductInboundService {

    private final FinishedProductInboundRepository repository;
    private final ModelMapper modelMapper;

    /** ✅ 1. 페이징 + 검색 목록 */
    @Override
    public PageResponseDTO<FinishedProductInboundDTO> getPagedFinishedProductInbounds(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("inboundId").descending());

        // 검색 조건 적용 예시
        Page<FinishedProductInbound> result = repository.searchWithPaging(pageRequestDTO.getKeyword(), pageable);

        List<FinishedProductInboundDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, FinishedProductInboundDTO.class))
                .collect(Collectors.toList());

        // ✅ 빌더에 pageRequestDTO, dtoList, total만 전달
        return PageResponseDTO.<FinishedProductInboundDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    /** ✅ 2. 상세 조회 */
    @Override
    public FinishedProductInboundDTO getFinishedProductInbound(Long inboundId) {
        FinishedProductInbound entity = repository.findById(inboundId).orElseThrow();
        return modelMapper.map(entity, FinishedProductInboundDTO.class);
    }

    /** ✅ 3. 등록 */
    @Override
    public void createFinishedProductInbound(FinishedProductInboundDTO dto) {
        FinishedProductInbound entity = modelMapper.map(dto, FinishedProductInbound.class);
        repository.save(entity);
    }

    /** ✅ 4. 수정 (상태 또는 전체) */
    @Override
    public void modifyInboundStatus(Long inboundId, FinishedProductInboundDTO dto) {
        FinishedProductInbound entity = repository.findById(inboundId).orElseThrow();

        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }

        if (dto.getCompleteTime() != null) {
            entity.setCompleteTime(dto.getCompleteTime());
        }

        if (dto.getQuantity() != null) {
            entity.setQuantity(dto.getQuantity());
        }

        if (dto.getInboundCode() != null) {
            entity.setInboundCode(dto.getInboundCode());
        }

        if (dto.getSupplierId() != null) {
            entity.setSupplierId(dto.getSupplierId());
        }

        repository.save(entity);
    }

    /** ✅ 5. 삭제 */
    @Override
    public void deleteFinishedProductInbound(Long inboundId) {
        repository.deleteById(inboundId);
    }

    /** ✅ 6. 히스토리 조회 */
    @Override
    public PageResponseDTO<FinishedProductInboundDTO> getFinishedProductInboundHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by(direction, sorter));

        Page<FinishedProductInbound> result = repository.searchWithPaging(pageRequestDTO.getKeyword(), pageable);

        List<FinishedProductInboundDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, FinishedProductInboundDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<FinishedProductInboundDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
