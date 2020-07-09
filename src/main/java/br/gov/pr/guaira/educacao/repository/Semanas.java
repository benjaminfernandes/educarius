package br.gov.pr.guaira.educacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.model.Semana;
import br.gov.pr.guaira.educacao.repository.helper.SemanasQueries;

public interface Semanas extends JpaRepository<Semana, Long>, SemanasQueries {

	public List<Semana> findByOrderByDataFinalDesc();
}
