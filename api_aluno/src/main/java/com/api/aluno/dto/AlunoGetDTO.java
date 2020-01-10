package com.api.aluno.dto;

import java.io.Serializable;
import com.api.aluno.domain.Aluno;

public class AlunoGetDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private Integer idade;
	
	public AlunoGetDTO() {
		
	}

	public AlunoGetDTO(Aluno objAluno) {
		id = objAluno.getId();
		nome = objAluno.getNome();
		idade = objAluno.getIdade();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

}