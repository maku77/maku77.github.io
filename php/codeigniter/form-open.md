---
title: CodeIgniter - Form タグの出力には Form ヘルパーの form_open を使う
created: 2012-08-30
---

HTML フォームの開始タグは、例えば以下のように記述できます。

~~~ html
<form class="stylish_form" action="/pages/do_create" method="post">
~~~

このように、`action` 属性に指定する「コントローラ名/アクション名」を絶対パスで指定してもよいのですが、絶対パスで記述してしまうと、Web サーバの DocumentRoot を変更するたびに `action` 属性の値を変更しなければならなくなります。

`Form` ヘルパーの提供する `form_open` メソッドを使用して、以下のように `form` タグを出力すると、DocumentRoot の設定を気にせずに「コントローラ名/アクション名」を指定できます。

~~~ php
<?php echo form_open('pages/do_create'); ?>
~~~

追加の属性は以下のように連想配列で指定できます。

~~~ php
<?php echo form_open('pages/do_create', array('class'=>'stylish_form')); ?>
~~~

