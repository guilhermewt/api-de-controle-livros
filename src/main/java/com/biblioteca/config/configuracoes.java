//package com.biblioteca.config;
//
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//
//import com.biblioteca.entities.Emprestimo;
//import com.biblioteca.entities.Livro;
//import com.biblioteca.entities.RoleModel;
//import com.biblioteca.entities.Usuario;
//import com.biblioteca.enums.RoleName;
//import com.biblioteca.repository.RepositorioEmprestimo;
//import com.biblioteca.repository.RepositorioLivro;
//import com.biblioteca.repository.RepositorioUsuario;
//import com.biblioteca.repository.RoleModelRepository;
//
//import lombok.Builder;
//
//@Configuration
//public class configuracoes implements CommandLineRunner{
//
//	@Autowired
//	private RepositorioUsuario repositorioUsuario;
//	
//	@Autowired
//	private RepositorioLivro repositorioLivro;
//	
//	@Autowired
//	private RepositorioEmprestimo repositorioEmprestimo;
//	
//	@Autowired
//	private RoleModelRepository roleModelRepository;
//		
//	@Override
//	public void run(String... args) throws Exception {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		
//		RoleModel roleAdmin = 	new RoleModel(1l, RoleName.ROLE_ADMIN);
//		RoleModel roleUser = new RoleModel(2l, RoleName.ROLE_USER);
//		
//		roleModelRepository.saveAll(Arrays.asList(roleAdmin,roleUser));
//	    List<RoleModel> roleModelUser = List.of(roleUser);
//		List<RoleModel> rolesModelAdmin = List.of(roleAdmin,roleUser);
//		
//		Usuario user1 = new Usuario(null,"rafael silva","rafa@gmail.com","rafasilva","$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6");
//		Usuario user2 = new Usuario(null, "marcos pereira","marcos@gmail.com","marcospereira","$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6");
//		Usuario user3 = new Usuario(null, "tony roque","tony@gmail.com","tonyroque","$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6");
//			
////		user1.setRoles(rolesModelAdmin);
////		user2.setRoles(roleModelUser);
////		user3.setRoles(roleModelUser);
//		
////		user1.setRoles(rolesModelAdmin);
////		user2.setRoles(roleModelUser);
////		user3.setRoles(roleModelUser);
//		
//		
////		repositorioUsuario.saveAll(Arrays.asList(user1,user2,user3));
//		
//		repositorioUsuario.save(user1);
//						
////		Livro lv1 = new Livro(1l,"the lord of the kings" , sdf.parse("2009/05/26"));
////		Livro lv2 = new Livro(2l,"o poder da acao" , sdf.parse("2012/04/01"));
////			
////		lv1.setUsuario(user1);
////		lv2.setUsuario(user2);
////			
////		repositorioLivro.saveAll(Arrays.asList(lv1,lv2));
////		
////		Emprestimo emp1 = new Emprestimo(1l, sdf.parse("2022/05/22"), sdf.parse("2022/06/26"));
////		emp1.setUsuario(user1);
////		
////		repositorioEmprestimo.save(emp1);
//		
//	}
//
//}
