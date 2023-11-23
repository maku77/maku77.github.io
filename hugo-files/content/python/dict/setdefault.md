---
title: "Python で dictionary にキーが存在しない場合のみ新しい値を格納する (setdefault)"
url: "p/bq6yzpr/"
date: "2013-06-15"
tags: ["Python"]
aliases: /python/dictionary/setdefault.html
---

Python の dictionary に値をセットするときに、まだそのキーが存在しない場合だけ新しい値をセットしたい場合は、__`dict.setdefault()`__ メソッドを使用します。
`setdefault()` メソッドは、既にキーが存在している場合は格納されている値、存在しない場合は第 2 引数で指定した値を返します。

```python
val = d.setdefault('key', 'default_value')
```

つまり、`dict.setdefault()` は、__`dict.get()` を行いつつ、存在しないキーを指定した場合は、値のセットまで行う__ という振る舞いをします。

典型的な使用例として、dictionary 要素の値としてリストを格納するというユースケースがあります。
次のように、キーに対応するリストを取り出すときに `setdefault()` を使えば、キーがまだ存在しないときに自動的に空のリストを生成し、dictionary に登録してくれます。

```python
d = {}
l = d.setdefault('key', [])  # このキーは存在しないので初期値として空リストを格納し、さらにその参照を返す
l.append(100)  # d['key'] が [100] になる
```

もう一度、同じキー `key` で参照しようとすると、今度はすでに dictionary に格納されているリストへの参照を取得することができます。

```python
l = d.setdefault('key', [])  # このキーは既に存在しているので、格納されているリスト [100] の参照を返す
l.append(200)  # d['key'] は [100, 200] になる
```

上記のコードは、次のように `get()` メソッドでデフォルト値を指定しておく方法と似ています。
ただし、このようにした場合は、`get()` によって返された新規リストオブジェクトを忘れずに dictionary に格納しなければいけません。

{{< code lang="python" title="setdefault() を使わないとちょっと面倒になる" >}}
l = d.get('key', [])
l.append(100)
d['key'] = l  # デフォルト値が生成されたときのために dictionary に格納する必要がある
{{< /code >}}

