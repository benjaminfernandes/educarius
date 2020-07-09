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
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.filter.SemanaFilter;
import br.gov.pr.guaira.educacao.model.Semana;
import br.gov.pr.guaira.educacao.repository.Semanas;
import br.gov.pr.guaira.educacao.service.SemanaService;
import br.gov.pr.guaira.educacao.validation.DataValidacaoException;

@Controller
@RequestMapping("/semanas")
public class SemanaController {
	
	@Autowired
	private SemanaService semanaService;
	@Autowired
	private Semanas semanas;

	@GetMapping("/nova")
	public ModelAndView nova(Semana semana) {
		ModelAndView mv = new ModelAndView("semana/CadastroSemana");
		
		return mv;
	}
	
	@PostMapping(value = {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid Semana semana, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return nova(semana);
		}
		try {
			this.semanaService.salvar(semana);
		}catch (DataValidacaoException e) {
			result.rejectValue("codigo", e.getMessage(), e.getMessage());
			return nova(semana);
		}
		
		attributes.addFlashAttribute("mensagem", "Semana criada com sucesso!");
		return new ModelAndView("redirect:/semanas/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Semana semana = this.semanas.findById(codigo).get();
		ModelAndView mv = nova(semana);
		mv.addObject(semana);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Semana semana){
		
		try {
			this.semanaService.excluir(semana);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ModelAndView pesquisar(SemanaFilter semanaFilter, BindingResult binding,@PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("semana/PesquisaSemanas");
		PageWrapper<Semana> paginaWrapper = new PageWrapper<>(this.semanas.filtrar(semanaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
}
