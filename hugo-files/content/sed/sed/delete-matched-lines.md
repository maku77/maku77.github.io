---
title: "sed/awkメモ: sed でパターンに一致する行を削除する"
url: "/p/hyizkbq/"
date: "2015-10-27"
tags: ["sed/awk"]
aliases: [/sed/sed/delete-matched-lines.html]
---

sed での行削除
----

`sed` の行削除コマンド `d` の直前に `/pattern/` を指定すると、そのパターンに一致する文字列を含む行を削除できます。
例えば、下記のようなファイルの `#` で始まるコメント行だけを削除して出力するとします。

{{< code title="input.txt" >}}
# Comment 1
# Comment 2
Body 1
Body 2
{{< /code >}}

正規表現で `#` で始まる行は、`^#` と表現できるので、これに一致した行を下記のように `d` 命令で削除して出力すれば OK です。

```console
$ sed '/^#/d' input.txt
Body 1
Body 2
```


参考（awk や grep の場合）
----

実は、`awk` コマンドでも同様のことを行うことができます。

```console
$ awk '!/^#/' input.txt
Body 1
Body 2
```

ただ、`sed` コマンドであれば、`-i` オプションを使って自分自身のファイルに書き戻すこともできるので、単純な行削除処理をしたいのであれば `sed` の方がオススメです。
`awk` コマンドを使った場合の行削除に関して、詳しい説明は下記を参照してください。

* [awk でパターンに一致する行を削除する](/p/mypf4rw/)

`grep` コマンドの `-v` オプションを使用しても同様のことを行えます。

```console
$ grep -v '^#' input.txt
Body 1
Body 2
```

