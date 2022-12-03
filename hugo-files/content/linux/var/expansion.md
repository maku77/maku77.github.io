---
title: "Bash の変数展開機能を活用する（文字列の置換、デフォルト値など）"
date: "2010-07-20"
url: "p/jsctar8/"
tags: ["Linux"]
description: "Bash の変数展開の仕組みを活用すると、変数に格納された文字列を置換したり、変数のデフォルト値を指定したりすることができるようになります。"
aliases: /linux/var/expansion.html
---

Bash の変数は通常 `${x}` のような形で参照しますが、様々な変数展開機能を利用すると、変数の存在を確認したり、一部を置換したり、デフォルト値を設定したりすることができます。
下記にざっと紹介しますが、公式なドキュメントは `man bash` の `Parameter Expansion` のセクションで参照することができます。


パターン照合演算子
----

次のような変数展開の構文を使用すると、変数に格納された文字列の一部を置換した文字列を作成することができます。

| 構文 | 意味 |
| ---- | ---- |
| __`${変数/パターン/置換文字列}`__ | パターンに一致する部分を置換文字列に置き換える（１つだけ） |
| __`${変数//パターン/置換文字列}`__ | パターンに一致する部分を置換文字列に置き換える（すべて） |
| __`${変数#パターン}`__ | <b>先頭</b>から<b>最短一致</b>でパターンに一致する部分を取り除く |
| __`${変数##パターン}`__ | <b>先頭</b>から<b>最長一致</b>でパターンに一致する部分を取り除く |
| __`${変数%パターン}`__ | <b>末尾</b>から<b>最短一致</b>でパターンに一致する部分を取り除く |
| __`${変数%%パターン}`__ | <b>末尾</b>から<b>最長一致</b>でパターンに一致する部分を取り除く |

パターン部分では、ワイルドカード（`*`、`?`、`[a-z]` など）を使用できます。

### 例: 変数内の b を B に置換する

```console
$ x=aaabbbccc
$ y=${x/b/B}
$ echo $y
aaaBbbccc
```

最初に見つかった文字列だけが置換されていることがわかります。
パターンに一致する文字列をすべて置換するには次のようにします（最初のスラッシュを2つにします）。

```console
$ x=aaabbbccc
$ y=${x//b/B}
$ echo $y
aaaBBBccc
```

以下のように sed コマンドを使ったのと同様の効果を bash の機能だけで実現できていることになります。

```console
$ y=$(echo $x | sed -e 's/bbb/BBB/g')
```

### 例: フルパスから basename を取り出す

下記の例では `/aaa/bbb/ccc` という絶対パスから、ベースネーム部分の `ccc` を抽出しています。
`*/` というパターンで先頭から最長一致させ、`/aaa/bbb/` という部分を取り除いています。

```console
$ x=/aaa/bbb/ccc
$ y=${x##*/}
$ echo $y
ccc
```

ちなみに、ベースネーム部分を取得したいのであれば、通常は `basename` コマンドを使って以下のようにした方が簡単です。

```console
$ basename $x
```

### 例: フルパスから dirname を取り出す

下記の例では、`/aaa/bbb/ccc` という絶対パスから、ディレクトリ名部分の `/aaa/bbb` を抽出しています。
`/*` というパターンで末尾から最短一致させ、`/ccc` という部分を取り除いています。

```console
$ x=/aaa/bbb/ccc
$ y=${x%/*}
$ echo $y
/aaa/bbb
```

ちなみに、ディレクトリ部分を取得したいのであれば、通常は `dirname` コマンドを使用して以下のようにした方が簡単です。

```console
$ dirname $x
```

### 例: '#' 以降のコメントを削除する

```console
$ line="aaa bbb # This is a comment"
$ line=${line%%#*}
$ echo $line
aaa bbb
```

`line` 変数の中を末尾から見て、`#*` という表現で最長一致する部分を削除してます。


