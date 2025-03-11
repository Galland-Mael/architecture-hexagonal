package mael.dev.army.spi;



import mael.dev.army.Person;

import java.util.List;

public interface PersonInventory {
    List<Person> fetchPersons();
}
