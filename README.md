# HogwartsApp - Projeto de Estudo Java para Estagiários Rethink

## Visão Geral

Este projeto é parte do programa de estudo de Java para estagiários Rethink que entraram no cliente Esfera. O objetivo é ensinar a linguagem Java dentro do contexto do negócio do Cliente Esfera, proporcionando uma introdução ao desenvolvimento de software relacionado a pontos, cashbacks e resgate.

## Especificações

O projeto utiliza as seguintes tecnologias e ferramentas:

- Spring 2.7.xx
- Java 11
- Testes Unitários com Mockito e JUnit
- Banco de Dados SQL

## Pré-requisitos

Certifique-se de ter o seguinte instalado em sua máquina:

- [Maven](#instalação-do-maven)
- [Docker](https://www.docker.com/get-started)

## Configuração

O repositório inclui os seguintes arquivos de configuração:

- `application.yml`: Variáveis de ambiente para acesso ao banco de dados.
- `pom.xml`: Arquivo de configuração do Maven.
- `Dockerfile`: Configuração para construir a imagem do servidor de banco de dados MySQL.

## Instalação do Maven

### Windows

1. [Faça o download do Maven](https://maven.apache.org/download.cgi) e extraia os arquivos para um diretório de sua escolha.

2. Configure as variáveis de ambiente:
   ```bash
   SETX M2_HOME "<caminho-do-maven>"
   SETX Path "%Path%;%M2_HOME%\bin"
  
3. Verifique a instalação

Após seguir as instruções acima, execute o seguinte comando para verificar se o Maven foi instalado corretamente:

```bash
  mvn -v
```

### Linux

1. Instale o Maven usando o gerenciador de pacotes:
   ```bash
   sudo apt update
   sudo apt install maven
   ```

2. Verifique a instalação:
   ```bash
   mvn -v
   ```

Certifique-se de reiniciar o terminal após a instalação para que as alterações nas variáveis de ambiente entrem em vigor.

## Docker

O Dockerfile fornecido permite criar um contêiner MySQL com a configuração necessária para o projeto. Em caso de problemas em executar o arquivo, execute o seguinte comando:

```bash
docker run --name hogwartsapp -e MYSQL_ROOT_PASSWORD=pwd123 -p 3306:3306 -v mysql-data:/var/lib/mysql -d mysql:8.0
```
