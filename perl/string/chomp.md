---
title: "文字列の末尾の改行を取り除く (chomp)"
date: "2008-02-27"
---

文字列変数に対して、`chomp` 演算子を適用すると、末尾の改行文字を（1文字だけ）削除します。

```perl
my $text = "This is a pen.\n";
chomp($text);
```

標準入力 (`<STDIN>`) などから読み込んだ行にも改行が含まれます。
代入演算子 (assignment operator) が左結合であることを利用して、行の読み込みと改行の削除は以下のように1行で記述することができます。

```perl
chomp($answer = <STDIN>);
chomp($cwd = `pwd`);
```

`chomp` の詳細は `perldoc -f chomp` で調べることができます。

