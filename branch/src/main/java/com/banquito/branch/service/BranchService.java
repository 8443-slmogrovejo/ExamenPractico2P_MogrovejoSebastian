package com.banquito.branch.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.branch.exception.NotFoundException;
import com.banquito.branch.model.Branch;
import com.banquito.branch.repository.BranchRepository;

@Service
public class BranchService {
    
    public static final String ENTITY_NAME = "Branch";
    private final BranchRepository repository;

    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }

    public List<Branch> findAll() {
        return this.repository.findAll();
    }

    public Branch findById(String id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new NotFoundException(id, ENTITY_NAME));
    }

    public Branch create(Branch branch) {
        branch.setCreationDate(LocalDateTime.now());
        branch.setLastModifiedDate(LocalDateTime.now());
        return this.repository.save(branch);
    }

    public Branch update(Branch branch) {
        Branch branchDB = this.findById(branch.getId());
        branchDB.setPhoneNumber(branch.getPhoneNumber());
        branchDB.setLastModifiedDate(LocalDateTime.now());
        return this.repository.save(branchDB);
    }

    public void delete(String id) {
        Branch branch = this.findById(id);
        this.repository.delete(branch);
    }
}
