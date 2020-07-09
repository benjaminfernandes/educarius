package br.gov.pr.guaira.educacao.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

@Entity
@Table(name="aula")
@DynamicUpdate
public class Aula implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;
	@NotBlank(message="Informe a URL da vídeo aula")
	@Column(name="url_video_aula1")
	private String urlVideoAula1;
	@Column(name="url_video_aula2")
	private String urlVideoAula2;
	@NotBlank(message="Informe os objetos do conhecimento")
	@Column(name="objetos_conhecimento")
	private String objetosConhecimento;
	@Column(name="objetos_conhecimento_html")
	private String objetosConhecimentoHtml;
	@Lob
	@NotBlank(message = "Informe os objetivos de aprendizagem")
	@Column(name="objetivos_aprendizagem")
	private String objetivosAprendizagem;
	@Lob
	@Column(name="referencia_bibliograficas")
	private String referenciaBibliograficas;
	@Lob
	@Column(name="objetivos_aprendizagem_html")
	private String objetivosAprendizagemHtml;
	@Lob
	@Column(name="referencia_bibliograficas_html")
	private String referenciaBibliograficasHtml;
	@NotNull(message = "Informe a série")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serie", referencedColumnName = "codigo")
	private Serie serie;
	@NotNull(message = "Informe a matéria")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materia", referencedColumnName = "codigo")
	private Materia materia;
	@NotNull(message = "Informe a semana que será disponibilizada esta aula")
	@ManyToOne
	@JoinColumn(name="semana", referencedColumnName = "codigo")
	private Semana semana;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getUrlVideoAula1() {
		return urlVideoAula1;
	}
	public void setUrlVideoAula1(String urlVideoAula1) {
		this.urlVideoAula1 = urlVideoAula1;
	}
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	public boolean isNova() {
		return this.codigo == null;
	}
	public Semana getSemana() {
		return semana;
	}
	public void setSemana(Semana semana) {
		this.semana = semana;
	}
	public String getUrlVideoAula2() {
		return urlVideoAula2;
	}
	public void setUrlVideoAula2(String urlVideoAula2) {
		this.urlVideoAula2 = urlVideoAula2;
	}
	public String getObjetosConhecimento() {
		return objetosConhecimento;
	}
	public void setObjetosConhecimento(String objetosConhecimento) {
		this.objetosConhecimento = objetosConhecimento;
	}
	public String getObjetivosAprendizagem() {
		return objetivosAprendizagem;
	}
	public void setObjetivosAprendizagem(String objetivosAprendizagem) {
		this.objetivosAprendizagem = objetivosAprendizagem;
	}
	public String getReferenciaBibliograficas() {
		return referenciaBibliograficas;
	}
	public void setReferenciaBibliograficas(String referenciaBibliograficas) {
		this.referenciaBibliograficas = referenciaBibliograficas;
	}
	public String getObjetivosAprendizagemHtml() {
		return objetivosAprendizagemHtml;
	}
	public void setObjetivosAprendizagemHtml(String objetivosAprendizagemHtml) {
		this.objetivosAprendizagemHtml = objetivosAprendizagemHtml;
	}
	public String getReferenciaBibliograficasHtml() {
		return referenciaBibliograficasHtml;
	}
	public void setReferenciaBibliograficasHtml(String referenciaBibliograficasHtml) {
		this.referenciaBibliograficasHtml = referenciaBibliograficasHtml;
	}
	public String getObjetosConhecimentoHtml() {
		return objetosConhecimentoHtml;
	}
	public void setObjetosConhecimentoHtml(String objetosConhecimentoHtml) {
		this.objetosConhecimentoHtml = objetosConhecimentoHtml;
	}
	
	public boolean temVideo2() {
		return !StringUtils.isEmpty(this.urlVideoAula2);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
