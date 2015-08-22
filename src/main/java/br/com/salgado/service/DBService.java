/**
 * 
 */
package br.com.salgado.service;

import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;

import br.com.salgado.common.Estrada;
import br.com.salgado.common.RelTypes;

/**
 * @author thomas
 *
 */
public class DBService {

	private GraphDatabaseService graphDb;

	private static DBService dbService;

	private DBService() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("C:\\Users\\thoma\\Documents\\Neo4j\\default.graphdb");
		registerShutdownHook(graphDb);
	}

	public static DBService getInstance() {
		if (dbService == null) {
			dbService = new DBService();
		}

		return dbService;
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	/**
	 * @param args
	 */
	public void persitirMalha(final List<Estrada> estradas) {


		try (Transaction tx = graphDb.beginTx()) {

			IndexManager index = graphDb.index();
			Index<Node> cidades = index.forNodes("cidades");
			
			for (Estrada estrada : estradas) {

				IndexHits<Node> hitsPrimeiraCidade = cidades.get("nome", estrada.getPrimeiraCidade());
				Node primeiraCidade = hitsPrimeiraCidade.getSingle();

				if (primeiraCidade == null) {
					primeiraCidade = graphDb.createNode();
					primeiraCidade.setProperty("nome", estrada.getPrimeiraCidade());
					cidades.add( primeiraCidade, "nome", primeiraCidade.getProperty( "nome" ) );
				}

				IndexHits<Node> hitsSegundaCidade = cidades.get("nome", estrada.getSegundaCidade());
				Node segundaCidade = hitsSegundaCidade.getSingle();

				if (segundaCidade == null) {
					segundaCidade = graphDb.createNode();
					segundaCidade.setProperty("nome", estrada.getSegundaCidade());
					cidades.add( segundaCidade, "nome", segundaCidade.getProperty( "nome" ) );
				}

				Relationship relation = primeiraCidade.createRelationshipTo(segundaCidade, RelTypes.LIGASE);
				relation.setProperty("distancia", estrada.getDistancia());
			}
			tx.success();
		}
	}

	public void apagarMalha() {

		try (Transaction tx = graphDb.beginTx()) {
			@SuppressWarnings("deprecation")
			Iterable<Node> iterator = graphDb.getAllNodes();
			for (Node node : iterator) {
				for(Relationship rel : node.getRelationships())
					rel.delete();
				node.delete();
			}
			tx.success();
		};

	}

}
