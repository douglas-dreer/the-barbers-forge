# 💈 The Barber's Forge



[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=douglas-dreer_the-barbers-forge&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=douglas-dreer_the-barbers-forge)

## 📝 Descrição
**The Barber's Forge** é um sistema completo para a gestão de barbearias, desenvolvido com Java 21 e Spring Boot. O projeto oferece funcionalidades para gerenciar clientes, produtos, serviços, agendamentos e controle financeiro, além de otimizar o gerenciamento de estoque.

## 🚦 Status do Projeto
🚧 Em desenvolvimento 🚧

## ✨ Recursos Implementados ✅
- 🛠️ Configuração inicial do ambiente
- 🐘 Integração com PostgreSQL
- 🔄 Configuração de CI/CD
- 📊 Análise de código com SonarCloud
- 🧪 Testes unitários básicos
- 🛡️ Tratamento de exceções
- 📑 Mapeamento inicial de DTOs

## 🔄 Recursos em Desenvolvimento
- **👥 Módulo de Clientes**
  - 📝 Cadastro e gestão de clientes
  - 📋 Histórico de atendimentos
  - ⭐ Sistema de fidelização

- **🛍️ Módulo de Produtos e Serviços**
  - 📦 Cadastro de produtos
  - ✂️ Gestão de serviços
  - 📊 Controle de estoque

- **📅 Módulo de Agendamentos**
  - 📆 Calendário interativo
  - 🔔 Sistema de notificações
  - ✅ Confirmação automática

- **💰 Módulo Financeiro**
  - 💵 Controle de caixa
  - 📈 Relatórios gerenciais
  - 💳 Gestão de pagamentos

## 🛠️ Tecnologias Utilizadas
- **⚡ Back-end:** Java 21
- **🔧 Framework:** Spring Boot 3.x
- **🗃️ Banco de Dados:** PostgreSQL
- **📚 Documentação:** Swagger
- **📊 Qualidade:** SonarCloud
- **🔄 CI/CD:** GitHub Actions

## 📋 Pré-requisitos
- ☕ Java Development Kit (JDK) 21
- 🔨 Maven 3.8.x ou superior
- 🐘 PostgreSQL 15.x

## 🚀 Instalação e Execução

1. **📥 Clone o repositório:**
   ```bash
   git clone https://github.com/douglas-dreer/the-barbers-forge.git
   cd the-barbers-forge
   ```

2. **⚙️ Configure o banco de dados:**
   - Crie um banco de dados PostgreSQL
   - Copie o arquivo `application.properties.example` para `application.properties`
   - Configure as credenciais do banco no arquivo `application.properties`

3. **🔨 Compile o projeto:**
   ```bash
   mvn clean install
   ```

4. **▶️ Execute a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

5. **🌐 Acesse a aplicação:**
   - API: http://localhost:8080
   - Documentação Swagger: http://localhost:8080/swagger-ui.html

## 🔧 Configuração do Ambiente de Desenvolvimento

1. **📝 Variáveis de Ambiente:**
   ```properties
   POSTGRES_HOST=localhost
   POSTGRES_PORT=5432
   POSTGRES_DB=barbers_forge
   POSTGRES_USER=seu_usuario
   POSTGRES_PASSWORD=sua_senha
   ```

2. **🐘 Configuração do PostgreSQL:**
   ```sql
   CREATE DATABASE barbers_forge;
   ```

3. **🔄 Atualize as dependências:**
   ```bash
   mvn dependency:resolve
   ```

## Versão: 3.2.5
Última atualização: 10/04/2025
