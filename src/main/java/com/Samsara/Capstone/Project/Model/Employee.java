package com.Samsara.Capstone.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long  id;
	@Version
	@Column(name = "version")
	private int version;
	private String username;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String healthProblem;
	
	@NotNull
	private String email;
	private String password;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dob;
	private int experience;
	

	
	
}
