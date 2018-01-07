---
title: Node.js とは／Node.js をインストールする
date: "2012-11-25"
---

Node.js とは
----

Node.js は、Javascript によって記述されたプログラム（スクリプト）を実行するための実行環境です。
Javascript は、元々は Web ブラウザ内のコンテキストで実行されるスクリプト言語として開発されたものですが、Node.js のランタイム (Windows の場合は `node.exe`) を使用すると、ネイティブな実行環境で、Javascript のプログラムを実行することができます。
Python や Ruby のような実行環境が、Javascript 言語にも提供されたと考えると分かりやすいです。
例えば、Python によるプログラムは以下のように実行しますが、

```
$ python sample.py
```

同様に Javascript で記述したプログラムを、下記のように実行できます。

```
$ node sample.js
```

Node.js は、内部的には下記のような構成要素で成り立っています。

* V8 — JavaScript 実行エンジン。
* libuv — 非同期イベントライブラリ。Node.js の作者の Ryan Dahl によって作成された。

Node.js では、libuv をベースとする非同期 I/O の仕組みによって、シングルスレッドながらも多くの接続を処理することができるようになっており、サーバサイドのプログラミング言語としても注目を集めています。


Node.js のインストール
----

各 OS 用の Node.js インストーラが提供されています。
下記のサイトからダウンロードして実行するだけで簡単にインストールできます。

- [Node.js (https://nodejs.org/)](https://nodejs.org/)

Node.js の実行環境は、デフォルトで下記のようなディレクトリにインストールされます。

* Windows の場合: `C:\Program Files\nodejs\`
* Mac OSX の場合: `/usr/local/bin/node` および `/usr/local/bin/npm`

Node.js 0.6.0 以降は、Node.js 用のパッケージ管理ツールである **npm (Node Package Manager)** も一緒にインストールされます。
正しくインストールできたかどうかは、下記のようにして確認できます。

```
C:\> node -v
v0.8.14

C:\> npm -v
1.1.65
```

