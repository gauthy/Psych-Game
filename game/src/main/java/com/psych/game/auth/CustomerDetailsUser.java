package com.psych.game.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.psych.game.model.Role;
import com.psych.game.model.User;

public class CustomerDetailsUser extends User implements UserDetails {

	public CustomerDetailsUser(User user) {
		super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles=super.getRoles();
		List<GrantedAuthority> authorities=new ArrayList<>();
		for(Role role:roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return super.getSaltedHashedPassword();
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}