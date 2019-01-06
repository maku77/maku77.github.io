---
title: "HTML の特殊文字をエスケープする (htmlspecialchars)"
date: "2012-10-06"
---

小なり記号 (`<`) や、大なり記号 (`>`) など、HTML テキスト内で特別な意味を持つ文字を出力する場合は、HTML エスケープ処理を行ってから出力する必要があります。
PHP の `htmlspecialchars` 関数を使用すると、一連のエスケープ処理をまとめて行ってくれます。

#### 例: 小なり、大なり記号のエスケープ

~~~ php
htmlspecialchars('<strong>', ENT_QUOTES);  //==> '&lt;strong&gt;'
~~~

`htmlspecialchars()` はデフォルトで以下のような変換を行います。

* `<` → `&lt;`
* `>` → `&gt;`
* `&` → `&amp;`
* `"` → `&quot;`

さらに、以下のようにシングルクォートを `&#039;`（あるいは `&apos;`）に変換するには、第二引数 (`$flags`) に `ENT_QUOTES` を指定しておく必要があります。

* `'` → `&#039;` or `&apos;`

PHP プログラムから、フォームの input 要素や textarea 要素の内容を出力する場合、その内容が不定なときは必ず `htmlspecialchars()` でエスケープ処理を行っておく必要があります。

#### 例: textarea 要素の表示内容をエスケープ

~~~ php
<?php
    echo "<textarea name='content'>\n";
    echo htmlspecialchars($content) . "\n";
    echo "</textarea>\n";
?>
~~~

#### 例: input 要素の表示内容をエスケープ

~~~ php
<?php
    echo '<input type="text" name="title" value="' . htmlspecialchars($title) . '" />' . "\n";
?>
~~~

