package com.api.aluno.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.aluno.dto.AlunoPostDTO;



@Service
public class DBService {

	@Autowired
	private AlunoService alunoService;
	
	public void instantiateTestDatabase() throws ParseException {	
		
		criarAlunoFake(Arrays.asList("Lênin Müller Smapaio", 23));
		criarAlunoFake(Arrays.asList("Adre Luis Sampaio", 45));
		criarAlunoFake(Arrays.asList("Adriana Müller", 44));
	}

	private void criarAlunoFake(List<Object> listObjAluno) throws ParseException {
		AlunoPostDTO objAlunoPostDTO = new AlunoPostDTO();
		objAlunoPostDTO.setId(null);
		objAlunoPostDTO.setNome(listObjAluno.get(0).toString());
		objAlunoPostDTO.setIdade(Integer.parseInt(listObjAluno.get(1).toString()));
		
		alunoService.insert(objAlunoPostDTO);
	}

}