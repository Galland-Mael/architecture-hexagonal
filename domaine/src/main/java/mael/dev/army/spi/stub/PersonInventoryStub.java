package mael.dev.army.spi.stub;

import mael.dev.army.Person;
import mael.dev.army.spi.PersonInventory;
import mael.dev.ddd.Stub;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Stub
public class PersonInventoryStub implements PersonInventory {
    private List<Person> persons;

    private static final List<Person> DEFAULT_PERSON = new ArrayList<>(asList(
            new Person("John", 100),
            new Person("Doe", 200),
            new Person("Jane", 300),
            new Person("Smith", 400)
    ));

    public PersonInventoryStub() {
        this(DEFAULT_PERSON);
    }

    public PersonInventoryStub(List<Person> persons) {
        this.persons = persons;
    }


    @Override
    public List<Person> fetchPersons() {
        return this.persons;
    }
}
