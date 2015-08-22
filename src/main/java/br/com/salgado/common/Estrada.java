/**
 * 
 */
package br.com.salgado.common;

/**
 * @author thomas
 *
 */
public class Estrada {
	
	public Estrada() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Estrada(String primeiraCidade, String segundaCidade, Double distancia) {
		super();
		this.primeiraCidade = primeiraCidade;
		this.segundaCidade = segundaCidade;
		this.distancia = distancia;
	}
	private String primeiraCidade;
	private String segundaCidade;
	private Double distancia;
	/**
	 * @return the primeiraCidade
	 */
	public final String getPrimeiraCidade() {
		return primeiraCidade;
	}
	/**
	 * @param primeiraCidade the primeiraCidade to set
	 */
	public final void setPrimeiraCidade(String primeiraCidade) {
		this.primeiraCidade = primeiraCidade;
	}
	/**
	 * @return the segundaCidade
	 */
	public final String getSegundaCidade() {
		return segundaCidade;
	}
	/**
	 * @param segundaCidade the segundaCidade to set
	 */
	public final void setSegundaCidade(String segundaCidade) {
		this.segundaCidade = segundaCidade;
	}
	/**
	 * @return the distancia
	 */
	public final Double getDistancia() {
		return distancia;
	}
	/**
	 * @param distancia the distancia to set
	 */
	public final void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	
}
