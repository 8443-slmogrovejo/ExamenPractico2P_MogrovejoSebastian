package com.banquito.branch.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.branch.controller.dto.BranchHolidayDto;
import com.banquito.branch.controller.mapper.BranchHolidayMapper;
import com.banquito.branch.exception.NotFoundException;
import com.banquito.branch.model.BranchHoliday;
import com.banquito.branch.service.BranchHolidayService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/branch-holidays")
@Slf4j
public class BranchHolidayController {

    private final BranchHolidayService service;
    private final BranchHolidayMapper mapper;

    public BranchHolidayController(BranchHolidayService service, BranchHolidayMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<BranchHolidayDto>> findAll() {
        List<BranchHoliday> holidays = this.service.findAll();
        List<BranchHolidayDto> dtos = new ArrayList<>(holidays.size());
        log.info("Se encontraron {} feriados", holidays.size());
        for(BranchHoliday holiday : holidays) {
            dtos.add(mapper.toDto(holiday));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<BranchHolidayDto>> findByBranch(@PathVariable("branchId") String branchId) {
        try {
            List<BranchHoliday> holidays = this.service.findByBranch(branchId);
            List<BranchHolidayDto> dtos = new ArrayList<>(holidays.size());
            log.info("Se encontraron {} feriados para la sucursal {}", holidays.size(), branchId);
            for(BranchHoliday holiday : holidays) {
                dtos.add(mapper.toDto(holiday));
            }
            return ResponseEntity.ok(dtos);
        } catch (NotFoundException e) {
            log.error("No se encontró la sucursal con id: {}", branchId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<BranchHolidayDto>> findByDate(@PathVariable("date") LocalDate date) {
        List<BranchHoliday> holidays = this.service.findByDate(date);
        List<BranchHolidayDto> dtos = new ArrayList<>(holidays.size());
        log.info("Se encontraron {} feriados para la fecha {}", holidays.size(), date);
        for(BranchHoliday holiday : holidays) {
            dtos.add(mapper.toDto(holiday));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/verify/{branchId}/{date}")
    public ResponseEntity<List<BranchHolidayDto>> verifyHoliday(
            @PathVariable("branchId") String branchId,
            @PathVariable("date") LocalDate date) {
        try {
            List<BranchHoliday> holidays = this.service.findByBranch(branchId);
            List<BranchHolidayDto> holidaysOnDate = new ArrayList<>();
            
            for(BranchHoliday holiday : holidays) {
                if(holiday.getDate().equals(date)) {
                    holidaysOnDate.add(mapper.toDto(holiday));
                }
            }
            
            if(holidaysOnDate.isEmpty()) {
                log.info("No hay feriados para la sucursal {} en la fecha {}", branchId, date);
                return ResponseEntity.notFound().build();
            }
            
            log.info("Se encontraron {} feriados para la sucursal {} en la fecha {}", 
                    holidaysOnDate.size(), branchId, date);
            return ResponseEntity.ok(holidaysOnDate);
            
        } catch (NotFoundException e) {
            log.error("No se encontró la sucursal con id: {}", branchId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BranchHolidayDto> create(@Valid @RequestBody BranchHolidayDto dto) {
        try {
            BranchHoliday holiday = this.service.create(this.mapper.toModel(dto));
            return ResponseEntity.ok(this.mapper.toDto(holiday));
        } catch (NotFoundException e) {
            log.error("No se encontró la sucursal asociada");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al crear el feriado", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchHolidayDto> update(
            @PathVariable("id") String id,
            @Valid @RequestBody BranchHolidayDto dto) {
        try {
            dto.setIdHoliday(id);
            BranchHoliday holiday = this.service.update(this.mapper.toModel(dto));
            return ResponseEntity.ok(this.mapper.toDto(holiday));
        } catch (NotFoundException e) {
            log.error("No se encontró el feriado con id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al actualizar el feriado", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/date/{date}")
    public ResponseEntity<Void> delete(@PathVariable("date") LocalDate date) {
        try {
            this.service.deleteByDate(date);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error("No se encontró ningún feriado para la fecha: {}", date);
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.notFound().build();
    }
}
