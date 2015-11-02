---
title: Python でテキストファイルを読み込む
created: 2007-03-06
---

テキストファイルを一行ずつ読み出す
====

ファイルハンドル使って iterate する方法
----

```python
f = open('input.txt')
for line in f:
    line = line.rstrip('\r\n')
    print(line)
f.close()
```

```line``` には改行コードを含むので、```line.rstrip('\r\n')``` で末尾の改行系文字を削除しています。
別の方法としては、```line``` 内の改行はそのままで、```print(line, end='')``` として出力時の改行を抑制する方法あります。（Python 2.x の頃は ```print line,``` のように最後にカンマを付ければ改行抑制できてました）。

with を使って自動クローズ
----

Python 2.5 以降なら ```with``` を使って ```close``` を自動化できます。
ファイルを使用している区間が明示的になるので、**この方法を使うのがオススメ**です。

```python
with open('input.xml') as f:
    for line in f:
        line = line.rstrip('\r\n')
        print(line)
```

readline で一行ずつ読み込む方法
----


```python
f = open('input.txt')
while True:
    line = f.readline()
    if not line: break
    line = line.rstrip('\r\n')
    print(line)
f.close()
```

エンコーディング形式を指定してテキストファイルを読み込む
----

例えば、エンコーディング形式に utf-8 を指定して、テキストファイルを読み込みたい場合は、codecs モジュールを使用して下記のように記述します。

```python
import codecs

with codecs.open('input.txt', 'r', 'utf-8') as f:
    for line in f:
        print(line)
```


テキストファイルのすべての行を一度に読み出す
====

各行を配列に読み出す（各要素の末尾には改行が残る）
----

```python
f = open('input.txt')
lines = f.readlines()
f.close()
```

各行を配列に読み出す（各要素の末尾の空白、改行を削除）
----

```python
lines = [line.rstrip() for line in open('input.txt')]
```

すべての行を連結して読み出す（改行文字は残す）
----

```python
f = open('input.txt')
text = f.read()
f.close()
```

すべての行を連結して読み出す（改行文字を削除）
----

```python
text = ''.join([line.rstrip('\r\n') for line in open('input.txt')])
```


