---
title: "GitHub のオートリンク機能で外部サイトへ ID ベースでリンクする (autolink)"
url: "p/ias8kt3/"
date: "2020-12-03"
lastmod: "2022-07-27"
tags: ["GitHub"]
aliases: /git/github/autolink.html
---

オートリンク機能とは
----

GitHub の Issue（イシュー）や PR（プルリクエスト）のページの概要欄、コメント欄では、次のように Issue 番号を書いておくだけで該当ページに自動リンクすることができます。

```
#1234
```

- 参考: [Autolinked references and URLs - GitHub Docs](https://docs.github.com/en/free-pro-team@latest/github/writing-on-github/autolinked-references-and-urls)

この機能はあくまで GitHub サイト内のリンクを簡単に記述するためのものですが、__カスタムの Autolink references__ を設定しておくことで、任意の外部サイトに ID ベースで自動リンクできるようになります。

- 参考: [Configuring autolinks to reference external resources - GitHub Docs](https://docs.github.com/en/free-pro-team@latest/github/administering-a-repository/configuring-autolinks-to-reference-external-resources)

例えば、仕様書サイトのあるページ（ID=123 とします）にリンクしたい場合に、次のように簡単にリンクできるようになります。

```
SPEC-123
```

URL そのものを記述する必要がなくなるので、仕様書サイトのサーバー移転などでドメイン名が変わったとしても、Issue のコメントに記述したリンクが無効になってしまうことがありません。
ちなみにブログサイトなどの各ページの URL には、ID ベースのパーマリンクアドレスを割り当てる運用にしておくと、こういった機能と連携しやすくなります。

- 参考: [サイト構造を変えてもページの URL が変わらないようにする - まくまくHugoノート](/p/u9r9p7n/)


カスタム・オートリンクの設定
----

（2022年7月時点で、このカスタム・オートリンクは、GitHub Pro、GitHub Team などの __有料プランのみで設定可能__ です）

外部サイトへのオートリンク機能を有効にするには、次のように設定します。
ここでは、外部サイトの URL は `https://spec.example.com/p/123/` といったパーマリンクで提供されるものとします（123 といった ID はサーバー移転などしても変わらないことが保証されていると過程）。

1. 対象のリポジトリの __`Settings`__（設定）を選択
2. サイドバーから __`Autolink references`__ を選択
3. 次のような感じで Autolink 方法を設定
   - Reference prefix: __`SPEC-`__
   - Target URL: __`https://spec.example.com/p/<num>/`__

すると、Issue や PR のコメントとして `SPEC-123` のように記述するだけで、自動的に `https://spec.example.com/p/123/` という URL へリンクされるようになります。

### 注意点

~~`SPEC-XXX` という形でリンクを張るとき、`XXX` の部分には __数値しか指定できない__ ということに注意してください。~~
~~`SPEC-xyz1234` といった数値以外の文字が含まれる ID では自動リンクしてくれません。~~
~~GitHub チームに問い合わせましたが、2020年12月時点では、やはり数値のみのページ ID にしか対応していないとのこと。~~
~~パーマリンクのアドレスとしては、`xyz1234` といった数値以外の文字を含む ID は一般的に使われているので、このあたりは改善されることを期待しています。~~

__（2022年7月追記）__ これまで、オートリンクの ID 部分には数値しか指定できませんでしたが、GitHub 側の対応により、英数字及びハイフンの組み合わせ (`[a-zA-Z0-9-]+`)が使えるようになりました。
例えば、`SPEC-xyz1234` のように、ID 部分に英数字を含んでいてもオートリンクが機能します。

- 参考: [Autolinks with alphanumeric IDs - GitHub Changelog](https://github.blog/changelog/2022-07-01-autolinks-with-alphanumeric-ids/)

