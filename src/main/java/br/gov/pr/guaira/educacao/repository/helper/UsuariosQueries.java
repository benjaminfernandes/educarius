package br.gov.pr.guaira.educacao.repository.helper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.UsuarioFilter;
import br.gov.pr.guaira.educacao.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailEAtivo(String email);
	public List<String> permissoes(Usuario usuario);	
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);
	public Usuario buscarComGrupos(Long codigo);
}
