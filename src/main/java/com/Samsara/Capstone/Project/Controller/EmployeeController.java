package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.Employee;
import com.Samsara.Capstone.Project.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/Employee")
public class EmployeeController {
    final public EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService= employeeService;
    }
    @GetMapping(value = "/display-Register")
    public String getRegisterPage(Model model){
        model.addAttribute("employee",new Employee());
        return "EmployeeRegister";
    }
    @PostMapping(value = "/create")
    public String createUser(@ModelAttribute Employee employee, Model model) {
        if (employeeService.userNameExists(employee.getUsername())) {
            model.addAttribute("errorMessage", "Username is already in use");
            return "EmployeeRegister";
        }

        if (employeeService.userEmailExists(employee.getEmail())) {
            model.addAttribute("errorMessage", "Email is already in use");
            return "EmployeeRegister";
        }
        if (employeeService.userPhoneNumberExists(employee.getPhoneNumber())) {
            model.addAttribute("errorMessage", "Phone number is already in use");
            return "EmployeeRegister";
        }
        employeeService.createEmployee(employee);
        return "HomePage";

    }
}
