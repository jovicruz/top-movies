# TopMovies

#### TopMovies é uma aplicação web desenvolvida com Spring Boot para gerenciar uma lista de filmes. O projeto utiliza a [API do The Movie Database (TMDB)](https://www.themoviedb.org/) para obter informações sobre os filmes, usa Docker para facilitar a implantação e Tailwind CSS para estilização

## Imagens
![Screenshot_7](https://github.com/user-attachments/assets/b52c04d3-19f1-4bf0-9718-bcd101f7d76c)
![Screenshot_6](https://github.com/user-attachments/assets/9c593ac7-1e92-4842-b364-533106c681df)
![Screenshot_5](https://github.com/user-attachments/assets/b0566606-f4d3-4695-8f09-f8cd152fcd7e)

## Tecnologias Utilizadas

- Java 17

- Spring Boot 3.2.3

- Maven

- Docker & Docker Compose

- Javascript

- Tailwind CSS

## Funcionalidades

- Adicionar filmes aos favoritos

- Pesquisar filmes

- Alterar a capa dos filmes

## Requisitos

- Java 17+

- Docker e Docker Compose

- Maven

## Como Rodar o Projeto

#### 1. Clone o repositório:

  git clone https://github.com/seu-usuario/topmovies.git
  cd topmovies

#### 2. Configure as variáveis de ambiente no arquivo docker composer:

  ``` 
  POSTGRES_USER=seu_usuario 
  POSTGRES_PASSWORD=sua_senha 
  POSTGRES_DB=topmovies 
  API_TOKEN=sua_chave_api
  ```

#### 3. Compile e execute a aplicação com Maven:

 ` mvn spring-boot:run `

#### 4. Rodar com Docker:

 ` docker-compose up -d `

A aplicação estará disponível em http://localhost:8080.


## Contribuição

### Contribuições são bem-vindas! Para sugerir melhorias ou corrigir bugs, abra uma issue ou envie um pull request.
