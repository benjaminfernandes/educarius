package br.gov.pr.guaira.educacao.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.SerieFilter;
import br.gov.pr.guaira.educacao.model.Serie;


public interface SeriesQueries {

	public Page<Serie> filtrar(SerieFilter serieFilter, Pageable pageable);
}
