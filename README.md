# Projeto Gerador de Insights
# Descrição do Problema
No contexto da operação de atendimento, é fundamental que os gestores possam acompanhar o trabalho dos operadores para garantir a qualidade do atendimento e a performance da equipe. Para isso, é necessário realizar o processamento dos dados de conversas e criar visualizações gráficas que apresentem métricas relevantes de operações.

# Conjunto de Dados
O conjunto de dados consiste em um arquivo CSV (Comma Separated Values), contendo informações das conversas, como a data de criação e o status atual no Smart Care. Os status possíveis são: Pendente, Aberto, Em Espera, Respondido, Fechado e Ignorado. Cada conversa possui um valor de status que indica o estado atual da conversa no sistema.

# Objetivos do Projeto
- Transformar o modelo de dados do CSV para um formato estruturado, facilitando a comunicação e a leitura por diferentes aplicações.
- Armazenar os dados em um banco de dados estruturado de preferência (SQL ou NoSQL).
- Criar um serviço para realizar as operações de CRUD (Create, Read, Update, Delete) das conversas nesse formato.
- Criar um endpoint que liste todas as conversas.
- Utilizar a biblioteca Stanford CoreNLP para realizar a análise de sentimentos das mensagens das conversas.
- Gerar insights por meio da contagem de ocorrências dos diferentes status das conversas.
- Implementar uma API que forneça esses dados e insights.

# Principais métodos utilizados no projeto são:
- lerArquivoCSV(): Este método é responsável por ler o arquivo CSV contendo as conversas. Ele utiliza a biblioteca OpenCSV para fazer a leitura e retorna uma lista de objetos Conversas, contendo os dados das conversas.

- analiseDeSentimentosStrategy(): Esse método realiza a análise de sentimentos das mensagens das conversas. Ele utiliza a biblioteca Stanford CoreNLP para processar as mensagens e calcular o sentimento médio das conversas.
- processarMensagem(): Esse método processa uma mensagem utilizando o pipeline do Stanford CoreNLP. Ele itera sobre as frases da mensagem, extrai o sentimento de cada frase e realiza a soma dos sentimentos.
- calcularMedia(): Esse método calcula a média dos sentimentos das mensagens a partir da soma dos sentimentos e do número de frases processadas.
- converterSentimentoEmNumero(): Esse método converte o sentimento (representado por uma string) em um valor numérico, que varia de -1 a 1.

- contagemDeOcorrencias(): Esse método de contagem de ocorrências é usado para fornecer informações sobre a distribuição dos diferentes status das conversas. Esse método permite visualizar a quantidade de conversas em cada status, oferecendo insights sobre o andamento da operação de atendimento.

* Além dos métodos CRUD(Criar, atualizar, buscar e deletar). 

# Funcionalidades
- Análise de Sentimentos
- Contagem de Ocorrências
- Armazenamento Estruturado
- CRUD para Conversas
- API de Acesso aos Dados
- Visualização de Dados

# Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- StanfordCoreNLP
- Docker
- MongoDB
- Postman
- DataGrip
- Git e GitHub

# Estrutura da API
A API do projeto Gerador de Insights foi desenvolvida seguindo boas práticas de arquitetura e design de software. Foram utilizados diversos conceitos e padrões para garantir a modularidade, escalabilidade e facilidade de manutenção do código.

# Paradigma de Programação Orientada a Objetos:
A API foi desenvolvida com base no paradigma de programação orientada a objetos (POO), o qual enfatiza a organização do código em classes, objetos, herança, encapsulamento e polimorfismo. A POO permite uma estrutura de código mais modular, reutilizável e de fácil entendimento.

#  Bibliotecas e Frameworks:

- Spring Framework:
O Spring é um framework de desenvolvimento Java amplamente utilizado. Ele oferece recursos como injeção de dependência, controle transacional, criação de API RESTful e suporte a testes unitários.

- StanfordCoreNLP:
Essa biblioteca foi utilizada para realizar a análise de sentimentos das mensagens das conversas. Ela fornece ferramentas de processamento de linguagem natural para extrair informações semânticas e sintáticas.

# Classes Enums:
- Foram utilizadas classes enums para representar conceitos fixos no projeto, como os status das conversas. Essas classes enums permitem definir um conjunto de valores pré-determinados e fornecer uma forma consistente de representação desses valores ao longo do código.

#  Tratamento de Exceptions
- O tratamento de exceções é feito em diferentes camadas, desde os controllers até os serviços, para garantir a integridade da aplicação.

#  Testes Unitários e Testes de Integração:
- Os testes unitários verificam o comportamento correto das classes e métodos isoladamente. Além disso, foram desenvolvidos testes de integração que verificam a interação correta entre as diferentes partes da API, como os controllers, serviços e banco de dados.

