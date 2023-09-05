package tn.esb.lmad.flightPlannerAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esb.lmad.flightPlannerAPI.Domains.Aircraft;

//the repository is an interface that extends the JpaRepository interface or CrudRepository
// interface or PagingAndSortingRepository interface,...
//the JpaRepository interface is a generic interface that takes two parameters: the entity
// class and the type of the primary key of the entity class.
//the JpaRepository interface provides a lot of methods to perform CRUD operations or more
// on the entity class.
//examples of methods provided by the JpaRepository interface:
//save(), deleteById(), findById(), findAll(), count(), existsById(), ...
//if we want to get all aircrafts, we will call the findAll() method
//AircraftRepository repository;
//List<Aircraft> aircrafts = repository.findAll();
// <=> The ORM(Object Relational Mapping) execute SELECT * FROM aircrafts; on the database
//if we want to get an aircraft by its code, we will call the findById() method
//if we want to add an aircraft, we will call the save() method
//Aircraft a1= new Aircraft();
//a1.setCode("A1"); a1.setName("aaa"); a1.setDescription("gfhjkl");....
//repository.save(a1);<=> The ORM(Object Relational Mapping) execute INSERT INTO aircrafts values('A1','aaa','gfhjkl',...);
@Repository

public interface AircraftRepository extends JpaRepository<Aircraft,String> {
}
