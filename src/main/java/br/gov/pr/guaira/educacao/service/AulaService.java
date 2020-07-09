package br.gov.pr.guaira.educacao.service;

import java.time.LocalDate;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.exception.AulaJaExistenteException;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.model.Aula;
import br.gov.pr.guaira.educacao.repository.Aulas;

@Service
public class AulaService {

	@Autowired
	private Aulas aulas;
	
	public void salvar(Aula aula) {
			
		if(!this.aulas.buscaAulaCadastrada(aula).isEmpty() && aula.isNova()){
				throw new AulaJaExistenteException("Já existe uma aula com estes dados na semana selecionada!");
		}
		
		aula.setDataCadastro(LocalDate.now());
		configuraUrlVideos(aula);
		this.aulas.saveAndFlush(aula);
	}
	
	@Transactional
	public void excluir(Aula aula) {
		
		try {
			this.aulas.delete(aula);
			this.aulas.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Esta aula não pode ser excluída!");
		}
	}
	
	private void configuraUrlVideos(Aula aula) {
		String url1 = aula.getUrlVideoAula1();
		String url2 = aula.getUrlVideoAula2();
		
		if(!StringUtils.isEmpty(url1)) {
			aula.setUrlVideoAula1(url1.substring(url1.indexOf("?v=")+3));
		}
		
		if(!StringUtils.isEmpty(url2)) {
			aula.setUrlVideoAula2(url2.substring(url2.indexOf("?v=")+3));
		}
	}
}
