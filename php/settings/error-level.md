---
title: PHP のエラー出力レベルを設定する
created: 2012-12-24
---

デフォルトのエラー出力設定
----

PHP のデフォルトのエラー出力設定は、`php.ini` の中で以下のように定義されています。

#### php.ini

~~~
error_reporting = E_ALL & ~E_NOTICE
display_errors = On
~~~

`error_reporting` はエラーの出力レベルを示しており、次のような値を指定できます。

* `E_PARSE` -- 構文エラー（実行前にチェックされる）
* `E_ERROR` -- 実行時の致命的エラー
* `E_WARNING` -- 実行時の警告（重要）
* `E_NOTICE` -- 実行時の警告
* `E_DEPRECATED` -- 廃止予定の文法、関数が使用されている (PHP 5.3)
* `E_STRICT` -- 非推奨な記述がある（コンパイル時に発生するものもあり）

上記に加えて、`E_ALL` が用意されており、これを指定すると、基本的に全てのチェックが行われるようになります。
ただし、`E_STRICT` だけは `E_ALL` に含まれていないため、`E_STRICT` レベルの警告を表示する場合は、`E_ALL | E_STRICT` と指定する必要があります。

#### 設定例

~~~
; 本番環境（E_NOTICE, E_DEPRECATED, E_STRICT 以外を表示）
error_reporting  = E_ALL ^ E_NOTICE ^ E_DEPRECATED

; 開発環境（全て表示）
error_reporting  = E_ALL | E_STRICT
~~~


コード内でエラー出力設定を変更する
----

PHP コード内で動的にエラー出力設定を変更するには、`error_reporting()` 関数でエラーレベルを設定し、`ini_set()` 関数でエラー表示の有効無効を切り替えます。

~~~ php
<?php
error_reporting(E_ALL | E_STRICT);
ini_set('display_errors', 'On');
~~~

