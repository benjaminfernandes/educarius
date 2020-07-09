package br.gov.pr.guaira.educacao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pr.guaira.educacao.controller.page.PageWrapper;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.MateriaJaCadastradaException;
import br.gov.pr.guaira.educacao.filter.MateriaFilter;
import br.gov.pr.guaira.educacao.model.Materia;
import br.gov.pr.guaira.educacao.repository.Materias;
import br.gov.pr.guaira.educacao.service.MateriaService;

@Controller
@RequestMapping("/materias")
public class MateriaController {
	
	@Autowired
	private MateriaService materiaService;
	@Autowired
	private Materias materias;

	@GetMapping("/nova")
	public ModelAndView nova(Materia materia) {
		ModelAndView mv = new ModelAndView("materia/CadastroMateria");
		
		return mv;
	}
	
	@PostMapping(value= {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid Materia materia, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return nova(materia);
		}
		
		try {
			this.materiaService.salvar(materia);
		}catch (MateriaJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(materia);
		}
		attributes.addFlashAttribute("mensagem", "Materia salva com sucesso!");
		return new ModelAndView("redirect:/materias/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Materia materia = this.materias.findById(codigo).get();
		ModelAndView mv = nova(materia);
		mv.addObject(materia);
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(MateriaFilter materiaFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable ,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("materia/PesquisaMaterias");
		PageWrapper<Materia> paginaWrapper = new PageWrapper<>(this.materias.filtrar(materiaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Materia materia){
		System.out.println("TESTE EXCLUIR!!!");
		try {
			this.materiaService.excluir(materia);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
}
