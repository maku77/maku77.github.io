---
title: "JavaScriptメモ: jQuery 関連用語"
url: "p/mxevfzw/"
date: "2013-01-04"
tags: ["javascript"]
aliases: [/js/jquery/terms.html]
---

jQuery function（jQuery 関数）
----

`jQuery()` や `$()` のこと。あるいは、`jQuery.noConflict()` などの jQuery namespace に属する static method のこと。これらを区別するため、「jQuery Pocket Reference」では、前者を **the** jQuery function、後者を **a** jQuery function と呼び分けています。日本語の場合は… orz

`jQuery()` は、セレクタ文字列で要素を取得するところが **document.querySelectAll()** に似ています。でも `jQuery()` を使うことによって、CSS3 に対応していないブラウザでも同様のセレクタ指定を行うことができます。また、`document.querySelectAll()` が返す `NodeList` オブジェクトよりも、`jQuery()` が返す `jQuery` オブジェクトの方が便利です。

jQuery object（jQuery オブジェクト）
----

`jQuery()` や `$()` が返すオブジェクト。`document` 要素 (document element) の集まりを管理します。

`jQuery` オブジェクトは **length** プロパティや、square-bracket notation (`[]`) によるアクセスができるので、配列のように扱えますが、あくまで `jQuery` オブジェクトであって配列ではありません。**.toArray** メソッドを使うことで、本物の配列に変換することもできます。

jQuery method（jQuery メソッド）
----

`jQuery` オブジェクトが持つメソッド。

本家のドキュメントでは、jQuery function を、**$.each** のように表し、jQuery method を **.each** のように表しています。

