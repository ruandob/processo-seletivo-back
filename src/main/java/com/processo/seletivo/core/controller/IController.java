package com.processo.seletivo.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IController<T> {

    ResponseEntity<?> findAll();

    ResponseEntity<?> findById(@PathVariable long id);

    ResponseEntity<?> save(@Validated @RequestBody T object);

    ResponseEntity<?> update(@Validated @RequestBody T object);

    ResponseEntity<?> delete(@PathVariable long id);

}
