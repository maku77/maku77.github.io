---
title: sed の置換パターンの中でシェルの変数を参照する
created: 2010-08-26
---

sed の置換パターンの中で変数展開するには、シェルに先に変数展開させるために、ダブルクォーテーションで式を囲む必要があります。
シングルクォーテーションで囲むと、`$val` という文字列そのものを置換してしまいます。

~~~
$ val=AAA

$ echo 'AAA BBB CCC' | sed -e "s/$val/hoge/"
hoge BBB CCC

$ echo 'AAA BBB CCC' | sed -e "s/CCC/$val/"
AAA BBB AAA
~~~

