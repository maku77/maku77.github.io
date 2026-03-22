---
title: "PHPメモ: 標準出力への出力を行う"
url: "/p/v4nb5r6/"
date: "2012-01-15"
tags: ["php"]
aliases: [/php/io/stdio.html]
---

PHP で標準出力への出力を行うときは、主に `echo`、`print` が使用されます。
`echo` が戻り値を返さないのに対し、`print` は `int` 型の戻り値を返します。

```php
echo "Hello\n";   // 戻り値なし
print "Hello\n";  // 戻り値あり (PHP4、PHP5 では常に１)
```

`echo` も `print` も正確には関数ではなく、言語構造として組み込まれているため、引数を括弧で括る必要はありません。

文字列をダブルクォーテーションで囲むと、変数の値が展開されます。

```php
print "val = $val";
```

変数名が部分文字列になっている場合は、変数名の切れ目を示すために中括弧で囲む必要があります。

```php
print "Three {$fruit}s";
```

複数の文字列や変数を繋げて出力する場合は、文字列の結合演算子であるドット (`.`) を使用します。

```php
print 'val = ' . $val;
```

文字列の中には改行を含むことができます。

```php
print '<ul>
<li>item 1
<li>item 2
<li>item 3
</ul>';
```

{{< code lang="html" title="出力結果" >}}
<ul>
<li>item 1
<li>item 2
<li>item 3
</ul>
{{< /code >}}

