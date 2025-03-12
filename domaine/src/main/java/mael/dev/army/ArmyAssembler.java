package mael.dev.army;



import mael.dev.army.api.AssembleArmy;
import mael.dev.army.spi.PersonInventory;
import mael.dev.ddd.DomaineService;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

@DomaineService
public class ArmyAssembler implements AssembleArmy {
    private final PersonInventory personInventory;

    public ArmyAssembler(@Qualifier("swapiClient") PersonInventory personInventory) {
        this.personInventory = personInventory;
    }

    @Override
    public Army forWeight(Integer weightToCarry) {
        List<Person> persons = getPersons();
        List<Person> personsToCarry = getEnoughPersonsToCarry(weightToCarry, persons);
        return new Army(personsToCarry);
    }

    private List<Person> getEnoughPersonsToCarry(Integer weightToCarry, List<Person> persons) {
        List<Person> personsToCarry = new ArrayList<>();
        while (weightToCarry > 0 && !persons.isEmpty()) {
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
