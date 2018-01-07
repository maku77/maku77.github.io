---
title: CodeIgniter - 使用中の CodeIgniter のバージョンを調べる
date: "2012-10-04"
---

CodeIgniter のバージョンは、`system/core/CodeIgniter.php` の中で以下のように定義されています。

~~~ php
define('CI_VERSION', '2.1.0');
~~~

この値は、コントローラなどから、以下のように呼び出せば表示できます。

~~~
echo CI_VERSION;
~~~

ビューの中からも、以下のような感じで CodeIgniter へのリンクをバージョン付きで表示できます。

~~~ php
<footer>
Powerd by <a href="http://codeigniter.com">CodeIgniter <?php echo CI_VERSION ?></a>
</footer>
~~~

