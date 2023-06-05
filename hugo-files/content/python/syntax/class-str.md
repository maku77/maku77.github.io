---
title: "Python のオブジェクトを print 関数で出力できるようにする (__str__)"
url: "p/uatcsaq/"
date: "2009-06-06"
lastmod: "2020-06-01"
tags: ["Python"]
aliases: /python/print-object.html
---

クラスを定義するときに特殊メソッド __`__str__`__ を実装しておくと、`print()` や `str()` にそのオブジェクトを渡したときの出力をカスタマイズすることができます。

{{< code lang="python" title="sample.py" >}}
class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def __str__(self):
        return f"Name={self.name}, Age={self.age}"


if __name__ == "__main__":
    p = Person("Maku", 14)
    print(p)
{{< /code >}}

{{< code title="実行結果" >}}
$ python sample.py
name=Maku, age=14
{{< /code >}}

