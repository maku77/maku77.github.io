---
title: "Web サーバ上のコンテンツを HTTP で取得する (file_get_contents)"
date: "2013-06-11"
---

file_get_contents の基本
----

PHP の `file_get_contents()` を使用すると、Web サーバ上のコンテンツを簡単に取得することができます。

#### 例: http://example.com/sample.html の内容を取得する

~~~ php
<?php
$text = file_get_contents('http://example.com/sample.html');
print($text);
~~~


Basic 認証のかかったサイトからコンテンツを取得する
----

Basic 認証が要求されるコンテンツを取得したい場合は、以下のようにアドレス部分にユーザ ID とパスワードを含めてアクセスできます。

~~~ php
<?php
$text = file_get_contents('http://<user>:<password>@example.com/sample.html');
~~~


プロキシ経由での file_get_contents
----

プロキシ環境にいる場合は、以下のようにプロキシサーバを指定して `file_get_contents()` を実行することができます。

~~~ php
<?php
$proxy = array(
    "http" => array(
        "proxy" => "proxy.example.com:8080",
        'request_fulluri' => true
    )
);

$context = stream_context_create($proxy);
$text = file_get_contents('http://example.com/sample.html', false, $context);
~~~

