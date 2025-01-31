package com.safetynet.alerts.repositories;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonRepository extends BaseRepository<Person, PersonName> {

    PersonRepository() {
        super("persons", Person[].class);
    }

    @Override
    public Optional<Person> get(PersonName name) {
        return _models.stream().filter(p -> p.sameName(name)).findAny();
    }
}
