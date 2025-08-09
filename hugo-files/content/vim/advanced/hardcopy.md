---
title: "Vim で編集中のファイルを印刷する (hardcopy)"
url: "p/nw89tw2/"
date: "2014-01-27"
tags: ["vim"]
aliases: ["/vim/advanced/hardcopy.html"]
---

Vim で印刷する
----

以下のように実行すると、OS の印刷機能を使って現在開いているファイルを印刷することができます。

```
:hardcopy
```

印刷する範囲を指定したい場合は、ビジュアルモード (`Shift-v`) で行選択してから上記を実行します。


Vim の印刷設定
----

Vim のオプション設定で、印刷時のヘッダー出力などの設定を行うことができます。
詳しくは、下記のようにしてヘルプを参照してみてください。

```
:help printdevice
:help printencoding
:help printheader
:help printfont
:help printoptions
```

`printoptions` は、次のようにいろいろな設定を組み合わせて指定します。

{{< code title="例: 長い行を折り返し、行番号付き、横向きで印刷" >}}
:set printoptions=wrap:y,number:y,portrait:n
{{< /code >}}

{{< code title="例: B5 サイズで、縦向き、両面印刷（長辺閉じ）" >}}
:set printoptions=paper:B5,portrait:y,duplex:long
{{< /code >}}

{{< code title="例: A3 サイズで、横向き、両面印刷（短辺閉じ）" >}}
:set printoptions=paper:A3,portrait:n,duplex:short
{{< /code >}}

