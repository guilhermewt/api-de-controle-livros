package com.biblioteca.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_userDomain")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id","name"})
@SuperBuilder
@ToString(exclude = {"books"})
public class UserDomain implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L; 
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "the userDomain name cannot be empty")
	private String name;
	private String email;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(name = "TB_USERS_ROLES",
			joinColumns = @JoinColumn(name = "user_domain_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Builder.Default
	private List<RoleModel> roles = new ArrayList<>();

	@OneToMany(mappedBy = "userDomain",cascade = CascadeType.REMOVE)
	@Builder.Default
	@JsonIgnore
	private Set<Book> books = new HashSet<>();

	@OneToMany(mappedBy = "userDomain",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@Builder.Default
	private Set<Loan> loans = new HashSet<>();
	
	public UserDomain(Long id, String name, String email, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	@Override
	@JsonProperty("roles")
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
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
