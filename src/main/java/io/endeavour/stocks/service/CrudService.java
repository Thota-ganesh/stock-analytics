package io.endeavour.stocks.service;

import io.endeavour.stocks.StockException;
import io.endeavour.stocks.entity.AddressEntity;
import io.endeavour.stocks.entity.PersonEntity;
import io.endeavour.stocks.entity.SectorEntity;
import io.endeavour.stocks.repository.AddressRepository;
import io.endeavour.stocks.repository.PersonRepository;
import io.endeavour.stocks.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CrudService {
    private final PersonRepository personRepository;
    private final SectorRepository sectorRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CrudService(PersonRepository personRepository, SectorRepository sectorRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.sectorRepository = sectorRepository;
        this.addressRepository = addressRepository;
    }

    public List<PersonEntity> getPersons(){
        return personRepository.findAll();
    }

    public PersonEntity savePerson(PersonEntity personEntity) {
        Optional.ofNullable(personEntity.getAddressEntities())
                .ifPresent(addressEntities -> {
                    addressEntities.forEach(addressEntity -> addressEntity.setPersonEntity(personEntity));
                });
        return personRepository.save(personEntity);
    }

    public List<SectorEntity> getSectorId(){
        return sectorRepository.findAll();
    }

    public Optional<PersonEntity> getPersons(Integer personID) {
        return personRepository.findById(personID);
    }

    public void updatePerson(Integer personID, PersonEntity personEntity) {
        boolean isIdExists = personRepository.existsById(personID);
        if (!isIdExists || !Objects.equals(personID,personEntity.getPersonID())){
            throw new StockException("Person does not Exist");
        }
            if (personEntity.getAddressEntities() == null) {
                personEntity.setAddressEntities(Collections.emptyList());
            }
        savePerson(personEntity);
    }

    public void deletePerson(Integer personID) {
        personRepository.deleteById(personID);
    }

    public List<PersonEntity> getPersonDetails(String query) {
        List<PersonEntity> personEntityList = personRepository.getPersonDetails(query);
        List<AddressEntity> addressEntityList = addressRepository.getAddressDetails(query);
        List<PersonEntity> personAddressEntities = new ArrayList<>();

        personAddressEntities.addAll(personEntityList);
        addressEntityList.forEach(addressEntity -> {
            personAddressEntities.add(addressEntity.getPersonEntity());
        });

        return personAddressEntities;
    }
}
