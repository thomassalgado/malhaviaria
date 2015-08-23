/**
 * 
 */
package br.com.salgado.exception;

/**
 * Excecao lancada em casos de ponto inexistente
 * @author thoma
 *
 */
public class DadosInconsistentesException extends Exception {

	/**
	 * Construtor
	 * @param mensagem mensagem de erro
	 */
	public DadosInconsistentesException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

}
