---
title: "Perlメモ: サブルーチン内では exit を使わずに die を使う"
url: "p/muvh9w2/"
date: "2008-04-26"
tags: ["perl"]
aliases: ["/perl/subroutine/use-die-instead-of-exit.html"]
---

`exit` の呼び出しは、`eval` ブロックの動きに影響を与えます。
サブルーチン内で `exit` を呼び出したくなったら、`die` を使うようにしてください。

`eval` ブロックを使用すると、ブロック内で発生したエラーをトラップすることができますが、サブルーチン内で `exit` が呼ばれると無条件にプログラムを終了してしまいます。
サブルーチン内のエラーを `eval` ブロックでトラップできるようにするには、`exit` でなく `die` を使用しなければいけません。
