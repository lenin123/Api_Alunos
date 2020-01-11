package com.api.aluno.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.api.aluno.domain.Aluno;
import com.api.aluno.dto.AlunoPostDTO;
import com.api.aluno.repositories.AlunoRepository;
import com.api.aluno.services.exceptions.DataIntegrityException;
import com.api.aluno.services.exceptions.ObjectNotFoundException;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	public Aluno find(Integer id) {  

		Optional<Aluno> objAluno = alunoRepository.findById(id);
		return objAluno.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Aluno.class.getName())); 
	}		
	
	public List<Aluno> findAll() {
		return alunoRepository.findAllByOrderByNome();
	}
	
	public Page<Aluno> findPage(Integer page, Integer top, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, top, Direction.valueOf(direction), orderBy);
		return alunoRepository.findAll(pageRequest);
	}	
	
	public Page<Aluno> findPageFilter(Integer page, Integer top, String orderBy, String direction, String nome) {
		List<Aluno> listAluno = alunoRepository.findAll();
		if(nome.hashCode() != 0) {
			listAluno = listAluno.stream().filter(obj -> obj.getNome().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());
		}
	
		PageRequest pageRequest = PageRequest.of(page, top, Sort.by(direction, orderBy));
		int inicioPaginacao = (int) pageRequest.getOffset();
		int fimPaginacao = (int) (inicioPaginacao + pageRequest.getPageSize()) > listAluno.size() ? listAluno.size() : (inicioPaginacao + pageRequest.getPageSize());
		int totalLinhas = listAluno.size();
		Page<Aluno> pageRetornoAluno = new PageImpl<Aluno>(listAluno.subList(inicioPaginacao, fimPaginacao), PageRequest.of(page, top, Sort.by(direction, orderBy)), totalLinhas); 
		return pageRetornoAluno;
	}		
		
	public Aluno insert(AlunoPostDTO objAlunoPostDTO) {
		Aluno novoObjAluno = new Aluno();
	
		novoObjAluno.setNome(objAlunoPostDTO.getNome());
		novoObjAluno.setIdade(objAlunoPostDTO.getIdade());
		
		novoObjAluno = alunoRepository.save(novoObjAluno);
		return novoObjAluno;	
	}	
	
	public Aluno update(Aluno objAluno, AlunoPostDTO objAlunoPostDTO) {
		Aluno novoObjAluno = find(objAluno.getId());
		updateAluno(novoObjAluno, objAlunoPostDTO);
		return alunoRepository.save(novoObjAluno);
	}	
	
	private void updateAluno(Aluno novoObjAluno, AlunoPostDTO objAlunoPostDTO) {
		novoObjAluno.setNome(objAlunoPostDTO.getNome() != null ? objAlunoPostDTO.getNome() : novoObjAluno.getNome());
		novoObjAluno.setIdade(objAlunoPostDTO.getIdade() != null ? objAlunoPostDTO.getIdade() : novoObjAluno.getIdade());
	}
		
	
	public void delete(Integer id) {
		find(id);	
		try {
			alunoRepository.deleteById(id);	
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque a entidades relacionadas");
		} 
	}
	
		
}