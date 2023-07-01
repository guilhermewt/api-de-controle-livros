package com.biblioteca.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_genrer")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id","name"})
@ToString(exclude = {"books"})
@SuperBuilder
public class Genrer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "genrers")
	@Builder.Default
	private List<Book> books = new ArrayList<>();
	
	public Genrer(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
