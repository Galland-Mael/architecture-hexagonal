package dev.army;

import mael.dev.army.Army;
import mael.dev.army.ArmyAssembler;
import mael.dev.army.Person;

import mael.dev.army.api.AssembledArmy;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class AssembledArmyFunctionalTest {

    @Test
    void isArmyStrongEnough() {
        //Given

        List<Person> persons = new ArrayList<>(asList(
                new Person("John", 100),
                new Person("Doe", 200),
                new Person("Jane", 300),
                new Person("Smith", 400)
        ));

        final Integer weightToCarry = 1000;

        final AssembledArmy assembleArmy = new ArmyAssembler(() -> persons);
        //When
        final Army army = assembleArmy.forWeight(weightToCarry);

        //Then
        System.out.println(army);
        assertThat(army.persons())
                .has(isStringEnoughToCarry(weightToCarry))
                .allMatch(hasCapacity());

    }

    private Predicate<? super Person> hasCapacity() {
        return person -> person.weight() > 0;
    }

    private Condition<? super List<? extends Person>> isStringEnoughToCarry(Integer weightToCarry) {
        return new Condition<>(
                person ->
                        person.stream()
                                .map(Person::weight)
                                .reduce(0, Integer::sum) >= weightToCarry,
                "weight check");
    }


}
