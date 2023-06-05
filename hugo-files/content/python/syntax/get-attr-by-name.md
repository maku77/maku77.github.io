---
title: "Python でクラスの属性に名前でアクセスする (getattr, setattr)"
url: "p/r3m4k2h/"
date: "2007-03-31"
lastmod: "2023-06-06"
tags: ["Python"]
changes:
  - 2023-06-06: 記事をリファイン
aliases: /python/get-attribute-by-name.html
---

Python でオブジェクトの属性にアクセスするには、通常 `obj.属性名` という構文を使用しますが、次のような仕組みを使うと、属性名を文字列で指定してアクセスすることができます。

- ビルトイン関数の __`getattr`__ / __`setattr`__ 関数
- オブジェクトの __`__dict__`__ 属性

変数などに格納した属性名を使えるようになるため、属性名のリストをループ処理したり、あらかじめ属性名が決められない場合に後付けで属性を追加したりすることができます。


getattr/setattr 関数を使う方法
----

ビルトイン関数の __`getattr`__ や __`setattr`__ を使うと、任意のオブジェクトの属性に次のような形式でアクセスできます。

```python
getattr(object, name [, default])  # 属性値の取得
setattr(object, name, value)       # 属性値の設定
```

`getattr` 関数で指定した名前の属性が見つからない場合は、__`default`__ 引数で指定した値が返されます。
`default` 引数が指定されていないと __`AttributeError`__ が発生するので、できるだけ `default` 引数は指定しておくのが安全です。

次の例では、属性名のタプル `("name", "age")` をループ処理して、対応する属性値を順番に取り出しています。

{{< code lang="python" title="例: person オブジェクトの name 属性と age 属性を参照する" >}}
class Person:
    def __init__(self, name: str, age: int):
        self.name = name
        self.age = age

person = Person(name="Maku", age=14)
for attr_name in ("name", "age"):
    attr_val = getattr(person, attr_name)
    print(f"{attr_name} = {attr_val}")
{{< /code >}}

{{< code title="実行結果" >}}
name = Maku
age = 14
{{< /code >}}


\_\_dict\_\_ 属性を使用する方法
----

特殊属性の __`__dict__`__ でも属性値にアクセスできます。
この属性は辞書オブジェクトになっているので、`__dict__["属性名"]` という形で参照します。

```python
class MyClass:
    pass

if __name__ == "__main__":
    obj = MyClass()

    # 名前指定で属性値を設定する
    obj.__dict__["foo"] = 100
    print(obj.foo)  # => 100

    # 名前指定で属性値を取得する
    obj.bar = 200
    print(obj.__dict__["bar"])  # => 200
```

