package br.gov.pr.guaira.educacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.model.Aula;
import br.gov.pr.guaira.educacao.repository.helper.AulasQueries;

public interface Aulas extends JpaRepository<Aula, Long>, AulasQueries{

}
