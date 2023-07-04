package com.biblioteca.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.data.UserDomainDetails;
import com.biblioteca.entities.UserDomain;
import com.biblioteca.mapper.UserDomainMapper;
import com.biblioteca.repository.RoleModelRepository;
import com.biblioteca.repository.UserDomainRepository;
import com.biblioteca.requests.UserDomainPostRequestBody;
import com.biblioteca.requests.UserDomainPutRequestBody;
import com.biblioteca.services.exceptions.BadRequestException;
import com.biblioteca.services.utilService.GetUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserDomainService implements UserDetailsService{
	
	private final UserDomainRepository userRepository;
		
	private final GetUserDetails userAuthenticated;
	
	private final RoleModelRepository roleModelRepository;
	
	public List<UserDomain> findAllNonPageable(){
		return userRepository.findAll();
	}
	
	public Page<UserDomain> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public List<UserDomain> findByName(String name){
		return userRepository.findBynameContainingIgnoreCase(name);
	}
	
	public UserDomain findByIdOrElseThrowResourceNotFoundException(long id) {
		return userRepository.findById(id).orElseThrow(() -> new BadRequestException("UserDomain not found"));
	}
	
	public UserDomain getMyUser() {
		return userAuthenticated.userAuthenticated();
	}
	

	public UserDomain saveUser(UserDomainPostRequestBody userDomainPostRequestBody) {	
	   	log.info(roleModelRepository.findById(2l).get());

		UserDomain userDomain = UserDomainMapper.INSTANCE.toUserDomain(userDomainPostRequestBody);
		userDomain.setPassword(new BCryptPasswordEncoder().encode(userDomain.getPassword()));		
		userDomain.getRoles().add(roleModelRepository.findById(2l).get());	    
		return userRepository.save(userDomain);		
	}
	
	public UserDomain saveUserAdmin(UserDomainPostRequestBody userDomainPostRequestBody) {	
		UserDomain userDomain = UserDomainMapper.INSTANCE.toUserDomain(userDomainPostRequestBody);
		userDomain.setPassword(new BCryptPasswordEncoder().encode(userDomain.getPassword()));
		userDomain.getRoles().addAll(Arrays.asList(roleModelRepository.findById(1l).get(),roleModelRepository.findById(2l).get()));
	    return userRepository.save(userDomain);
	}
	
	public void delete(long id) {	
		userRepository.delete(findByIdOrElseThrowResourceNotFoundException(id));		
	}
	
	public UserDomain update(UserDomainPutRequestBody userDomainPutRequestBody) {
		UserDomain savedUserDomain = userRepository.findById(userAuthenticated.userAuthenticated().getId()).get();
		UserDomain UserDomain = UserDomainMapper.INSTANCE.updateUserDomain(userDomainPutRequestBody, savedUserDomain);
		UserDomain.setPassword(new BCryptPasswordEncoder().encode(UserDomain.getPassword()));
		return userRepository.save(UserDomain);
	}
	
	public void updatePassword(String oldPassword,String newPassword) {		
		UserDomain userDomain = userRepository.findById(userAuthenticated.userAuthenticated().getId()).get();
		
		if(BCrypt.checkpw(oldPassword,userDomain.getPassword())  == false) {
			throw new BadRequestException("the password is wrong");
		}
		
		userDomain.setPassword(new BCryptPasswordEncoder().encode(newPassword));
		userRepository.save(userDomain);		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		Optional<UserDomain> userDomain = userRepository.findByUsername(username);

		return new UserDomainDetails(userDomain);
	}
}
