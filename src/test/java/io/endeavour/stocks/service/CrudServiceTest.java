package io.endeavour.stocks.service;

import io.endeavour.stocks.StockException;
import io.endeavour.stocks.UnitTestBase;
import io.endeavour.stocks.entity.PersonEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class CrudServiceTest extends UnitTestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrudServiceTest.class);
    @Autowired
    private CrudService crudService;

    @Test
    void testUpdatePerson(){
        PersonEntity savePerson = createPerson();
        LOGGER.info("Saved person with id {}", savePerson.getPersonID());

        assertThrows(StockException.class, ()-> {
            crudService.updatePerson(savePerson.getPersonID() + 1, savePerson);
        });

        savePerson.setFirstName("changed");
        crudService.updatePerson(savePerson.getPersonID(), savePerson);

        Optional<PersonEntity> updatedPerson = crudService.getPersons(savePerson.getPersonID());
        assertTrue(updatedPerson.isPresent());
        assertEquals("changed", updatedPerson.get().getFirstName());

    }

    private PersonEntity createPerson() {
        PersonEntity person = new PersonEntity();
        person.setFirstName("test first name");
        person.setLastName("test last name");
        person.setDob(LocalDate.now().minusYears(30));
        PersonEntity savePerson = crudService.savePerson(person);
        return savePerson;
    }

    @Test
    void testDelete(){
        PersonEntity person = createPerson();
        crudService.deletePerson(person.getPersonID());

        Optional<PersonEntity> nonExistingPerson = crudService.getPersons(person.getPersonID());
        assertFalse(nonExistingPerson.isPresent());
    }

}