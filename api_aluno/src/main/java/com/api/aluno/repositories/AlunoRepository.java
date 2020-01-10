package com.api.aluno.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.aluno.domain.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
	
	@Transactional(readOnly=true)
	public List<Aluno> findAllByOrderByNome();
	
	@Transactional(readOnly=true)
	Aluno findByIdade(Integer idade);
}