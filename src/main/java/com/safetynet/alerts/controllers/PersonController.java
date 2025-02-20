package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.services.PersonService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController extends BaseController<Person, PersonName, PersonService> {

    PersonController(PersonService service) {
        super(service);
    }

    @DeleteMapping
    public void delete(@Validated PersonName param) {
        _service.delete(param);
    }
}
