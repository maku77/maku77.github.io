---
title: "Python で文字列を URL エンコード／デコードする (urllib.parse.quote, unquote, urlencode)"
url: "p/w7p7n4j/"
date: "2020-05-30"
tags: ["Python", "URL"]
aliases: /python/web/url-encode.html
---

Python の標準モジュールとして提供されている [urllib.parse](https://docs.python.org/ja/3/library/urllib.parse.html) を使用すると、任意の文字列を URL 内で扱えるようにエンコード／デコードすることができます。
ユーザーが入力したテキストや、日本語を含むテキストをエスケープ処理して URL として扱いたいときに使用できます。


文字列を URL エンコードする (quote)
----

任意の文字列を URL として扱いたいときは、[urllib.parse.quote 関数](https://docs.python.org/ja/3/library/urllib.parse.html#urllib.parse.quote) を使うと、エスケープすべき文字を __`%xx`__ という形に変換してくれます。

```python
from urllib.parse import quote

print(quote('あ'))     #=> '%E3%81%82'
print(quote('ABC'))    #=> 'ABC'
print(quote('A B C'))  #=> 'A%20B%20C'
print(quote('A/B/C'))  #=> 'A/B/C'
```

上記の結果からわかるように、`quote` 関数はデフォルトではスラッシュ (`/`) をエスケープしません。
これは、変換しない文字を示すオプションパラメータ `safe` のデフォルト値が `'/'` になっているからです。
次のようにすると、スラッシュが `%2F` にエスケープされるようになります。

```python
print(quote('A/B/C', safe=''))  #=> 'A%2FB%2FC'
```

URL の特定階層のパス名をユーザーに入力させるようなケースで使用できます。


URL をデコードする (unquote)
----

逆に、URL エンコードされた文字列をデコードして元に戻すには、[urllib.parse.unquote 関数](https://docs.python.org/ja/3/library/urllib.parse.html#urllib.parse.unquote) を使用します。

```python
from urllib.parse import unquote

print(unquote('%E3%81%82'))  #=> 'あ'
print(unquote('ABC'))        #=> 'ABC'
print(unquote('A%20B%20C'))  #=> 'A B C'
print(unquote('A%2FB%2FC'))  #=> 'A/B/C'
```


クエリ文字列を作成する (urlencode)
----

HTTP の GET リクエストを送るときに、URL の末尾に次のようにクエリ文字列を付加したいことがあります。

```
https://example.com/?key1=value1&key2=value2
```

[urllib.parse.urlencode 関数](https://docs.python.org/ja/3/library/urllib.parse.html#urllib.parse.urlencode) を使用すると、任意のディクショナリオブジェクトからクエリ文字列を作成することができます。
ディクショナリの各要素が `key1=value1` という形で、`&` 記号により結合された文字列が返されます。
キーと値は適切に URL エンコードされるので、そのまま URL の末尾にくっつけることができます。

```python
from urllib.parse import urlencode

query = urlencode({'a':100, 'b':200})
print(query)  #=> a=100&b=200

query = urlencode({'a':'あ', 'b':'い'})
print(query)  #=> a=%E3%81%82&b=%E3%81%84

query = urlencode({'msg':'hello world'})
print(query)  #=> msg=hello+world
```

次のようにすれば、クエリ文字列の付いた URL を簡単に生成できます。

```python
from urllib.parse import urlencode

base = 'https://example.com/'
query = {
    'key1': 100,
    'key2': 200
}

url = '%s?%s' % (base, urlencode(query))
print(url)  #=> https://example.com/?key1=100&key2=200
```

