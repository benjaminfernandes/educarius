package br.gov.pr.guaira.educacao.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.model.Serie;

@Component
public class SerieConverter implements Converter<String, Serie>{

	@Override
	public Serie convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Serie serie = new Serie();
			serie.setCodigo(Long.valueOf(codigo));
			return serie;
		}
		return null;
	}

}
