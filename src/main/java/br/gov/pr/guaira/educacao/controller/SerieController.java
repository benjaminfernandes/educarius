package br.gov.pr.guaira.educacao.controller;

import java.io.Serializable;

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
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.SerieJaCadastradaException;
import br.gov.pr.guaira.educacao.filter.SerieFilter;
import br.gov.pr.guaira.educacao.model.Serie;
import br.gov.pr.guaira.educacao.repository.Series;
import br.gov.pr.guaira.educacao.service.SerieService;

@Controller
@RequestMapping("/series")
public class SerieController implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private SerieService serieService;
	@Autowired
	private Series series;

	@RequestMapping("/nova")
	public ModelAndView nova(Serie serie) {
		ModelAndView mv = new ModelAndView("serie/CadastroSerie");
		
		return mv;
	}
	
	@PostMapping(value = {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid Serie serie, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return nova(serie);
		}
		try {
			this.serieService.salvar(serie);
		}catch (SerieJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(serie);
		}
		attributes.addFlashAttribute("mensagem", "Série cadastrada com sucesso!");
		return new ModelAndView("redirect:/series/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Serie serie = this.series.findById(codigo).get();
		ModelAndView mv = nova(serie);
		mv.addObject(serie);
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(SerieFilter serieFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("serie/PesquisaSeries");
		PageWrapper<Serie> paginaWrapper = new PageWrapper<>(series.filtrar(serieFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Serie serie){
		
		try {
			this.serieService.excluir(serie);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
