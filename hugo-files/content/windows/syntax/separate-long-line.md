---
title: "Windowsメモ: バッチファイル内で長いコマンドを複数行に分けて記述する"
url: "p/bat276x/"
date: "2010-01-08"
lastmod: "2019-12-04"
tags: ["windows"]
aliases: /windows/syntax/separate-long-line.html
---

Windows のバッチファイル内で、長いコマンドを次の行へ継続して記述するには、行末に `^` を置きます。

```bat
echo AAA ^
BBB
```

複数のコマンドを連続して実行するなら、その前に `&&` を付ければ OK です。

```bat
echo AAA && ^
echo BBB
```

次のように、括弧を使う方法もあります。

```bat
(
  echo AAA
  echo BBB
)
```

ちなみに、括弧には標準入出力をまとめる効果があるため、次のようにしてまとめてファイルにリダイレクトできたりします。

```bat
(
  echo AAA
  echo BBB
) >> hoge.txt
```

括弧は、[FOR ループ](/p/xrjbcty/) などで複数のコマンドを実行するときにも使われます。
