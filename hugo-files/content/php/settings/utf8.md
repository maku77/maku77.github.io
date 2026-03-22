---
title: "PHPメモ: PHP で UTF-8 を使用するための設定"
url: "/p/67tskzv/"
date: "2012-08-04"
tags: ["php"]
aliases: [/php/settings/utf8.html]
---

PHP で UTF-8 を使用するための設定サンプルです。

{{< code lang="ini" title="php.ini" >}}
default_charset = "utf-8"
mbstring.language = Japanese
mbstring.internal_encoding = UTF-8
mbstring.http_input = pass
mbstring.http_output = pass
mbstring.encoding_translation = Off
mbstring.detect_order = UTF-8,SJIS,EUC-JP,JIS,ASCII
mbstring.substitute_character = none
{{< /code >}}

`default_charset` の指定により、HTTP プロトコルのヘッダ情報に `Content-Type: text/html; charset=utf-8` が追加されます。

`mbstring.detect_order` は、文字コードの自動検出時に優先する順に記述しておく必要があります。

