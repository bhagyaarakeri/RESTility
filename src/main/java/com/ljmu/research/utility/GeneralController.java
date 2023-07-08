package com.ljmu.research.utility;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class GeneralController<DTO, PrimaryDatatype, Service extends GeneralService, Repository extends JpaRepository<EntityClass, PrimaryDatatype>, EntityClass> {

    @Autowired
    private Service service;

    private final Class<DTO> dtoClass;

    private final Class<EntityClass> entityClass;

    public GeneralController(Class<DTO> dtoClass, Class<EntityClass> entityClass) throws ClassNotFoundException, NoSuchMethodException {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    @PostConstruct
    public void initialize() throws ClassNotFoundException, NoSuchMethodException {
        setDtoAndEntityClass();
    }

    public void setDtoAndEntityClass() throws ClassNotFoundException, NoSuchMethodException {
        ((GeneralService)service).setDtoAndEntityClass(dtoClass, entityClass);
    }

    public GeneralController(Class<DTO> dtoClass, Class<EntityClass> entityClass, Service service) throws ClassNotFoundException, NoSuchMethodException {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DTO> findById(@PathVariable PrimaryDatatype id) {
        return new ResponseEntity<>((DTO) service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public <C extends DTO> ResponseEntity<C> save(@RequestBody C dto) {
        return new ResponseEntity<>((C) service.save(dto, dtoClass, entityClass), HttpStatus.CREATED);
    }

    @PostMapping(headers = "action=save-all")
    public <C extends DTO> ResponseEntity<List<C>> saveAll(@RequestBody List<C> dtos) {
        return new ResponseEntity<>(service.saveAll(dtos, dtoClass, entityClass), HttpStatus.CREATED);
    }

    @PutMapping
    public <C extends DTO> ResponseEntity<C> update(@RequestBody C dto) {
        return new ResponseEntity<>((C) service.update(dto, dtoClass, entityClass), HttpStatus.OK);
    }

    @PutMapping(headers = "action=update-all")
    public <C extends DTO> ResponseEntity<List<C>> updateAll(@RequestBody List<C> dtos) {
        return new ResponseEntity<>(service.updateAll(dtos, dtoClass, entityClass), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable PrimaryDatatype id) {
        service.delete(id);
        return new ResponseEntity<>("Resource with id "+id+" deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping
    public <C extends DTO> ResponseEntity<String> deleteAll(@RequestBody List<C> dtos) {
        service.deleteAll(dtos);
        return new ResponseEntity<>("Resources deleted successfully", HttpStatus.OK);
    }

}
