package com.Samsara.Capstone.Project.Security;



import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {
    @Getter
    private Long id;
    private String username;
    private String password;
    private String email;
    @Getter
    private String phoneNumber;
    @Getter
    private String profilePhoto;
    private Long  EmployeeId;
    private String firstName;
    private String lastName;
    private String EmployeePhoneNumber;
    private String healthProblem;
    private String EmployeeEmail;
    private String EmployeePassword;
    private Date dob;
    private int experience;
    private String UserName;
    public UserInfoDetails(Client client){
        this.id = client.getId();
        this.username = client.getUserName();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.phoneNumber=client.getPhoneNumber();
        this.profilePhoto=client.getProfilePhoto();
    }
    public UserInfoDetails(Employee employee){
        this.EmployeeId = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.EmployeePhoneNumber = employee.getPhoneNumber();
        this.healthProblem = employee.getHealthProblem();
        this.EmployeeEmail = employee.getEmail();
        this.EmployeePassword = employee.getPassword();
        this.dob = employee.getDob();
        this.experience = employee.getExperience();
        this.UserName = employee.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getUserId(){ return this.id;}
    public String getEmail(){return this.email;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
