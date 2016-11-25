---
title: 文字列内の部分文字列を検索する／抽出する
created: 2016-11-25
---

単純な文字列検索
----

`str.find()` や `str.rfind()` を使用することで、文字列内に存在する部分文字列の位置を調べることができます。
部分文字列が見つからない場合は -1 を返します。

```python
>>> s = 'ABCABC'
>>> s.find('B')    # 前から検索する
1
>>> s.rfind('B')   # 後ろから検索する
4
>>> s.find('X')    # 見つからない場合は -1 を返す
-1
```


正規表現による文字列マッチング (re.search, re.match)
----

`re.search` を使用すると、正規表現によるパターンで文字列を検索できます。
`re.search` は、パターンに一致する部分文字列が見つかると `Match` オブジェクトを返します。
一致しない場合は `None` を返すので、戻り値をそのまま `if` に渡して、一致したかどうかを確認することができます。

#### sample.py

```python
import re

match = re.search(r'\d+', 'aaa123zzz')
if match:
    print('matched:', match.group(0))
```

#### 実行結果

```
$ python sample.py
matched: 123
```

`re.search` の代わりに `re.match` を使用すると、文字列の先頭からパターンに一致しているかを調べるようになります（`re.match` の場合でも部分一致であることは変わらないので、行末まで一致している必要はありません）。

```python
# search を使うと文字列の途中からでも一致する
m = re.search(r'B+', 'AAABBBCCC')
assert(m != None)  # 一致

# match を使うと文字列の先頭から一致させる
m = re.match(r'B+', 'AAABBBCCC')
assert(m == None)  # 不一致
```

先頭からの一致を調べればよいケースでは、パフォーマンスを考慮して `re.match` を使うようにしましょう。


正規表現で部分文字列を抽出する
----

`re.search` や `re.match` による文字列マッチングの結果、一致する文字列が見つかった場合は `Match` オブジェクトが返されます。
`Match` オブジェクトの `group` メソッドを使用することで、検索パターンに実際に一致した部分文字列を取得することができます。
また、検索パターンを括弧でグルーピングしておくと、その部分に一致する部分文字列を個別に抽出することができます。

下記のサンプルでは、`<b>` と `</b>` で囲まれた部分文字列を抽出しています。

#### sample.py

```python
import re

match = re.search(r'<b>(.+)</b>', 'This is a <b>nice</b> pen')
if match:
    print(match.group(0))  # 検索パターン全体に一致する文字列
    print(match.group(1))  # 検索パターン中の括弧で囲まれた部分に一致する文字列
```

#### 実行結果

```
$ python sample.py
<b>nice</b>
nice
```


パターンに一致する部分を繰り返し抽出する
----

`re.search()` の代わりに `re.findall()` を使用すると、正規表現パターンに一致する部分文字列を繰り返し抽出することができます。
`re.findall()` 戻り値は、文字列のリストです。

```python
import re

arr = re.findall(r'\d+', 'aaa100xxx200eee300')  #=> ['100', '200', '300']
```


正規表現パターンをコンパイルして検索を高速化する
----

`re.match()`、`re.search()` などで、正規表現パターンを毎回指定してマッチングを行うことはできますが、同じ正規表現パターンを繰り返し使用する場合は、あらかじめ正規表現オブジェクトにコンパイルしておくと高速に処理できます。

```python
import re

# コンパイルしない場合
match = re.search(r'\d+', 'abc123')

# コンパイルする場合
regex = re.compile(r'\d+')
match = regex.search('abc123')
```

ただし、`re.match()`、`re.search()` に最後に渡されたパターンは、内部的にキャッシュされているので、パターンを 1 つのみ連続して使用する場合は、`re.compile()` の必要はありません。

