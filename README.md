# starbank-microsservices

Aplicação de microsserviços com Java e Spring que simula a concessão de cartão de crédito através de uma avaliação de crédito do cliente. 
Foi implementada uma arquitetura com service discovery, centralizando requisições através de um API Gateway, 
fazendo balanceamento de carga e implementando comunicação síncrona usando ferramentas do Spring Cloud.
Também implementei a comunicação assíncrona utilizando o RabbitMQ para realizar a solicitação de cartões aos clientes.


<img width="554" alt="image" src="https://user-images.githubusercontent.com/90730406/219190365-e5a32af1-d52f-410d-9120-ef5fb1352a15.png">

Para rodar com o docker, deverão ser realizados os seguintes comandos:

- Criar a network:
  - docker network create starbank-network

- Criar a imagem do rabbitmq:
  - docker run --name starbankrabbitmq -p 5672:5672 -p 15672:15672 --network starbank-network rabbitmq:3.9-management



- Criar a imagem do Server:
  - docker build --tag starbank-eureka .
  - docker run --name starbank-eureka -p 8761:8761 --network starbank-network starbank-eureka


- Criar a imagem do Gateway:
  - docker build --tag starbank-gateway .
  - docker run --name starbank-gateway -p 8082:8080 -e EUREKA_SERVER=starbank-eureka --network starbank-network -d starbank-gateway


- Criar a imagem do microsserviço Clientes:
  - docker build --tag starbank-clientes .
  - docker run --name starbankms-clientes --network starbank-network -e EUREKA_SERVER=starbank-eureka -d starbank-clientes

- Criar a imagem do microsserviço Cartões:
  - docker build --tag starbank-cartoes .
  - docker run --name starbankms-cartoes --network starbank-network -e RABBITMQ_SERVER=starbankrabbitmq -e EUREKA_SERVER=starbank-eureka -d starbank-cartoes

- Criar a imagem do microsserviço Avaliador de Crédito:
  - docker build --tag starbank-avaliador .
  - docker run --name starbankms-avaliador --network starbank-network -e RABBITMQ_SERVER=starbankrabbitmq -e EUREKA_SERVER=starbank-eureka -d starbank-avaliador
