# 💈 UP Barber

> API REST back-end para o sistema de autoatendimento de barbearia.

---

## 📋 Sobre o Projeto

O **UP Barber** é um sistema de autoatendimento inspirado nos totens do McDonald's, desenvolvido como Projeto Integrador do 5º Semestre de DSM. O objetivo é modernizar o atendimento de barbearias, permitindo que os clientes realizem agendamentos e pagamentos de forma autônoma através de um app mobile (React Native), consumindo esta API.

**Repositório do Front-end (App Mobile):** _em breve_

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Finalidade |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot | Framework da API REST |
| Spring Data JPA | ORM e acesso ao banco de dados |
| PostgreSQL | Banco de dados relacional |
| EFI Pay SDK (Gerencianet) | Integração de pagamentos via PIX |
| Docker | Orquestração do ambiente de desenvolvimento |

---

## ⚙️ Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Java 21+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8+](https://maven.apache.org/)
- [Docker e Docker Compose](https://www.docker.com/)
- Credenciais da [EFI Pay (Gerencianet)](https://sejaefi.com.br/)

---

## 🐳 Rodando com Docker

O banco de dados PostgreSQL sobe automaticamente via Docker Compose.

```bash
# Clone o repositório
git clone https://github.com/RafaelBorges22/PI-5SM-BACK.git
cd PI-5SM-BACK

# Suba o banco de dados
docker compose up -d

#Baixar o JDK 21 caso não tenha (Após isso recomendo fechar e abrir o cmd novamente)
winget install EclipseAdoptium.Temurin.21.JDK

#Baixar dependencias Maven
mvnw clean install

# Rode a aplicação
mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 🔧 Variáveis de Ambiente / Configuração

Configure o arquivo `src/main/resources/application.properties` com as suas credenciais:

```properties
# Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5432/barberkiosk
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.profiles.active=local
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.docker.compose.enabled=false

# EFI Pay (Gerencianet)
efi.client-certificate=./.local/seu_certificado
efi.client-id=seu_client_id
efi.client-secret=seu_client_secret
efi.client-key=sua_chave_pix
efi.sandbox=true
```
---
## 🔐 Certificado PIX (EFI Pay)
 
Para que o pagamento via PIX funcione, é necessário adicionar o certificado fornecido pela EFI Pay dentro da pasta `.local` na raiz do projeto.
 
```
PI-5SM-BACK/
└── .local/
    └── seu_certificado.p12   ← coloque aqui
```
 
> ⚠️ **Importante:** nunca suba o certificado para o repositório! Certifique-se de que a pasta `.local` está no `.gitignore`.
 
```gitignore
# .gitignore
.local/
```

---

## 📁 Estrutura do Projeto

```
src/
└── main/
    ├── java/dsm/api/dm/
    │   ├── controller/     # Endpoints da API
    │   ├── service/        # Regras de negócio
    │   ├── repository/     # Acesso ao banco de dados
    │   ├── model/          # Entidades JPA
    │   └── dto/            # Objetos de transferência de dados
    └── resources/
        └── application.properties
```

---

## 🛠️ Funcionalidades

- [x] Criação de serviços da barbearia
- [x] Integração com gateway de pagamento via Pix com EFI Pay 

---
## ⚠️**Documentação da API (Swagger)** 
Assim que subir o servidor local acesse a url `http://localhost:8080/swagger-ui/index.html`.

## 👨‍💻 Autores

Desenvolvido por **Rafael Mascarenhas Borges**.

