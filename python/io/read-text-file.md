---
title: "Python でテキストファイルを読み込む (open, read, readline, readlines)"
date: "2007-03-06"
---

テキストファイルを一行ずつ読み出す
----

### ファイルハンドル使って一行ずつ iterate する方法

`open()` によって得られるファイルオブジェクトを直接イテレートすることで、テキストファイルを一行ずつ読み込むことができます。
Python 2.5 以降なら `open` するときに、`with` キーワードを使用することによって `close` 処理を自動化することができます。
ファイルを使用している区間が明示的になるので、**この方法を使うのがオススメ**です。

```python
with open('input.txt', encoding='utf-8') as f:
    for line in f:
        line = line.rstrip('\r\n')
        print(line)
```

`line` には改行コードを含むので、`line.rstrip('\r\n')` で末尾の改行系文字を削除しています。
別の方法としては、`line` 内の改行はそのままで、`print(line, end='')` として出力時の改行を抑制する方法あります（Python 2.x の頃は `print line,` のように最後にカンマを付ければ改行抑制できてました）。

`with` によってインデントが深くなってしまうのが嫌な場合は、下記のようにユーティリティ関数を用意しておくのもよいですね。

```python
def each_line(filename):
    with open(filename, encoding='utf-8') as f:
        for line in f:
            yield line.rstrip('\r\n')

for line in each_line('input.txt'):
    print(line)
```

ちなみに、自力で `close` 処理を記述する場合は下記のようになります。

```python
f = open('input.txt', encoding='utf-8')
for line in f:
    line = line.rstrip('\r\n')
    print(line)
f.close()
```

### readline で一行ずつ読み込む方法

`readline()` は次の一行を文字列として返します。

```python
with open('input.txt') as f:
    while True:
        line = f.readline()
        if not line: break
        line = line.rstrip('\r\n')
        print(line)
```


テキストファイルのすべての行を一度に読み出す
----

それほど大きなテキストファイルでなければ、テキストファイル全体の内容を一度にメモリ上に読み出してしまうこともできます。

### 各行を配列に読み出す（各要素の末尾には改行が残る）

```python
with open('input.txt', encoding='utf-8') as f:
    lines = f.readlines()
```

### 各行を配列に読み出す（各要素の末尾の空白、改行を削除）

```python
lines = [line.rstrip() for line in open('input.txt')]
```

### すべての行を連結して読み出す（改行文字は残す）

```python
with open('input.txt', encoding='utf-8') as f:
    content = f.read()
```

### すべての行を連結して読み出す（改行文字を削除）

```python
text = ''.join([line.rstrip('\r\n') for line in open('input.txt')])
```


テキストファイルを１文字ずつ処理する
----

テキストファイルの内容を `read()` で一度に読み込み、そのテキストをさらにイテレートすることで、テキストファイルの内容を１文字ずつ処理することができます。

```python
with open('input.txt', encoding='utf-8') as f:
    content = f.read()

for ch in content:
    if ch == '\n':
        print('改行発見！')
    else:
        print(ch)
```

次のようなユーティリティメソッドを作っておけば、１文字ずつ処理するコードを簡単に書けるようになります。

```python
def iter_chars(filename):
    """ Reads a text file char by char. """
    with open(filename, encoding='utf-8') as f:
        content = f.read()
    for ch in content:
        yield ch

# 使用例
for ch in iter_chars('input.txt'):
    print('--> ' + ch)
```

