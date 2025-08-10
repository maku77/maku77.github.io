---
title: "Vim で Java や C/C++ のコメント記述に便利な formatoptions の設定"
url: "p/i9y5wsd/"
date: "2010-01-05"
tags: ["vim"]
aliases: [/vim/settings/formatoptions.html]
---

Vim で次のように設定しておくと、C 言語などのコメントを記述しているときに、改行を入力するだけで自動的にコメントの途中の記号を挿入してくれるようになります。

```
:set formatoptions=tcqro
```

下記、それぞれのオプションの意味です。

- **`t`** ... （`textwidth` の値を超える）長いテキスト行を入力した場合に自動で改行する。
- **`c`** ... （`textwidth` の値を超える）長いコメント行を入力した場合に自動で改行しコメントリーダーを挿入する。
- **`q`** ... <kbd>gq</kbd> コマンドで選択部分をコメント整形する。
- **`r`** ... <kbd>Enter</kbd> 入力時にコメントリーダーを挿入する。
- **`o`** ... <kbd>o</kbd> あるいは <kbd>O</kbd> で行を挿入した場合にコメントリーダーを挿入する。


例えば、Java のコメントを編集中に、

```java
/**
 * This is a comment line.
```

ここまで入力して改行すると、自動的に以下のように行の先頭にアスタリスクが挿入されます。
これは、`formatoptions` に **r** を指定した効果です。

```java
/**
 * This is a comment line.
 *
```

