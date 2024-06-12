package com.user.entity;


import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userName;

	
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	   @JoinColumn(name="u_id")
   @JsonManagedReference
   @EqualsAndHashCode.Exclude
	   private Set<UserRoles> userRoles;
	private String email;

	private String password;

	

	private Date createdAt;

	private Boolean deleted = false;
	

	@PrePersist
	public void created() {
		this.createdAt = new Date(System.currentTimeMillis());		
	}


	
}
