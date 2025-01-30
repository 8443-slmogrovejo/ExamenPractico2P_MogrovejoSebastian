package com.banquito.branch.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.branch.exception.NotFoundException;
import com.banquito.branch.model.Branch;
import com.banquito.branch.model.BranchHoliday;
import com.banquito.branch.repository.BranchHolidayRepository;

@Service
public class BranchHolidayService {
    
    public static final String ENTITY_NAME = "BranchHoliday";
    private final BranchHolidayRepository repository;
    private final BranchService branchService;

    public BranchHolidayService(BranchHolidayRepository repository, BranchService branchService) {
        this.repository = repository;
        this.branchService = branchService;
    }

    public List<BranchHoliday> findAll() {
        return this.repository.findAll();
    }

    public BranchHoliday findById(String id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new NotFoundException(id, ENTITY_NAME));
    }

    public List<BranchHoliday> findByBranch(String branchId) {
        Branch branch = this.branchService.findById(branchId);
        return this.repository.findByIdBranch(branch);
    }

    public List<BranchHoliday> findByDate(LocalDate date) {
        return this.repository.findByDate(date);
    }

    public BranchHoliday create(BranchHoliday holiday) {
        if (holiday.getIdBranch() != null) {
            this.branchService.findById(holiday.getIdBranch().getId());
        }
        return this.repository.save(holiday);
    }

    public BranchHoliday update(BranchHoliday holiday) {
        BranchHoliday holidayDB = this.findById(holiday.getIdHoliday());
        holidayDB.setDate(holiday.getDate());
        holidayDB.setName(holiday.getName());
        return this.repository.save(holidayDB);
    }

    public void delete(String id) {
        BranchHoliday holiday = this.findById(id);
        this.repository.delete(holiday);
    }
}
