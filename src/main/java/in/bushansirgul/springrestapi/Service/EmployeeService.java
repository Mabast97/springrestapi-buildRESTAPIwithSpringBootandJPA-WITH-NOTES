package in.bushansirgul.springrestapi.Service;

import in.bushansirgul.springrestapi.model.Employee;

import java.util.List;

public interface EmployeeService {

//    List<Employee> getEmployees() ;  // because of pagination topic, bellow is the modification of it for the pagination topic:

    List<Employee> getEmployees(int pageNumber, int pageSize);

    Employee saveEmployee(Employee employee) ;

    Employee getSimpleEmployee(Long id);

    void deleteEmployee(Long id);

    Employee updateEmployee(Employee employee);

    /////////////////////////////////////////////////////////

    List<Employee> getEmployeesByName(String name);

    List<Employee> getEmployeesByNameAndLocation(String name, String location);

    List<Employee> getEmployeesByKeyword(String keyword);


    ////////////////////////////// JPQl /////////////////////////////////

    List<Employee> getEmployeesByNameOrLocation(String name, String location);

    Integer deleteByEmployeeName(String name) ;

}
