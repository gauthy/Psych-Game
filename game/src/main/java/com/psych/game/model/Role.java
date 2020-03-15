package com.psych.game.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
public class Role extends Auditable{
	
	    @NotBlank
	    @Column(unique = true)
	    @Getter
	    @Setter
	    private String name;

	    @NotBlank
	    @Getter
	    @Setter
	    private String description;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	    
	    
  
}
