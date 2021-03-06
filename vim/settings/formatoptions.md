---
title: "Java や C/C++ のコメント記述に便利な formatoptions の設定"
date: "2010-01-05"
---

```
:set formatoptions=tcqro
```

と設定しておくと、C 言語などのコメントを記述しているときに、改行を入力するだけで自動的にコメントの途中の記号を挿入してくれるようになります。
下記、それぞれのオプションの意味です。

- <b>t</b>: （`textwidth` の値を超える）長いテキスト行を入力した場合に自動で改行する。
- <b>c</b>: （`textwidth` の値を超える）長いコメント行を入力した場合に自動で改行しコメントリーダーを挿入する。
- <b>q</b>: <kbd>gq</kbd> コマンドで選択部分をコメント整形する。
- <b>r</b>: <kbd>Enter</kbd> 入力時にコメントリーダーを挿入する。
- <b>o</b>: <kbd>o</kbd> あるいは <kbd>O</kbd> で行を挿入した場合にコメントリーダーを挿入する。


例えば、Java のコメントを編集中に、

``` java
/**
 * This is a comment line.
```

ここまで入力して改行すると、自動的に以下のように行の先頭にアスタリスクが挿入されます。
これは、`formatoptions` に **r** を指定した効果です。

``` java
/**
 * This is a comment line.
 *
```

