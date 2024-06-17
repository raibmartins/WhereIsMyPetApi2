# MODULO DE INTERFACE DE PROGRAMAÇÃO DE APLICAÇÃO - WHERE IS MY PET (API)

Este projeto é uma API desenvolvida em Java 17 usando o framework Spring Boot. Ele é projetado para fornecer uma interface de programação de aplicativos (API) robusta e escalável. Utilizado no trabalho de conclusão de curso para a Universidade do Extremo Sul Catarinense (UNESC)

## Requisitos

- Java 17
- Maven 3.6+
- PostgreSQL

## Instalação

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/raibmartins/WhereIsMyPet-Api
   cd seu-repositorio
   ```
2. **Configure o ambiente:**

Certifique-se de que o Java 17 está instalado e configurado corretamente no seu sistema. Você pode verificar a versão do Java com o seguinte comando:

   ```bash
   java -version
  ```

Deve retornar algo como:
  ```bash
  java version "17.0.1" 2021-10-19 LTS
  Java(TM) SE Runtime Environment (build 17.0.1+12-LTS-39)
  Java HotSpot(TM) 64-Bit Server VM (build 17.0.1+12-LTS-39, mixed mode, sharing)
  ```
3. **Configurar o banco de dados:**

Certifique-se de que o postgress está instalado e em execução. Crie um banco de dados para o projeto chamado "whereismypetdb" e atualize o arquivo application.yaml com as configurações corretas do banco de dados.
```bash
spring.datasource:
  driverClassName: org.postgresql.Driver
  url: jdbc:postgresql://localhost:5432/whereismypetdb
  username: postgres
  password: postgres
```

4. **Build do projeto:**

Navegue até o diretório do projeto e execute o comando Maven para construir o projeto:

```bash
mvn clean install
```

5. **Executar a aplicação:**

Após a construção bem-sucedida, execute o seguinte comando para iniciar a aplicação:

```
mvn spring-boot:run
```


6. **Problemas Comuns:**

**Java não configurado corretamente**

Se o Java não estiver configurado corretamente, você poderá enfrentar problemas ao tentar construir ou executar a aplicação. Certifique-se de que o Java 17 está instalado e configurado no PATH do sistema. Se necessário, defina a variável de ambiente JAVA_HOME apontando para o diretório de instalação do Java 17.

**Banco de dados não existente**

Se o banco de dados não estiver configurado corretamente ou não existir, a aplicação não conseguirá se conectar e você verá erros relacionados à conexão com o banco de dados. Certifique-se de que:

- O serviço do banco de dados está em execução.
- O banco de dados especificado no application.yaml existe.
- As credenciais de acesso estão corretas.

**Porta em uso**

Está definido para que use a porta 8082 ao executar o sistema, entretanto em caso desta estiver em uso, a mesma deve ser alterada no arquivo application.yaml

```bash
server:
  port: 8082
```

7. **Endpoints**

**Autenticação**

```bash
POST {server-ip}/auth/login

POST {server-ip}/auth/register
```

**Pets**

```bash
GET {server-ip}/pets
#Retorna todos os pets do usuário

GET {server-ip}/pets/getNumeros
#Lista todos os números dos pets cadastrados

PUT {server-ip}/pets/excluir/{id}
#Exclui um pet

POST {server-ip}/pets
#Cria um novo ped
```

**Pets Location**

```bash
POST {server-ip}/petsLocation
#Salva uma localização de um pet

GET {server-ip}/petsLocation
#Retorna a localização de todos os pets cadastrados para o usuário

POST {server-ip}/petsLocation/sendSmsGetLocation/{id}
#Força um envio de SMS solicitando localização

```

8. **Contribuindo**
   
- Faça um fork do projeto.
- Crie uma nova branch (git checkout -b feature/sua-feature).
- Commit suas mudanças (git commit -am 'Adiciona nova feature').
- Faça push para a branch (git push origin feature/sua-feature).
- Crie um novo Pull Request.
