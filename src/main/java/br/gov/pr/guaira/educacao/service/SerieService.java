package br.gov.pr.guaira.educacao.service;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.SerieJaCadastradaException;
import br.gov.pr.guaira.educacao.model.Serie;
import br.gov.pr.guaira.educacao.repository.Series;

@Service
public class SerieService {

	@Autowired
	private Series series;
	
	
	public void salvar(Serie serie) {
		
		Optional<Serie> serieOptional = this.series.findByNomeIgnoreCase(serie.getNome());
		
		if(serieOptional.isPresent() && serie.isNova()) {
			throw new SerieJaCadastradaException("Esta série já está cadastrada!");
		}
		this.series.saveAndFlush(serie);
	}
	
	@Transactional
	public void excluir(Serie serie) {
		
		try {
			this.series.delete(serie);
			this.series.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir esta série!");
		}
		
	}
}
