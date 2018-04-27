package com.processo.seletivo.core.service;

import org.springframework.stereotype.Component;

import java.util.List;

public interface IService<T> {

    public T save(T obj);

    public boolean delete(long id);

    public List<T> findAll();

    public T findOne(long id);

    public boolean exists(long id);

}
