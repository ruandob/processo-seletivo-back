package com.processo.seletivo.core.service;

import com.processo.seletivo.core.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public class AbstractService<T> implements IService<T> {

    @Autowired
    protected CrudRepository<T, Long> repository;

    @Override
    public T save(T obj) throws CustomDuplicatedException {
        try {
            return repository.save(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomInternalException(e.getMessage());
        }
    }

    @Override
    public boolean delete(long id) throws GenericException {
        T obj = repository.findOne(id);
        if (obj != null) {
            try {
                repository.delete(id);
                return true;
            } catch (Exception e) {
                throw new CustomDependencyException(e.getMessage());
            }
        }

        throw new CustomNotFoundException(ExceptionMessageCode.MENSAGEM_NOT_FOUND);
    }

    @Override
    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    @Override
    public T findOne(long id) throws GenericException {
        T obj = repository.findOne(id);
        if (obj == null) {
            throw new CustomNotFoundException(ExceptionMessageCode.MENSAGEM_NOT_FOUND);
        }
        return obj;
    }

    @Override
    public boolean exists(long id) throws GenericException {
        T obj = repository.findOne(id);
        if (obj == null) {
            return false;
        }
        return true;
    }
}
