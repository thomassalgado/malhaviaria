/**
 * 
 */
package br.com.salgado.common;

import java.util.ResourceBundle;

/**
 * @author thomas
 * Classe de constantes
 *
 */
public final class Constantes {
	
	/**
	 * Propriedade que referencia o nome de cada no do grafo no banco de dados
	 */
	public static final String NOME = "nome";

	/**
	 * Propriedade que referencia a distancia entre dois nos do grafo no banco de dados
	 */
	public static final String DISTANCIA = "distancia";
	
	/**
	 * Propriedade que referencia o indice de relacionamentos do grafo no banco de dados
	 */
	public static final String RELACIONAMENTOS = "relacionamentos";
	
	/**
	 * Propriedade que referencia um relacionamento no grafo no banco de dados
	 */
	public static final String RELACIONAMENTO = "relacionamento";

	/**
	 * Propriedade que referencia o indice de cidades do grafo no banco de dados
	 */
	public static final String CIDADES = "cidades";
	
	/**
	 * Caminho fisico do banco de dados
	 */
	public static final String CAMINHO_DB = ResourceBundle.getBundle("malhaviaria").getString("malhaviaria.caminho.db");//properties.getProperty("malhaviaria.caminho.db");
}
