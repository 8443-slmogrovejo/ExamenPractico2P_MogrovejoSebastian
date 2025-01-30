package com.banquito.branch.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.branch.model.Branch;
import com.banquito.branch.model.BranchHoliday;

@Repository
public interface BranchHolidayRepository extends MongoRepository<BranchHoliday, String> {
    List<BranchHoliday> findByIdBranch(Branch branch);
    List<BranchHoliday> findByDate(LocalDate date);
}
