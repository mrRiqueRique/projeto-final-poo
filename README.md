# 📚 Gerenciador Acadêmico

## 🛠️ Comandos Chave do Git

### 🌿 **Mudar de branch**

Para mudar de branch, use o comando:

```bash
git checkout -b nome-da-nova-branch
```

Voltar pra main:

```bash
git checkout main
```

### 🔄 Atualizar sua branch com as mudanças da main

1. Esteja na sua branch

   > Não sabe em que branch está? Roda um `git status`

2. Pegue as atualizações gerais do projeto da nuvem para seu projeto local

```bash
git fetch
```

3. Atualize sua branch com a main

```bash
git rebase origin/main
```

4. Suba a atualização da sua branch pra nuvem
```bash
git push --force-with-lease
```

