---
title: "sed/awkメモ: awk でパターンに一致する行を削除する"
url: "/p/mypf4rw/"
date: "2015-10-27"
tags: ["sed/awk"]
aliases: [/sed/awk/delete-matched-lines.html]
---

awk で「**パターンに一致する行を削除する**」という処理を行いたいときは、「**パターンに一致しない行だけを出力する**」と考えます。

例えば、下記のようなファイルの `#` で始まるコメント行だけを抜き出す、あるいは逆に、コメント行以外を抜き出したいとします。

{{< code title="input.txt" >}}
# This is a comment.
# This is also a comment.
Hello. My name is Maku.
Nice to meet you.
{{< /code >}}

ここで、`#` で始まるコメント行だけを抜き出す場合は、下記のように正規表現パターンを指定します。

```awk
$ awk '/^#/' input.txt
# This is a comment.
# This is also a comment.
```

逆に `#` で始まらない行を抜き出すには、下記のように、パターンの前に否定を表す `!` を付けます。

```awk
$ awk '!/^#/' input.txt
Hello. My name is Maku.
Nice to meet you.
```

これはつまり、指定したパターンに一致する行を削除していることにほかなりません。


おまけ
----

コメント行だけを出力するときに、ついでに先頭の `#` を削除してから出力するサンプル。

```awk
$ awk '/^#/ { sub("^[# ]+", ""); print }' input.txt
This is a comment.
This is also a comment.
```

参考（sed の場合）
----

`sed` コマンドでも同様のことを行うことができます。

* [sed でパターンに一致する行を削除する](/p/hyizkbq/)

