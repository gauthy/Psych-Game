package com.psych.game.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="contentwriters")
public class ContentWriter extends Employee{
	
	@Getter
	@Setter
	@JsonIdentityReference
	@ManyToMany(cascade=CascadeType.PERSIST)
	Set<Question> editedQuestions=new HashSet<Question>();
	

}
