---
title: "標準入力から読み込む"
date: "2012-09-18"
---

sys.stdin をイテレータで処理する方法
====

```python
#!/usr/bin/env python
import sys
for line in sys.stdin:
    print('>> ' + line, end='')
```

上記のように `sys.stdin` からテキストを一行ずつ読み込むと、`line` 変数の末尾には改行文字が含まれます。
`print()` 関数はデフォルトで改行を付けて出力しようとする (`end='\n'`) ので、改行の重複を防ぐために、`print()` 関数のパラメータ `end` を空文字 (`''`) に設定しています。

以下のように、`str.rstrip()` を使って、あらかじめ各行の末尾から改行コードを削除しておく方法もあります。

```python
import sys
for line in sys.stdin:
    line = line.rstrip('\r\n')    # 改行が残ってるので削除
    print('>> ' + line)
```


sys.stdin.readline() を使う方法
====
`sys.stdin` からは、明示的に `sys.stdin.readline()` で一行ずつ読み込むこともできます。
これを利用して以下のように while ループで一行ずつ処理することもできますが、通常は上記のように `for ~ in` によるイテレータでループした方がスッキリ書けます。

```python
import sys
while True:
    line = sys.stdin.readline()
    if not line: break
    line = line.rstrip('\r\n')
    print('>> ' + line)
```

Python の代入文は、戻り値を返さないので、以下のように C 言語風に記述することはできません。

#### 間違い
```python
while line = sys.stdin.readline():
    ...
```


fileinput モジュールを使う方法
====
`fileinput` モジュールを使用すると、ファイル名が指定されたらファイルから、指定されなかったら標準入力から入力を読み取るプログラムを簡単に記述することができます。

```python
#!/usr/bin/env python
import fileinput
for line in fileinput.input():
    print('>> ' + line, end='')
```

このプログラムは、以下のどの方法でも実行できます。

```
$ ./sample.py input.txt
$ ./sample.py < input.txt
$ cat input.txt | ./sample.py
```

