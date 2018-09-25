---
title: "Bash の構文 ─ case による分岐処理"
date: "2007-05-08"
---

`case` を使用すると、ある値がどのパターンに一致するかによって分岐処理を行うことができます。
構文は下記の通りです。

~~~
case 式 in
  パターン1)
    処理1 ;;
  パターン2)
    処理2 ;;
  パターン3)
    処理3 ;;
  ...
  *)
    いずれにも一致しない場合の処理 ;;
esac
~~~

`式` の評価結果が、`パターン1` ～ `パターン3` のいずれかに一致すると、対応する `処理N` が実行されます。

パターンには正規表現を使うことができます。

~~~ bash
case $1 in
  -[mM])   # -m あるいは -M だったら
    more $2;;
esac
~~~

#### 例: パラメータの内容によって分岐処理

~~~ bash
case $1 in
  one | One) echo 1 ;;
  two | Two) echo 2 ;;
  three | Three) echo 3 ;;
  *) echo unknown ;;
esac
~~~

次のように処理部分を複数行に分けて記述することもできます。

~~~ bash
case $1 in
  one | One)
    echo 1
    ;;
  two | Two)
    echo 2
    ;;
  three | Three)
    echo 3
    ;;
  *)
    echo unknown
    ;;
esac
~~~

