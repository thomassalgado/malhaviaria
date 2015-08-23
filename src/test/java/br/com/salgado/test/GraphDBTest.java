/**
 * 
 */
package br.com.salgado.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.salgado.common.Caminho;
import br.com.salgado.common.ConstantesDeMensagens;
import br.com.salgado.common.Estrada;
import br.com.salgado.exception.CaminhoImpossivelException;
import br.com.salgado.exception.DadosInconsistentesException;
import br.com.salgado.service.DBService;

/**
 * 
 * Classe de testes das funcionalidades da aplicacao
 * @author thomas
 *
 */
public class GraphDBTest {

	private static List<Estrada> malhaDeTeste;
	private static BigDecimal custo;
	private static List<String> cidades;
	private static String testNode1 = "testNode1";
	private static String testNode2 = "testNode2";
	private static String testNode3 = "testNode3";
	private static String testNode4 = "testNode4";
	private static String testNode5 = "testNode5";
	private static String testNodeX = "testNodeX";
	private static String testNodeZ = "testNodeZ";
	private static String CidadeInexistente = "CidadeInexistente";
	
	/**
	 * Preenchimento de dados necessarios para os testes
	 */
	@BeforeClass
	public static void preencherDados(){
		
		
		
		malhaDeTeste = new LinkedList<Estrada>();
		final Estrada estrada1 = new Estrada(testNode1, testNode2, 10D);
		malhaDeTeste.add(estrada1);
		final Estrada estrada2 = new Estrada(testNode2, testNode3, 20D);
		malhaDeTeste.add(estrada2);
		final Estrada estrada3 = new Estrada(testNode1, testNode3, 30D);
		malhaDeTeste.add(estrada3);
		final Estrada estrada4 = new Estrada(testNode3, testNode4, 10D);
		malhaDeTeste.add(estrada4);
		final Estrada estrada5 = new Estrada(testNode1, testNode5, 35D);
		malhaDeTeste.add(estrada5);
		final Estrada estrada6 = new Estrada(testNodeX, testNodeZ, 10D);
		malhaDeTeste.add(estrada6);
		cidades = new ArrayList<String>();
		cidades.add(testNode1);
		cidades.add(testNode2);
		cidades.add(testNode3);
		cidades.add(testNode4);
		cidades.add(testNode5);
		cidades.add(testNodeX);
		cidades.add(testNodeZ);
		custo = new BigDecimal("9.84");
	}
	
	/**
	 * Testando a carga de novos nodes 
	 */
	@Test
	public void testCargaNodes(){
		try {
			DBService.getInstance().persitirMalha(malhaDeTeste);
		} catch (DadosInconsistentesException e) {
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	/**
	 * Testando a recarga de novos nodes
	 */
	@Test
	public void testRecargaNodes(){
		try {
			DBService.getInstance().persitirMalha(malhaDeTeste);
		} catch (DadosInconsistentesException e) {
			Assert.fail();
		}
		Assert.assertTrue(true);
	}
	
	/**
	 * Verificando validacao de dados inconsistentes
	 */
	@Test
	public void testDadosInconsistentes(){
		malhaDeTeste.get(0).setPrimeiraCidade(null);
		try {
			DBService.getInstance().persitirMalha(malhaDeTeste);
		} catch (DadosInconsistentesException e) {
			Assert.assertTrue(ConstantesDeMensagens.DADOS_INCONSISTENTES.equals(e.getMessage()));
		}
	}
	
	/**
	 * Validando melhor caminho
	 */
	@Test
	public void testMelhorCaminho(){
		try {
			Caminho caminho = DBService.getInstance().melhorCaminho(testNode1, testNode4, 12D, 2.95D);
			Assert.assertTrue(custo.equals(caminho.getCusto()));
			Assert.assertTrue(testNode1.equals(caminho.getRota().get(0)));
			Assert.assertTrue(testNode3.equals(caminho.getRota().get(1)));
			Assert.assertTrue(testNode4.equals(caminho.getRota().get(2)));
			Assert.assertTrue(custo.equals(caminho.getCusto()));
			Assert.assertTrue(custo.equals(caminho.getCusto()));
		} catch (CaminhoImpossivelException e) {
			Assert.fail();
		} catch (DadosInconsistentesException e) {
			Assert.fail();
		}
	}

	/**
	 * Validando cidade de origem inexistente
	 */
	@Test
	public void testCidadeOrigemNaoExiste(){
		try {
			DBService.getInstance().melhorCaminho(CidadeInexistente, testNode3, 12D, 2.95D);
		} catch (CaminhoImpossivelException e) {
			Assert.assertTrue(ConstantesDeMensagens.ORIGEM_INEXISTENTE.equals(e.getMessage()));
		} catch (DadosInconsistentesException e) {
			Assert.fail();
		}
	}

	/**
	 * Validando cidade de destino inexistente
	 */
	@Test
	public void testCidadeDestinoNaoExiste(){
		try {
			DBService.getInstance().melhorCaminho(testNode3, CidadeInexistente, 12D, 2.95D);
		} catch (CaminhoImpossivelException e) {
			Assert.assertTrue(ConstantesDeMensagens.DESTINO_INEXISTENTE.equals(e.getMessage()));
		} catch (DadosInconsistentesException e) {
			Assert.fail();
		}
	}

	/**
	 * Validando dados de busca inconsistentes
	 */
	@Test
	public void testDadosDeBuscaInconsistentes(){
		try {
			DBService.getInstance().melhorCaminho(null, CidadeInexistente, 0D, 2.95D);
		} catch (CaminhoImpossivelException e) {
			Assert.fail();			
		} catch (DadosInconsistentesException e) {
			Assert.assertTrue(ConstantesDeMensagens.DADOS_INCONSISTENTES.equals(e.getMessage()));
		}
	}

	/**
	 * Validando caminho inexistente
	 */
	@Test
	public void testNaoExisteCaminho(){
		try {
			DBService.getInstance().melhorCaminho(testNode3, testNodeZ, 12D, 2.95D);
		} catch (CaminhoImpossivelException e) {
			Assert.assertTrue(ConstantesDeMensagens.CAMINHO_INEXISTENTE.equals(e.getMessage()));
		} catch (DadosInconsistentesException e) {
			Assert.fail();
		}
	}
	
	/**
	 * Removendo dados de teste
	 */
	@AfterClass
	public static void removerNodesDeTeste(){
		DBService.getInstance().apagarNodes(cidades);
	}	
}
