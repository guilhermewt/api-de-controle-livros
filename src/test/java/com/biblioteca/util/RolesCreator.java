package com.biblioteca.util;

import com.biblioteca.entities.RoleModel;
import com.biblioteca.enums.RoleName;

public class RolesCreator {
	public static RoleModel createAdminRoleModel() {
		return RoleModel.builder()
				.roleId(1l)
				.roleName(RoleName.ROLE_ADMIN)
				.build();
	}
	
	public static RoleModel createUserRoleModel() {
		return RoleModel.builder()
				.roleId(2l)
				.roleName(RoleName.ROLE_USER)
				.build();
	}
}
