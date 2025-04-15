package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.domain.RawMaterialOutbound;
import com.team1.team1project.dto.*;
import com.team1.team1project.repository.RawMaterialOutboundRepository;
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
public class RawMaterialOutboundServiceImpl implements RawMaterialOutboundService {

    private final RawMaterialOutboundRepository repository;
    private final ModelMapper modelMapper;

    /** ✅ 1. 페이징 + 검색 목록 */
    @Override
    public PageResponseDTO<RawMaterialOutboundDTO> getPagedRawMaterialOutbounds(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("outboundId").descending());

        // 검색 조건 적용 예시
        Page<RawMaterialOutbound> result = repository.searchWithPaging(pageRequestDTO.getKeyword(), pageable);

        List<RawMaterialOutboundDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, RawMaterialOutboundDTO.class))
                .collect(Collectors.toList());

        // ✅ 빌더에 pageRequestDTO, dtoList, total만 전달
        return PageResponseDTO.<RawMaterialOutboundDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    /** ✅ 2. 상세 조회 */
    @Override
    public RawMaterialOutboundDTO getRawMaterialOutbound(Long outboundId) {
        RawMaterialOutbound entity = repository.findById(outboundId).orElseThrow();
        return modelMapper.map(entity, RawMaterialOutboundDTO.class);
    }

    /** ✅ 3. 등록 */
    @Override
    public void createRawMaterialOutbound(RawMaterialOutboundDTO dto) {
        RawMaterialOutbound entity = modelMapper.map(dto, RawMaterialOutbound.class);
        repository.save(entity);
    }

    /** ✅ 4. 수정 (상태 또는 전체) */
    @Override
    public void modifyOutboundStatus(Long outboundId, RawMaterialOutboundDTO dto) {
        RawMaterialOutbound entity = repository.findById(outboundId).orElseThrow();

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

        if (dto.getWarehouse() != null) {
            entity.setWarehouse(dto.getWarehouse());
        }

        repository.save(entity);
    }

    /** ✅ 5. 삭제 */
    @Override
    public void deleteRawMaterialOutbound(Long outboundId) {
        repository.deleteById(outboundId);
    }

    /** ✅ 6. 히스토리 조회 */
    @Override
    public PageResponseDTO<RawMaterialOutboundDTO> getRawMaterialOutboundHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by(direction, sorter));

        Page<RawMaterialOutbound> result = repository.searchWithPaging(pageRequestDTO.getKeyword(), pageable);

        List<RawMaterialOutboundDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, RawMaterialOutboundDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<RawMaterialOutboundDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