#  Injeção de Dependências e Inversão de Controle
- Com a ajuda do Spring Framework, é possível declarar as dependências e deixar que o framework as injete automaticamente, facilitando a manutenção, testabilidade e flexibilidade do código.

#  Logs:
- Através dos logs, é possível rastrear o fluxo de execução da aplicação e identificar possíveis problemas ou erros. Os logs também fornecem informações úteis para o monitoramento do sistema em produção.

- Entre outros pontos da estrutura como pacote Strategy, orientação a interfaces e não a implementações. Tentei deixar a API o maximo possivel reutilizavel e de manutenção fácil. 

# Configuração do Ambiente de Desenvolvimento
Para utilizar a API do projeto Gerador de Insights, siga os passos abaixo para configurar o ambiente corretamente:

# Pré-requisitos
- Git: Certifique-se de ter o Git instalado no seu sistema. Você pode fazer o download do Git em: https://git-scm.com/downloads
- Docker: O Docker é necessário para executar a aplicação em um contêiner. Certifique-se de ter o Docker instalado e funcionando corretamente. Você pode encontrar instruções de instalação para o seu sistema operacional em: https://docs.docker.com/get-docker/

# Passo 1: Clone o repositório
- Abra o terminal ou prompt de comando.

- Navegue até o diretório onde você deseja clonar o repositório.

- Execute o seguinte comando para clonar o repositório:
 ( git clone https://github.com/pauloren7/Desafio-TakeBlip )
 
# Passo 2: Inicialize a aplicação com Docker Compose
- Navegue até o diretório do projeto clonado: 
( cd gerador-de-insights )
- Execute o seguinte comando para construir e iniciar os contêineres Docker:
( docker-compose up ) Aguarde enquanto o Docker Compose baixa as imagens e configura o ambiente.

# Passo 3: Importe as collections no software de testes de API
- As Collections para o Postman se encontram no diretorio do projeto, você pode fazer a importação direto para o Postman após fazer o clone do projeto na sua maquina ou procurar no seguinte link: [collections-insights-api.postman_collection.json](https://github.com/pauloren7/Desafio-TakeBlip/tree/main/sumarizacao/Collections)
- Abra o software de testes de API (como o Postman) e importe a collection baixada. Isso importará todas as requisições pré-configuradas para facilitar o uso da API.

# Passo 4: Adicione o arquivo CSV através do endpoint

* Obs: Para que a analise de sentimentos seja feita de forma mais precisa é preferivel que as mensagens a serem analisadas estejam na lingua inglesa. A biblioteca Stanford CoreNLP ainda não possui suporte para o português.
* Obs: Disponibilizei no diretório "ArquivosCSVParaTeste" alguns arquivos, um com mensagens de tom neutro, outro com tom negativo e outro com tom positivo. Caso queira testar suas proprias mensagens, construa um arquivo CSV com a mesma estrutura que há nos testes, para que a API consiga ler e atribuir os dados aos campos corretos.

- Com a aplicação em execução, abra o software de testes de API (Postman).
- Selecione a requisição "carregar-arquivo-csv" na collection importada.
- Selecione o método "POST" e o endpoint correspondente ao upload do arquivo CSV.
- Clique em "Body" e selecione o tipo de dados "form-data".
- Adicione uma chave chamada "file" e selecione o arquivo CSV que você deseja enviar.
- Envie a requisição para adicionar o arquivo CSV à API.

Com esses passos, você configurará corretamente o ambiente, importará as collections para o software de testes de API e estará pronto para utilizar a API do projeto Gerador de Insights.

# Agradecimentos
Gostaria de expressar minha sincera gratidão à empresa Take Blip pela oportunidade de participar deste processo seletivo e trabalhar no projeto Gerador de Insights. Estou extremamente contente por ter tido a oportunidade de colaborar neste desafio, onde pude aplicar e aprimorar meus conhecimentos como programador.

Durante o desenvolvimento deste projeto, enfrentei algumas dificuldades, mas com dedicação, pesquisa em documentações, consegui superá-las com sucesso. As dificuldades encontradas ao longo do caminho me desafiaram a buscar soluções criativas e a aprender novos conceitos. Essas dificuldades se mostraram oportunidades valiosas para o meu crescimento profissional e foi extremamente enriquecedor e fortaleceu ainda mais minha paixão pela programação.

Estou animado com o que aprendi ao longo do processo e como pude aplicar esses conhecimentos em um contexto real.

# Outras Informações

**Swagger**: http://localhost:8082/swagger-ui/index.html
