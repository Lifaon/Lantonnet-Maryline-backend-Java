package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService extends BaseService<Person, PersonName, PersonRepository> {

    PersonService(PersonRepository repository) {
        super(repository);
    }

    public List<Person> getPeopleByAddress(String address) {
        return _repository.getAll().stream().filter(
                person -> person.getAddress().equals(address)
        ).toList();
    }

    public Set<String> getCommunityEmail(String city) {
        return _repository.getAll().stream().filter(
                person -> person.getCity().equals(city)
        ).map(
                Person::getEmail
        ).collect(Collectors.toSet());
    }
}

