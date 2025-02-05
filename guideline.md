# Diretrizes de Versionamento

Neste documento, estabelecemos as diretrizes para nomear as versões do projeto **The Barber's Forge** utilizando itens mágicos de **D&D 5e**. Os nomes dos itens devem estar em inglês, seguindo a tabela de itens de D&D 5e, e devem incluir um link para a descrição oficial do item, juntamente com uma miniatura (thumbnail) da imagem do item.

## Classificação das Alterações

- **COMMON** (Comum):
    - **Testes**: Adição ou atualização de testes unitários e de integração.
    - **Melhoria de Qualidade de Teste**: Ajustes relacionados ao SonarQube, cobertura de código, entre outros.
    - **Exemplo**: Adição de novos testes de integração ou ajustes em testes existentes para melhorar a cobertura.
    - **Nomes de Itens**: Escolher itens de raridade **Common**.

- **UNCOMMON** (Incomum):
    - **CRUD**: Criação, leitura, atualização e exclusão de funcionalidades.
    - **Exemplo**: Criação de endpoints CRUD para entidades como **Customer**.
    - **Nomes de Itens**: Escolher itens de raridade **Uncommon**.

- **RARE** (Raro):
    - **Novas Funcionalidades Significativas**: Implementação de novos módulos ou serviços que ampliem consideravelmente as funcionalidades do sistema.
    - **Refatoração de Grande Escala**: Refatorações que envolvam múltiplos módulos, melhorando a arquitetura ou a performance do sistema.
    - **Exemplo**: Introdução de um novo sistema de autenticação ou refatoração completa do módulo de pagamentos.
    - **Nomes de Itens**: Escolher itens de raridade **Rare**.

- **VERY RARE** (Muito Raro):
    - **Melhorias Críticas de Segurança**: Implementação de importantes melhorias de segurança.
    - **Grandes Otimizações de Performance**: Alterações que melhoram significativamente a performance do sistema.
    - **Nomes de Itens**: Escolher itens de raridade **Very Rare**.

- **LEGENDARY** (Lendária):
    - **Reestruturação Completa**: Mudanças na estrutura principal do projeto, como migração para uma nova tecnologia ou framework.
    - **Melhoria de Escalabilidade**: Implementação de soluções que permitam um aumento significativo na escalabilidade e robustez do sistema.
    - **Exemplo**: Migração para uma nova infraestrutura de nuvem ou adoção de arquitetura de microserviços.
    - **Nomes de Itens**: Escolher itens de raridade **Legendary**.

## Exemplo de Nomenclatura de Versões

- **[0.0.0.x]** - { Item de raridade Common } - {data}
- **[0.0.x.x]** - { Item de raridade Uncommon } - {data}
- **[0.x.x.x]** - { Item de raridade Rare } - {data}
- **[x.x.x.x]** - { Item de raridade Legendary } - {data}
