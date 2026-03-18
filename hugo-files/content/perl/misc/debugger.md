---
title: "Perlメモ: Perl のデバッガを使用する"
url: "p/89hb8nb/"
date: "2008-05-19"
tags: ["perl"]
aliases: ["/perl/misc/debugger.html"]
---

Perl には標準でデバッガを起動するオプションが付いています。
デバッガは下記のように起動できます。

{{< code lang="console" title="デバッガの起動" >}}
$ perl -d yourscript
{{< /code >}}

デバッガを起動した後は、下記のようなコマンドでステップ実行させながらデバッグを進めることができます。

#### デバッガの操作

- `s` -- ステップ実行
- `x $hoge` -- 変数 `$hoge` の内容を表示
- `x \%hash` -- ハッシュ全体をダンプ

詳しい情報は、`perldoc perldebug` で参照することができます。
