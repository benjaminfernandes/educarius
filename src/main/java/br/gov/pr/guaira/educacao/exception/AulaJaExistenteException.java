package br.gov.pr.guaira.educacao.exception;

public class AulaJaExistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AulaJaExistenteException(String msg) {
		super(msg);
	}
}
