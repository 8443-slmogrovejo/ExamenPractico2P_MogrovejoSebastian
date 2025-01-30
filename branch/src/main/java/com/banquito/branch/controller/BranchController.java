package com.banquito.branch.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.branch.controller.dto.BranchDto;
import com.banquito.branch.controller.mapper.BranchMapper;
import com.banquito.branch.exception.NotFoundException;
import com.banquito.branch.model.Branch;
import com.banquito.branch.service.BranchService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/branches")
@Slf4j
public class BranchController {

    private final BranchService service;
    private final BranchMapper mapper;

    public BranchController(BranchService service, BranchMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<BranchDto>> findAll() {
        List<Branch> branches = this.service.findAll();
        List<BranchDto> dtos = new ArrayList<>(branches.size());
        log.info("Se encontraron {} sucursales", branches.size());
        for(Branch branch : branches) {
            dtos.add(mapper.toDto(branch));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> findById(@PathVariable("id") String id) {
        try {
            Branch branch = this.service.findById(id);
            log.info("Se encontró la sucursal con id: {}", id);
            return ResponseEntity.ok(this.mapper.toDto(branch));
        } catch (NotFoundException e) {
            log.error("No se encontró la sucursal con id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BranchDto> create(@Valid @RequestBody BranchDto dto) {
        try {
            Branch branch = this.service.create(this.mapper.toModel(dto));
            log.info("Se creó la sucursal con id: {}", branch.getId());
            return ResponseEntity.ok(this.mapper.toDto(branch));
        } catch (Exception e) {
            log.error("Error al crear la sucursal", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> update(
            @PathVariable("id") String id,
            @RequestBody BranchDto dto) {
        try {
            dto.setId(id);
            Branch branch = this.service.update(this.mapper.toModel(dto));
            return ResponseEntity.ok(this.mapper.toDto(branch));
        } catch (NotFoundException e) {
            log.error("No se encontró la sucursal con id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al actualizar la sucursal", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        try {
            this.service.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error("No se encontró la sucursal con id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.notFound().build();
    }
}
