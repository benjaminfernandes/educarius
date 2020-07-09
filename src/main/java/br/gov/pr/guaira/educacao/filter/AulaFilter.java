package br.gov.pr.guaira.educacao.filter;

import java.time.LocalDate;

import br.gov.pr.guaira.educacao.model.Materia;
import br.gov.pr.guaira.educacao.model.Serie;
import br.gov.pr.guaira.educacao.model.Semana;

public class AulaFilter {

	private LocalDate dataCadastro;
	private Semana semana;
	private Materia materia;
	private Serie serie;

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Semana getSemana() {
		return semana;
	}
	public void setSemana(Semana semana) {
		this.semana = semana;
	}
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	
}
