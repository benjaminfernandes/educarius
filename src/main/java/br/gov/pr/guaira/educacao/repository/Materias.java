package br.gov.pr.guaira.educacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.model.Materia;
import br.gov.pr.guaira.educacao.repository.helper.MateriasQueries;

public interface Materias extends JpaRepository<Materia, Long>, MateriasQueries {

	public Optional<Materia> findByNomeIgnoreCase(String nome);
	public List<Materia> findByOrderByNomeAsc();
}
