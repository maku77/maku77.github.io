---
title: "Golang DRAFT"
url: "p/rwafc3y/"
date: "2023-05-10"
tags: ["Golang"]
draft: true
---

Go プロジェクトのビルドに Mage を使う
====

Mage とは
----

__Mage__ は Go 言語を使ったビルドツールです。

- [Mage - a Make/rake-like dev tool using Go](https://magefile.org/)

「Go 言語を使った」という部分を具体的に言うと次のような感じです。

- Mage 自体が Go 言語で作られている（だから 1 ファイルで提供されている）
- ビルドタスクを Go 言語のコードで定義する（OS に依存しない定義が可能）
- 主に Go 言語のプロジェクトのビルドに使用する（内部で `go build` を使っている）

Mage は（おそらく）地上最速の Web サイトジェネレーターである [Hugo](https://gohugo.io/) などでも採用されており、実績は十分にありそうです。
GitHub Actions 用の [Mage Action](https://github.com/marketplace/actions/mage-action) も用意されており、CI/CD 環境への導入もバッチリです。

Mage では、ビルドタスク（ビルドターゲット）を通常の Go コードで定義し、__`mage` コマンド__ 一発で呼び出せるようにします。
自ら Make/rake-like tool と言っていますが、どちらかというと、Kotlin/Java でよく使われている Gradle や、Node.js の Gulp と同様のツールだと言った方が伝わりやすいかもしれません。
Mage を使うと、OS に依存しない高度なビルドタスクを定義できます。


Makefile (make) からは脱却すべし
----

Go 言語のプロジェクトでは、ビルドツールとしてレガシーな `make` (`Makefile`) を使っているものをよく見かけますが、`Makefile` はどうしてもシェルに依存した記述になり、特定の OS でしかビルドできないという問題が発生しがちです。
Go 言語を使ってせっかくクロスプラットフォームなアプリを作っているのに、ビルドスクリプトが OS 依存というのはいただけません。
例えば、`Makefile` 内で何らかのファイル削除タスクを定義しようとして、Linux の `rm` コマンドを呼び出した時点で Windows 上では動作しなくなります。

そもそも、Windows には `make` コマンドは入っていません。
今さら、[Make for Windows](http://gnuwin32.sourceforge.net/packages/make.htm) を入れるの？嘘でしょ？
`make` のために WSL2 入れちゃうの？嘘でしょ？

もちろん、Mage もインストールの必要はありますが、[とてもコンパクト](https://github.com/magefile/mage/releases) で導入の敷居は低いです。
Mage の明示的なインストールすら必要のない [Zero Install Option](https://magefile.org/zeroinstall/) という方法も提供されています。
Go 言語には `go run` 実行時に依存ライブラリを自動で取得する仕組みがあり、それをうまく利用しています。

Go 言語のプロジェクトを扱っているのであれば、Go の実行環境はインストールされているはずですし、Go 言語でビルドタスクの定義を行うのは理にかなっています。


Magefile でビルドタスクを定義する
----

Magefile と呼んでいますが、実態はただの Go ファイルです。

