---
title: "Pythonメモ: 全てが真 (all)、少なくとも一つが真 (any) かどうか調べる"
url: "p/z6xo3xs/"
date: "2014-05-02"
tags: ["python"]
aliases: /python/syntax/all-any.html
---

Python の all 関数と any 関数
----

組み込み関数の `all(iterable)`、`any(iterable)` を使うと、指定した（イテレート可能な）要素がすべて真であること、あるいは、少なくとも１つ以上が真であることを調べることができます。

* [all — Python 3 ドキュメント](http://docs.python.jp/3/library/functions.html#all)
* [any — Python 3 ドキュメント](http://docs.python.jp/3/library/functions.html#any)

単純なリストを渡してみると振る舞いが分かります。

```python
all([True, True, True])     # => True
all([True, True, False])    # => False
all([False, False, False])  # => False

any([True, True, True])     # => True
any([True, False, False])   # => True
any([False, False, False])  # => False
```


all 関数と any 関数の使用例
----

{{< code lang="python" title="例: すべて偶数かどうか調べる" >}}
arr = [2, 3, 4]
all(x % 2 == 0 for x in arr)  #=> False
{{< /code >}}

{{< code lang="python" title="例: 少なくとも1つが偶数か調べる" >}}
arr = [2, 3, 4]
any(x % 2 == 0 for x in arr)  #=> True
{{< /code >}}

{{< code lang="python" title="例: 文字列がいずれかのプレフィックスで始まっているか調べる" >}}
PREFIXES = ('http://', 'https://', 'ftp://')
s = 'ftp://example.com/'
any(s.startswith(x) for x in PREFIXES)  #=> True
{{< /code >}}

