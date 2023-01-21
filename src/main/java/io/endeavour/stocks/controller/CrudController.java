package io.endeavour.stocks.controller;

import io.endeavour.stocks.StockException;
import io.endeavour.stocks.entity.PersonEntity;
import io.endeavour.stocks.entity.SectorEntity;
import io.endeavour.stocks.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/crud")
public class CrudController {
    private final CrudService crudService;
    @Autowired
    public CrudController(CrudService crudService) {
        this.crudService = crudService;
    }

    @GetMapping("/person")
    @Operation(description = "Gets Data from the PersonId and Address")
    public List<PersonEntity> getPersons(){
        return crudService.getPersons();
    }

    @PostMapping("/person")
    public PersonEntity savePerson(@RequestBody PersonEntity personEntity) {
        if (personEntity.getPersonID() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot do an update. Can only do insert");
        }
        return crudService.savePerson(personEntity);
    }

    @GetMapping("/person/{personID}")
    public ResponseEntity<PersonEntity> getPersons(@PathVariable("personID") Integer personID){
        return ResponseEntity.of(crudService.getPersons(personID));
    }

    @PutMapping("/person/{personID}")
    public void updatePerson(@PathVariable("personID") Integer personID,
                                     @RequestBody PersonEntity personEntity){
        crudService.updatePerson(personID, personEntity);
    }

    @DeleteMapping("/person/{personID}")
    public void deletePerson(@PathVariable("personID") Integer personID){
        crudService.deletePerson(personID);
    }

    @ExceptionHandler({StockException.class})
    public ResponseEntity<String> handleException(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @GetMapping("/sector")
    public List<SectorEntity> getSectorId(){
        return crudService.getSectorId();
    }

}
