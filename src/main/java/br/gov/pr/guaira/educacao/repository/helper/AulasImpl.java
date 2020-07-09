package br.gov.pr.guaira.educacao.repository.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pr.guaira.educacao.filter.AulaFilter;
import br.gov.pr.guaira.educacao.model.Aula;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;

public class AulasImpl implements AulasQueries{

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Aula> filtrar(AulaFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aula> query = builder.createQuery(Aula.class);
		Root<Aula> aulaEntity = query.from(Aula.class);
		aulaEntity.fetch("materia", JoinType.INNER);
		aulaEntity.fetch("serie", JoinType.INNER);
		aulaEntity.fetch("semana", JoinType.INNER);
		
		Predicate[] filtros = adicionarFiltro(filtro, aulaEntity);

		query.select(aulaEntity).where(filtros);
		
		TypedQuery<Aula> typedQuery =  (TypedQuery<Aula>) paginacaoUtil.prepararOrdem(query, aulaEntity, pageable);
		typedQuery = (TypedQuery<Aula>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Aula> buscaAulaCadastrada(Aula aula) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aula> query = builder.createQuery(Aula.class);
		Root<Aula> aulaEntity = query.from(Aula.class);
		aulaEntity.fetch("materia", JoinType.INNER);
		aulaEntity.fetch("serie", JoinType.INNER);
		aulaEntity.fetch("semana", JoinType.INNER);
		
		AulaFilter aulaFilter = new AulaFilter();
		aulaFilter.setMateria(aula.getMateria());
		aulaFilter.setSerie(aula.getSerie());
		aulaFilter.setSemana(aula.getSemana());
		
		Predicate[] filtros = adicionarFiltro(aulaFilter, aulaEntity);

		query.select(aulaEntity).where(filtros);
		
		TypedQuery<Aula> typedQuery = manager.createQuery(query);
		return typedQuery.getResultList();
	}
	
	private Long total(AulaFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Aula> aulaEntity = query.from(Aula.class);
		
		query.select(criteriaBuilder.count(aulaEntity));
		query.where(adicionarFiltro(filtro, aulaEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(AulaFilter filtro, Root<Aula> serieEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		if (filtro != null) {
			
			if (filtro.getMateria() != null){
				predicateList.add(builder.equal(serieEntity.get("materia"), filtro.getMateria()));
			}
			if (filtro.getSerie() != null){
				predicateList.add(builder.equal(serieEntity.get("serie"), filtro.getSerie()));
			}
			if (filtro.getSemana() != null){
				predicateList.add(builder.equal(serieEntity.get("semana"), filtro.getSemana()));
			}
		}
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}
}