デフォルト値など
----

| 構文 | 意味 |
| ---- | ---- |
| __`${変数:-word}`__ | 変数が未定義のとき word を返す |
| __`${変数:=word}`__ | 変数が未定義のとき word を代入して返す |
| __`${変数:?}`__<br>__`${変数:?word}`__ | 変数が未定義のときエラーを表示する |
| __`${変数:+word}`__ | 変数が定義されているとき word を返す |

### `${param:-word}` の使用例

下記は man bash の抜粋です。

> Use  Default Values. If parameter is unset or null, the expansion of word is substituted. Otherwise, the value of  parameter is substituted. If parameter is unset or null, the expansion of word is substituted. Otherwise, the value of parameter is substituted.

例えば、

```bash
x=${count:-0}
```

とすると、`$x` の値は `$count` が定義されていなければ 0 になります。
つまり、`$count` のデフォルト値を 0 とみなして参照していることになります。

この構文は、コマンドライン引数が省略されたときのデフォルト値を設定するために使用できます。
例えば、

```bash
filename=${1:-input.txt}
```

とすると、変数 `$filename` の値は、第1パラメータで指定された値か、あるいはデフォルト値の `input.txt` になります。

### `${param:=word}` の使用例

下記は man bash の抜粋です。

> Assign Default Values. If parameter is unset or null, the expansion of word is assigned to parameter. The value of parameter is then substituted. Positional parameters and special parameters may not be assigned to in this way.

例えば、

```bash
x=${count:=0}
```

とすると、`$x` の値は `$count` が定義されていなければ 0 になります。
ここまでは、`${count:-0}` とした場合と同様ですが、`${count:=0}` とすると、`$count` にも 0 が代入されます（代入 `=` だと考えると分かりやすいですね）。

変数に代入しようとするため、コマンドラインパラメータや関数のパラメータの `$1` を参照するときにこの方法は使用できません。

### `${param:?word}` の使用例

下記は man bash の抜粋です。

> Display Error if Null or Unset. If parameter is null or unset, the expansion of word (or a message to that effect if word is not present) is written to the standard error and the shell, if it is not interactive, exits. Otherwise, the value of parameter is substituted.

例えば、下記のようにすると、`$count` 変数が定義されていないければエラーを出力して終了します。

```console
$ echo ${count:?}
-bash: count: パラメータが null または設定されていません
```

`?` の後ろに表示するメッセージを指定することもできます。

```console
$ echo ${count:?パラメータが設定されていません}
-bash: count: パラメータが設定されていません
```

### `${param:+word}` の使用例

下記は man bash の抜粋です。

> Use Alternate Value. If parameter is null or unset, nothing is substituted, otherwise the expansion of word is substituted.

これはちょっと特殊で、変数 `$param` が定義されているときに、代わりに `word` の方の値が返されます。
例えば、`${count:+1}` は、`$count` が定義されていたら 1 と評価されます。

```console
$ count=9999
$ echo ${count:+1}
1

$ unset count
$ echo ${count:+1}

```


位置指定で部分文字列を抽出する
----

| 構文 | 意味 |
| ---- | ---- |
| __`${変数:offset}`__ | offset で指定した位置から末尾までの文字列を抽出する |
| __`${変数:offset:length}`__ | offset で指定した位置から length 分の文字列を抽出する |

```console
$ hoge=ABCDEFGHIJ
$ echo ${hoge:3}
DEFGHIJ

$ echo ${hoge:3:4}
DEFG
```

`offset` や `length` に負の値を指定して、末尾からの位置を示すこともできます。
ただし、その場合、`offset` の前に 1 つ以上のスペースが必要です（スペースを入れないと、`${変数:-word}` という形でデフォルト値が指定されたとみなされてしまいます）。

```console
$ hoge=ABCDEFGHIJ
$ echo ${hoge: -3}
HIJ

$ echo ${hoge: -5:-2}
FGH
```

