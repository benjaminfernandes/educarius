package br.gov.pr.guaira.educacao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pr.guaira.educacao.controller.page.PageWrapper;
import br.gov.pr.guaira.educacao.exception.AulaJaExistenteException;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.filter.AulaFilter;
import br.gov.pr.guaira.educacao.model.Aula;
import br.gov.pr.guaira.educacao.repository.Aulas;
import br.gov.pr.guaira.educacao.repository.Materias;
import br.gov.pr.guaira.educacao.repository.Semanas;
import br.gov.pr.guaira.educacao.repository.Series;
import br.gov.pr.guaira.educacao.service.AulaService;

@Controller
@RequestMapping("/aulas")
public class AulaController {

	@Autowired
	private Series series;
	@Autowired
	private Materias materias;
	@Autowired
	private Aulas aulas;
	@Autowired
	private AulaService aulaService;
	@Autowired
	private Semanas semanas;
	
	@GetMapping("/nova")
	public ModelAndView nova(Aula aula) {
		ModelAndView mv = new ModelAndView("aula/CadastroAula");
		mv.addObject("series", this.series.findByOrderByNomeAsc());
		mv.addObject("materias", this.materias.findByOrderByNomeAsc());
		mv.addObject("semanas", this.semanas.findByOrderByDataFinalDesc());
		return mv;
	}
	
	@PostMapping(value = {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid Aula aula, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return nova(aula);
		}
		try {
			this.aulaService.salvar(aula);
		}catch (AulaJaExistenteException e) {
			result.rejectValue("semana", e.getMessage(), e.getMessage());
			return nova(aula);
		}
		
		attributes.addFlashAttribute("mensagem", "Aula salva com sucesso!");
		return new ModelAndView("redirect:/aulas/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Aula aula) {
		ModelAndView mv = nova(aula);
		mv.addObject(aula);
		
		return mv;
	}
	@GetMapping
	public ModelAndView pesquisar(AulaFilter aulaFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("aula/PesquisaAulas");
		mv.addObject("materias", this.materias.findAll());
		mv.addObject("series", this.series.findAll());
		mv.addObject("semanas", this.semanas.findAll());
		PageWrapper<Aula> paginaWrapper = new PageWrapper<>(this.aulas.filtrar(aulaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Aula aula){
		try {
			this.aulaService.excluir(aula);
		}catch (ImpossivelExcluirEntidadeException e) {
			ResponseEntity.badRequest().body(e.getMessage());
			nova(aula);
		}
		return ResponseEntity.ok().build();
	}
}
