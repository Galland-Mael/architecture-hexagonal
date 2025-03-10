package com.example.demo.army.spi;


import com.example.demo.army.Person;

import java.util.List;

public interface PersonInventory {
    List<Person> fetchPersons();
}
