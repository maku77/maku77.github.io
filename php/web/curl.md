---
title: "cURL 関数を使って HTTP リクエストを送る"
date: "2013-01-26"
---

cURL の基本的な使い方
----

cURL 関数を使うと、http、https、ftp リクエストを統一された手順で実行できます。
どのリクエストの場合も、以下のような手順で実行します。

1. `curl_init()` で cURL セッションを作成
2. `curl_setopt()` でオプションを設定
3. `curl_exec()` でリクエストを送信
4. `curl_close()` cURL セッションを閉じる


HTTP GET のレスポンスを標準出力に出力する
----

以下のようにすると、`http://example.com/` のページ内容を取得して、標準出力に出力します。

~~~ php
<?php
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, 'http://example.com/');
curl_exec($ch);
curl_close($ch);
~~~

上記では、リクエスト先のアドレスを `curl_setopt()` で指定していますが、`curl_init()` で cURL セッションを初期化するときに同時に指定することもできます。

~~~ php
$ch = curl_init('http://example.com/');
curl_exec($ch);
curl_close($ch);
~~~

ちなみに、`$ch` は cURL handle の略です。


HTTP レスポンスのヘッダの内容を取得する
----

HTTP レスポンスのヘッダ内容まで取得するには、`CURLOPT_HEADER` オプションを `true` に設定します。

~~~ php
<?php
$ch = curl_init('http://localhost/');
curl_setopt($ch, CURLOPT_HEADER, true);
curl_exec($ch);
curl_close($ch);
~~~

#### 実行例

~~~
$ php test.php
HTTP/1.1 200 OK
Date: Sun, 27 Jan 2013 04:34:00 GMT
Server: Apache/2.2.14 (Unix) DAV/2 mod_ssl/2.2.14 OpenSSL/0.9.8l PHP/5.3.1 mod_perl/2.0.4 Perl/v5.10.1
Last-Modified: Mon, 21 Jan 2013 13:23:59 GMT
ETag: "4bd58c-20c-4d3cc5faff9c0"
Accept-Ranges: bytes
Content-Length: 524
Content-Type: text/html

<!DOCTYPE html>
<html>
    ...
</html>
~~~


HTTP GET のレスポンスを文字列で受け取る
----

`curl_exec()` を実行すると、デフォルトではレスポンスを標準出力に出力します。
レスポンスを文字列変数に受け取りたい場合は、`CURLOPT_RETURNTRANSFER` オプションを `true` に設定します。

~~~ php
<?php
$ch = curl_init('http://example.com/');
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
$res = curl_exec($ch);
curl_close($ch);
echo $res;
~~~

デフォルトでは `curl_exec()` は、成功、失敗に応じて `true` か `false` を返しますが、`CURLOPT_RETURNTRANSFER` が設定されている場合は、`curl_exec()` は成功時にレスポンステキストを返し、失敗時に `false` を返すようになります。


HTTP GET のレスポンスをファイルに保存する
----

`CURLOPT_FILE` オプションを指定すると、HTTP レスポンスの出力先を、標準出力からファイルに切り替えることができます。

~~~ php
<?php
$ch = curl_init('http://example.com/');
$fp = fopen('output.txt', 'w');

curl_setopt($ch, CURLOPT_FILE, $fp);  // ファイルにレスポンスを出力
curl_setopt($ch, CURLOPT_HEADER, false);  // ヘッダは出力しない

curl_exec($ch);
curl_close($ch);
fclose($fp);
~~~

