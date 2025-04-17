# The Barber's Forge

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=douglas-dreer_the-barbers-forge&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=douglas-dreer_the-barbers-forge)

## Descrição
**The Barber's Forge** é um sistema completo para a gestão de barbearias, desenvolvido com Java 21 e Spring Boot. O projeto oferece funcionalidades para gerenciar clientes, produtos, serviços, agendamentos e controle financeiro, além de otimizar o gerenciamento de estoque.

## Funcionalidades (Em Progresso)

- **Gerenciamento de Cadastro de Clientes:** Registro detalhado com informações pessoais e histórico de atendimentos.
- **Gerenciamento de Cadastro de Produtos:** Gestão de itens para venda e consumo interno.
- **Gerenciamento de Cadastro de Serviços:** Definição de serviços oferecidos com preços e durações personalizadas.
- **Agendamento de Horário:** Calendário interativo com controle de disponibilidade.
- **Notificações de Horário:** Notificação via e-mail, mensagem de texto ou whatsApp.
- **Envio de E-mail Propaganda** Envio de promoções via e-mail.
- **Sistema de Fidelização**: Descontos especiais para fidelização de clientes.
- **Controle de Caixa (Financeiro):** Monitoramento de movimentações financeiras.
- **Controle de Estoque:** Acompanhamento de estoque com alertas para reposição.

## Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot
- **Banco de Dados:** PostgreSQL

## Como Executar o Projeto

1. **Clonar o Repositório:**
   ```bash
   git clone https://github.com/douglas-dreer/the-barbers-forge.git
   ```

2. **Acessar o Diretório:**
   ```bash
   cd the-barbers-forge
   ```

3. **Compilar e Executar:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acessar a Aplicação:**
    - URL padrão: [http://localhost:8081](http://localhost:8080)

## Configuração do Banco de Dados

O projeto está configurado com o banco de dados **PostgreSQL**.

### Passos para Configuração:

1. **Instalar o PostgreSQL:**
    - Se ainda não tiver o PostgreSQL instalado, siga [este link](https://www.postgresql.org/download/) para instalar.

2. **Criar o Banco de Dados:**
    - No PostgreSQL, crie o banco de dados `the-barbers-forge`:
      ```sql
      CREATE DATABASE the-barbers-forge;
      ```

3. **Configuração do Banco de Dados no Projeto:**
    - O arquivo `application.properties` está configurado para conectar-se ao banco de dados PostgreSQL:
      ```yml
      spring:
       datasource:
         url: jdbc:postgresql://localhost:5432/the-barbers-forge
         username: your_username
         password: your_password
         driver-class-name: org.postgresql.Driver
       jpa:
         database-platform: POSTGRES
      ```

4. **Configuração do Banco de Dados:**
    - Certifique-se de ter o driver do PostgreSQL incluído nas dependências do projeto:
      ```xml
      <dependency>
          <groupId>org.postgresql</groupId>
          <artifactId>postgresql</artifactId>
          <version>42.2.5</version>
      </dependency>
      ```

### Acesso ao Banco de Dados:

Você pode acessar o banco de dados PostgreSQL usando um cliente de banco de dados, como
o [pgAdmin](https://www.pgadmin.org/), ou usando a linha de comando do PostgreSQL:

```bash
psql -h localhost -U your_username -d the_barbers_forge
```

## Estrutura do Projeto

- `src/main/java`: Contém as classes principais do projeto.
- `src/main/resources`: Contém arquivos de configuração.
- `application.properties`: Configuração do projeto.

## Futuras Melhorias

- Gerenciamento de Cadastro de Clientes.
- Gerenciamento de Cadastro de Produtos.
- Gerenciamento de Cadastro de Serviços.
- Gerenciamento de Agendamento de Horário.
- Notificações de Horário.
- Envio de E-mail Propaganda.
- Sistema de Fidelização.
- Controle de Caixa (Financeiro).
- Controle de Estoque.
- Implementação de autenticação e autorização.
- Documentação em SWAGGER.
- Relatórios financeiros detalhados.

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo para colaborar:

1. Fork o repositório.
2. Crie uma branch com sua feature: `git checkout -b feature/nova-feature`
3. Commit suas alterações: `git commit -m 'Adiciona nova funcionalidade'`
4. Push para a branch: `git push origin feature/nova-feature`
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

## Versão: 3.2.9

Última atualização: 17/04/2025
