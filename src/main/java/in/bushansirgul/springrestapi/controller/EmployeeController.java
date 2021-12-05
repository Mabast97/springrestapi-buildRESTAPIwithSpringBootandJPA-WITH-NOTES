package in.bushansirgul.springrestapi.controller;

import in.bushansirgul.springrestapi.Service.EmployeeService;
import in.bushansirgul.springrestapi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// By adding this @Controller annotation, this class will be responsible for handling http request.
//@Controller  instead, we can use @RestController.
@RestController  // @Controller + @ResponseBody
// @RequestMapping("/api/v1")  // if we want to change the url. like (localhost:8087/api/v1/employee2) // But in that way,
// if we add another controller, it isn't contain this until we specify it on the class. So, the alternate way is that in the
// application.properties we add (server.servlet.context-path=/api/v1).
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/version")
    public String getAppDetails() {
        return appName + " - " + appVersion;
    }

    // For mapping the url, we should use:
    @GetMapping("/employees2")  // or by this way :  @RequestMapping(value = "/employees", method = RequestMethod.GET)

    // Everytime when we make a http request, we should expect the http response. So, in order to return http response for this
    // method, we should use @RespnseBody
//    @ResponseBody   Here, we don't need to write it anymore, because we used @RestController. So, it means that if we
    // used @Controller, then we should use @ResponseBody on each of the methods that we want to have a response.
    public String getEmployees() {  //http://localhost:8087/employees2
        return "desplaying the list of Employees !!! " ;
    }

    // How to pass data from client to server
    @GetMapping("/employees2/{id}")
    public String getEmployees(@PathVariable("id") Long id) {  // http://localhost:8087/employees2/5
        return "Fetching the employee details for the Id: " + id  ;
    }

    @DeleteMapping("/employees2")  // localhost:8087/employees?id=30
    public String deleteEmployees(@RequestParam("id") Long id) {
        return "Deleting the employee details for the Id : " + id;
    }

    // Mapping JSON data to the Java Object

    @PostMapping("/employees2")
    public String saveEmployee (@RequestBody Employee employee) {
        return "saving the employee details inside the db " + employee;
    }

    @PutMapping("/employees2/{id}")
    public Employee updateEmployee (@PathVariable("id") Long id, @RequestBody Employee employee) { // in order to receive Employee object, we use @RequestBody
        System.out.println("updating the employee data for id "+ id );
        return employee;
    }

    // ...................... Updated and from now on, we will use the database  ..............................


    // When the client do the CRUD operations, we should send for him/her the status and something in order to know that
    // the things happened. So, I commented the original codes and bellow each method I created the updated one for that purpose.


//    @GetMapping("/listemployees2")
//    public List<Employee> getListEmployeesWithDb() {  //http://localhost:8087/listemployees2
//        return employeeService.getEmployees() ;
//    }
    // watch bellow for the changes :

    /*
    // ResponseEntity is not only returning the data, also contains the other information like http status and header and so on ...
    @GetMapping("/listemployees2")
    public ResponseEntity<List<Employee>> getListEmployeesWithDb() {  //http://localhost:8087/listemployees2
        return new ResponseEntity<List<Employee>>(employeeService.getEmployees(), HttpStatus.OK) ;
                                                // ( data ,  status )
    }

     Commented because of the pagination topic.

     */

