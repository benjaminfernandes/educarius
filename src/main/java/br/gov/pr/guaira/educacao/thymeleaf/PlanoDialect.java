package br.gov.pr.guaira.educacao.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.gov.pr.guaira.educacao.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.gov.pr.guaira.educacao.thymeleaf.processor.MenuAttributeTagProcessor;
import br.gov.pr.guaira.educacao.thymeleaf.processor.OrderElementTagProcessor;
import br.gov.pr.guaira.educacao.thymeleaf.processor.PaginationElementTagProcessor;

@Component
public class PlanoDialect extends AbstractProcessorDialect{

	public PlanoDialect() {
		super("SETI Portal", "plano", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		return processadores;
	}
}
