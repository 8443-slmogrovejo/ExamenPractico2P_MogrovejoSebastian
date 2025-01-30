package com.banquito.branch.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.branch.controller.dto.BranchDto;
import com.banquito.branch.model.Branch;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BranchMapper {
    
    BranchDto toDto(Branch model);
    Branch toModel(BranchDto dto);
}
