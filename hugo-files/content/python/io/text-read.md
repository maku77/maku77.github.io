---
title: "Python でテキストファイルを読み込む (open, read, readline, readlines)"
url: "p/f4ckt29/"
date: "2007-03-06"
tags: ["Python"]
aliases: /python/io/read-text-file.html
---

テキストファイルを 1 行ずつ読み込む
----

### for-in ループで 1 行ずつ処理する方法

Python の `open` 関数で生成したファイルオブジェクトを `for-in` ループでイテレートすることで、テキストファイルを 1 行ずつ読み込むことができます。
Python 2.5 以降であれば、`open` するときに、__`with`__ キーワードを使用することによって `close` 処理を自動化することができます。
ファイルを使用している区間が明示的になるので、この方法を使うのがオススメです。

{{< code lang="python" title="テキストファイルを 1 行ずつ処理" >}}
with open('input.txt', encoding='utf-8') as f:
    for line in f:
        line = line.rstrip('\r\n') # 改行文字の削除
        print(line) # 改行付きで出力
{{< /code >}}

`line` には改行コードを含むので、`line.rstrip('\r\n')` で末尾の改行系文字を削除しています。
別の方法としては、`line` 内の改行はそのままで、__`print(line, end='')`__ として出力時の改行を抑制する方法あります（Python 2.x の頃は `print line,` のように最後にカンマを付ければ改行抑制できました）。

`with` によってインデントが深くなってしまうのが嫌な場合は、下記のようにユーティリティ関数を用意しておくのもよいです。

```python
def each_line(filename):
    with open(filename, encoding='utf-8') as f:
        for line in f:
            yield line.rstrip('\r\n')

for line in each_line('input.txt'):
    print(line)
```

### readline で 1 行ずつ読み込む方法

__`readline`__ メソッドを使うと、次の 1 行を文字列として取得することができます。
ファイルの最後まで読み込み終わると、`readline` は空文字列 (`""`) を返します。

```python
with open('input.txt', encoding='utf-8') as f:
    while True:
        line = f.readline()
        if not line: break
        line = line.rstrip('\r\n')
        print(line)
```


テキストファイルのすべての行を一度に読み込む
----

それほど大きなテキストファイルでなければ、テキストファイル全体の内容を一度にメモリ上に読み出してしまうこともできます。

### すべての行を文字列リストとして取得する

__`readlines`__ メソッドで、全ての行を文字列のリスト (`list[str]`) の形で取得できます。
各要素の末尾には改行が残っています（最終行には改行は含まれていない可能性があります）。

```python
with open('input.txt', encoding='utf-8') as f:
    lines = f.readlines()  #=> list[str]
    print(lines)
```

{{< code lang="python" title="実行結果" >}}
['First line\n', 'Second line\n', 'Third line']
{{< /code >}}

次のようなリスト内包表記を使用して、各行の末尾の空白・改行を削除しながら、リスト形式で読み込むこともできます。

```python
lines = [line.rstrip() for line in open('input.txt', encoding='utf-8')]
```

### すべての行を連結して読み出す（改行文字は残す）

__`read`__ メソッドを使用すると、ファイルの内容すべてを 1 つの文字列変数に取得することができます。

```python
with open('input.txt', encoding='utf-8') as f:
    content = f.read()  #=> str
    print(content)
```

{{< code title="実行結果" >}}
First line
Second line
Third line
{{< /code >}}


テキストファイルの内容を 1 文字ずつ処理する
----

テキストファイルの内容を __`read`__ メソッドで一度に読み込み、そのテキストをさらにイテレートすることで、テキストファイルの内容を 1 文字ずつ処理することができます。

{{< code lang="python" title="input.txt ファイルの内容を 1 文字ずつ処理" >}}
with open('input.txt', encoding='utf-8') as f:
    content = f.read()

for ch in content:
    if ch == '\n':
        print('改行発見！')
    else:
        print(ch)
{{< /code >}}

次のようなユーティリティメソッドを作っておけば、1 文字ずつ処理するコードを簡単に書けるようになります。

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

