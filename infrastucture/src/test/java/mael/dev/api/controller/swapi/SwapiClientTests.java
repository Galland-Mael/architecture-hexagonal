package mael.dev.api.controller.swapi;

import mael.dev.api.swapi.SwapiClient;
import mael.dev.ddd.Stub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import mael.dev.army.Person;
import mael.dev.army.spi.PersonInventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(SwapiClient.class)
class SwapiClientTests {

    @Autowired
    private PersonInventory swapiClient;
    @Autowired
    private MockRestServiceServer mockServer;

    @Value("classpath:__files/playloads/swapi-page1.json")
    private Resource swapiPage1;

    @Value("classpath:__files/playloads/swapi-page2.json")
    private Resource swapiPage2;

    @Test
    void should_return_persons() {
        configureMockServer();

        List<Person> persons = swapiClient.fetchPersons();

        assertThat(persons).containsExactly(
            new Person("Luke Skywalker", 77),
            new Person("C-3PO", 75),
            new Person("R2-D2", 32),
            new Person("Darth Vader", 136),
            new Person("Leia Organa", 49),
            new Person("Owen Lars", 120),
            new Person("Beru Whitesun lars", 75),
            new Person("R5-D4", 32),
            new Person("Biggs Darklighter", 84),
            new Person("Obi-Wan Kenobi", 77)
        );
    }

    private void configureMockServer() {
        this.mockServer
                .expect(method(HttpMethod.GET))
                .andExpect(requestTo("https://swapi.dev/api/people"))
                .andRespond(withSuccess(swapiPage1, MediaType.APPLICATION_JSON));

        this.mockServer
                .expect(method(HttpMethod.GET))
                .andExpect(requestTo("https://swapi.dev/api/people?page=2"))
                .andRespond(withSuccess(swapiPage2, MediaType.APPLICATION_JSON));
    }

    @TestConfiguration
    @ComponentScan(
            basePackageClasses = {Person.class},
            includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Stub.class})})
    static class StubConfiguration {
    }

}
