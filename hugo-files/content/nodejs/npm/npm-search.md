---
title: "Node.jsメモ: npm コマンドでインストール可能なパッケージを検索する (npm search)"
url: "p/akf8eh9/"
date: "2018-11-22"
tags: ["nodejs"]
aliases: /nodejs/npm/npm-search.html
---

**`npm search`** コマンドを使用すると、npm によって取得可能なパッケージを検索することができます。
例えば、次のように実行すると、パッケージ名や Description、キーワードリストなどに `express` という単語が含まれているパッケージの一覧を表示することができます。

{{< code lang="console" title="例: express に関するパッケージを検索" >}}
$ npm search express
NAME           | DESCRIPTION          | AUTHOR        | DATE       | VERSION | KEYWORDS
express        | Fast,…               | =dougwilson…  | 2018-10-11 | 4.16.4  | express framework sinatra web rest restful router app api
path-to-regexp | Express style path…  | =blakeembrey… | 2018-08-26 | 2.4.0   | express regexp route routing
cors           | Node.js CORS…        | =dougwilson…  | 2018-11-04 | 2.8.5   | cors express connect middleware
morgan         | HTTP request logger… | =dougwilson   | 2018-09-11 | 1.9.1   | express http logger middleware
...
{{< /code >}}

また、下記の npm のホームページでパッケージを検索することもできます。

- [https://www.npmjs.com](https://www.npmjs.com)

