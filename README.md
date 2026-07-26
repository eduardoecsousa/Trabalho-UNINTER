# 🌵 Raízes do Nordeste — API REST Back-end

API REST desenvolvida em Java com Spring Boot para gerenciamento de pedidos multicanal da rede de lanchonetes **Raízes do Nordeste**.

---

## 📋 Descrição

Sistema back-end que centraliza o fluxo de pedidos recebidos por múltiplos canais (APP, TOTEM, BALCÃO, PICKUP e WEB), com autenticação JWT, controle de estoque por unidade, pagamento mock e programa de fidelização por pontos.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.x
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL 15
- Docker + Docker Compose
- Maven
- Lombok
- Swagger / OpenAPI 3.0

---

## ⚙️ Pré-requisitos

Você precisa ter instalado apenas:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

> Não é necessário ter Java, Maven ou PostgreSQL instalados localmente.

---

## 🔧 Como Executar o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/SEU_USUARIO/raizes-do-nordeste-api.git
cd raizes-do-nordeste-api
```

### 2. Configure as variáveis de ambiente

Copie o arquivo de exemplo:

```bash
cp .env.example .env
```

Edite o arquivo `.env` com suas configurações:

```env
POSTGRES_DB=raizes_nordeste
POSTGRES_USER=postgres
POSTGRES_PASSWORD=123456
JWT_SECRET=sua_chave_secreta_aqui
JWT_EXPIRATION=86400000
```

### 3. Suba os containers

```bash
docker-compose up --build
```

A API estará disponível em: `http://localhost:8080`

### 4. Para parar os containers

```bash
docker-compose down
```

---

## 📖 Documentação da API (Swagger)

Após subir o projeto, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🗄️ Banco de Dados

As migrations são executadas automaticamente pelo Spring Boot ao iniciar a aplicação.

Para acessar o banco via terminal:

```bash
docker exec -it raizes-postgres psql -U postgres -d raizes_nordeste
```

---

## 🧪 Como Rodar os Testes

### Via coleção Postman/Insomnia

1. Importe o arquivo `/docs/colecao-postman.json` no Postman ou Insomnia
2. Configure a variável de ambiente `base_url` como `http://localhost:8080`
3. Execute primeiro a requisição `Auth/Register` para criar um usuário
4. Execute `Auth/Login` para obter o token JWT
5. O token será salvo automaticamente nas variáveis de ambiente

### Ordem sugerida de testes

```
1. POST /auth/register     → cadastrar usuário
2. POST /auth/login        → obter token JWT
3. GET  /unidades          → listar unidades
4. GET  /produtos          → listar produtos
5. POST /pedidos           → criar pedido (informar canalPedido)
6. POST /pedidos/{id}/pagamento → processar pagamento mock
7. GET  /pedidos/{id}      → consultar status do pedido
8. GET  /fidelidade/meus-pontos → consultar pontos
```

---

## 📁 Estrutura do Projeto

```
src/main/java/raizes_do_nordeste_api/
├── controller/        # Endpoints REST
├── service/           # Regras de negócio
├── repository/        # Acesso ao banco de dados
├── Entity/models/     # Entidades JPA
├── dto/               # Objetos de transferência de dados
├── enums/             # Enumerações do domínio
├── security/          # Configuração JWT e Spring Security
└── exception/         # Tratamento global de erros
```

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)**. Para acessar rotas protegidas:

1. Faça login em `POST /auth/login`
2. Copie o `accessToken` retornado
3. Envie no header de todas as requisições protegidas:

```
Authorization: Bearer {seu_token_aqui}
```

---

## 📦 Endpoints Principais

| Método | Rota | Descrição | Acesso |
|--------|------|-----------|--------|
| POST | /auth/register | Cadastrar usuário | Público |
| POST | /auth/login | Autenticar e obter JWT | Público |
| GET | /unidades | Listar unidades da rede | Público |
| GET | /produtos | Listar produtos | Público |
| POST | /pedidos | Criar pedido | CLIENTE |
| GET | /pedidos | Listar meus pedidos | CLIENTE |
| GET | /pedidos/{id} | Consultar pedido | CLIENTE |
| POST | /pedidos/{id}/pagamento | Processar pagamento mock | CLIENTE |
| PATCH | /pedidos/{id}/cancelar | Cancelar pedido | CLIENTE |
| GET | /fidelidade/meus-pontos | Consultar pontos | CLIENTE |
| GET | /admin/pedidos | Listar todos os pedidos | ADMIN |

---

## 📄 Documentação Completa

A documentação técnica completa (PDF) está disponível em:

```
/docs/4830341_Projeto_Back_End.PDF
```

---

## 👤 Autor

**Eduardo Santos de Sousa**
RU: 4830341
UNINTER — Análise e Desenvolvimento de Sistemas
Projeto Multidisciplinar — Trilha Back-end 2026