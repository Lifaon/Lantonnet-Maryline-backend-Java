package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Identifier;
import com.safetynet.alerts.repositories.BaseRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseService <Model extends Identifier<T>, T, Repository extends BaseRepository<Model, T>> {

    protected final Repository _repository;

    public Optional<Model> get(T param) {
        return _repository.get(param);
    }

    public void create(Model p) {
        _repository.create(p);
    };

    public void edit(Model p) {
        _repository.edit(p);
    };

    public void delete(T param) {
        _repository.delete(param);
    };

}
