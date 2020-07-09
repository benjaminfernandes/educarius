package br.gov.pr.guaira.educacao.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.MateriaFilter;
import br.gov.pr.guaira.educacao.model.Materia;

public interface MateriasQueries {

	public Page<Materia> filtrar(MateriaFilter materiaFilter, Pageable pageable);
}
