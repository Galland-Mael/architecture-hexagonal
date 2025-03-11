package mael.dev.api.swapi;

import mael.dev.api.swapi.model.SwapiResponse;
import mael.dev.army.Person;
import mael.dev.army.spi.PersonInventory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component
public class SwapiClient implements PersonInventory {
    private final RestTemplate restTemplate;

    @Value("${swapi.url}")
    private String baseUrl;

    public SwapiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
                .map(swapiPerson -> new Person(swapiPerson.name(), parseInt(swapiPerson.mass())))
                .toList();
    }

    private SwapiResponse getPersonFromSwapi(String nextUrl) {
        return restTemplate.getForObject(nextUrl, SwapiResponse.class);
    }
}
