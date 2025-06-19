# CityEventsManager
# 📅 Sistema de Cadastro e Notificação de Eventos

Projeto em Java com interface de console que permite o cadastro de eventos em uma cidade, gerenciamento de usuários, confirmação de presença e persistência dos dados em arquivo JSON.

## 🧠 Objetivo

Implementar, utilizando orientação a objetos, um sistema de eventos com:
- Cadastro e listagem de eventos
- Participação e cancelamento de presença
- Filtro de eventos futuros, em andamento e passados
- Armazenamento em `events.json`
- Interface de linha de comando

## 💻 Tecnologias

- Java 17+
- Gson (para JSON)
- IDE: NetBeans (recomendado)

## ✅ Funcionalidades

- [x] Cadastro de usuários com nome, email e cidade
- [x] Criação de eventos com nome, endereço, categoria, data/hora e descrição
- [x] Confirmação e cancelamento de presença em eventos
- [x] Listagem de:
  - Eventos futuros
  - Eventos em andamento
  - Eventos passados
  - Eventos que o usuário confirmou
- [x] Persistência automática em `events.json`
- [x] Ordenação por data e hora

## 🗂 Categorias disponíveis

- FESTA
- SHOW
- ESPORTE
- CONFERENCIA
- OUTRO
