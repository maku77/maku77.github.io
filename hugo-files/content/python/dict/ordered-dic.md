---
title: "Python の dictionary の内部的な要素順序を変更する (dict, OrderedDictionary)"
url: "p/vexfweu/"
date: "2021-07-24"
tags: ["Python"]
aliases: /python/dictionary/ordered-dic.html
---

dict が保持する要素の順序
----

Python の辞書オブジェクト (`dict`) は要素の追加順序を保持しており、要素のイテレート時や `print` での出力時はこの順序で出力されるようになっています（Python 3.7 以降）。

{{< code lang="python" title="sample.py" >}}
book = {
    'id': '123',
    'authors': ['Author 1'],
    'title': 'Title 1'
}
print(book)
{{< /code >}}

{{< code lang="python" title="出力結果" >}}
{'id': '123', 'authors': ['Author 1'], 'title': 'Title 1'}
{{< /code >}}

単純にキー名のアルファベット順にループ処理したい場合は、例えば次のようにします。

{{< code lang="python" title="キーのリストをソート" >}}
for key in sorted(book.keys()):
    print('%s: %s' % (key, book[key]))
{{< /code >}}

でもこれは、出力時に一時的なキーリストをソートしているだけで、辞書オブジェクト内部の要素順序を制御しているわけではありません。
次のようにすれば、あるキーの要素を辞書オブジェクト内で末尾に持ってくることができます。

```python
book['id'] = book.pop('id')
```

単純に `id` キーの要素を取り出して、再度辞書オブジェクトに追加しているだけです (^^;

この後、`print(book)` とすると、`id` 要素が最後に出力されます。
もっといい方法があるかもしれませんが、この仕組みだけである程度柔軟な並び替えが可能です。
例えば、次の `sort_dict` 関数は、辞書オブジェクト内部の要素をキー名順に並び替える関数ですが、`priority_keys` 引数でキー名のリストを渡すと、それらのキーは優先的にその順番で先頭に並べられます。

{{< code lang="python" title="sort_dict 関数の実装例" >}}
def sort_dict(d, priority_keys=()):
    """
    辞書オブジェクト d の要素を内部的にキー名でソートします。
    ただし、priority_keys にキー名のリストが指定されたときは、
    そのキーを優先的に先頭に並べます。
    """
    keys1 = list(filter(lambda k: k in d, priority_keys))
    keys2 = sorted(d.keys() - keys1)
    for k in (keys1 + keys2):
        d[k] = d.pop(k)
{{< /code >}}

下記はこの `sort_dict` 関数の使用例です。
`book` オブジェクト内のキーをアルファベット順にソートしつつ、`id` と `title` キーは優先的に先頭に並べるようにしています。

{{< code lang="python" title="sort_dict 関数の使用例" >}}
import json

book = {
    'authors': ['Maku', 'Ponyo', 'Chi'],
    'title': 'Title 1',
    'id': '123',
    'note': 'Very interesting'
}

sort_dict(book, ('id', 'title'))
print(json.dumps(book, indent=2))
{{< /code >}}

{{< code lang="json" title="実行結果" >}}
{
  "id": "123",
  "title": "Title 1",
  "authors": [
    "Maku",
    "Ponyo",
    "Chi"
  ],
  "note": "Very interesting"
}
{{< /code >}}


OrderedDict について
----

ちなみに、Python は `dict` のサブクラスとして [collections.OrderedDict](https://docs.python.org/ja/3/library/collections.html#ordereddict-objects) クラスを用意しています。
通常の `dict` が要素の追加順序を保持するようになった今、`OrderedDict` の出番はあまり多くないかもしれませんが、`OrderedDict` は独自のメソッドを備えていたりします。
例えば、指定した要素を末尾か先頭に移動させる `move_to_end(key, last=True)` メソッドがあります。

{{< code lang="python" title="sample.py" hl_lines="8 9" >}}
from collections import OrderedDict

book = OrderedDict({
    'title': 'Title 1',
    'authors': ['Author 1'],
    'id': '123'
})
book.move_to_end('title')  # title を末尾へ
book.move_to_end('id', last=False)  # id を先頭へ

print(dict(book))
{{< /code >}}

{{< code lang="python" title="実行結果" >}}
{'id': '123', 'authors': ['Author 1'], 'title': 'Title 1'}
{{< /code >}}

