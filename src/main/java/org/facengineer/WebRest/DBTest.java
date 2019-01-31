package org.facengineer.WebRest;

import org.facengineer.DaoMapper.PersonModel;
import org.facengineer.Model.Person;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/db")
public class DBTest {
    private PersonModel personModel;

    public DBTest(PersonModel personModel) {
        this.personModel = personModel;
    }

    @RequestMapping("/")
    public String ForeachInsertValue() {
        List<Person> personlist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person person = new Person();
            person.setName("" + i);
            person.setPassword("" + i);
            person.setEmail("" + i);
            personlist.add(person);
        }
        personModel.insertPersonBatch(personlist);
        return "TRUE";
    }
}
