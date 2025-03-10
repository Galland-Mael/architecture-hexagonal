package com.example.demo.army;


import com.example.demo.army.api.AssembleArmy;
import com.example.demo.army.spi.PersonInventory;

import java.util.ArrayList;
import java.util.List;

public class ArmyAssembler implements AssembleArmy {
    private PersonInventory personInventory;

    public ArmyAssembler(PersonInventory personInventory) {
        this.personInventory = personInventory;
    }

    @Override
    public Army forWeight(int weightToCarry) {
        List<Person> persons = getPersons();
        List<Person> personsToCarry = getEnoughPersonsToCarry(weightToCarry, persons);
        return new Army(personsToCarry);
    }

    private List<Person> getEnoughPersonsToCarry(int weightToCarry, List<Person> persons) {
        List<Person> personsToCarry = new ArrayList<>();
        while (weightToCarry > 0) {
            Person person = persons.removeFirst();
            personsToCarry.add(person);
            weightToCarry -= person.weight();
        }
        return personsToCarry;
    }

    private List<Person> getPersons() {
        return personInventory.fetchPersons();
    }
}
