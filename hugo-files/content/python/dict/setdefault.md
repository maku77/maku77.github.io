---
title: "Python で dictionary にキーが存在しない場合のみ新しい値を格納する (setdefault)"
url: "p/bq6yzpr/"
date: "2013-06-15"
tags: ["Python"]
aliases: /python/dictionary/setdefault.html
---

{{% private %}}
- https://docs.python.org/3/library/stdtypes.html#dict.setdefault
{{% /private %}}

Python の dictionary 内の値を参照するときに、キーが存在しないときにそのデフォルト値をセットした上で値を取得したい場合は、__`dict.setdefault()`__ メソッドを使用します。

```python
val = d.setdefault('key', 'default_value')
```

メソッド名は `setdefault()` ですが、実際には __値のセットと取得を同時に行う__ という意味合いが強いので、`setdefault_and_get` のような名前の方がイメージしやすいかもしれません。
具体的には次のように動作します。

- キーが存在しない場合: 第 2 引数の値を dictionary に登録した上で、その値を返す
- キーが存在する場合: dictionary に格納されている値を返す

典型的な使用例として、dictionary の値としてリストを管理するユースケースがあります。
`setdefault()` を使えば、キーがまだ存在しないときに自動的に空のリストを生成して dictionary に登録しつつ、その参照を返してくれます。

```python
d = {}
l = d.setdefault('key', [])  # キーが存在しないので空リストを格納し、その参照を返す
l.append(100)  # d['key'] が [100] になる
```

同じキーで再度呼び出すと、今度はすでに dictionary に格納されているリストへの参照を返します。

```python
l = d.setdefault('key', [])  # キーが既に存在しているので、格納済みのリスト [100] の参照を返す
l.append(200)  # d['key'] は [100, 200] になる
```

さらに、次のように戻り値に直接 `append()` を連結して書くことも可能です。

```python
d.setdefault('key', []).append(100)
```

上記のコードは、次のように `get()` メソッドのデフォルト値を指定する方法と似ていますが、`get()` は生成したデフォルト値を dictionary に書き込まないため、リストの参照を忘れずに dictionary へ格納し直す必要があります。
この代入を忘れると不具合になるため、`setdefault()` を使う方が簡潔で安全です。

{{< code lang="python" title="setdefault() を使わないとちょっと面倒になる" >}}
l = d.get('key', [])
l.append(100)
d['key'] = l  # デフォルト値（新しいリスト参照）が生成されたときのために dictionary に格納する必要がある
{{< /code >}}

