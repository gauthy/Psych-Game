package com.psych.game.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends Auditable {

	@Email
    @NotBlank
    @Column(unique=true)
    @Getter
    @Setter
    private String email;

    @NotBlank
    @Getter @Setter
    private String saltedHashedPassword;

    @ManyToMany(fetch=FetchType.EAGER)
    @Getter @Setter
    Set<Role> roles = new HashSet<>();

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSaltedHashedPassword(String saltedHashedPassword) {
		this.saltedHashedPassword = new BCryptPasswordEncoder(5).encode(saltedHashedPassword);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
	public String getEmail() {
		return email;
	}

	public String getSaltedHashedPassword() {
		return saltedHashedPassword;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	public User() {
		
	}

	public User(User user) {
		email=user.getEmail();
		saltedHashedPassword=user.getSaltedHashedPassword();
		roles=user.getRoles();
	}
    

}
