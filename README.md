# Projeto TodoSimple

Este é um projeto de exemplo para demonstrar a implementação de autenticação e autorização em uma aplicação Spring Boot utilizando Spring Security e JWT (JSON Web Token).

## Funcionalidades Principais

- **UserController**: Controla as operações relacionadas aos usuários, como busca, criação, atualização e exclusão.
- **SecurityConfiguration**: Configura as regras de segurança da aplicação, determinando quais endpoints são acessíveis sem autenticação e quais exigem papéis específicos.
- **SecurityFilter**: Filtro personalizado para interceptar requisições HTTP e realizar autenticação com base nos JWTs fornecidos no cabeçalho da requisição.
- **ErrorResponse**: Formato padronizado para respostas de erro retornadas pela API, incluindo código de status, mensagem de erro, rastreamento de pilha (opcional) e erros de validação.
- **GlobalExceptionHandler**: Trata exceções lançadas durante a execução dos endpoints da API, traduzindo-as em respostas HTTP adequadas com detalhes do erro.
- **AuthenticationServiceImpl**: Implementa a interface AuthenticationService e fornece métodos para autenticação de usuário e geração/validação de tokens JWT.
- **UserSpringSecurity**: Implementa a interface UserDetails do Spring Security, representando o principal do usuário e fornecendo informações como nome de usuário, senha, papéis e autorizações.

## Tecnologias Utilizadas
- Java
- Spring Framework
- Spring Boot
- Spring Security
- Hibernate
- RESTful API
- JWT (JSON Web Tokens) para autenticação
- MySql meu database
- Lombok (para geração automática de getters, setters, etc.)
- Jakarta Validation (para validação de entrada)
- BCrypt (para criptografia de senhas)
- JSON Web Tokens (JWT) para autenticação e autorização
- Maven  para gestão de dependências e construção do projeto
- Git (sistema de controle de versão) para controle de código-fonte

## Configuração

Para executar o projeto localmente, siga estas etapas:

1. Clone o repositório para o seu ambiente de desenvolvimento.
2. Certifique-se de ter o Java JDK e o Maven instalados em sua máquina.
3. Configure as variáveis de ambiente necessárias, como as credenciais do banco de dados, se aplicável.
4. Execute a aplicação usando o Maven: `mvn spring-boot:run`.
