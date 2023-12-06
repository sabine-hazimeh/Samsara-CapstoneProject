package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Employee;
import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Service.ClientService;
import com.Samsara.Capstone.Project.Service.EmployeeService;
import com.Samsara.Capstone.Project.Service.PostService;
import com.Samsara.Capstone.Project.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/Employee")
public class EmployeeController {
    final public EmployeeService employeeService;
    final public PostService postService;
    final public ReportService reportService;
    final public ClientService clientService;
    @Autowired
    public EmployeeController(EmployeeService employeeService, PostService postService, ReportService reportService, ClientService clientService){
        this.employeeService = employeeService;
        this.postService = postService;
        this.reportService  = reportService;
        this.clientService = clientService;
    }
    @GetMapping(value = "/display-Register")
    @PreAuthorize("hasAnyAuthority('employee')")
    public String getRegisterPage(Model model){
        model.addAttribute("employee",new Employee());
        return "EmployeeRegister";
    }
    @PostMapping(value = "/create")
    @PreAuthorize("hasAnyAuthority('employee')")
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
    @GetMapping(value = "/display-posts")
    @PreAuthorize("hasAnyAuthority('employee')")
    public String getPosts(Model model){
        model.addAttribute("posts", getAllPosts());
        return "EmployeePostPage";
    }
    @GetMapping("/get-posts")
    @PreAuthorize("hasAnyAuthority('employee')")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

@PostMapping("/delete-post/{postId}")
@PreAuthorize("hasAnyAuthority('employee')")
public String deletePost(@PathVariable Long postId) {
    postService.DeletePost(postId);
    return "redirect:/Employee/display-posts";
}
    @GetMapping(value="display-reports")
    @PreAuthorize("hasAnyAuthority('employee')")
    public String getReports(Model model) throws Exception {
        model.addAttribute("reports", reportService.getAllReports());
        return "ReportsPage";
    }
    @GetMapping("/delete-report/{id}")
    @PreAuthorize("hasAnyAuthority('employee')")
    public String deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return "redirect:/Employee/display-reports";
    }

}
