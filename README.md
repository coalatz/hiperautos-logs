# Hiperautos Log Monitor

O Hiperautos Log Monitor é uma arquitetura completa de observabilidade para ambientes Docker, combinando a extração eficiente de logs estruturados (Vector), enriquecimento de inteligência artificial via Groq (Spring Boot) e dashboards dinâmicos e interativos (Grafana/PostgreSQL).

## Arquitetura do Sistema

O fluxo de funcionamento do monitoramento é o seguinte:

1. **Docker / Containers da Aplicação**: Os containers executam e emitem logs no stdout. (Nota: o monitor coleta os logs apenas dos containers etiquetados com a label específica).
2. **Vector**: Lê os logs do socket do Docker em tempo real e encaminha via HTTP para a API central.
3. **Log-Service (Spring Boot)**: Recebe os logs, se comunica de forma assíncrona com a LLM via Groq (Qwen) para extrair o status, categoria, o problema e a solução, salvando no Postgres.
4. **PostgreSQL**: Armazena permanentemente os logs analisados para pesquisa histórica e dashboards.
5. **Grafana**: Fornece um Dashboard visual interativo com painéis, gráficos de distribuição e uma tabela de incidentes com Modal customizado em HTML/JS para detalhar as soluções geradas pela IA.

---

## Estrutura do Repositório

- `/log-service`: API REST Java (Spring Boot) para ingestão e comunicação com a IA.
- `/vector`: Configurações de coleta de logs.
- `/grafana`: Configurações de UI, datasources e dashboards pré-construídos.
- `docker-compose.dev.yml`: Configuração principal para o ambiente de desenvolvimento.
- `docker-compose.yml`: Ambiente de produção usando imagens pré-buildadas do Docker Hub.
- `docker-compose.user.yml`: Template para as aplicações que desejam ser monitoradas pela stack.

---

## Pré-requisitos

- **Docker** e **Docker Compose**
- Uma Chave de API da **Groq** (necessária para gerar as análises com o modelo `qwen3.8-27b`).

---

## 🚀 Como Iniciar (Ambiente de Desenvolvimento)

Use este método para rodar o projeto buildando as imagens localmente a partir do código fonte.

### 1. Configurar Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto (baseado no `.env.example`):
```bash
cp .env.example .env
```
Abra o `.env` e insira sua chave da API da Groq:
```env
GROQ_API_KEY=gsk_sua_chave_aqui
```

### 2. Subir a Infraestrutura
Execute o comando abaixo na raiz do projeto:
```bash
docker-compose -f docker-compose.dev.yml up -d --build
```
Isso fará o build e o deploy do Postgres, Log-Service, Vector e Grafana simultaneamente.

### 3. Acessar o Dashboard
O Grafana ficará acessível em:
**URL:** [http://localhost:3000](http://localhost:3000)
**Usuário padrão:** `admin`
**Senha padrão:** `admin` (será solicitado que você troque no primeiro acesso).

Navegue até **Dashboards -> Hiperautos Log Monitor**.

---

## 🚀 Como Iniciar (Ambiente de Produção)

Para rodar em um servidor e baixar as imagens oficiais publicadas no Docker Hub (via CI/CD):

1. Crie o `.env` com a sua `GROQ_API_KEY`.
2. Rode o docker-compose padrão:
```bash
docker-compose up -d
```

---

## 🛠️ Como Monitorar as Minhas Aplicações

Para que o Vector (e consequentemente o dashboard) comece a capturar os logs das **SUAS** aplicações, você deve adicionar uma `label` obrigatória no container da aplicação e definir um `container_name` para que fique legível no dashboard.

Exemplo no seu próprio `docker-compose.yml` (ou no `docker-compose.user.yml` deste repo):

```yaml
services:
  api-pagamentos:
    image: minha-imagem:latest
    container_name: api-pagamentos # OBRIGATÓRIO: Use nomes amigáveis aqui. Evita nomes aleatórios do docker!
    labels:
      - "hiperautos.monitor=true" # OBRIGATÓRIO: Só containers com esta label são lidos
```
*(Assim que a aplicação printar um erro no console, ele aparecerá automaticamente no Grafana).*

---

## Dicas de UX no Dashboard

1. **Filtragem Rápida:** Você pode clicar nos painéis numéricos grandes ("Total de Logs" ou "Erros Críticos") para aplicar imediatamente um filtro à tabela de eventos.
2. **Modal Investigativo IA:** Clique em qualquer linha da tabela principal de eventos para abrir um Modal flutuante. O Modal exibirá o contexto completo, incluindo o motivo da falha e a ação recomendada pela inteligência artificial.

## CI/CD 

O projeto contém GitHub Actions configurado para construir e publicar as imagens Docker no registro assim que um `push` for realizado na branch `main`.

**Segredos necessários no GitHub:**
- `DOCKERHUB_USERNAME`: Seu usuário.
- `DOCKERHUB_TOKEN`: Token de acesso do Docker Hub.
