package mael.dev.api.swapi;

import mael.dev.api.swapi.model.SwapiPerson;
import mael.dev.api.swapi.model.SwapiResponse;
import mael.dev.army.Person;
import mael.dev.army.spi.PersonInventory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.lang.Integer.parseInt;

@Component
public class SwapiClient implements PersonInventory {

    private final RestTemplate restTemplate;

    public SwapiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Value("${swapi.url}")
    private String baseUrl;


    @Override
    public List<Person> fetchPersons() {
        List<Person> persons = new ArrayList<>();
        String nextUrl = baseUrl + "/people";
        while (nextUrl != null) {
            SwapiResponse response = getPersonFromSwapi(nextUrl);
            persons.addAll(convertSwapiResponseToPersons(response));
            nextUrl = response.next();
        }
        return persons;
    }

    private List<Person>convertSwapiResponseToPersons(SwapiResponse response) {
        return response.results().stream()
                .filter(hasValidMass())
                .map(swapiPerson -> new Person(swapiPerson.name(), parseInt(swapiPerson.mass().replace(",", "").replace(".", ""))))
                .toList();
    }

    private Predicate<? super SwapiPerson> hasValidMass() {
        return swapiPerson -> !Objects.equals("unknown", swapiPerson.mass());
    }

    private SwapiResponse getPersonFromSwapi(String nextUrl) {
        return restTemplate.getForObject(nextUrl, SwapiResponse.class);
    }
}
