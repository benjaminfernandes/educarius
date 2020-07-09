package br.gov.pr.guaira.educacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.model.Serie;
import br.gov.pr.guaira.educacao.repository.helper.SeriesQueries;

public interface Series extends JpaRepository<Serie, Long>, SeriesQueries {

	public Optional<Serie> findByNomeIgnoreCase(String nome);
	public List<Serie> findByOrderByNomeAsc();
}
