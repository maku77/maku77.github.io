---
title: Python で XML をパースする
date: "2014-11-28"
---

ElementTree XML API
====

Python 2.5 以降には、XML を扱うための標準 API として、**ElementTree XML API** が用意されています。

  * [The ElementTree XML API](https://docs.python.org/3/library/xml.etree.elementtree.html)

ElementTree では、パースした XML を DOM オブジェクトとしてメモリ上に保持するため、XML データを読み込んだあとは、各 XML 要素へ柔軟にアクセスすることができます。

XML ファイルをパースする
----

* [ElementTree object](https://docs.python.org/3/library/xml.etree.elementtree.html#elementtree-objects)
* [Element object](https://docs.python.org/3/library/xml.etree.elementtree.html#element-objects)

ElementTree モジュールが提供している ```ElementTree.parse``` 関数を使って、XML ファイルを読み込むことができます。
```ElementTree.parse``` 関数は、```ElementTree``` オブジェクトを返します。
```ElementTree``` オブジェクトは、XML ツリー全体を表現するオブジェクトです。
XML ツリーの先頭要素を表す ```Element``` オブジェクトは、```ElementTree#getroot()``` メソッドで取得することができます。

#### input.xml
```xml
<tree name="hello">
  <trunk>...</trunk>
</tree>
```

#### main.py
```python
from xml.etree import ElementTree

# XML ファイルから ElementTree オブジェクトを生成
tree = ElementTree.parse('input.xml')

# 先頭要素を表す Element オブジェクトを取得
elem = tree.getroot()
print(elem.tag)     #=> tree
print(elem.attrib)  #=> {'name': 'hello'}
```

XML 形式の文字列をパースする
----

```ElementTree.fromstring(str)``` 関数を使用して、XML 形式の文字列データをパースすることができます。
戻り値は、先頭要素を表す ```Element``` オブジェクトです。

#### main.py
```python
from xml.etree import ElementTree

# XML 文字列をパースして Element オブジェクトを生成
elem = ElementTree.fromstring('<tree name="hello"><trunk>...</trunk></tree>')
print(elem.tag)     #=> tree
print(elem.attrib)  #=> {'name': 'hello'}
```

Element オブジェクトから要素の情報を取得する
----

```Element``` オブジェクトは 1 つずつの XML 要素を表現しており、下記のようなプロパティやメソッドが用意されています。
要素に含まれるテキストノードや属性を表すためのオブジェクトは存在せず、```Element``` オブジェクトに付随する情報として管理されていることに注意してください。

* ```Element#tag``` -- タグ名を取得
* ```Element#text``` -- その要素の先頭にあるテキストノードを取得（```<b>コレ</b>```）
* ```Element#tail``` -- その要素の直後にあるテキストノードを取得（```<b>...</b>コレ```）
* ```Element#attrib``` -- dictionary 形式で属性をすべて取得
* ```Element#get(key, default=None)``` -- 指定したキーの属性を取得
* ```Element#items()``` -- (name, value) のペアで属性をすべて取得
* ```Element#keys()``` -- 属性のキーをすべて取得

下記の例では、各要素のタグ名や、属性、テキストノードを再帰的に出力しています。
```Element``` 要素を ```for-in``` ループでイテレートすることで、子要素を取得することができます。

#### books.xml
```xml
<?xml version="1.0"?>
<books>
    <book id="001">
        <title>Title 1</title>
        <author>Author 1</author>
    </book>
    <book id="002">
        <title>Title 2</title>
        <author>Author 2</author>
    </book>
</books>
```

#### parse_books.py
```python
from xml.etree import ElementTree

def dump_node(node, indent=0):
    print("{}{} {} {}".format('    ' * indent, node.tag, node.attrib, node.text.strip()))
    for child in node:
        dump_node(child, indent + 1)

if __name__ == '__main__':
    tree = ElementTree.parse('books.xml')
    dump_node(tree.getroot())
```

#### 実行結果
```bash
$ python parse_books.py
books {}
    book {'id': '001'}
        title {} Title 1
        author {} Author 1
    book {'id': '002'}
        title {} Title 2
        author {} Author 2
```

