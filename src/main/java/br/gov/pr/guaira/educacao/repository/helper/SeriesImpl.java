package br.gov.pr.guaira.educacao.repository.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.filter.SerieFilter;
import br.gov.pr.guaira.educacao.model.Serie;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;

public class SeriesImpl implements SeriesQueries{

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Serie> filtrar(SerieFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Serie> query = builder.createQuery(Serie.class);
		Root<Serie> serieEntity = query.from(Serie.class);
		Predicate[] filtros = adicionarFiltro(filtro, serieEntity);

		query.select(serieEntity).where(filtros);
		
		TypedQuery<Serie> typedQuery =  (TypedQuery<Serie>) paginacaoUtil.prepararOrdem(query, serieEntity, pageable);
		typedQuery = (TypedQuery<Serie>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(SerieFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Serie> serieEntity = query.from(Serie.class);
		
		query.select(criteriaBuilder.count(serieEntity));
		query.where(adicionarFiltro(filtro, serieEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(SerieFilter filtro, Root<Serie> serieEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicateList.add(builder.like(serieEntity.get("nome"), "%"+filtro.getNome().toUpperCase()+"%"));
			}
		}
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}

}
