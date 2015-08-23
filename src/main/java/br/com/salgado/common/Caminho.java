/**
 * 
 */
package br.com.salgado.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

/**
 * @author thomas
 * Classe que representa o melhor caminho
 */
public class Caminho {

	private List<String> rota;
	private BigDecimal custo;
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
	 * @return Valorem escala monetaria
	 */
	public final BigDecimal getCusto() {
		if(custo != null){
			custo = custo.setScale(2, RoundingMode.CEILING);
		}
		
		return custo;
	}
	/**
	 * @param custo the custo to set
	 */
	public final void setCusto(BigDecimal custo) {
		this.custo = custo;
	}
	/**
	 * @param rota the rota to set
	 */
	public final void setRota(List<String> rota) {
		this.rota = rota;
	}
}
