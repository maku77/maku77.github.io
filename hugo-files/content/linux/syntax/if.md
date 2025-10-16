---
title: "Linuxメモ: Bash の構文: if-else による分岐処理"
url: "p/seogpah/"
date: "2007-05-08"
tags: ["linux"]
aliases: /linux/syntax/if.html
---

if-else の基本
----

Bash スクリプト内での `if` による条件分岐は下記のように記述します。

```bash
if 条件
then
  処理
fi
```

`then` を `if` と同じ行に記述しているスクリプトもよく見かけます。
Google もこのようなスタイルを採用しています。

```bash
if 条件; then
  処理
fi
```

処理内容が簡潔な場合は、下記のようにセミコロンを使用して一行で記述してしまうこともできます。

```bash
if 条件; then 処理; fi
```

複数の条件分岐を連ねる場合 (else if) は、以下のように `elif` と `else` を使って記述します。

```bash
if 条件1; then
  処理1
elif 条件2; then
  処理2
elif 条件3; then
  処理3
else
  処理4
fi
```


test コマンドによる比較処理
----

`if` の条件部では、**`test`** コマンドを使用して、数値比較や文字列比較を行うことができます。
実際には、`test` コマンドへのシンボリックリンクである **`[`** を使って記述するのが一般的です。
例えば、以下では数値比較を行うために `test` コマンドを使っていますが、

```bash
if test 1 -gt 0; then echo 'OK'; fi
```

これは次のように書くことができます。

```bash
if [ 1 -gt 0 ]; then echo 'OK'; fi
```

`[` は単に `test` コマンドのエイリアスなので、**`[` の後ろには必ず 1 つ以上のスペースが必要です**（上記では 1 が `test` コマンドへ渡すパラメータとして扱われています）。
`[` で使用できる `-eq` や `-gt` などのオプションは、`man test` とすれば調べることができます。


### test コマンドの例

`test` コマンドのオプション指定により、様々な判定を行うことができます。

* **`-z`**: 文字列の長さが 0 なら True
* **`-f`**: 指定したファイルが存在するなら True

{{< code lang="bash" title="例: 環境変数 $HOGE が空かどうかを調べる" >}}
if [ -z "$HOGE" ]; then
  ...
fi
{{< /code >}}

{{< code lang="bash" title="例: $HOME/.vimrc というファイルが存在するか調べる" >}}
if [ -f "$HOME/.vimrc" ]; then
  ...
fi
{{< /code >}}


文字列を比較する
----

文字列変数の値を他の文字列と比較するときは、変数値にスペースが含まれているケースを考慮して、ダブルクォートで囲んで参照するようにします。

```bash
if [ "$str" = 'hoge' ]; then
  # match
fi
```

上記のように記述しておけば、`$str` に値がセットされていないときもエラーになりません。


&amp;&amp; を短絡演算子として使用する
----

`if` の条件部が真 (true) になるケースだけ簡単な処理を行いたい場合は、下記のように `&&` を利用して `if` の代わりにすることができます。

```bash
echo "ごみ箱を空にしますか？ [y/n]"
read input
[ "$input" = 'Y' -o "$input" = 'y' ] && rm ~/trash/*
```

上記は、下記のように記述したのと同様に振る舞います。

```bash
echo "ごみ箱を空にしますか？ [y/n]"
read input
if [ "$input" = "Y" -o "$input" = "y" ]; then
  rm ~/trash/*
fi
```
