---
title: "Perlメモ: require で別ファイル（ライブラリ）をインクルードする"
url: "p/zanj67e/"
date: "2008-04-30"
tags: ["perl"]
aliases: ["/perl/syntax/require.html"]
---

require 演算子によるライブラリのインクルード
----

サブルーチンなどの定義を独立したファイルに記述しておけば、複数のコードからそのファイルをインクルードして使いまわすことができます。

下記の例では、サブルーチンの定義を `mylib.pl` というファイルにまとめ、このファイルを `require` 演算子でインクルードしています（pl という拡張子は、Perl Library の略です）。

{{< code lang="perl" title="mylib.pl（ライブラリ側）" >}}
sub greet {
    print "Hello\n";
}
1;
{{< /code >}}

{{< code lang="perl" title="main（ライブラリを使用する側）" >}}
require 'mylib.pl';
&greet();
{{< /code >}}

`require` 演算子は、既にインクルード済みのファイル名を覚えているので、重複したインクルードを避けてくれます。
インクルードされるファイルに構文エラーが存在する場合、`require` 演算子は自動的に `die` を実行します。
インクルードされるファイルで最後に評価される値は、真にならなければいけないので、慣例として最後の行に `1;` と書きます。

### コラム: do 演算子

`require` の代わりに `do` 演算子を使用すると、ファイルを無条件でインクルードします（重複したインクルードでもお構いなし）。
`do` によってインクルードされたファイルに構文エラーが存在すると、変数 `$@` が設定されるので、この値を使って `die` できます。

```perl
do 'mylib.pl';
die $@ if $@;
```


ライブラリの検索パスの設定 (@INC)
----

`require` や `do` 演算子によってインクルードするライブラリの検索パスは、**`@INC`** という特殊変数に設定されています。

```
$ perl -le 'print for @INC'
/usr/site/lib
/usr/lib
.
```

デフォルトでカレントディレクトリが検索パスに含まれているので、ライブラリはカレントディレクトリに置けば読み込めます。

ライブラリの検索パスを追加するには次のような方法があります。

{{< code title="(a) 環境変数 PERL5LIB で追加する（@INC の先頭に追加される）" >}}
export PERL5LIB=/home/joe/perl-lib
{{< /code >}}

{{< code title="(b) perl 実行時に -I オプションで追加する" >}}
$ perl -I/home/joe/perl-lib myprog
{{< /code >}}

{{< code lang="perl" title="(c) プログラム内で @INC に直接追加する" >}}
unshift @INC, '/home/joe/perl-lib';
{{< /code >}}

(c) の方法は、`use` でモジュールをインポートする場合には効果がありません。
なぜなら、`use` によるインポートはコンパイル時に行われてしまうからです。

```perl
unshift @INC, '/home/joe/perl-lib';
use MyLib;  # こっちが先に実行されてしまう×
```

これを防ぐには、`use lib` プラグマを使用して検索パスを追加するようにします。

```perl
use lib '/home/mike/lib';  # コンパイル時に検索パスが追加される○
use MyLib;
```

