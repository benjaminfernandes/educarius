package br.gov.pr.guaira.educacao.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.model.Materia;

@Component
public class MateriaConverter implements Converter<String, Materia> {

	@Override
	public Materia convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Materia materia = new Materia();
			materia.setCodigo(Long.valueOf(codigo));
			return materia;
		}
		return null;
	}

}
