---
title: "Pythonメモ: 標準入力から読み込む、標準入力とファイル入力に両対応する (sys.stdin, fileinput)"
url: "p/3966z2v/"
date: "2012-09-18"
tags: ["python"]
aliases: /python/io/stdin.html
---

`sys.stdin` をイテレータで処理する方法
----

`for ~ in` ループで **`sys.stdin`** をイテレータとして処理すると、標準入力から 1 行ずつテキストを読み込むことができます。

```python
#!/usr/bin/env python
import sys
for line in sys.stdin:
    print('>> ' + line, end='')
```

`line` 変数の末尾には **改行文字が含まれる** ことに注意してください。
`print()` 関数はデフォルトで改行を付けて出力しようとする (`end='\n'`) ので、改行の重複を防ぐために、`print()` 関数のパラメータ `end` を空文字 (`''`) に設定しています。

以下のように、`str.rstrip()` を使って、あらかじめ各行の末尾から改行コードを削除しておく方法もあります。

```python
import sys
for line in sys.stdin:
    line = line.rstrip('\r\n')  # 行末の改行コードを削除
    print('>> ' + line)
```


`sys.stdin.readline()` を使う方法
----

明示的に **`sys.stdin.readline()`** 関数を呼び出して標準入力から 1 行読み込むこともできます。
これを利用して以下のように `while` ループで 1 行ずつ処理することもできますが、通常は前述のように `for ~ in` によるイテレータでループした方がスッキリ書けます。

```python
import sys
while True:
    line = sys.stdin.readline()
    if not line:
        break
    line = line.rstrip('\r\n')
    print('>> ' + line)
```

{{% note %}}
Python の代入文は戻り値を返さないので、以下のように C 言語風に記述することはできません。

{{< code lang="python" title="間違い" >}}
while line = sys.stdin.readline():
    ...
{{< /code >}}
{{% /note %}}


標準入力とファイル入力の両方に対応する（`fileinput` モジュール）
----

**`fileinput`** モジュールを使用すると、ファイル名が指定された場合はそのファイルから、指定されなかった場合は標準入力から入力を読み取るプログラムを簡単に記述することができます。

```python
#!/usr/bin/env python
import fileinput

for line in fileinput.input():
    print('>> ' + line, end='')
```

このプログラムは、以下のどの方法でも実行できます。

```console
$ ./sample.py input.txt
$ ./sample.py < input.txt
$ cat input.txt | ./sample.py
```
