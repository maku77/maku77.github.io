---
title: "ループ内での出力をまとめてファイル出力する"
date: "2009-12-02"
---

ループ内の出力をリダイレクト
----

`for` ループや `while` ループの中で `echo` 出力した結果は、`done` の後ろでリダイレクトするようにすれば、まとめてファイルに出力することができます。

~~~ bash
for x in AAA BBB CCC; do
  echo $x
  echo "......"
done > output.txt
~~~

#### 出力結果 (output.txt)

~~~
AAA
......
BBB
......
CCC
......
~~~

上記では `>` を使用していますが、`>>` にすれば追記処理に変更することができます。


ループ内の出力をパイプで接続
----

ループ内の出力結果は、パイプ処理 (`|`) で別のコマンドにつなげることができます。

~~~ bash
for x in CCC AAA DDD EEE BBB; do
  echo $x
done | sort
~~~

#### 実行結果

~~~
AAA
BBB
CCC
DDD
EEE
~~~

