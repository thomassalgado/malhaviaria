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
 * @author thomas
 *
 */
@Path("/rota")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MalhaViariaService {
	
	@GET
	public Caminho obterMelhorCaminho(@QueryParam("origem") String origem, @QueryParam("destino") String destino,
			@QueryParam("autonmia") Double autonomia,@QueryParam("valor_litro") Double valorLitro) {

		return DBService.getInstance().melhorCaminho(origem, destino, autonomia, valorLitro);
		
	}

	@PUT
	public boolean inserirMalhaViaria(List<Estrada> malha) {
		
		DBService.getInstance().persitirMalha(malha);
		return true;
		
	}
}
