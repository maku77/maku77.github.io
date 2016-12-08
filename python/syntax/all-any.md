---
title: 全てが真 (all)、少なくとも一つが真 (any) かどうか調べる
created: 2014-05-02
---

Python の all 関数と any 関数
----

組み込み関数の `all(iterable)`、`any(iterable)` を使うと、指定した（イテレート可能な）要素がすべて真であること、あるいは、少なくとも１つ以上が真であることを調べることができます。

* [all — Python 3 ドキュメント](http://docs.python.jp/3/library/functions.html#all)
* [any — Python 3 ドキュメント](http://docs.python.jp/3/library/functions.html#any)

単純なリストを渡してみると振る舞いが分かります。

#### all の振る舞い

```python
all([True, True, True])     # => True
all([True, True, False])    # => False
all([False, False, False])  # => False
```

#### any の振る舞い

```python
any([True, True, True])     # => True
any([True, False, False])   #=> True
any([False, False, False])  # => False
```


all 関数の使用例
----

### 例: すべてが偶数であるか調べる

```python
arr = [2, 3, 4]
all(x % 2 == 0 for x in arr)  #=> False
```

any 関数の使用例
----

### 例: 少なくとも１つが偶数であるか調べる

```python
arr = [2, 3, 4]
any(x % 2 == 0 for x in arr)  #=> True
```

### 例: 文字列がいずれかのプレフィックスで始まっているか調べる

```python
PREFIXES = ('http://', 'https://', 'ftp://')
s = 'ftp://example.com/'
any(s.startswith(x) for x in PREFIXES)  #=> True
```

