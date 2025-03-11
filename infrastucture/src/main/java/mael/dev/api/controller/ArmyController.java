package mael.dev.api.controller;

import mael.dev.api.request.ArmyRequest;
import mael.dev.army.Army;
import mael.dev.army.api.AssembleArmy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/army")
public class ArmyController {

    private final AssembleArmy assembleArmy;

    public ArmyController(AssembleArmy assembleArmy) {
        this.assembleArmy = assembleArmy;
    }

    @PostMapping
    public ResponseEntity<Army> assembleArmy(@RequestBody ArmyRequest armyRequest) throws URISyntaxException {
        return ResponseEntity.created(new URI(""))
                .body(assembleArmy.forWeight(armyRequest.weight));
    }
}
