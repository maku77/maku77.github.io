---
title: CakePHP - クライアントからのリクエストデータを取得する (Controller::request)
created: 2012-05-27
---


GET リクエストの場合
----

Action の実装の中で以下のように取得します。

~~~ php
<?php
// URL が http://example.com/index?param=1 のような場合
$this->request->query['param'];  // => 1
~~~


POST リクエストの場合
----

Action の実装の中で以下のように取得します。

~~~ php
<?php
$this->request->data['param'];
~~~