//    @PostMapping("/employeeswithdb")
//    public Employee saveEmployeeWithDb(@Valid @RequestBody Employee employee) {
//        // @Valid for validation. whenever the user sends a null value, before it's been bind into the employee object, it's going to throw an exception
//        return employeeService.saveEmployee(employee);
//    }
    // watch bellow for the changes :

    @PostMapping("/employeeswithdb")
    public ResponseEntity<Employee> saveEmployeeWithDb(@Valid @RequestBody Employee employee) {
        // @Valid for validation. whenever the user sends a null value, before it's been bind into the employee object, it's going to throw an exception
        return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

//    @GetMapping("/employeeswithdb/{id}")
//    public Employee getEmployeeWithDb(@PathVariable("id") Long id) {  // http://localhost:8087/employees2/5
//        return employeeService.getSimpleEmployee(id)  ;
//    }
    // watch bellow for the changes :

    @GetMapping("/employeeswithdb/{id}")
    public ResponseEntity<Employee> getEmployeeWithDb(@PathVariable("id") Long id) {  // http://localhost:8087/employees2/5
        return new ResponseEntity<Employee>(employeeService.getSimpleEmployee(id), HttpStatus.OK)  ;
    }

//    @DeleteMapping("/employeeswithdb")  // localhost:8087/employees?id=30
//    public void deleteEmployeesWithDb(@RequestParam("id") Long id) {
//        employeeService.deleteEmployee(id);
//    }
    // watch bellow for the changes :

    @DeleteMapping("/employeeswithdb")  // localhost:8087/employees?id=30
    public ResponseEntity<HttpStatus> deleteEmployeesWithDb(@RequestParam Long id) {
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);  // NO_CONTENT is used for delete .
    }

//    @PutMapping("/employeeswithdbupdate/{id}")
//    public Employee updateEmployeeWithDb (@PathVariable("id") Long id, @RequestBody Employee employee) { // in order to receive Employee object, we use @RequestBody
//        employee.setId(id);
//        return employeeService.updateEmployee(employee);
//    }
    // watch bellow for the changes :

    @PutMapping("/employeeswithdbupdate/{id}")
    public ResponseEntity<Employee> updateEmployeeWithDb (@PathVariable("id") Long id, @RequestBody Employee employee) { // in order to receive Employee object, we use @RequestBody
        employee.setId(id);
        return new ResponseEntity<Employee>(employeeService.updateEmployee(employee), HttpStatus.OK);
    }

///////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/employees/filterByName")
    public ResponseEntity<List<Employee>> getEmployeesByName(@RequestParam String name) {
        return new ResponseEntity<List<Employee>>(employeeService.getEmployeesByName(name), HttpStatus.OK) ;
    }

    @GetMapping("/employees/filterByNameAndLocation")
    public ResponseEntity<List<Employee>> getEmployeesByNameAndLocation(@RequestParam String name,@RequestBody String location) {
        return new ResponseEntity<List<Employee>>(employeeService.getEmployeesByNameAndLocation(name, location), HttpStatus.OK);
    }

    @GetMapping("/employees/filterByKeyword")
    public ResponseEntity<List<Employee>> getEmployeeByKeyword (@RequestParam String keyword) {
        return new ResponseEntity<List<Employee>>(employeeService.getEmployeesByKeyword(keyword), HttpStatus.OK );
    }



    //////////////////////////// PAGINATION ////////////////////////////////////////


    @GetMapping("/employeesWithPagination")  // http://localhost:8087/employeesWithPagination?pageNumber=0&pageSize=4
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return  new ResponseEntity<List<Employee>>(employeeService.getEmployees(pageNumber, pageSize), HttpStatus.OK);
    }

    //////////////////////////// JPQL ////////////////////////////////////////
    @GetMapping("/employees/{name}/{location}")  // http://localhost:8087/employeesWithPagination?pageNumber=0&pageSize=4
    public ResponseEntity<List<Employee>> getEmployeesByNameOrLocation(@PathVariable String name, @PathVariable String location) {
        return new ResponseEntity<List<Employee>>(employeeService.getEmployeesByNameOrLocation(name, location), HttpStatus.OK);
    }

    @DeleteMapping("/employees/delete/{name}")  // http://localhost:8087/employeesWithPagination?pageNumber=0&pageSize=4
    public ResponseEntity<String> deleteEmployeeByName(@PathVariable String name) {
        return new ResponseEntity<String>(employeeService.deleteByEmployeeName(name) + " no. of records deleted", HttpStatus.OK);
    }




}
