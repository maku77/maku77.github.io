---
title: "Rubyメモ: Ruby でワンライナープログラミング"
url: "p/f6fj4c7/"
date: "2018-12-05"
tags: ["ruby"]
aliases: ["/ruby/io/oneliner.html"]
---

ワンライナーは、コマンドラインから `ruby` を実行するときに、1 行だけコードを入力して簡単な処理を行うものです。
コマンドの出力をサクッと加工するのに適しています。

{{< code lang="console" title="例: 各行の 3 番目のフィールドのみ抜き出す" >}}
$ cat data.txt
AAA 100 xxx
BBB 200 yyy
CCC 300 zzz

$ cat data.txt | ruby -ane "puts $F[2]"
xxx
yyy
zzz
{{< /code >}}

上記のように、テキストファイルの内容をそのままワンライナーに渡すのであれば、リダイレクトで入力することもできます。
こちらの方が `cat` コマンドなどに依存しないのでよいかもしれません。

```console
$ ruby -ane "puts $F[2]" < data.txt
```

オプションの意味:

- <b>`-a`</b>: autosplit mode（各行の `$_` が分割されて `$F` に格納される）
- <b>`-n`</b>: 各行を処理する（内部で `while gets()` ループが実行される）
- <b>`-e`</b>: 後ろにコマンドを直接記述していることを示す

`-n` オプションを付けて各行を処理する場合は、[Awk](/sed/) のように、最初に一度だけ実行される `BEGIN` ブロックや、最後に一度だけ実行される `END` ブロックを利用できます。

{{< code lang="console" title="例: 各行の 2 番目のフィールドの値を合計する" >}}
$ ruby -ane "BEGIN{$a=0}; $a+=$F[1].to_i; END{puts $a}" < data.txt
{{< /code >}}

## 参考サイト

- [Ruby one-liners (by Benoit Hamelin)](https://benoithamelin.tumblr.com/ruby1line)
