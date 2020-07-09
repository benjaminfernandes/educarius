package br.gov.pr.guaira.educacao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrosController {

	@GetMapping("/400")
	public String paginaNaoEncontrada() {
		return "404";
	}
}
