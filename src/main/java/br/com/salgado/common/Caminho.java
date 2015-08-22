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

	private List<String> rota;
	private Double custo;
	/**
	 * @return the rota
	 */
	public List<String> getRota() {
		if(this.rota == null){
			this.rota = new LinkedList<String>();
		}
		return rota;
	}
	/**
	 * @param rota the rota to set
	 */
	public void setCaminho(List<String> rota) {				
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
