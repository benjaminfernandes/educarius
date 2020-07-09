package br.gov.pr.guaira.educacao.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.MateriaJaCadastradaException;
import br.gov.pr.guaira.educacao.model.Materia;
import br.gov.pr.guaira.educacao.repository.Materias;

@Service
public class MateriaService {

	@Autowired
	private Materias materias;
	
	public void salvar(Materia materia) {
		
		Optional<Materia> materiaOptional = this.materias.findByNomeIgnoreCase(materia.getNome());
		
		if(materiaOptional.isPresent() && materia.isNova()) {
			
			throw new MateriaJaCadastradaException("Esta matéria já está cadastrada!");
		}
		
		this.materias.saveAndFlush(materia);
	}
	
	@Transactional
	public void excluir(Materia materia) {
		
		try {
			this.materias.delete(materia);
			this.materias.flush();
		}catch (RuntimeException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir esta matéria!");
		}
		
	}
}
