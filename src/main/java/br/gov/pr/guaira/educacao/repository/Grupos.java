package br.gov.pr.guaira.educacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.pr.guaira.educacao.model.Grupo;


@Repository
public interface Grupos extends JpaRepository<Grupo, Long>{

}
