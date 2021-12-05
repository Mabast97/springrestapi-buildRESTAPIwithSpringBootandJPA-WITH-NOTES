package in.bushansirgul.springrestapi.repository;

import in.bushansirgul.springrestapi.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// we'll comment it because of the pagination and sorting subjects.
// public interface EmployeeRepository extends JpaRepository<Employee, Long> {

/*
When we have thousands or more records in our database, instead we are going to fetch all the records once at a time,
we're going to divide that records into multiple pages and each page contains a certain size or certain number of records.
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {


    // Now, we will talk about some of the jpa finder methods according different fields

    // we don't need to use the database column name, instead we should use the entity class field name for naming the methods.
    List<Employee> findByName(String name) ;

    // Select * from table where name="hi" and location="india"
    List<Employee> findByNameAndLocation(String name, String location);

    // Select * from table where name like "%bas%"
//    List<Employee> findByNameContaining(String keyword);
    // we commented if because of the pagination topic, bellow is the modification of it for the pagination topic :
    List<Employee> findByNameContaining(String keyword, Sort sort);


    ////////////////////////  JPQL  ////////////////////////////

    @Query("From Employee where name = :name Or location = :location")
    List<Employee> getEmployeesByNameAndLocation(String name, String location);

    @Transactional
    @Modifying  // for modifying queries, we should first use this annotation like (delete, update, create)
    @Query("delete from Employee where name = :name ")
    Integer deleteEmployeeByName(String name) ;


}
