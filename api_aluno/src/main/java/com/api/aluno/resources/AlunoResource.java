package com.api.aluno.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.aluno.domain.Aluno;
import com.api.aluno.dto.AlunoGetDTO;
import com.api.aluno.dto.AlunoPostDTO;
import com.api.aluno.services.AlunoService;

@RestController
@RequestMapping(value="/aluno")
public class AlunoResource {
	
	@Autowired
	private AlunoService alunoService;

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {	
		Aluno aluno = alunoService.find(id);
		AlunoGetDTO alunoGetDTO = new AlunoGetDTO(aluno);
		return ResponseEntity.ok(alunoGetDTO);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AlunoGetDTO>> findAll() {
		List<Aluno> listAluno = alunoService.findAll(); 
		List<AlunoGetDTO> listAlunoGetDto = new ArrayList<AlunoGetDTO>();		
		for (Aluno Aluno : listAluno) {
			AlunoGetDTO alunoGetDTO = new AlunoGetDTO(Aluno);
			listAlunoGetDto.add(alunoGetDTO);
		}  
		return ResponseEntity.ok().body(listAlunoGetDto);		
		
	}
	
	@RequestMapping(value="/pages", method=RequestMethod.GET)
	public ResponseEntity<Page<AlunoGetDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="top", defaultValue="25") Integer top, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Aluno> listAluno = alunoService.findPage(page, top, orderBy, direction); 
		List<AlunoGetDTO> listAlunoGetDTO = new ArrayList<AlunoGetDTO>();		
		for (Aluno aluno : listAluno) {
			AlunoGetDTO alunoGetDTO = new AlunoGetDTO(aluno);		
			listAlunoGetDTO.add(alunoGetDTO);
		}  	
		Page<AlunoGetDTO> pageAlunoGetDTO = new PageImpl<>(listAlunoGetDTO, listAluno.getPageable(), listAluno.getTotalElements());
		return ResponseEntity.ok().body(pageAlunoGetDTO);
	}

	@RequestMapping(value="/filter", method=RequestMethod.GET)
	public ResponseEntity<Page<AlunoGetDTO>> findPageFilter(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="top", defaultValue="25") Integer top, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="nome", defaultValue="") String nome) {
		Page<Aluno> listAluno = alunoService.findPageFilter(page, top, orderBy, direction, nome);  
		List<AlunoGetDTO> listAlunoGetDTO = new ArrayList<AlunoGetDTO>();		
		for (Aluno aluno : listAluno) {
			AlunoGetDTO alunoGetDTO = new AlunoGetDTO(aluno);
			listAlunoGetDTO.add(alunoGetDTO);
		}  
		Page<AlunoGetDTO> pageAlunoGetDTO = new PageImpl<>(listAlunoGetDTO, listAluno.getPageable(), listAluno.getTotalElements());
		return ResponseEntity.ok().body(pageAlunoGetDTO);
	}		
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AlunoPostDTO objAlunoPostDTO) {
		Aluno objAluno = new Aluno();
		objAluno = alunoService.insert(objAlunoPostDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objAluno.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AlunoPostDTO objAlunoPostDTO, @PathVariable Integer id) {
		Aluno objAluno = new Aluno();
		objAluno.setId(id);
		objAluno = alunoService.update(objAluno, objAlunoPostDTO);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		alunoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}