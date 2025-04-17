package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.domain.RawMaterialInbound;
import com.team1.team1project.dto.*;
import com.team1.team1project.repository.RawMaterialInboundRepository;
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
public class RawMaterialInboundServiceImpl implements RawMaterialInboundService {

    private final RawMaterialInboundRepository repository;
    private final ModelMapper modelMapper;

    /** ✅ 1. 페이징 + 검색 목록 */
    @Override
    public PageResponseDTO<RawMaterialInboundDTO> getPagedRawMaterialInbounds(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("inboundId").descending());

        // 검색 조건 적용 예시
        Page<RawMaterialInbound> result = repository.searchWithPaging(pageRequestDTO.getKeyword(), pageable);

        List<RawMaterialInboundDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, RawMaterialInboundDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<RawMaterialInboundDTO>builder() // 수정
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    /** ✅ 2. 상세 조회 */
    @Override
    public RawMaterialInboundDTO getRawMaterialInbound(Long inboundId) {
        RawMaterialInbound entity = repository.findById(inboundId).orElseThrow();
        return modelMapper.map(entity, RawMaterialInboundDTO.class);
    }

    /** ✅ 3. 등록 */
    @Override
    public void createRawMaterialInbound(RawMaterialInboundDTO dto) {
        RawMaterialInbound entity = modelMapper.map(dto, RawMaterialInbound.class);
        repository.save(entity);
    }

    /** ✅ 4. 수정 (상태 또는 전체) */
    @Override
    public void modifyInboundStatus(Long inboundId, RawMaterialInboundDTO dto) {
        RawMaterialInbound entity = repository.findById(inboundId).orElseThrow();

        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }

        if (dto.getInboundDate() != null) {
            entity.setInboundDate(dto.getInboundDate());
        }

        if (dto.getQuantity() != null) {
            entity.setQuantity(dto.getQuantity());
        }

        if (dto.getInboundCode() != null) {
            entity.setInboundCode(dto.getInboundCode());
        }

        if (dto.getMaterialCode() != null) {
            entity.setMaterialCode(dto.getMaterialCode());
        }

        repository.save(entity);
    }

    /** ✅ 5. 삭제 */
    @Override
    public void deleteRawMaterialInbound(Long inboundId) {
        repository.deleteById(inboundId);
    }

    /** ✅ 6. 히스토리 조회 */
    @Override
    public PageResponseDTO<RawMaterialInboundDTO> getRawMaterialInboundHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by(direction, sorter));

        Page<RawMaterialInbound> result = repository.searchWithPaging(pageRequestDTO.getKeyword(), pageable);

        List<RawMaterialInboundDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, RawMaterialInboundDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<RawMaterialInboundDTO>builder() // 수정
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
