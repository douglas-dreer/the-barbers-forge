# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/) e às [Diretrizes de Versionamento](GUIDELINE.md).

## [0.0.2] - "Potion of Healing" - 2024-01-19

### Adicionado
- ✨ Implementação de testes unitários para validação de documentos
- 🧪 Testes para mapeamento de documentos (DocumentMapper)
- 🚨 Testes para exceções personalizadas (ValidateDocumentServiceException e UniqueConstraintViolationException)
- 🔍 Testes para o controlador de documentos (DocumentController)
- 🛠️ Implementação da classe base de mapeamento (BaseMapper)

### Alterado
- ♻️ Refatoração dos testes para melhor cobertura
- 📊 Melhoria na organização dos testes unitários

### Técnico
- Implementação de testes para conversão entre entidades e DTOs
- Adição de testes para validação de exceções
- Melhoria na estrutura de testes do controlador
- Implementação de classe base para mapeamento de objetos

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
