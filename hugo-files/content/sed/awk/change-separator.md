---
title: "sed/awkメモ: awk でフィールドセパレータを変更する"
url: "/p/qju45tc/"
date: "2007-12-19"
tags: ["sed/awk"]
aliases: [/sed/awk/change-separator.html]
---

デフォルトのセパレータは空白
----

awk は、デフォルトのフィールドセパレータとして空白やタブを使用します。

{{< code title="input.txt（空白で区切られたレコード）" >}}
A1 A2 A3
B1 B2 B3
C1 C2 C3
{{< /code >}}

{{< code lang="console" title="2 番目のフィールドを抜き出す" >}}
$ awk '{print $2}' input.txt
A2
B2
C2
{{< /code >}}


セパレータをカンマに変更する
----

例えば、CSV ファイルのように、カンマで区切られたテキストファイルを扱いたい場合は、フィールドセパレータを変更しなければいけません。
フィールドセパレータとして扱う文字を変更するには、`-F` オプションを使用します。

{{< code title="input.csv（カンマで区切られたレコード）" >}}
A1,A2,A3
B1,B2,B3
C1,C2,C3
{{< /code >}}

{{< code lang="console" title="2 番目のフィールドを抜き出す（セパレータをカンマに変更）" >}}
$ awk -F, '{print $2}' input.txt
A2
B2
C2
{{< /code >}}

