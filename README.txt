Premissas:

Para a construção do projeto tomei como definições as seguintes premissas:

 - A aplicação poderá ser acessada por clientes na web e dispositivos mobile
 - A aplicação precisa ser leve e sue tempo de resposta eficiente
 - A aplicação precisa possuir uma segurança minima para evitar ataques

Escolha da arquitetura:

Com base nas premissas, fiz as seguintes escolhas de Framework:

 - Para que a aplicação seja leve e possa ser acessada por diversos clientes, defini que os serviços criados seriam em rest devido a sua leveza
 - Para simplificar a comunicação, a troca de informações seguiria o padrão json
 - Como linguagem de desenvolvimento escolhi Java, por ser a linguagem que tenho mais afinidade
 - Como servidor escolhi o apache tomcat 8, devido a sua leveza e baixo custo
 - Para a segurança, escolhi o spring security pela facilidade de utilização e compatibilidade com o servidor utilizado
 - Para a persistência dos dados, escolhi o banco de dado baseado em grafos Neo4J, pois toda a complexidade da logica de busca do melhor caminho ficaria
   como responsabilidade do banco de dados.
 - Utilização do Maven para controle de dependências.
   
Desenvolvimento:

O desenvolvimento foi iniciado definindo-se o contrato do serviço.
Para o serviço de persistência da malha, defini um objeto de entrada contendo uma lista de objetos nomeados estradas, contendo a informação dos
pontos de inicio e fim e a distancia entre os mesmos
Para o serviço de busca de melhor caminho, as informações necessárias são passadas como parâmetros da query string.

Por se tratar de um problema clássico de grafos (problema de melhor caminho), escolhi como banco de dados o Neo4J. A logica de escolha do melhor caminho
também poderia ser construída na aplicação, entretanto, a logica precisa trabalhar com todos os dados em memoria e, se tratando de uma aplicação 
que pode conter milhões de pontos, trazer todos para a memoria do servidor poderia derrubar a aplicação.

Defini dois tipos de usuários para acesso a aplicação. O usuário user que pode utilizar o serviço de busca de melhor caminho, e o usuário admin, responsável
por efetuar a carga dos dados no sistema.

Problemas encontrados

O único problema encontrado foi em relação ao conector do banco de dados. Ate o momento não consegui resolver o mesmo, tomando como manobra de contorno
o acesso direto ao arquivo do banco (ação recomendada pela própria empresa em sua documentação)

Controle de exceções 

O controle de exceções foi feito utilizando-se exceções de negocio e retornando para o cliente o status HTTP correspondente.

Testes Unitários

Os testes unitários foram construídos utilizando o Junit, visando abranger todos os cenários possíveis de utilização do sistema

Testes de performance

Executei testes de performance utilizando o load test do SoapUI com uma malha de 7 pontos

Os teste foram executados utilizando-se 5 threads simultâneas, com um total de 1000 requisições

Para o serviço de carga de dados, obtive como tempo minimo 16 ms e como tempo máximo 391 ms tendo uma media de 26,91 ms

Para o serviço de consulta de dados, obtive como tempo minimo 11 ms e como tempo máximo 44 ms tendo uma media de 19,77 ms

Como executar a aplicação

Para a execução da aplicação sera necessário primeiramente baixar o código da aplicação de minha pagina no github:

https://github.com/thomassalgado/malhaviaria

Para o download do código pode ser utilizado o eclipse com o plugin do git instalado ou a própria ferramenta do git

Para a instalação do banco de dados é necessário baixar e instalar a versão comunity do Neo4J da pagina abaixo

http://neo4j.com/download/

Apos a instalação do banco de dados e download do código fonte, alterar a propriedade malhaviaria.caminho.db do arquivo 
src/main/resorces/malhaviaria.properties da aplicação com o caminho físico do banco de dados.

Gerar o arquivo war da aplicação utilizando o maven ou o eclipse.

Baixar e instalar o servidor TomCat no link abaixo

https://tomcat.apache.org/download-80.cgi

Instalar a aplicação no servidor

Estou disponibilizando na pasta soapui da aplicação, um projeto do soapui contendo exemplo de mensagens e os Load Tests criados

Qualquer duvida, estou a disposição.

Rafael Thomas Salgado