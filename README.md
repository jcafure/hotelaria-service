# Desafio Backend

## `Proposta:`

Desenvolver uma aplicação (Somente o backend) que possibilite realizar o cadastro de
hóspedes e o check in.

Queremos ver como você resolve problemas no seu dia-a-dia. Não há necessidade de
desenvolver o frontend da aplicação, vamos utilizar o Postman para testar sua aplicação.

 `Requisitos Funcionais`

    • Um CRUDL para o cadastro de hóspedes;
    • No check in deve ser possível buscar hóspedes cadastrados pelo nome, documento ou telefone;
    • Consultar hóspedes que já realizaram o check in e não estão mais no hotel;
    • Consultar hóspedes que ainda estão no hotel;
    • As consultas devem apresentar o valor (valor total e o valor da última hospedagem) já gasto pelo hóspede no hotel.
    
    `Regras de negócio` 

    • Uma diária no hotel de segunda à sexta custa R$120,00;
    • Uma diária no hotel em finais de semana custa R$150,00;
    • Caso a pessoa precise de uma vaga na garagem do hotel há um acréscimo diário, 
      sendo R$15,00 de segunda à sexta e R$20,00 nos finais de semana;
    • Caso o horário da saída seja após às 16:30h deve ser cobrada uma diária extra.
    
     `Expectativas` 

    • Desenvolva um sistema em Java, com banco de dados PostgreSQL e serviços Rest.
    • Desenvolva o problema utilizando frameworks, bibliotecas e/ou componentes que você
      julgue adequado para resolver o problema;
    • Caso seja preciso realizar algum build ou algum passo extra para gerar a sua solução, você deve detalhar 
      o que deve ser feito no arquivo README.md de seu projeto.
      
## `Descrições técnicas`

  Projeto construido na linguagem Java utilizando a versão 8. 

Tecnologias utilizadas :

 - Spring Boot.
 - Gerenciador de dependencias e build, Maven.
 - Banco de dados Postgresql.
 - Lombok
 - Swagger para a geração da documentação. 
 - Interface de comunicação REST.
 
 ## `Requisitos do sistema`

Requisitos

- Maven
- Java 8
- Lombok

### Gerando o Pacote

Sendo um projeto Maven, execute os goals clean e install na raiz do projeto para baixar as dependências e gerar jar do projeto.

    #!/hotelaria-service
    $ mvn clean install
 
 Executando o Jar
Como se trata de um projeto Spring Boot, podemos simplismente executar o jar que foi gerado na pasta target e a 
aplicação irá subir em um tomcat embedded.

    #!/hotelaria-service/target
    $ java -jar hotelaria-service-0.0.1-SNAPSHOT.jar
    
    Configuração da porta da api se encontra no application.yml:
  port: 9000
    
    Após iniciar aplicação a documentação gerada com Swagger estará disponível automaticamente no endereço:
        http://localhost:9000/hotelaria/v1/swagger-ui.html#/
        
         datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/desafiohotel
    username: postgres
    password: root
        
 # `Exemplos:`

Inserção de Hóspede:

         curl -X POST "http://localhost:9000/hotelaria/v1/hospedes/novo-hospede" -H "accept: */*" -H "Content-Type:        application/json" -d "{ \"documento\": \"123456\", \"id\": 0, \"nome\": \"Jaime A\", \"telefone\": \"92001578\"}"
         
saída esperada:


         {
        "id": 12,
        "nome": "Jaime A",
        "documento": "123456",
        "telefone": "92001578"
      }
      
Inserção de Checkin:
   
    curl -X POST "http://localhost:9000/hotelaria/v1/checkins/checkin-entrada" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"adicionaVeiculo\": true, \"checkout\": true, \"dataCheckin\": \"2020-01-19T18:51:07.830Z\", \"dataCheckout\": \"2020-01-22T18:51:07.830Z\", \"hospede\": { \"documento\": \"string\", \"id\": 12, \"nome\": \"string\", \"telefone\": \"string\" }, \"id\": 0, \"valorTotal\": 0}"
    
saída esperada: 


     {
     "id": 11,
     "dataCheckin": "2020-01-19T18:51:07.83",
     "dataCheckout": "2020-01-22T18:51:07.83",
     "valorTotal": 1015,
     "hospede": {
       "id": 12,
       "nome": "Jaime A",
       "documento": "123456",
       "telefone": "92001578"
     },
     "adicionaVeiculo": true,
     "checkout": false
   }
   
Consultar hóspedes que já realizaram o check in e não estão mais no hotel:

    curl -X GET "http://localhost:9000/hotelaria/v1/checkins/todos-hospedes-checkout" -H "accept: */*"
   
saída esperada: 

     [
       {
        "id": 4,
    "dataCheckin": "2019-12-16T19:00:00",
    "dataCheckout": "2019-12-20T19:00:00",
    "valorTotal": 710,
    "hospede": {
      "id": 3,
      "nome": "Bruno",
      "documento": "789546",
      "telefone": "99524599"
     }
     ]
     
valor da última hospedagem já gasto pelo hóspede no hotel.

    curl -X GET "http://localhost:9000/hotelaria/v1/checkins/ultimo-valor-hospede/1" -H "accept: */*"
saida esperada:
   390
 
