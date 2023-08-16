package com.biblioteca.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.mapper.GenrerMapper;
import com.biblioteca.requests.GenrerGetRequestBody;
import com.biblioteca.services.GenrerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/genrers")
@RequiredArgsConstructor
public class GenrerResources {

	private final GenrerService serviceGenrer;

	@GetMapping(value = "/all")
	@Operation(summary = "find all Genrers non paginated")
	public ResponseEntity<List<GenrerGetRequestBody>> findAllNonPageable(){
		return ResponseEntity.ok(GenrerMapper.INSTANCE.toListOfGenrerGetRequetBody(serviceGenrer.findAllNonPageable()));
	}
	
}
