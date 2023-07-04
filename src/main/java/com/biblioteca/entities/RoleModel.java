package com.biblioteca.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.biblioteca.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_role")
@NoArgsConstructor
@Data
@EqualsAndHashCode
@SuperBuilder
@ToString(exclude = "userDomain")
public class RoleModel implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName roleName;
    
    @ManyToMany(mappedBy = "roles",cascade = CascadeType.PERSIST)
    private List<UserDomain> userDomain;
    
    public RoleModel(Long roleId, RoleName roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
     
	@Override
    public String getAuthority() {
        return this.roleName.toString();
    }

	@JsonIgnore
	public List<UserDomain> getUserDomain() {
		return userDomain;
	}
	
	
	
}
