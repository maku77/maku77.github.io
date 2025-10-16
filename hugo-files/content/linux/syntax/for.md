---
title: "Linuxメモ: Bash の構文: for/while によるループ処理"
url: "p/eimpfje/"
date: "2008-10-28"
tags: ["linux"]
aliases: /linux/syntax/for.html
---

スペースで区切られた単語（ワード）を順番に処理する
----

次の Bash スクリプトでは、ループ処理したいテキスト (`aaa`, `bbb`, `ccc`) を直接 `for` ループで指定しています。
ここでは、`do` を `for` と同じ行に記述するコーディングスタイルを採用しています。

{{< code lang="bash" title="例: aaa bbb ccc を順番に処理" >}}
for x in aaa bbb ccc; do
  echo $x
done
{{< /code >}}

{{< code title="実行結果" >}}
aaa
bbb
ccc
{{< /code >}}

以下のように、スペースで区切られた文字列が格納された文字列変数を処理することもできます。

```bash
list="aaa bbb ccc"
for x in $list; do
  echo $x
done
```


スペースを含む文字列を1つの要素として扱う
----

リスト内の各要素をダブルクォートで囲んでおくと、その単位でループ処理されるようになります。

```bash
#!/bin/bash

for i in "aaa bbb" "ccc ddd" "eee fff"; do
  echo $i
done
```

{{< code title="実行結果" >}}
aaa bbb
ccc ddd
eee fff
{{< /code >}}

変数を使う場合もほぼ同様ですが、**それぞれの変数をダブルクォートで囲む** 必要があります（これを忘れると、それぞれの変数内の文字列がスペースで区切られてループ処理されてしまいます）。

```bash
x="aaa bbb"
y="ccc ddd"
z="eee fff"

for i in "$x" "$y" "$z"; do
  echo $i
done
```

{{< code title="実行結果" >}}
aaa bbb
ccc ddd
eee fff
{{< /code >}}

ちなみに、下記のようにすべての変数をまとめてダブルクォートで囲むと、ひとつの要素として処理されます。

```bash
for i in "$x $y $z"; do
  echo $i
done
```

{{< code title="実行結果" >}}
aaa bbb ccc ddd eee fff
{{< /code >}}


応用: 複数の要素の組み合わせ
----

次のように `for` ループに渡す処理を `{` と `}` を使ってグルーピングすると、それぞれのグループ内の要素の組み合わせでループすることができます。

```bash
for x in {A,B,C}-{1,2}; do
  echo $x
done
```

{{< code title="実行結果" >}}
A-1
A-2
B-1
B-2
C-1
C-2
{{< /code >}}


応用: C/C++ のような for ループを記述する
----

次のように括弧を重ねて使用することで、C/C++ のようなフォーマットで for ループを記述することができます。
気持ち悪いのでオススメはしません。

```bash
for (( i=0 ; i<3 ; ++i )); do
  echo $i
done
```

{{< code title="実行結果" >}}
0
1
2
{{< /code >}}

ちなみに、上記のような連番を生成したい場合は、`seq` コマンドを使うと柔軟に生成することができます。

```bash
for i in `seq 0 2`; do
  echo $i
done
```

{{< code title="実行結果" >}}
0
1
2
{{< /code >}}

`seq` コマンドでは、1ループごとのステップをマイナスにしたり、小数点数にしたりできます。

```bash
for i in `seq 1 -0.1 0.5`; do
  echo $i
done
```

{{< code title="実行結果" >}}
1.0
0.9
0.8
0.7
0.6
0.5
{{< /code >}}
