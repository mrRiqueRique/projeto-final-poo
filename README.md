# 📚 Gerenciador Acadêmico
- [UML](https://app.diagrams.net/#G1gtKTzDHyposuOEo_MXRLSdPxKTevUIVq#%7B%22pageId%22%3A%22xjf5HWkRNmCtRanLKS7O%22%7D)
- [Google Docs](https://docs.google.com/document/d/1oL_Vor21liBjnLuWA0td5tS9-XJMVKEF66hNHhqbRN0/edit?tab=t.0)
- [Google Sheets](https://docs.google.com/spreadsheets/d/1Ie0FsmkwFZHv4AO-4pdGRV4lLMaRH1mLMQq-BSKB0BA/edit?gid=1304622245#gid=1304622245)

## 🛠️ Comandos Chave do Git

**📥 Pega as alterações da sua branch da nuvem e joga pra local**

```bash
git pull
```

**➕ Salva todas suas alterções no código pra commitar**

```bash
git add .
```

**✅ Cria um commit**

```bash
git commit -m "Descrição do commit"
```

**⬆️ Sobe as atualizações da sua branch local pra nuvem**

```bash
git push
```

## 🌿 **Mudar de branch**

**🔀 Para mudar de branch, use o comando:**

```bash
git checkout -b nome-da-nova-branch
```

**↩️ Voltar pra main:**

```bash
git checkout main
```

## 🔄 Atualizar sua branch com as mudanças da main

**1. Esteja na sua branch**

> Não sabe em que branch está? Roda um `git status`

**2. Pegue as atualizações gerais do projeto da nuvem para seu projeto local**

```bash
git fetch
```

**3. Atualize sua branch com a main**

```bash
git rebase origin/main
```

**4. Suba a atualização da sua branch pra nuvem**

```bash
git push --force-with-lease
```

## 📌 Mergear sua branch na main

**1. Faça a [atualização da sua branch com a main](https://github.com/mrRiqueRique/projeto-final-poo/tree/main?tab=readme-ov-file#-atualizar-sua-branch-com-as-mudan%C3%A7as-da-main) do tópico anterior**

**2. No github vá em [`Pull requests`](https://github.com/mrRiqueRique/projeto-final-poo/pulls)**

**3. Crie seu PR e clique no botão de mergear caso não tenha conflito**

> Se deu conflito é porque o passo **1** não aconteceu

![image](https://github.com/user-attachments/assets/c78b3ec7-3e50-4af9-b6e2-b592d8594bf7)
