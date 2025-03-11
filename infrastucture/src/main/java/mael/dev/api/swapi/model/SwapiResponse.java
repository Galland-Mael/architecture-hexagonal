package mael.dev.api.swapi.model;

import java.util.List;

public record SwapiResponse(String next, List<SwapiPerson> results) {
}
