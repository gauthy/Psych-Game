package com.psych.game.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.URL;

import com.sun.istack.NotNull;

@Entity
@Table(name = "gamemode")
public class GameMode extends Auditable {

	@NotNull
	@Column(unique = true)
	private String name;

	@URL
	private String picture;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GameMode(String name, @URL String picture, String description) {
		super();
		this.name = name;
		this.picture = picture;
		this.description = description;
	}

	public GameMode() {
		super();
		// TODO Auto-generated constructor stub
	}

}
