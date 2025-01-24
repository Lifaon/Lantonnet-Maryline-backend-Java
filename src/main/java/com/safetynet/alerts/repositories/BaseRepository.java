package com.safetynet.alerts.repositories;

import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.models.Identifier;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository <Model extends Identifier<IdentifierType>, IdentifierType> {
    @Autowired
    private DBHandle _dbHandle;
    private final String _dbKey;
    private final Class<Model[]> _dbFmt;

    protected final List<Model> _models = new ArrayList<>();
    protected final Logger LOGGER = LogManager.getLogger(this);

    BaseRepository(String dbKey, Class<Model[]> dbFmt) {
        _dbKey = dbKey;
        _dbFmt = dbFmt;
    }

    @PostConstruct
    private void _init() {
        _dbHandle.get(_dbKey, _dbFmt).ifPresent(values -> {
            _models.addAll(Arrays.asList(values));
            LOGGER.debug("Imported {} {}", _models.size(), _dbKey);
        });
    }

    protected void _updateDB() {
        _dbHandle.set(_dbKey, _models);
    }

    public List<Model> getAll() {
        return Collections.unmodifiableList(_models);
    };

    public abstract Optional<Model> get(IdentifierType param);

    public void create(Model model) {
        LOGGER.debug("Start 'create()'");
        if (get(model.getIdentifier()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item already exists");
        }
        _models.add(model);
        _updateDB();
    };

    public void edit(Model model) {
        LOGGER.debug("Start 'edit()'");
        get(model.getIdentifier()).ifPresentOrElse(prev -> {
            _models.set(_models.indexOf(prev), model);
            _updateDB();
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item does not exist");
        });
    };

    public void delete(IdentifierType param) {
        LOGGER.debug("Start 'delete()'");
        get(param).ifPresentOrElse(model -> {
            _models.remove(model);
            _updateDB();
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Item already deleted");
        });
    };

}
