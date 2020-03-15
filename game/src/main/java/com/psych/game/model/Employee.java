package com.psych.game.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public abstract class Employee extends User {

	@NotBlank
	@Getter
	@Setter
	private String address;

	@NotBlank
	@Getter
	@Setter
	private String phoneNumber;

	@NotBlank
	@Getter
	@Setter
	private String name;

}
