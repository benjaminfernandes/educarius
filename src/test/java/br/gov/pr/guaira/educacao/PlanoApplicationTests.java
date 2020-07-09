package br.gov.pr.guaira.educacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlanoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void ajustarUrlVideo() {
		String email="https://www.youtube.com/watch?v=IneKwI27yIc";
	    System.out.println("Provedor:");
	    System.out.println(email.substring(email.indexOf("?v=")+3));
	}
}
