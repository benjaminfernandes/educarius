package br.gov.pr.guaira.educacao.repository.helper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.SemanaFilter;
import br.gov.pr.guaira.educacao.model.Semana;
import br.gov.pr.guaira.educacao.model.Serie;

public interface SemanasQueries {

	public Page<Semana> filtrar(SemanaFilter semanaFilter, Pageable pageable);
	public List<Semana> buscaSemanasSite(String usuario, Serie serie);
}
