package com.biblioteca.entities;

import java.io.Serializable;
import java.util.List;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TB_ROLE")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"roleId","roleName"})
@SuperBuilder
@ToString
public class RoleModel implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName roleName;
    
    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuario;
    
    public RoleModel(long roleId, RoleName roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
    
	@Override
    public String getAuthority() {
        return this.roleName.toString();
    }
    
}