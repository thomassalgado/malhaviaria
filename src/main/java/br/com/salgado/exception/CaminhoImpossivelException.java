/**
 * 
 */
package br.com.salgado.exception;

/**
 * Excecao lancada em casos de ponto inexistente
 * @author thoma
 *
 */
public class CaminhoImpossivelException extends Exception {

	/**
	 * Construtor
	 * @param mensagem mensagem de erro
	 */
	public CaminhoImpossivelException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

}
