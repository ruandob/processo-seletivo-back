package com.processo.seletivo.core.controller;

import com.processo.seletivo.core.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public class AbstractController<T> extends ResponseAbstractController implements IController<T> {

    @Autowired
    IService<T> service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        return jsonResponse(service.findAll());
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return jsonResponse(service.findOne(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@Validated @RequestBody T object) {
        return jsonResponse(service.save(object));
    }

    @Override
    @PutMapping
    public ResponseEntity<?> update(@Validated @RequestBody T object) {
        return jsonResponse(service.save(object));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return jsonResponse(null);
    }
}
