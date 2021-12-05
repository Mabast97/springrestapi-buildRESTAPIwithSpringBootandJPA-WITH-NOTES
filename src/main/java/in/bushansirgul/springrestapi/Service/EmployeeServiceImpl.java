package in.bushansirgul.springrestapi.Service;

import in.bushansirgul.springrestapi.model.Employee;
import in.bushansirgul.springrestapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service  // To make the class a service class
public class EmployeeServiceImpl implements EmployeeService{

    /*
    private static List<Employee> list = new ArrayList<>();
    static {
        Employee e = new Employee();
        e.setName("Ahmed");
        e.setAge(30L);
        e.setDepartment("IT");
        e.setEmail("Ahmed@protonmail.com");
        e.setLocation("Iraq");
        list.add(e);

        e.setName("Mohammed");
        e.setAge(28L);
        e.setDepartment("Chemistry");
        e.setEmail("Mohammed@mail.com");
        e.setLocation("Poland");
        list.add(e);
    }
     */ // Because now we will fetch the values from the database from now on ...

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Override
//    public List<Employee> getEmployees() {
//       return employeeRepository.findAll();
//        //return list;
//    }
    // Because of PAGINATION TOPIC. bellow is the modification of it for the pagination topic :

    @Override
    public List<Employee> getEmployees(int pageNumber, int pageSize) {
//        Pageable pages = PageRequest.of(pageNumber, pageSize); // bellow line is the combination of paging and sorting :
        Pageable pages = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id");
        return employeeRepository.findAll(pages).getContent();  // .getContent() used in order to return a List<Employees>
//        return list;
    }


    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getSimpleEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent())
            return optionalEmployee.get();
        throw new RuntimeException("Employee is not found for the ID: "+id);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }




    ////////////////////////////////// Jpa finder methods : ////////////////////////////////////////

    @Override
    public List<Employee> getEmployeesByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public List<Employee> getEmployeesByNameAndLocation(String name, String location) {
        return employeeRepository.findByNameAndLocation(name, location);
    }

//    @Override
//    public List<Employee> getEmployeesByKeyword(String keyword) {
//        return employeeRepository.findByNameContaining(keyword);
//    }
    // Because of PAGINATION TOPIC. bellow is the modification of it for the pagination topic :

    @Override
    public List<Employee> getEmployeesByKeyword(String name) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");  // in the second argument, we will specify to sort it according to what
        return employeeRepository.findByNameContaining(name, sort);
    }


    ///////////////////////////////// JPQL ///////////////////////////////////////////////

    @Override
    public List<Employee> getEmployeesByNameOrLocation(String name, String location) {
        return employeeRepository.getEmployeesByNameAndLocation(name, location);
    }

    @Override
    public Integer deleteByEmployeeName(String name) {
        return employeeRepository.deleteEmployeeByName(name);
    }

}
