# EstagioXX

**EstagioXX** é uma aplicação web desenvolvida para o gerenciamento de ofertas de estágio por empresas e candidaturas de alunos. O sistema permite que empresas criem e gerenciem ofertas de estágio, e que alunos possam se candidatar a estas vagas. A aplicação também conta com mecanismos de autenticação e autorização para controle de acesso.

## Funcionalidades

- Empresas podem criar, editar e remover ofertas de estágio.
- Alunos podem visualizar e se candidatar às ofertas disponíveis.
- Coordenação tem acesso ao gerenciamento e aprovação de estágios.
- Sistema de login com autenticação e autorização.
- Paginação de dados para melhorar a performance de consultas.
- Validação de formulários com feedback ao usuário sobre possíveis erros.


O desenvolvimento seguiu os seguintes requisitos não funcionais (RNF):

- **RNF 01**: Utilização do **Spring Boot** como framework principal para a implementação.
- **RNF 02**: Uso de **Bootstrap** para estilização de componentes e layout responsivo.
- **RNF 03**: Banco de dados **PostgreSQL** para armazenamento das informações.
- **RNF 04**: Persistência de dados feita com **Hibernate**.
- **RNF 05**: Implementação de validação em formulários, com exibição de mensagens de erro em tempo real.
- **RNF 06**: Adoção do padrão **Post/Redirect/Get** para evitar submissões duplicadas em formulários.
- **RNF 07**: Utilização do **Thymeleaf** com fragmentos e layouts para uma estrutura modular de templates.
- **RNF 08**: Paginação de tabelas, refletida no banco de dados para consultas eficientes (carregando apenas os registros da página atual).
- **RNF 09**: Implementação de **autenticação** com **Spring Security 6.0+**.
- **RNF 10**: Implementação de **autorização** com **Spring Security 6.0+** para controlar o acesso baseado em perfis de usuários (Coordenador, Aluno, Empresa).


