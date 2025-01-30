package com.banquito.branch.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.branch.controller.dto.BranchHolidayDto;
import com.banquito.branch.model.Branch;
import com.banquito.branch.model.BranchHoliday;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BranchHolidayMapper {

    @Mapping(target = "idBranch", source = "idBranch.id")
    BranchHolidayDto toDto(BranchHoliday model);

    @Mapping(target = "idBranch", source = "idBranch")
    BranchHoliday toModel(BranchHolidayDto dto);

    default Branch mapBranch(String idBranch) {
        if (idBranch == null) {
            return null;
        }
        Branch branch = new Branch();
        branch.setId(idBranch);
        return branch;
    }
}
