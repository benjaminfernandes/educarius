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

import br.gov.pr.guaira.educacao.filter.MateriaFilter;
import br.gov.pr.guaira.educacao.model.Materia;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;

public class MateriasImpl implements MateriasQueries {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Materia> filtrar(MateriaFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Materia> query = builder.createQuery(Materia.class);
		Root<Materia> materiaEntity = query.from(Materia.class);
		Predicate[] filtros = adicionarFiltro(filtro, materiaEntity);

		query.select(materiaEntity).where(filtros);
		
		TypedQuery<Materia> typedQuery =  (TypedQuery<Materia>) paginacaoUtil.prepararOrdem(query, materiaEntity, pageable);
		typedQuery = (TypedQuery<Materia>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(MateriaFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Materia> materiaEntity = query.from(Materia.class);
		
		query.select(criteriaBuilder.count(materiaEntity));
		query.where(adicionarFiltro(filtro, materiaEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(MateriaFilter filtro, Root<Materia> serieEntity) {
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
