---
title: "シェルスクリプト: テキストファイルを 1 行ずつ読み込む (read)"
url: "p/co9p7nj/"
date: "2012-11-05"
tags: ["Linux"]
description: "シェルスクリプトの while 構文と、read コマンドを組み合わせて使用すると、テキストファイルの内容を 1 行ずつ処理できます。"
aliases: /linux/io/read-file.html
---

テキストファイルを 1 行だけ読み込む
----

__`read`__ コマンドは、ユーザからの入力を 1 行取得するために使用できますが、ファイルからの入力を 1 行読み込むのにも使用できます。

{{< code lang="bash" title="sample.sh （input.txt の内容を 1 行だけ読み込む）" >}}
#!/bin/bash

read line < input.txt
echo "$line"
{{< /code >}}

{{< code title="input.txt（入力データ）" >}}
AAA   100
BBB   200
CCC   300
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ./sample.sh
AAA   100
{{< /code >}}

これだけだとあまり役に立たないので、通常は次のように `while` ループを組み合わせて、すべての行を読み出します。


テキストファイルから 1 行ずつ読み込む
----

__`read`__ コマンドと __`while`__ ループを組み合わせると、テキストファイルのすべての行を 1 行ずつ処理できます。

{{< code lang="bash" title="sample.sh （input.txt の内容を 1 行ずつ読み込む）" >}}
#!/bin/bash

while read line; do
  echo "$line"
done < input.txt
{{< /code >}}

{{< code title="input.txt（入力データ）" >}}
AAA   100
BBB   200
CCC   300
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ./sample.sh
AAA   100
BBB   200
CCC   300
{{< /code >}}

{{% note %}}
`echo` のパラメータ `"$line"` をダブルクォートで囲んでいることに注意してください。
こうすることで、1 行分の文字列を、ひとつのパラメータとして渡すことができます。
ダブルクォートで囲まないと、`echo AAA 100` のように、2 つのパラメータが渡されたかのように処理されてしまうため、連続するスペースが 1 つのスペースに置き換えられて出力されてしまいます。
{{% /note %}}

- 参考: [echo で出力した文字列の中の連続するスペースが 1 つのスペースになってしまうのを防ぐ](/p/25gqyai/)


行頭のスペースが消えてしまうのを防ぐ
----

例えば、下記のように行頭がインデントされたテキストファイルを読み込むとします。

{{< code title="input.txt（入力データ）" >}}
・カテゴリA
    ・カテゴリA-1
    ・カテゴリA-2
・カテゴリB
    ・カテゴリB-1
    ・カテゴリB-2
{{< /code >}}

このファイルを次のように読み込むと、行頭のスペースが削除されてしまします。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

while read line; do
  echo "$line"
done < input.txt
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ./sample.sh
・カテゴリA
・カテゴリA-1
・カテゴリA-2
・カテゴリB
・カテゴリB-1
・カテゴリB-2
{{< /code >}}

行頭のスペースをそのまま表示したい場合は、次の例のように、組み込み変数 __`IFS`__ を空文字にセットしてから処理するようにします。
変更した `IFS` の値は、最後に行儀よく元の値に戻してあげてください（`source` コマンドなどでスクリプトを読み込んだときに、呼び出し元シェルの `IFS` の値が変更されたままになってしまうのを防ぐため）。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

old_ifs=$IFS
IFS=''

while read line; do
  echo "$line"
done < input.txt

IFS=$old_ifs
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ./sample.sh
・カテゴリA
    ・カテゴリA-1
    ・カテゴリA-2
・カテゴリB
    ・カテゴリB-1
    ・カテゴリB-2
{{< /code >}}

`IFS` 特殊変数に関しての詳細は、`man bash` を参照してください。
`IFS` は内部的な文字列のセパレート処理に使われるキャラクター群を保持しています。

