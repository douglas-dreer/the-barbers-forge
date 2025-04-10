# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/) e às [Diretrizes de Versionamento](GUIDELINE.md).

## [0.0.2] - "Potion of Healing" - 10/04/2025

### Adicionado
- ✨ Implementação do gerenciamento de documentos com CRUD completo
- 🧩 Implementação de CRUD para gerenciamento de clientes com suporte a paginação
- 📝 Configuração do OpenAPI (Swagger) para documentação da API
- 🚨 Criação da enumeração MessageErroType para mensagens de erro
- 🛠️ Implementação da classe ConverterException para tratamento de exceções

### Alterado
- ♻️ Refatoração da estrutura do projeto para simplificação
- 📊 Melhoria na legibilidade do código

### Técnico
- Implementação de mapeamento e DTOs para documentos
- Adição de testes unitários para validar o CRUD de documentos
- Simplificação da arquitetura removendo classes desnecessárias

## [0.0.1] - 2024-01-17

### Adicionado
- 🎉 Primeira versão do projeto The Barber's Forge!
- 🔨 Implementação do CRUD básico de documentos
- 📝 Documentação inicial do projeto
- ✨ Validações para evitar documentos duplicados
- 🎯 Endpoints REST para manipulação de documentos
- 🔍 Busca paginada de documentos
- 🛠️ Configuração inicial do Spring Boot

### Técnico
- Implementação da camada de serviço para documentos
- Criação dos DTOs para transferência segura de dados
- Configuração do banco de dados H2 para desenvolvimento
- Implementação de tratamento de exceções personalizado
- Adição de testes unitários iniciais

[0.0.2]: https://github.com/douglasdreer/the-barbers-forge/compare/v0.0.1...v0.0.2
[0.0.1]: https://github.com/douglasdreer/the-barbers-forge/releases/tag/v0.0.1
