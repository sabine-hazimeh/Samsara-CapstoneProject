package com.Samsara.Capstone.Project.Security;



import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


public class UserInfoDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String profilePhoto;
    private boolean isEmployee;
    private boolean isAccountNonExpired;

    public UserInfoDetails(Client client){
        this.id = client.getId();
        this.username = client.getUserName();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.phoneNumber = client.getPhoneNumber();
        this.profilePhoto = client.getProfilePhoto();
        this.isEmployee = false;
        this.isAccountNonExpired = client.isAccountNonLocked();

    }

    public UserInfoDetails(Employee employee){
        this.id = employee.getId();
        this.username = employee.getUsername();
        this.email = employee.getEmail();
        this.password = employee.getPassword();
        this.isEmployee = true;
        this.isAccountNonExpired = employee.isAccountNonLocked();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isEmployee) {
            return Arrays.asList(new SimpleGrantedAuthority("employee"));
        } else {
            return Arrays.asList(new SimpleGrantedAuthority("client"));
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }



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
    public Long getUserId(){ return this.id;}
   public String getEmail(){return this.email;}

}
