---
title: PHP でヒアドキュメント
date: "2012-01-15"
---

PHP では下記のような構文でヒアドキュメントを扱うことができます。
ヒアドキュメント内の変数は、二重引用符で囲まれた文字列と同様に展開されます。

~~~
print <<<EOT
<UL>
  <LI>$val1
  <LI>$val2
  <LI>$val3
</UL>
EOT;
~~~

#### 実行結果

~~~
<UL>
  <LI>value of val1
  <LI>value of val2
  <LI>value of val3
</UL>
~~~

エスケープシーケンスや、変数の展開をしたくない場合は、**Nowdoc** (PHP 5.3.0) を使用します。
Nowdoc を使うには、終端を表すラベルをシングルクォーテーションで囲むだけです。
以下のようにすると、ドル記号 `$` がそのまま表示されます。

~~~
print <<<'EOT'
<UL>
  <LI>$val1
  <LI>$val2
  <LI>$val3
</UL>
EOT;
~~~

#### 実行結果

~~~
<UL>
  <LI>$val1
  <LI>$val2
  <LI>$val3
</UL>
~~~

