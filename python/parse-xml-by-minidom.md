---
title: Python で XML をパースする (minidom)
created: 2012-06-04
---

Python 2.0 以降では、Document Object Model インタフェース (DOM API) の最小の実装として、**xml.dom.minidom** が搭載されています。
DOM 標準の API を使って操作する必要のない場合は、より使いやすい `ElementTree` モジュールを使用した実装を検討するのがよいでしょう。

Document オブジェクト (xml.dom.minidom.Document) の取得
====

minidom を使用して、XML の各要素にアクセスするためには、まずは DOM の `Document` オブジェクトを取得する必要があります。

XML ファイルをパースする
----
```python
from xml.dom import minidom

doc = minidom.parse('input.xml')
print(doc.toxml())
```

XML 文字列をパースする
----
```python
from xml.dom import minidom

xml = '''
<tree>
  <branch>
    <leaf id='1'>Leaf 1</leaf>
    <leaf id='2'>Leaf 2</leaf>
    <leaf id='3'>Leaf 3</leaf>
  </branch>
</tree>'''

doc = minidom.parseString(xml)
print(doc.toxml())
```

Web 上の XML データをパースする
----

**urllib** モジュールを組み合わせて使えば、Web 上の XML も簡単にパースできます。

```python
import urllib
from xml.dom import minidom

dom = minidom.parse(urllib.urlopen("http://example.com/test.xml"))
print(dom.toxml())
```

DOM リソースの解放
====
DOM オブジェクトを使用し終わったらリソース解放のために `unlink()` を呼びます。

```
dom.unlink()
```



タグ名を指定して要素を取得する (getElementsByTagName)
====

`getElementsByTagName()` を使うと、指定したタグ名の要素のリスト（`NodeList` オブジェクト）を取得できます。
子ノードを再帰的に検索するので、同じ名前を持つタグはすべてリストに含まれます。
ノードが見つからない場合は、`None` が返されます。

```python
books = dom.getElementsByTagName("book")
```

上記の例では、DOM オブジェクトのメンバメソッドとして `getElementsByTagName()` を呼び出したので、全てのノードが検索対象になりますが、特定のノードのメンバメソッドとして `getElementsByTagName()` を呼び出すと、そのノード以下のノードのみが検索対象となります。

```python
authors = books[0].getElementsByTagName("author")
```

サンプルコード
----
#### sample.py
```python
#!/usr/bin/env python
from xml.dom import minidom

xml = '''
<tree>
  <branch>
    <leaf id='1'>Leaf 1</leaf>
    <leaf id='2'>Leaf 2</leaf>
    <leaf id='3'>Leaf 3</leaf>
  </branch>
</tree>'''

doc = minidom.parseString(xml)
elems = doc.getElementsByTagName('leaf')
for e in elems:
  print e.attributes['id'].value
  print e.firstChild.data
```

#### 実行結果
```
$ ./sample.py
1
Leaf 1
2
Leaf 2
3
Leaf 3
```


テキストノードの値を取得する
====

#### diary.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<diary>
  <entry>ほげほげ<empty/>ぽにょぽにょ</entry>
</diary>
```

#### sample.py
```python
from xml.dom import minidom

def getText(nodeList):
    rc = ""
    for node in nodeList:
        if node.nodeType == node.TEXT_NODE:
            rc += node.data
    return rc

dom = minidom.parse('sample.xml')
nodeList = dom.getElementsByTagName("entry")
print(getText(nodeList[0].childNodes))
```

#### 実行結果
```
ほげほげぽにょぽにょ
```


属性の値を取得する
====

#### sample.py
```python
from xml.dom import minidom

dom = minidom.parseString('<books><book id="001"/></books>')
nodeList = dom.getElementsByTagName('book')
print(nodeList[0].getAttributeNode('id').nodeValue)
```

#### 実行結果
```
001
```


ノードの子ノードを全て取得する
====
```python
childNodes = node.childNodes
```


ノード名（要素名）を取得する
====
```python
from xml.dom import minidom

dom = minidom.parseString('<books><book><author>AAA</author></book></books>')
books = dom.getElementsByTagName('book')
print(books[0].nodeName)  # => "book"
```


指定した要素をループで処理する
====

#### data.xml
```xml
<?xml version="1.0"?>
<books>
  <book id="100">Book name1</book>
  <book id="200">Book name2</book>
  <book id="300">Book name3</book>
</books>
```

#### sample.py
```python
from xml.dom.minidom import parse

# Parse a xml file.
dom = parse("data.xml")

# Get all book nodes
nodeList = dom.getElementsByTagName("book")

# Show text data.
for node in nodeList:
    print "Book ID =", node.getAttributeNode("id").nodeValue
    print "Book Name =", node.childNodes[0].data
```

#### 実行結果
```
Book ID = 100
Book Name = Book name1
Book ID = 200
Book Name = Book name2
Book ID = 300
Book Name = Book name3
```


子ノードをループで処理する
====

#### data.xml
```xml
<?xml version="1.0"?>
<books>
  <book>
    <name>Book name1</name>
    <author>Author 1</author>
    <author>Author 2</author>
    <author>Author 3</author>
  </book>
  <book>
    <name>Book name2</name>
    <author>Author 1</author>
    <author>Author 2</author>
  </book>
</books>
```

#### sample.py
```python
from xml.dom.minidom import parse

# Parse a xml file.
dom = parse("data.xml")

# Get all book nodes
nodeList = dom.getElementsByTagName("book")

# Process book nodes
for node in nodeList:
    # Process book's child nodes
    for child in node.childNodes:
        if child.nodeName == "name":
            print "Book name:", child.childNodes[0].data
        elif child.nodeName == "author":
            print "Author:", child.childNodes[0].data
```

#### 実行結果
```
Book name: Book name1
Author: Author 1
Author: Author 2
Author: Author 3
Book name: Book name2
Author: Author 1
```


ノードがテキストノードが調べる
====
```python
if node.nodeType == node.TEXT_NODE:
    print "This is a text node"
```


ノードに属性を追加する／属性の値を変更する
====
`xml.dom.minidom.Element#setAttribute(self, attname, value)`
を使用して、任意のノードの属性を設定することができます。

#### sample.py（属性 id の値を変更）
```python
from xml.dom import minidom

dom = minidom.parseString('<books><book id="old"/></books>')
nodeList = dom.getElementsByTagName('book')
nodeList[0].setAttribute('id', '002')
print(dom.toxml())
```

#### 実行結果
```xml
<?xml version="1.0" ?><books><book id="new"/></books>
```


テキストノードの値を変更する
====
`xml.dom.minidom.Element#nodeValue` の値を設定することで、テキストノードの値を変更することができます。

#### sample.py
```python
from xml.dom import minidom

dom = minidom.parseString('<book>Old text</book>')
nodeList = dom.getElementsByTagName('book')
bookNode = nodeList[0]
bookNode.childNodes[0].nodeValue = 'New text'
print(dom.toxml())
```

#### 実行結果
```xml
<?xml version="1.0" ?><book>New text</book>
```

