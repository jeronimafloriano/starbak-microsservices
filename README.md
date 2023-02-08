# starbank-microsservices

Aplicação de microsserviços com Java e Spring que simula a concessão de cartão de crédito através de uma avaliação de crédito do cliente. 
Foi implementada uma arquitetura com service discovery, centralizando requisições através de um API Gateway, 
fazendo balanceamento de carga e implementando comunicação síncrona usando ferramentas do Spring Cloud.
Também implementei a comunicação assíncrona utilizando o RabbitMQ para realizar a solicitação de cartões aos clientes.
