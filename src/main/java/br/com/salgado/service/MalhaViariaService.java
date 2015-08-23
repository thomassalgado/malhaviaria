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
import javax.ws.rs.core.Response;

import br.com.salgado.common.Caminho;
import br.com.salgado.common.Estrada;
import br.com.salgado.exception.CaminhoImpossivelException;
import br.com.salgado.exception.DadosInconsistentesException;

/**
 * Classe responsavel por disponbilizar os servicos REST
 * 
 * @author thomas
 *
 */
@Path("rota")
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
	@Path("/busca")
	public Response obterMelhorCaminho(@QueryParam("origem") final String origem, @QueryParam("destino") final String destino,
			@QueryParam("autonmia") final Double autonomia,@QueryParam("valor_litro") final Double valorLitro) {

		Response response = null;
		
		try {
			final Caminho caminho = DBService.getInstance().melhorCaminho(origem, destino, autonomia, valorLitro);
			response = Response.status(Response.Status.OK).entity(caminho).build();
		} catch (CaminhoImpossivelException | DadosInconsistentesException e) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		
		return response;
		
	}

	/**
	 * Metodo responsavel pela insercao da malha viaria no banco de dados
	 * 
	 * @param malha
	 *            Malha viaria contendo a lista de 2 pontos e a distancia entre
	 *            eles
	 * @return true em caso de sucesso
	 */
	@PUT
	@Path("/admin")
	public Response inserirMalhaViaria(final List<Estrada> malha) {
		
		Response response = null;
		
		try {
			DBService.getInstance().persitirMalha(malha);
			response = Response.status(Response.Status.OK).entity(true).build();
		} catch (DadosInconsistentesException e) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return response;

	}
}
