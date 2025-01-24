package com.safetynet.alerts.repositories;

import com.safetynet.alerts.DBHandle;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BaseRepository <Model> {
    @Autowired
    private DBHandle _dbHandle;
    private final String _dbKey;
    private final Class<Model[]> _dbFmt;

    protected final List<Model> _models = new ArrayList<>();
    protected final Logger LOGGER;

    BaseRepository(String dbKey, Class<Model[]> dbFmt) {
        _dbKey = dbKey;
        _dbFmt = dbFmt;
        LOGGER = LogManager.getLogger(this);
    }

    @PostConstruct
    private void _init() {
        _dbHandle.get(_dbKey, _dbFmt).ifPresent(values -> {
            _models.addAll(Arrays.asList(values));
            LOGGER.debug("Imported {} {}", _models.size(), _dbKey);
        });
    }

    public List<Model> getAll() {
        return Collections.unmodifiableList(_models);
    };

    protected void _updateDB() {
        _dbHandle.set(_dbKey, _models);
    }
}
