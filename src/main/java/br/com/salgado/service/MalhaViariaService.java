/**
 * 
 */
package br.com.salgado.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.salgado.common.Caminho;

/**
 * @author thomas
 *
 */
@Path("/service")
public class MalhaViariaService {

	@GET
	@Path("/rota")
	@Produces(MediaType.APPLICATION_JSON)
	public Caminho getMsg(@QueryParam("inicio") String inicio, @QueryParam("fim") String fim) {
		final Caminho caminho = new Caminho();

		caminho.getRota().add('A');
		caminho.getRota().add('D');
		caminho.getRota().add('E');
		caminho.getRota().add('F');
		caminho.setCusto(103.98D);

		return caminho;
	}

}
