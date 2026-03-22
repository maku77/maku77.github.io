---
title: "PHPメモ: PHP でヒアドキュメント"
url: "/p/77kfkoe/"
date: "2012-01-15"
tags: ["php"]
aliases: [/php/syntax/here-document.html]
---

PHP では下記のような構文でヒアドキュメントを扱うことができます。
ヒアドキュメント内の変数は、二重引用符で囲まれた文字列と同様に展開されます。

```php
print <<<EOT
<ul>
  <li>$val1
  <li>$val2
  <li>$val3
</ul>
EOT;
```

{{< code lang="html" title="実行結果" >}}
<ul>
  <li>value of val1
  <li>value of val2
  <li>value of val3
</ul>
{{< /code >}}

エスケープシーケンスや、変数の展開をしたくない場合は、**Nowdoc** (PHP 5.3.0) を使用します。
Nowdoc を使うには、終端を表すラベルをシングルクォーテーションで囲むだけです。
以下のようにすると、ドル記号 `$` がそのまま表示されます。

```php
print <<<'EOT'
<ul>
  <li>$val1
  <li>$val2
  <li>$val3
</ul>
EOT;
```

{{< code lang="html" title="実行結果" >}}
<ul>
  <li>$val1
  <li>$val2
  <li>$val3
</ul>
{{< /code >}}

