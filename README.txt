Premissas:

Para a construcao do projeto tomei como definicoes as seguintes premissas:

 - A aplicacao podera ser acessada por clientes na web e dispositivos mobile
 - A aplicacao precisa ser leve e sue tempo de resposta eficiente
 - A aplicacao precisa possuir uma seguranca minima para evitar ataques

Escolha da arquitetura:

Com base nas premissas, fiz as seguintes escolhas de frameworks:

 - Para que a aplicacao seja leve e possa ser acessada por diversos clientes, defini que os servicos criados seriam em rest devido a sua leveza
 - Para simplificar a comunicacao, a troca de informacoes seguiria o padrao json
 - Como linguagem de desenvolvimento escolhi Java, por ser a linguagem que tenho mais afinidade
 - Como servidor escolhi o apache tomcat 8, devido a sua leveza e baixo custo
 - Para a seguranca, escolhi o spring security pela facilidade de utilizacao e compatibilidade com o servidor utilizado
 - Para a persistencia dos dados, escolhi o banco de dado baseado em grafos Neo4J, pois toda a complexidade da logica de busca do melhor caminho ficaria
   como responsabilidade do banco de dados.
 - Utilizacao do Maven para controle de dependencias.
   
Desenvolvimento:

O desenvolvimento foi iniciado definindo-se o contrato do servico.
Para o servico de persisistencia da malha, defini um objeto de entrada contendo uma lista de objetos nomeados estradas, contendo a informacao dos
pontos de inicio e fim e a distancia entre os mesmos
Para o servico de busca de melhor caminho, as informacoes necessarias sao passadas como parametros da query string.

Por se tratar de um problema classico de grafos (problema de melhor caminho), escolhi como banco de dados o Neo4J. A logica de escolha do melhor caminho
tambem poderia ser contruida na aplicacao, entretanto, a logica precisa trabalhar com todos os dados em memoria e, se tratando de uma aplicacao 
que pode conter milhoes de pontos, trazer todos para a memoria do servidor poderia derrubar a aplicacao.

Defini dois tipos de usuarios para acesso a aplicacao. O usuario user que pode utilizar o servico de busca de melhor caminho, e o usuario admin, responsavel
por efetuar a carga dos dados no sistema.

Problemas encontrados

O unico problema encontrado foi em relacao ao conector do banco de dados. Ate o momento nao consegui resolver o mesmo, tomando como manobra de contorno
o acesso direto ao arquivo do banco (acao recomandada pela propria empresa em sua documentacao)

Controle de excecoes 

O controle de excecoes foi feito utilizando-se excecoes de negocio e retornando para o cliente o status HTTP correspondente.

Testes Unitarios

Os testes unitarios foram construidos utilizando o Junit, visando abranger todos os cenarios possiveis de utilizacao do sistema

Testes de performance

Execuitei testes de performance utilizando o load test do SoapUI com uma malha de 7 pontos

Os teste foram executados utilizando-se 5 threads simultaneas, com um total de 1000 requisicoes

Para o servico de carga de dados, obtive como tempo minimo 16 ms e como tempo maximo 391 ms tendo uma media de 26,91 ms

Para o servico de consulta de dados, obtive como tempo minimo 11 ms e como tempo maximo 44 ms tendo umam media de 19,77 ms

Como executar a aplicacao

Para a execucao da aplicacao sera necessario primeiramente baixar o codigo da aplicacao de minha pagina no github:

https://github.com/thomassalgado/malhaviaria

Para o download do codigo pode ser utilizado o eclipse com o plugin do git instalado ou a propria ferramenta do git

Para a instalacao do banco de dados e necessario baixar e instalar a versao comunity do Neo4J da pagina abaixo

http://neo4j.com/download/

Apos a instalacao do banco de dados e download do codigo fonte, alterar a propriedade malhaviaria.caminho.db do arquivo 
src/main/resorces/malhaviaria.properties da aplicacao com o caminho fisico do banco de dados.

Gerar o arquivo war da aplicacao utilizando o maven ou o eclipse.

Baixar e instalar o servidor TomCat no link abaixo

https://tomcat.apache.org/download-80.cgi

Instalar a aplicacao no servidor

Estou disponibilizando na pasta soapui da aplicacao, um projeto do soapui contendo exemplo de mensagens e os Load Tests criados

Qualquer duvida, estou a disposicao.

Rafael Thomas Salgado