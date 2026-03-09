---
title: "sed/awkメモ: awk で指定したフィールドを削除する"
url: "/p/wzdizih/"
date: "2015-10-27"
tags: ["sed/awk"]
aliases: [/sed/awk/delete-specified-field.html]
---

例えば、下記のような入力テキストファイルがあったとします。

{{< code title="input.txt" >}}
A1 A2 A3 A4 A5
B1 B2 B3 B4 B5
C1 C2 C3 C4 C5
{{< /code >}}

この各行から 3 番目のフィールド (A3, B3, C3) をそれぞれ削除したい場合は、次のように `$3` に空文字を代入してから全てのフィールド `$0` を出力すれば OK です（`print $0` の `$0` は省略できます）。

```console
$ awk '{$3 = ""; print}' input.txt
A1 A2  A4 A5
B1 B2  B4 B5
C1 C2  C4 C5
```

もちろん、次のように出力するフィールドをすべて指定することもできます。

```console
$ awk '{print $1, $2, $4, $5}' input.txt
A1 A2 A4 A5
B1 B2 B4 B5
C1 C2 C4 C5
```

ちなみに、`print` に対してカンマで複数のフィールドを渡すと、組み込み変数 `OFS` の値で区切られて出力されます。`OFS` のデフォルトの値は、スペース 1 文字なので、上記のようにスペース区切りで表示されるというわけです。

```console
$ awk 'BEGIN {OFS=","} {print $1, $2, $4, $5}' input.txt
A1,A2,A4,A5
B1,B2,B4,B5
C1,C2,C4,C5
```

