#  Mini Delivery Order Tracker

Sistema simplificado de rastreamento de pedidos de delivery desenvolvido com **Java + Spring Boot + React**, incluindo autenticação JWT, gerenciamento de clientes, endereços e pedidos.

---

#  Objetivo do Projeto

Construir uma versão simplificada de um sistema de delivery contendo:

- Cadastro de usuários;
- Login com JWT;
- CRUD de Clientes;
- CRUD de Endereços;
- Criação de Pedidos;
- Atualização de Status dos Pedidos;
- Consulta de Pedidos;
- Front-end React para gerenciamento.

---

#  Tecnologias Utilizadas

## Back-end

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- Maven
- Lombok
- Sqlite
- JUnit 5
- Mockito

## Front-end

- React
- Vite
- Axios
- React Router DOM

---

#  Arquitetura Utilizada

O projeto foi construído utilizando conceitos de:

## DDD (Domain Driven Design)

Separação das responsabilidades em camadas:

```text
Controller
    ↓
Service
    ↓
Domain
    ↓
Repository
```

Benefícios:

- Maior organização;
- Facilidade de manutenção;
- Regras de negócio centralizadas;
- Código mais testável.

---

## Rich Domain Model

As regras de negócio do Pedido foram mantidas na própria entidade.

Exemplo:

```java
pedido.atualizarStatus(...)
pedido.adicionarItem(...)
pedido.calcularValorTotal(...)
```

### Por que utilizar?

Evita:

```java
pedidoService.calcularTotal(...)
pedidoService.validarStatus(...)
```

fazendo com que o próprio domínio seja responsável pelas suas regras.

---

## DTO Pattern

Utilização de DTOs para comunicação externa.

```text
Request DTO
↓
Mapper
↓
Domain
↓
Mapper
↓
Response DTO
```

Benefícios:

- Evita exposição direta das entidades;
- Controle dos dados retornados;
- Flexibilidade para evolução da API.

---

## Mapper Pattern

Conversão entre:

```text
DTO ↔ Entity
```

Exemplo:

```java
PedidoRequestMapper
PedidoResponseMapper
ClienteRequestMapper
ClienteResponseMapper
```

---

#  Autenticação JWT

O sistema utiliza autenticação Stateless baseada em Token JWT.

Fluxo:

```text
Cadastro
    ↓
JWT gerado
    ↓
Login
    ↓
JWT gerado
    ↓
Front-end salva token
    ↓
Token enviado nos próximos requests
```

Header utilizado:

```http
Authorization: Bearer SEU_TOKEN
```

---

#  Funcionalidades

## Usuários

### Cadastro

```http
POST /auth/cadastro
```

```json
{
  "nome": "Francisco",
  "email": "francisco@email.com",
  "senha": "123456"
}
```

---

### Login

```http
POST /auth/login
```

```json
{
  "email": "francisco@email.com",
  "senha": "123456"
}
```

---

## Clientes

### Criar

```http
POST /clientes
```

### Listar

```http
GET /clientes
```

### Buscar por ID

```http
GET /clientes/{id}
```

### Atualizar

```http
PUT /clientes/{id}
```

### Excluir

```http
DELETE /clientes/{id}
```

---

## Endereços

### Criar

```http
POST /enderecos
```

### Listar

```http
GET /enderecos
```

### Buscar por ID

```http
GET /enderecos/{id}
```

### Atualizar

```http
PUT /enderecos/{id}
```

### Excluir

```http
DELETE /enderecos/{id}
```

---

## Pedidos

### Criar Pedido

```http
POST /pedidos
```

Exemplo:

```json
{
  "clienteId": "uuid",
  "enderecoId": "uuid",
  "itens": [
    {
      "nomeProduto": "Pizza",
      "quantidade": 2,
      "precoUnitario": 40
    }
  ]
}
```

---

### Buscar Todos

```http
GET /pedidos
```

---

### Buscar Por Id

```http
GET /pedidos/{id}
```

---

### Atualizar Status

```http
PATCH /pedidos/{id}/status
```

```json
{
  "status": "EM_PREPARO"
}
```

---

#  Status Disponíveis

```java
RECEBIDO
EM_PREPARO
SAIU_PARA_ENTREGA
ENTREGUE
CANCELADO
```

Fluxo esperado:

```text
RECEBIDO
    ↓
EM_PREPARO
    ↓
SAIU_PARA_ENTREGA
    ↓
ENTREGUE
```

ou

```text
RECEBIDO
    ↓
CANCELADO
```

---

#  Configuração JWT

No arquivo:

```properties
application.properties
```

utilize:

```properties
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

---

#  Configurando variável JWT_SECRET no IntelliJ

Abra:

```text
Run
↓
Edit Configurations
```

Selecione sua aplicação Spring Boot.

No campo:

```text
Environment Variables
```

adicione:

```text
JWT_SECRET=minha-chave-secreta-super-segura-com-pelo-menos-32-caracteres
```

Exemplo:

```text
JWT_SECRET=12345678901234567890123456789012
```

Salve e execute normalmente.

---

#  Configuração para Testes

No arquivo:

```text
src/test/resources/application-test.properties
```

utilize qualquer valor para:

```properties
jwt.secret=test-secret
jwt.expiration=86400000
```

Como o JWT não é validado de forma real durante os testes unitários, o valor pode ser qualquer string.

Exemplo:

```properties
jwt.secret=teste
jwt.expiration=86400000
```

---

#  Como Executar o Projeto Backend

## Clonar Repositório

```bash
git clone url-repository
```

---

## Entrar na pasta

```bash
cd api-delivery
```

---

## Executar

### Maven

```bash
mvn spring-boot:run
```

ou

### IntelliJ

Executar:

```java
ApiDeliveryApplication
```

---

Servidor disponível em:

```text
http://localhost:8080
```

---

#  Executar Testes

```bash
mvn test
```

---

Para permitir comunicação entre React e Spring Boot:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource()
```

Foi configurado para aceitar:

```text
http://localhost:5173
```

durante desenvolvimento.

---

#  Principais Boas Práticas Aplicadas

- DDD
- Rich Domain Model
- DTO Pattern
- Mapper Pattern
- Repository Pattern
- Service Layer Pattern
- JWT Authentication
- Testes Unitários com JUnit e Mockito
- Tratamento de Exceções Customizadas
- Separação de Responsabilidades
- API RESTful

---

# Contexto

Projeto desenvolvido para estudo e demonstração de conhecimentos em:

- Java
- Spring Boot
- Spring Security
- JWT
- React
- Arquitetura de Software
- APIs REST
- Testes Automatizados