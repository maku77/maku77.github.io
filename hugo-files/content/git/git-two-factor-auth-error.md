---
title: "Gitメモ: GitHub で git コマンドが Authentication failed になる場合（HTTPS 認証とアクセストークン）"
url: "p/c7ddt8i/"
date: "2015-06-24"
lastmod: "2026-03-21"
tags: ["git"]
aliases: [/git/git-two-factor-auth-error.html]
---

症状
----

GitHub に対して `git clone` や `git push` を HTTPS 経由で実行すると、以下のように認証エラーが発生することがあります。

```
$ git clone https://github.com/xxx/yyy.git
Cloning into 'yyy'...
Username for 'https://github.com': maku77
Password for 'https://maku77@github.com': *****
fatal: Authentication failed
```

これは、GitHub が 2021 年 8 月にパスワードによる HTTPS の Git 認証を廃止したことが原因です。
二要素認証の有無にかかわらず、HTTPS でアクセスする場合は **パーソナルアクセストークン (PAT)** を使う必要があります。


対処法 1: パーソナルアクセストークンを使う (HTTPS)
----

GitHub の [Personal access tokens ページ](https://github.com/settings/tokens) からアクセストークンを生成し、パスワードの代わりに入力します。

1. GitHub の **Settings → Developer settings → Personal access tokens → Tokens (classic)** を開く
2. **Generate new token** をクリック
3. 必要なスコープ（最低限 `repo`）を選択してトークンを生成
4. 生成されたトークンをコピーしておく（画面を離れると二度と表示されません）

`git clone` や `git push` でパスワードを求められたら、このトークンを入力します。


対処法 2: Git の資格情報ヘルパーでトークンを保存する
----

毎回トークンを入力するのが面倒な場合は、Git の **資格情報ヘルパー (credential helper)** を使ってトークンを保存できます。

```console
$ git config --global credential.helper store
```

この設定後に一度トークンを入力すると、`~/.git-credentials` ファイルに保存され、以降は入力が不要になります。

macOS の場合は、キーチェーンに安全に保存する `osxkeychain` ヘルパーが使えます。

```console
$ git config --global credential.helper osxkeychain
```


対処法 3: SSH 接続に切り替える
----

HTTPS の代わりに SSH でアクセスすれば、トークンの管理自体が不要になります。
SSH 鍵を GitHub に登録した上で、SSH 形式の URL を使用します。

```console
$ git clone git@github.com:xxx/yyy.git
```

既存のリポジトリのリモート URL を HTTPS から SSH に切り替えるには、次のようにします。

```console
$ git remote set-url origin git@github.com:xxx/yyy.git
```

