---
title: "Bash の構文: ループ内での複数の出力をまとめてリダイレクト、パイプ処理する"
date: "2009-12-02"
---


コマンドのグルーピング
----

Bash 上で任意のコマンドを実行するときに、

~~~ bash
$ (command1; command2; command3)
~~~

のようにコマンドを括弧 `()` で囲で囲んでグルーピングすると、リダイレクトなどの効果が全てのコマンドに対して働くようになります。

~~~ bash
$ (echo 'AAA'; echo 'BBB'; echo 'CCC') > output.txt
~~~

上記は一行で記述していますが、次のように複数行に分けて記述することもできます。
この場合、各コマンドの末尾にセミコロン (`;`) は必要ありません。

~~~ bash
(
  echo 'AAA'
  echo 'BBB'
  echo 'CCC'
) > output.txt
~~~

グルーピングしたコマンドの出力をパイプ処理でつなげていくこともできます。

~~~ bash
(
  echo 'foo'
  echo 'bar'
  echo 'yahoo'
  echo 'foo'
) | sort | uniq
~~~

括弧で囲まれたコマンドは子シェルで実行されるので、その中でカレントディレクトリや環境変数などを変更した場合は、コマンド終了時に元に戻ります。
一時的にカレントディレクトリを変更してコマンドを実行したい場合に便利です。

~~~ bash
$ pwd
/home

$ (cd /etc; pwd)
/etc

$ pwd
/home
~~~


ループ内の出力をリダイレクトする
----

`for` ループや `while` ループの中で `echo` 出力した結果は、`done` の後ろでリダイレクトするようにすれば、まとめてファイルに出力することができます。

~~~ bash
for x in AAA BBB CCC; do
  echo "$x"
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


ループ内の出力をパイプで接続する
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

