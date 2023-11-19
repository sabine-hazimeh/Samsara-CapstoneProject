package com.Samsara.Capstone.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Client")
public class Client{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long  id;
	@Version
	@Column(name = "version")
	private int version;
	
	@NotNull
	private String userName;
	@NotNull
	private String phoneNumber;
	
	@NotNull
	private String email;
	@NotNull
	private String password;
	
	@Column(length = 4000)
	private String profilePhoto;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Post> posts;
	@OneToOne
	public WishList wishList;



}
