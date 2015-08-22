/**
 * 
 */
package br.com.salgado.common;

import java.util.LinkedList;
import java.util.List;

/**
 * @author thomas
 *
 */
public class Caminho {

	private List<Character> rota;
	private Double custo;
	/**
	 * @return the rota
	 */
	public List<Character> getRota() {
		if(this.rota == null){
			this.rota = new LinkedList<>();
		}
		return rota;
	}
	/**
	 * @param rota the rota to set
	 */
	public void setCaminho(List<Character> rota) {				
		this.rota = rota;
	}
	/**
	 * @return the custo
	 */
	public Double getCusto() {
		return custo;
	}
	/**
	 * @param custo the custo to set
	 */
	public void setCusto(Double custo) {
		this.custo = custo;
	}
}
