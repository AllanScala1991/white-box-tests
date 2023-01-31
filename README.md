
# Teste de Caixa Branca

O que é ?
- São testes que olham diretamente o código fonte, com uma perspectiva interna do sistema para modelar os casos de teste.
- Caso a implementação seja alterada, muito provavelmente os testes também terão que ser.

Tipos de testes:
- Unitário
- Integração
- Regressão
- Sistema

Quais desses tipos esse projeto implementa?
- Unitário


## Funcionalidades

- Cadastrar novas tarefas
- Buscar todas as tarefas
- Buscar tarefas por nome
- Atualizar uma tarefa
- Deletar uma tarefa por ID

## Instalação
### Docker
Para rodar o projeto, é necessario ter o Docker instalado e inicializar através do docker compose o container do banco de dados (PostegreSQL).

### Testes
Para inicializar os testes basta rodar o script :
"/src/test/java/com.example.tasks/suite/Suite.java"
ou rodar individualmente.
## Documentação da API

#### Recebe o status da aplicação

```http
  GET /task/health
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `api_key` | `string` | **Obrigatório**. A chave da sua API |

#### Cria uma nova tarefa

```http
  POST /task
```
### Payload

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `name`      | `string` | **Obrigatório**. Titulo da tarefa |
| `description`      | `string` | **Obrigatório**. Descrição da tarera |
| `priority`      | `string` | **Obrigatório**. Prioridade da tarefa |

Retorna os dados da nova tarefa com ID.

#### Busca por todas as tarefas

```http
  GET /task
```
Retorna todas as tarefas cadastradas ou uma mensagem de nenhuma tarefa cadastrada.

#### Busca por todas as tarefas com o nome informado

```http
  GET /task?name={nome_da_tarefa}
```
### Payload

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `name`      | `string` | Titulo da tarefa |

Retorna as tarefas com aquele titulo/nome ou uma mensagem de nenhuma tarefa cadastrada.

#### Atualiza uma tarefa pelo ID

```http
  PUT /task/{id}
```
### URL Params

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. ID da tarefa que será atualizada |

### Payload

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `name`      | `string` | **Obrigatório**. Novo titulo da tarefa |
| `description`      | `string` | **Obrigatório**. Nova descrição da tarera |
| `priority`      | `string` | **Obrigatório**. Nova prioridade da tarefa |

Retorna os dados da  tarefa atualizados ou uma mensagem de erro caso o ID seja inválido.

#### Deleta uma tarefa

```http
  DELETE /task/{id}
```
### URL Params

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. ID da tarefa que será deletada |

Retorna uma mensagem de sucesso ou uma mensagem de erro caso o ID seja inválido.


## Autores

- [@AllanScala](https://github.com/AllanScala1991)