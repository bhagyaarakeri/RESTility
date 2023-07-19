package com.ljmu.restility.utility;


import com.ljmu.restility.configuration.SkipNullAndDefaultCondition;
import com.ljmu.restility.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralService<DTO, PrimaryDatatype, Repository extends JpaRepository<EntityClass, PrimaryDatatype>, EntityClass>{
    @Autowired
    private Repository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    private Class<DTO> dtoClass;

    private Class<EntityClass> entityClass;

    public void setDtoAndEntityClass(Class<DTO> dtoClass, Class<EntityClass> entityClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }


    public List<DTO> findAll() {
        return (List<DTO>) repository.findAll();
    }


    public DTO findById(PrimaryDatatype id) {
        return (DTO) repository.findAllById(Arrays.asList(id));
    }


    public <C extends DTO> C save(C dto, Class<DTO> dtoClass, Class<EntityClass> entityClass) {
        EntityClass entityObj = (EntityClass) modelMapper.map(dto, entityClass);
        EntityClass savedObj = repository.save(entityObj);
        return (C) modelMapper.map(savedObj, dtoClass);
    }


    public <C extends DTO> List<C> saveAll(List<C> dtos, Class<DTO> dtoClass, Class<EntityClass> entityClass) {
        List<EntityClass> entities = new ArrayList<>();
        for(C dto : dtos){
            entities.add((EntityClass) modelMapper.map(dto, entityClass));
        }
        List<EntityClass> savedObjs = repository.saveAll(entities);
        List<C> savedEntities = new ArrayList<>();
        for(EntityClass savedObj : savedObjs){
            savedEntities.add((C) modelMapper.map(savedObj, dtoClass));
        }
        return savedEntities;
    }


    public <C extends DTO> C update(C dto, Class<DTO> dtoClass, Class<EntityClass> entityClass) {
        EntityClass entityObj = (EntityClass) modelMapper.map(dto, entityClass);

        // Get the JPA entity manager from the repository
        JpaEntityInformation<EntityClass, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation(entityClass, entityManager);
        PrimaryDatatype primaryId = (PrimaryDatatype) entityInformation.getId(entityObj);

        if(null == primaryId) throw new ResourceNotFoundException("Resource not found with given primary key");

        // Find the existing entity by the primary key
        EntityClass existingEntity = repository.findById(primaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + primaryId));

        // Merge the changes from the DTO into the existing entity
        modelMapper.getConfiguration().setPropertyCondition(new SkipNullAndDefaultCondition());
        modelMapper.map(dto, existingEntity);

        EntityClass savedObj = repository.save(existingEntity);
        return (C) modelMapper.map(savedObj, dtoClass);
    }


    @Transactional
    public <C extends DTO> List<C> updateAll(List<C> dtos, Class<DTO> dtoClass, Class<EntityClass> entityClass) {
        List<C> result = new ArrayList<>();
        if(null != dtos && dtos.size() > 0){
            for(C dto : dtos){
                EntityClass entityObj = (EntityClass) modelMapper.map(dto, entityClass);

                // Get the JPA entity manager from the repository
                JpaEntityInformation<EntityClass, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation(entityClass, entityManager);
                PrimaryDatatype primaryId = (PrimaryDatatype) entityInformation.getId(entityObj);

                if(null == primaryId) throw new ResourceNotFoundException("Resource not found with given primary key");

                // Find the existing entity by the primary key
                EntityClass existingEntity = repository.findById(primaryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + primaryId));

                // Merge the changes from the DTO into the existing entity
                modelMapper.getConfiguration().setPropertyCondition(new SkipNullAndDefaultCondition());
                modelMapper.map(dto, existingEntity);

                EntityClass savedObj = repository.save(existingEntity);
                result.add((C) modelMapper.map(savedObj, dtoClass));
            }
        }
        return result;
    }


    public void delete(PrimaryDatatype id) {
        repository.deleteById(id);
    }


    public void deleteAll(List<DTO> dtos) {
        repository.deleteAll((Iterable<? extends EntityClass>) dtos);
    }

}
