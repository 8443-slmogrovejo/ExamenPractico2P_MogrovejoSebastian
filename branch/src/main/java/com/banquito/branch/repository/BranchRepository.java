package com.banquito.branch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.branch.model.Branch;

@Repository
public interface BranchRepository extends MongoRepository<Branch, String> {
}
