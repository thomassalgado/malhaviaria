Premissas:

Para a constru��o do projeto tomei como defini��es as seguintes premissas:

 - A aplica��o poder� ser acessada por clientes na web e dispositivos mobile
 - A aplica��o precisa ser leve e sue tempo de resposta eficiente
 - A aplica��o precisa possuir uma seguran�a minima para evitar ataques

Escolha da arquitetura:

Com base nas premissas, fiz as seguintes escolhas de Framework:

 - Para que a aplica��o seja leve e possa ser acessada por diversos clientes, defini que os servi�os criados seriam em rest devido a sua leveza
 - Para simplificar a comunica��o, a troca de informa��es seguiria o padr�o json
 - Como linguagem de desenvolvimento escolhi Java, por ser a linguagem que tenho mais afinidade
 - Como servidor escolhi o apache tomcat 8, devido a sua leveza e baixo custo
 - Para a seguran�a, escolhi o spring security pela facilidade de utiliza��o e compatibilidade com o servidor utilizado
 - Para a persist�ncia dos dados, escolhi o banco de dado baseado em grafos Neo4J, pois toda a complexidade da logica de busca do melhor caminho ficaria
   como responsabilidade do banco de dados.
 - Utiliza��o do Maven para controle de depend�ncias.
   
Desenvolvimento:

O desenvolvimento foi iniciado definindo-se o contrato do servi�o.
Para o servi�o de persist�ncia da malha, defini um objeto de entrada contendo uma lista de objetos nomeados estradas, contendo a informa��o dos
pontos de inicio e fim e a distancia entre os mesmos
Para o servi�o de busca de melhor caminho, as informa��es necess�rias s�o passadas como par�metros da query string.

Por se tratar de um problema cl�ssico de grafos (problema de melhor caminho), escolhi como banco de dados o Neo4J. A logica de escolha do melhor caminho
tamb�m poderia ser constru�da na aplica��o, entretanto, a logica precisa trabalhar com todos os dados em memoria e, se tratando de uma aplica��o 
que pode conter milh�es de pontos, trazer todos para a memoria do servidor poderia derrubar a aplica��o.

Defini dois tipos de usu�rios para acesso a aplica��o. O usu�rio user que pode utilizar o servi�o de busca de melhor caminho, e o usu�rio admin, respons�vel
por efetuar a carga dos dados no sistema.

Problemas encontrados

O �nico problema encontrado foi em rela��o ao conector do banco de dados. Ate o momento n�o consegui resolver o mesmo, tomando como manobra de contorno
o acesso direto ao arquivo do banco (a��o recomendada pela pr�pria empresa em sua documenta��o)

Controle de exce��es 

O controle de exce��es foi feito utilizando-se exce��es de negocio e retornando para o cliente o status HTTP correspondente.

Testes Unit�rios

Os testes unit�rios foram constru�dos utilizando o Junit, visando abranger todos os cen�rios poss�veis de utiliza��o do sistema

Testes de performance

Executei testes de performance utilizando o load test do SoapUI com uma malha de 7 pontos

Os teste foram executados utilizando-se 5 threads simult�neas, com um total de 1000 requisi��es

Para o servi�o de carga de dados, obtive como tempo minimo 16 ms e como tempo m�ximo 391 ms tendo uma media de 26,91 ms

Para o servi�o de consulta de dados, obtive como tempo minimo 11 ms e como tempo m�ximo 44 ms tendo uma media de 19,77 ms

Como executar a aplica��o

Para a execu��o da aplica��o sera necess�rio primeiramente baixar o c�digo da aplica��o de minha pagina no github:

https://github.com/thomassalgado/malhaviaria

Para o download do c�digo pode ser utilizado o eclipse com o plugin do git instalado ou a pr�pria ferramenta do git

Para a instala��o do banco de dados � necess�rio baixar e instalar a vers�o comunity do Neo4J da pagina abaixo

http://neo4j.com/download/

Apos a instala��o do banco de dados e download do c�digo fonte, alterar a propriedade malhaviaria.caminho.db do arquivo 
src/main/resorces/malhaviaria.properties da aplica��o com o caminho f�sico do banco de dados.

Gerar o arquivo war da aplica��o utilizando o maven ou o eclipse.

Baixar e instalar o servidor TomCat no link abaixo

https://tomcat.apache.org/download-80.cgi

Instalar a aplica��o no servidor

Estou disponibilizando na pasta soapui da aplica��o, um projeto do soapui contendo exemplo de mensagens e os Load Tests criados

Qualquer duvida, estou a disposi��o.

Rafael Thomas Salgado