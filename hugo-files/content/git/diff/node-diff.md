---
title: "Gitメモ: git diff で package-lock.json の差分が表示されるのを防ぐ (:(exclude))"
url: "p/4zoiaou/"
date: "2020-07-07"
tags: ["git"]
aliases: [/git/diff/node-diff.html]
---

Node.js でアプリを開発していると、npm が生成する依存情報ファイルである [package-lock.json も一緒にコミットする](https://docs.npmjs.com/configuring-npm/package-lock-json.html) ことになると思います。
`npm install` で新しくモジュールをインストールすると、`package-lock.json` に大量の差分が発生してしまい、自分が修正したソースコードの差分を `git diff` で確認するのが難しくなってしまいます。

そのような場合は、次のように `git diff` を実行すれば、`package-lock.json` 以外の差分を確認することができます。

```console
$ git diff -- . ":(exclude)package-lock.json"
```

ステージングされたものの差分を見るには次のようにします。

```console
$ git diff --staged -- . ":(exclude)package-lock.json"
```

これらのコマンドを頻繁に使用するようであれば、次のようにエイリアスを定義しておくのがよいでしょう（`ndiff` は node diff の略のつもり）。

```console
$ git config --global alias.ndiff "diff -- . :(exclude)package-lock.json"
$ git config --global alias.ndiff-staged "diff --staged -- . :(exclude)package-lock.json"
```

これで、次のように `package-lock.json` 以外の差分を簡単に確認できるようになります。

```console
$ git ndiff
$ git ndiff-staged
```

