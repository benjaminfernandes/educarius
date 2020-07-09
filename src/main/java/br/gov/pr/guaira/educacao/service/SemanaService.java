package br.gov.pr.guaira.educacao.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.model.Semana;
import br.gov.pr.guaira.educacao.repository.Semanas;
import br.gov.pr.guaira.educacao.validation.DataValidacaoException;

@Service
public class SemanaService {

	@Autowired
	private Semanas semanas;
	
	public void salvar(Semana semana) {
		
		confereDatas(semana);
		this.semanas.saveAndFlush(semana);
	}

	@Transactional
	public void excluir(Semana semana) {
		
		try {
			this.semanas.delete(semana);
			this.semanas.flush();
		}catch (RuntimeException e) {
			throw new ImpossivelExcluirEntidadeException("Esta semana não pode ser excluída!");
		}
	}
	
	private void confereDatas(Semana semana) {
		if(semana.getDataInicial().isBefore(LocalDate.now())) {
			throw new DataValidacaoException("Data inicial incorreta!");
		}
		
		if(semana.getDataInicial().isAfter(semana.getDataFinal())) {
			throw new DataValidacaoException("Data inicial incorreta!");
		}
		
		if(semana.getDataFinal().isBefore(LocalDate.now())) {
			throw new DataValidacaoException("Data final incorreta!");
		}
		
		if(semana.getDataFinal().isBefore(semana.getDataInicial())) {
			throw new DataValidacaoException("Data final incorreta");
		}
		
		if(semana.getDataFinal().isEqual(semana.getDataInicial())) {
			throw new DataValidacaoException("Datas iguais!");
		}
	}
}
