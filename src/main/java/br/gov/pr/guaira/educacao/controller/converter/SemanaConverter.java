package br.gov.pr.guaira.educacao.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.model.Semana;

@Component
public class SemanaConverter implements Converter<String, Semana> {

	@Override
	public Semana convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Semana semana = new Semana();
			semana.setCodigo(Long.valueOf(codigo));
			return semana;
		}
		return null;
	}
}
