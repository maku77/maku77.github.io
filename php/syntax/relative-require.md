---
title: PHP ファイルからの相対パスで require_once する
date: "2012-09-25"
---

以下のように `__DIR__` を使用すると、現在実行している PHP ファイルがあるディレクトリから、相対パスで別の PHP ファイルを読み込むことができます。
`__DIR__` は、末尾にスラッシュ `/` の付かないディレクトリパスを返すことに注意してください。

~~~ php
<?php
require_once __DIR__ . '/YourLibrary.php';
~~~

ちなみに、PHP 5.3 以前では、`__DIR__` が使えなかったため、以下のように `dirname()` を使って同じことを実現していました。

~~~ php
<?php
require_once dirname(__FILE__) . '/YourLibrary.php';
~~~

