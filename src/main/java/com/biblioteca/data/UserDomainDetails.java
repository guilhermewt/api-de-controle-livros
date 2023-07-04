package com.biblioteca.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.biblioteca.entities.UserDomain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDomainDetails implements UserDetails, Serializable{

	private static final long serialVersionUID = 1L;
	
	private final Optional<UserDomain> userDomain;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userDomain.get().getRoles();
	}

	@Override
	public String getPassword() {
		return userDomain.get().getPassword();
	}

	@Override
	public String getUsername() {
		return userDomain.get().getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
