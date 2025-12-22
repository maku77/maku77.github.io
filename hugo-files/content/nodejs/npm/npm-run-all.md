---
title: "Node.jsメモ: npm run のスクリプトを連続実行・並列実行する (npm-run-all)"
url: "p/fzudboe/"
date: "2019-10-07"
tags: ["nodejs"]
aliases: /nodejs/npm/npm-run-all.html
---

なぜ npm-run-all が必要か？
----

`package.json` の `scripts` プロパティで[スクリプトを定義](/p/9kiba2c/)しておくと、任意のコマンドを `npm run` コマンド経由で実行できるようになります（以降 NPM スクリプトと呼びます）。

しかし、デフォルトでは、複数の NPM スクリプトを連続して実行する方法（Linux の `cmd1 && cmd2`）や、並列に実行する方法（Linux の `cmd1 & cmd2`）は提供されていません。
強引に各 OS のシェルの `&&` や `&` を組み合わせることでできないことはありませんが、Windows と Linux で記述方法が異なるので、OS に依存した `package.json` になってしまいます。

このような場合に、**`npm-run-all`** という NPM パッケージを使用すると、複数の NPM スクリプトを逐次／並列実行するスクリプトを、OS に依存しない形で記述できるようになります。

- [npm-run-all パッケージ](https://www.npmjs.com/package/npm-run-all)


npm-run-all の使い方
----

### npm-run-all のインストール

`npm-run-all` は下記のようにインストールします。

```console
$ npm install --save-dev npm-run-all
```

`npm-run-all` コマンドは、`package.json` の `scripts` 定義の中から呼び出して使用するので、上記のように `--save-dev` オプションを指定して、`devDependencies` の依存情報に追加するようにしましょう。

### npm-run-all でシーケンシャル実行 (run-s)

**`run-s`** （`npm-run-all` のショートカット）というコマンドを使用すると、指定した NPM スクリプトをシーケンシャルに（ひとつずつ順番に）実行していくことができます。

{{< code lang="js" title="package.json" >}}
{
  // ...
  "scripts": {
    "hello": "run-s hello:foo hello:bar",
    "hello:foo": "echo FOO",
    "hello:bar": "echo BAR"
  },
  // ...
}
{{< /code >}}

上記の `hello` スクリプトを実行すると、`hello:foo` と `hello:bar` のスクリプトが順番に実行されます。

```console
$ npm run -s hello
FOO
BAR
```

ちなみに、上記のように `:` を使ってスクリプト名を細分化しておくと、グロブでスクリプトをまとめて指定することができます。

```js
"scripts": {
  "hello": "run-p hello:*",
```

### npm-run-all でパラレル実行 (run-p)

**`run-p`** （`npm-run-all --parallel` のショートカット）というコマンドを使用すると、指定した NPM スクリプトを並列に実行することができます。

{{< code lang="js" title="package.json" >}}
{
  // ...
  "scripts": {
    "hello": "run-p hello:*",
    "hello:foo": "timeout /t 3 > nul & echo FOO",
    "hello:bar": "timeout /t 3 > nul & echo BAR"
  },
  // ...
}
{{< /code >}}

このサンプルは、Windows の `timeout` コマンド（指定した秒数だけスリープ）を使用しているのであまりよくない例ですが、`run-p` の効果を確かめるにはこのようなスリープ＆エコーが手っ取り早いです。
下記のように実行すると、`hello:foo` と `hello:bar` スクリプトのスリープが同時に開始されるので、`FOO` と `BAR` の出力がほぼ同時に行われます。

```console
$ npm run -s hello
FOO
BAR
```

`hello` スクリプトの中の `run-p` という部分を `run-s` に変更すると、`FOO` が出力された後 3 秒待ってから `BAR` と表示されるはずです。

