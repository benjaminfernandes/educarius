package br.gov.pr.guaira.educacao.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SegurancaController {

	@GetMapping("/login")
	public String login(@AuthenticationPrincipal User user) {
		if(user != null) {
			return "redirect:/aulas/nova";
		}
		return "Login";
	}
	
	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}
	
	@GetMapping("/Logado")
	public String usuarioLogado() {
		
		return "Logado";
	}
}
