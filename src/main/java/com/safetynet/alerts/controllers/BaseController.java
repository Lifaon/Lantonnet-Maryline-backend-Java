package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.Identifier;
import com.safetynet.alerts.repositories.BaseRepository;
import com.safetynet.alerts.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
public abstract class BaseController <Model extends Identifier<T>, T, Service extends BaseService<Model, T, ? extends BaseRepository<Model, T>>> {

    protected final Service _service;

    @PostMapping
    public void create(@Validated @RequestBody Model model) {
        _service.create(model);
    }

    @PutMapping
    public void edit(@Validated @RequestBody Model model) {
        _service.edit(model);
    }

// Can't define this here because of FirestationController's weird delete endpoint
//    @DeleteMapping
//    public void delete(@Validated T param) {
//        _service.delete(param);
//    }
}
