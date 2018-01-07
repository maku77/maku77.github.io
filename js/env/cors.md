---
title: CORS - Cross-Origin Resource Sharing とは？
date: "2012-10-23"
---

CORS の概要
----

JavaScript のオブジェクト `XMLHttpRequest` を使用すると、非同期に Web サイト上からデータを取得することができます。
ただし、`XMLHttpRequest` はセキュリティ上の考慮から Same Domain Policy の上で動いており、JavaScript をダウンロードしたドメインからしかデータを取得することができません。
**CORS (Cross-Origin Resource Sharing)** は、この制限をなくして別のドメインからのデータ取得を可能にする仕組みです。


CORS の仕組み
----

CORS 以下のような感じで実現されてます。

* 前提として、Same Domain Policy による `XMLHttpRequest` のアクセス制限は、ブラウザが実装しているものである。
* CORS の仕組みを利用する場合は、HTTP サーバが Response ヘッダに `Acess-Control-Allow-Origin: http://example.com` のように、アクセス許可できるドメインを指定する。
* ブラウザが上記のヘッダ情報を解釈し、Same Domain Policy の制限を取り払って `XMLHttpRequest` でアクセスする。
* JavaScript のコードでは、`XMLHttpRequest#withCredentials = true` と設定し、cookie を送るようにする。

要するに、CORS の利用には以下の対応が必要になります。

* ブラウザの実装（HTTP response header の解釈）
* クライアント側コード (JavaScript) の対応
* サーバ側コードの対応

一般的なブラウザは CORS に対応している（CORS 用の HTTP response header を解釈する）ので、あとは各アプリが実装すれば CORS の仕組みを利用することができます。

参考リンク
----

* 本家: [W3C - Cross-Origin Resource Sharing](http://www.w3.org/TR/access-control/)
* Wikipedia: [Cross-origin resource sharing](http://en.wikipedia.org/wiki/Cross-origin_resource_sharing)

