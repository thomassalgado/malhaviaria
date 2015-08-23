/**
 * 
 */
package br.com.salgado.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.salgado.common.Caminho;
import br.com.salgado.common.Estrada;

/**
 * Classe responsavel por disponbilizar os servicos REST
 * @author thomas
 *
 */
@Path("/rota")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MalhaViariaService {
	
	/**
	 * Metodo responsavel por obter o melhor caminho para a entrega
	 * @param origem Ponto de origem
	 * @param destino Ponto destino
	 * @param autonomia Valor em KM que o veiculo consegue rodar com 1L de combutivel
	 * @param valorLitro Valor do litro do combstivel
	 * @return Lista de pontos representando o melhor caminho e o custo da viagem
	 */
	@GET
	public Caminho obterMelhorCaminho(@QueryParam("origem") String origem, @QueryParam("destino") String destino,
			@QueryParam("autonmia") Double autonomia,@QueryParam("valor_litro") Double valorLitro) {

		return DBService.getInstance().melhorCaminho(origem, destino, autonomia, valorLitro);
		
	}

	/**
	 * Metodo responsavel pela insercao da malha viaria no banco de dados
	 * @param malha Malha viaria contendo a lista de 2 pontos e a distancia entre eles
	 * @return true em caso de sucesso
	 */
	@PUT
	public boolean inserirMalhaViaria(List<Estrada> malha) {
		
		DBService.getInstance().persitirMalha(malha);
		
		return true;
		
	}
}
