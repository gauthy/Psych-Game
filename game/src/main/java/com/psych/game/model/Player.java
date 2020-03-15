package com.psych.game.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="players")
public class Player extends User  {
   
	@NotBlank
	@Getter
	@Setter
	private String alias;
	@Getter
	@Setter
	private String psychFaceURL;
	@Getter
	@Setter
	private String picFaceURL;
	
	@OneToOne(cascade = CascadeType.ALL)
	@Getter@Setter
	@JsonManagedReference
	private Stat stats=new Stat();
	
	@ManyToMany(mappedBy="players")
	@Getter@Setter
	@JsonIdentityReference
	private Set<Game> games=new HashSet<>();
	 
	public Player() {}
	
	private Player(Builder builder) {
		
        setEmail(builder.email);
        setSaltedHashedPassword(builder.saltedHashedPassword);
        setAlias(builder.alias);
        setPsychFaceURL(builder.psychFaceURL);
        setPicFaceURL(builder.picURL);
    }


	public static final class Builder {
        private @Email @NotBlank String email;
        private @NotBlank String saltedHashedPassword;
        private @NotBlank String alias;
        private String psychFaceURL;
        private String picURL;

        public Builder() {
        }

        public Builder email(@Email @NotBlank String val) {
            email = val;
            return this;
        }

        public Builder saltedHashedPassword(@NotBlank String val) {
            saltedHashedPassword = val;
            return this;
        }

        public Builder alias(@NotBlank String val) {
            alias = val;
            return this;
        }

        public Builder psychFaceURL(String val) {
            psychFaceURL = val;
            return this;
        }

        public Builder picURL(String val) {
            picURL = val;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }


	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setPsychFaceURL(String psychFaceURL) {
		this.psychFaceURL = psychFaceURL;
	}

	public void setPicFaceURL(String picFaceURL) {
		this.picFaceURL = picFaceURL;
	}

	public void setStats(Stat stats) {
		this.stats = stats;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

	public Game getCurrentGame() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAlias() {
		return alias;
	}

	public String getPsychFaceURL() {
		return psychFaceURL;
	}

	public String getPicFaceURL() {
		return picFaceURL;
	}

	public Stat getStats() {
		return stats;
	}

	public Set<Game> getGames() {
		return games;
	}
	
	
	
	
	
}
