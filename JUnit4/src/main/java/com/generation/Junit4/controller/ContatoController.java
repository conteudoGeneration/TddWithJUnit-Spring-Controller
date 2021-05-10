package com.generation.Junit4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.Junit4.model.ContatoModel;
import com.generation.Junit4.repository.ContatoRepository;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

	@Autowired
	private ContatoRepository contatoRepository;

	@GetMapping
	public ResponseEntity<List<ContatoModel>> getAll() {
		List<ContatoModel> contatos = contatoRepository.findAll();
		return ResponseEntity.ok(contatos);
	}

	@GetMapping("/contato/{id}")
	public ResponseEntity<ContatoModel> getById(@PathVariable Long id) {
		return contatoRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PostMapping("/inserir")
	public ResponseEntity<ContatoModel> post(@RequestBody ContatoModel contato) {
		contato = contatoRepository.save(contato);
		return ResponseEntity.status(HttpStatus.CREATED).body(contato);
	}

	@PutMapping("/alterar")
	public ResponseEntity<ContatoModel> put(@RequestBody ContatoModel contato) {
		contato = contatoRepository.save(contato);
		return ResponseEntity.status(HttpStatus.OK).body(contato);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		contatoRepository.deleteById(id);		
	}

}
