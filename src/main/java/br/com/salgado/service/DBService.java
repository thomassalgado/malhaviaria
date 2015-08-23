/**
 * 
 */
package br.com.salgado.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;

import br.com.salgado.common.Caminho;
import br.com.salgado.common.Constantes;
import br.com.salgado.common.Estrada;
import br.com.salgado.common.RelTypes;
import br.com.salgado.exception.CaminhoImpossivelException;
import br.com.salgado.exception.DadosInconsistentesException;

/**
 * Classe responsavel por efeuar a conexao com o banco de dados e executar os
 * metodos necessario para persistencia e busca das malhas viarias
 * 
 * @author thomas
 *
 */
public class DBService {

	/**
	 * Classe de conexao com o banco de dados (Graph Database)
	 */
	private GraphDatabaseService graphDb;

	/**
	 * Referencia interna (Singleton)
	 */
	private static DBService dbService;

	/**
	 * Construor privado (Singleton)
	 */
	private DBService() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Constantes.CAMINHO_DB);
		registerShutdownHook(graphDb);
	}

	/**
	 * Singleton
	 * 
	 * @return Referencia unica do objeto
	 */
	public static DBService getInstance() {
		if (dbService == null) {
			dbService = new DBService();
		}

		return dbService;
	}

	/**
	 * Metodo responsavel por efetuar o shutdown do banco
	 * 
	 * @param graphDb
	 *            Referencia do banco
	 */
	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	/**
	 * Metodo responsavel por persistir a malha viaria no banco de dados
	 * 
	 * @param estradas
	 *            lista de ligacoes entre as cidades
	 * @throws DadosInconsistentesException 
	 */
	public void persitirMalha(final List<Estrada> estradas) throws DadosInconsistentesException {

		try (Transaction tx = graphDb.beginTx()) {

			IndexManager index = graphDb.index();
			Index<Node> cidades = index.forNodes(Constantes.CIDADES);
			Index<Relationship> relacionamentos = index.forRelationships(Constantes.RELACIONAMENTOS);

			for (Estrada estrada : estradas) {

				if(StringUtils.isEmpty(estrada.getPrimeiraCidade()) || StringUtils.isEmpty(estrada.getSegundaCidade()) || estrada.getDistancia() == null){
					throw new DadosInconsistentesException("Os dados da malha estao inconsistentes, por favor verifique");
				}
				
				IndexHits<Node> hitsPrimeiraCidade = cidades.get(Constantes.NOME, estrada.getPrimeiraCidade());
				Node primeiraCidade = hitsPrimeiraCidade.getSingle();

				if (primeiraCidade == null) {
					primeiraCidade = graphDb.createNode();
					primeiraCidade.setProperty(Constantes.NOME, estrada.getPrimeiraCidade());
					cidades.add(primeiraCidade, Constantes.NOME, primeiraCidade.getProperty(Constantes.NOME));
				}

				IndexHits<Node> hitsSegundaCidade = cidades.get(Constantes.NOME, estrada.getSegundaCidade());
				Node segundaCidade = hitsSegundaCidade.getSingle();

				if (segundaCidade == null) {
					segundaCidade = graphDb.createNode();
					segundaCidade.setProperty(Constantes.NOME, estrada.getSegundaCidade());
					cidades.add(segundaCidade, Constantes.NOME, segundaCidade.getProperty(Constantes.NOME));
				}

				IndexHits<Relationship> hitsRelExistente = relacionamentos.get(Constantes.RELACIONAMENTO,
						estrada.getPrimeiraCidade() + "/" + estrada.getSegundaCidade());
				Relationship relExistente = hitsRelExistente.getSingle();

				if (relExistente == null) {
					hitsRelExistente = relacionamentos.get(Constantes.RELACIONAMENTO,
							estrada.getSegundaCidade() + "/" + estrada.getPrimeiraCidade());
					relExistente = hitsRelExistente.getSingle();
					if (relExistente == null) {
						Relationship relation = primeiraCidade.createRelationshipTo(segundaCidade, RelTypes.LIGASE);
						relation.setProperty(Constantes.DISTANCIA, estrada.getDistancia());
						relacionamentos.add(relation, Constantes.RELACIONAMENTO, estrada.getPrimeiraCidade() + "/" + estrada.getSegundaCidade());
					} else {
						relExistente.setProperty(Constantes.DISTANCIA, estrada.getDistancia());
					}
				} else {
					relExistente.setProperty(Constantes.DISTANCIA, estrada.getDistancia());
				}
			}

			tx.success();
		}
	}

	/**
	 * Metodo auxiliar para apagar as informacoes do banco. Utilizado somente em
	 * testes
	 */
	public void apagarMalha() {

		try (Transaction tx = graphDb.beginTx()) {
			@SuppressWarnings("deprecation")
			Iterable<Node> iterator = graphDb.getAllNodes();
			for (Node node : iterator) {
				for (Relationship rel : node.getRelationships())
					rel.delete();
				node.delete();
			}
			tx.success();
		}
		;

	}

	/**
	 * Metodo responsavel por encontrar o melhor caminho entre dois pontos
	 * 
	 * @param origem
	 *            Ponto de origem
	 * @param destino
	 *            Ponto destino
	 * @param autonomia
	 *            Autonomia do veiculo em KM/L
	 * @param valorLitro
	 *            Valor do litro do combustivel
	 * @return Lista contendo os pontos do melhor caminho e custo da viagem
	 * @throws CaminhoImpossivelException em caso de Ponto nao encontrado
	 */
	public Caminho melhorCaminho(final String origem, final String destino, final Double autonomia, final Double valorLitro) throws CaminhoImpossivelException {

		Caminho caminho = new Caminho();

		try (Transaction tx = graphDb.beginTx()) {

			IndexManager index = graphDb.index();
			Index<Node> cidades = index.forNodes(Constantes.CIDADES);

			IndexHits<Node> hitsPrimeiraCidade = cidades.get(Constantes.NOME, origem);
			Node cidadeOrigem = hitsPrimeiraCidade.getSingle();

			if(cidadeOrigem == null){
				throw new CaminhoImpossivelException("Ponto de Origem Inexistente");
			}
			
			IndexHits<Node> hitsSegundaCidade = cidades.get(Constantes.NOME, destino);
			Node cidadeDestino = hitsSegundaCidade.getSingle();

			if(cidadeDestino == null){
				throw new CaminhoImpossivelException("Ponto de Destino Inexistente");
			}
			
			PathFinder<WeightedPath> finder = GraphAlgoFactory
					.dijkstra(PathExpanders.forTypeAndDirection(RelTypes.LIGASE, Direction.BOTH), Constantes.DISTANCIA);

			WeightedPath path = finder.findSinglePath(cidadeOrigem, cidadeDestino);

			if(path == null){
				throw new CaminhoImpossivelException("Nao existe caminho possivel entre os dois pontos");
			}
			
			for (Node node : path.nodes()) {
				caminho.getRota().add(node.getProperty(Constantes.NOME).toString());
			}

			Double custo = (path.weight() / autonomia) * valorLitro;
			
			caminho.setCusto(new BigDecimal(custo.doubleValue()));

			tx.success();
		}

		return caminho;

	}
}
