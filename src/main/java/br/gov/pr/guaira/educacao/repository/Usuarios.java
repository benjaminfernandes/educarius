package br.gov.pr.guaira.educacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.pr.guaira.educacao.model.Usuario;
import br.gov.pr.guaira.educacao.repository.helper.UsuariosQueries;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries{

	public Optional<Usuario> findByEmailIgnoreCase(String email);

	public List<Usuario> findByCodigoIn(Long[] codigos);
}
