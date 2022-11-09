package com.biblioteca.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import com.biblioteca.entities.UserDomain;
import com.biblioteca.util.RolesCreator;
import com.biblioteca.util.UserDomainCreator;

@DataJpaTest
@DisplayName("test for userDomain repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDomainRepositoryTest {
	
	@Autowired
	private UserDomainRepository userRepository;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	@Test
	@DisplayName("find all user books by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomainToBeSaved = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		Page<UserDomain> userDomainSaved = this.userRepository.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(userDomainSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(userDomainSaved.toList().get(0)).isEqualTo(userDomainToBeSaved);
	}
	
	@Test
	@DisplayName("find all user books by id return list of userDomain whensuccessful")
	void findAll_returnListOfuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomainToBeSaved = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		List<UserDomain> userDomainSaved = this.userRepository.findAll();
		
		Assertions.assertThat(userDomainSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(userDomainSaved.get(0)).isEqualTo(userDomainToBeSaved);
	}
	
	@Test
	@DisplayName("findById return userDomain whenSuccessful")
	void findByid_returnuserDomain_whenSuccessful() {		
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomainToBeSaved = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
		UserDomain userDomainSaved = this.userRepository.findById(userDomainToBeSaved.getId()).get();
		
		Assertions.assertThat(userDomainSaved).isNotNull();
		Assertions.assertThat(userDomainSaved.getId()).isNotNull();
		Assertions.assertThat(userDomainSaved).isEqualTo(userDomainToBeSaved);
	}
			
	@Test
	@DisplayName("save return userDomain whenSuccessful")
	void save_returnuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomainToBeSaved = UserDomainCreator.createUserDomainWithRoleADMIN();
		UserDomain userDomainSaved = this.userRepository.save(userDomainToBeSaved);
		
		Assertions.assertThat(userDomainSaved).isNotNull();
		Assertions.assertThat(userDomainSaved.getId()).isNotNull();
		Assertions.assertThat(userDomainSaved).isEqualTo(userDomainToBeSaved);
	}
	
	@Test
	@DisplayName("delete removes userDomain whenSuccessful")
	void delete_removesuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
	
	    this.userRepository.delete(userDomain);
	    
	    Optional<UserDomain> userDomainDeleted = this.userRepository.findById(userDomain.getId());
	    
	    Assertions.assertThat(userDomainDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace userDomain whenSuccessful")
	void update_replaceuserDomain_whenSuccessful() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		this.userRepository.save(UserDomainCreator.createUserDomainWithRoleADMIN());
			
		UserDomain userDomainToBeUpdate = UserDomainCreator.createUserDomainToBeUpdate();
	
	    UserDomain userDomainUpdated = this.userRepository.save(userDomainToBeUpdate);
	    
	    Assertions.assertThat(userDomainUpdated).isNotNull();
	    Assertions.assertThat(userDomainUpdated.getId()).isNotNull();
	    Assertions.assertThat(userDomainUpdated).isEqualTo(userDomainToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when userDomain name is empty")
	void save_throwConstrationViolationException_whenuserDomainNameIsEmpty() {
		roleModelRepository.saveAll(Arrays.asList(RolesCreator.createAdminRoleModel(),RolesCreator.createUserRoleModel()));
		
		UserDomain userDomain = new UserDomain();
		
		Assertions.assertThatThrownBy(() -> this.userRepository.save(userDomain))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}