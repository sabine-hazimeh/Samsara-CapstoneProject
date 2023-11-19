package com.Samsara.Capstone.Project.Model;

import com.Samsara.Capstone.Project.enums.PostStatus;
import com.Samsara.Capstone.Project.enums.WaterType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "Post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	@Version
	@Column(name = "version")
	private int version;
	
	private String city;
	private int bedroomNb;
	private int bathroomNb;
	private PostStatus status;
	private WaterType waterType;
	private boolean available;
	private int price;
	private int area;
	private int floor;
	private int buildingFloorNb;
	private String description;
	private long longitude;
	private long altitude;
	private String location;
	@ElementCollection
	@CollectionTable(name = "post_pictures", joinColumns = @JoinColumn(name = "post_id"))
	@Lob
	@Column(name = "picture")
	private List<String> pictures = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<Report> reports;

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		return result;
	}

	public Post() {
		this.reports = new ArrayList<>();
//		this.reviews = new ArrayList<>();
		this.available = true;
	}

}