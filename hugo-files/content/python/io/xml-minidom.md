---
title: "Python で XML をパースする (minidom)"
url: "p/guducs9/"
date: "2012-06-04"
tags: ["Python", "XML"]
aliases: /python/parse-xml-by-minidom.html
---

Python 2.0 以降では、Document Object Model インタフェース (DOM API) の最小の実装として、[xml.dom.minidom](https://docs.python.org/3/library/xml.dom.minidom.html) が搭載されています。
DOM 標準の API を使って操作する必要のない場合は、よりシンプルなインターフェースを提供している [ElementTree モジュールを使用する](/p/cp9q7n5/) のがよいでしょう。


Document オブジェクトを取得する
----

minidom を使用して、XML の各要素にアクセスするためには、まずは DOM 全体を表現する __`xml.dom.minidom.Document`__ オブジェクトを取得する必要があります。
データソースとしては、XML ファイル、XML 文字列、Web 上のリソースなどを利用できます。

### XML ファイルを扱う場合

```python
from xml.dom import minidom

doc = minidom.parse("input.xml")
print(doc.toxml())
```

### XML 文字列を扱う場合

```python
from xml.dom import minidom

xml = """
<tree>
  <branch>
    <leaf id='1'>Leaf 1</leaf>
    <leaf id='2'>Leaf 2</leaf>
    <leaf id='3'>Leaf 3</leaf>
  </branch>
</tree>"""

doc = minidom.parseString(xml)
print(doc.toxml())
```

### Web 上の XML リソースを扱う場合

__`urllib`__ モジュールを組み合わせて使えば、Web 上の XML も簡単にパースできます。

```python
from urllib.request import urlopen
from xml.dom import minidom

with urlopen("https://example.com/test.xml") as res:
    doc = minidom.parse(res)
    print(doc.toxml())
```


DOM リソースの解放
----

使用し終わった `Document` オブジェクトは、ガーベジ・コレクションによって自動的に解放されますが、__`unlink`__ メソッドで明示的に解放することもできます。

```python
dom.unlink()
```

`Document` オブジェクトを生成するときに __`with`__ 構文を使用すれば、`unlink` 処理を自動化することができます。

```python
with minidom.parse("input.xml") as doc:
    print(doc.toxml())
```


タグ名を指定して要素を取得する (getElementsByTagName)
----

__`Document#getElementsByTagName`__ メソッドを使うと、指定したタグ名の要素のリスト（`NodeList` オブジェクト）を取得することができます。
子ノードを再帰的に検索するので、同じタグ名を持つ要素はすべてリストに含まれます。
ノードが見つからない場合は、`None` が返されます。

```python
books = doc.getElementsByTagName("book")
```

上記の例では、`Document` オブジェクトの `getElementsByTagName` メソッドを呼び出しているので、全てのノードが検索対象になりますが、特定のノード (`Element`) の `getElementsByTagName` メソッドを呼び出すと、そのノード以下のノードのみが検索対象となります。

```python
authors = books[0].getElementsByTagName("author")
```

{{< code lang="python" title="sample.py（サンプルコード）" >}}
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
elems = doc.getElementsByTagName("leaf")
for e in elems:
    print e.attributes["id"].value
    print e.firstChild.data
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ python3 sample.py
1
Leaf 1
2
Leaf 2
3
Leaf 3
{{< /code >}}


テキストノードの値を取得する
----

{{< code lang="xml" title="diary.xml" >}}
<?xml version="1.0" encoding="utf-8"?>
<diary>
  <entry>ほげほげ<empty/>ぽにょぽにょ</entry>
</diary>
{{< /code >}}

{{< code lang="python" title="sample.py" >}}
from xml.dom import minidom

def getText(nodeList):
    text = ""
    for node in nodeList:
        if node.nodeType == node.TEXT_NODE:
            text += node.data
    return text

doc = minidom.parse("sample.xml")
nodeList = doc.getElementsByTagName("entry")
print(getText(nodeList[0].childNodes))
{{< /code >}}

{{< code title="実行結果" >}}
ほげほげぽにょぽにょ
{{< /code >}}


属性値を取得する
----

{{< code lang="python" title="sample.py" hl_lines="5" >}}
from xml.dom import minidom

doc = minidom.parseString('<books><book id="001"/></books>')
elem = doc.getElementsByTagName("book")[0]
print(elem.getAttributeNode("id").nodeValue)
{{< /code >}}

{{< code title="実行結果" >}}
001
{{< /code >}}


ノードの子ノードを全て取得する
----

```python
childNodes = node.childNodes
```


ノード名（要素名）を取得する
----

{{< code lang="python" title="sample.py" hl_lines="5" >}}
from xml.dom import minidom

doc = minidom.parseString('<books><book><author>AAA</author></book></books>')
elem = doc.getElementsByTagName('book')[0]
print(elem.nodeName)  # => "book"
{{< /code >}}


指定した要素をループで処理する
----

次の例では、`book` というタグ名の要素をすべて取得してループ処理しています。

{{< code lang="xml" title="books.xml" >}}
<?xml version="1.0"?>
<books>
  <book id="100">Book name1</book>
  <book id="200">Book name2</book>
  <book id="300">Book name3</book>
</books>
{{< /code >}}

{{< code lang="python" title="sample.py" hl_lines="10" >}}
from xml.dom.minidom import parse

# Parse a xml file.
doc = parse("books.xml")

# Get all book nodes
nodeList = doc.getElementsByTagName("book")

# Show text data.
for node in nodeList:
    print "Book ID =", node.getAttributeNode("id").nodeValue
    print "Book Name =", node.childNodes[0].data
{{< /code >}}

{{< code title="実行結果" >}}
Book ID = 100
Book Name = Book name1
Book ID = 200
Book Name = Book name2
Book ID = 300
Book Name = Book name3
{{< /code >}}


子ノードをループで処理する
----

次の例では、`books` 以下の複数の子ノード (`book`) をループ処理しています。

{{< code lang="xml" title="books.xml" >}}
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
{{< /code >}}

{{< code lang="python" title="sample.py" hl_lines="12" >}}
from xml.dom.minidom import parse

# Parse a xml file.
doc = parse("books.xml")

# Get all book nodes
nodeList = doc.getElementsByTagName("book")

# Process book nodes
for node in nodeList:
    # Process book's child nodes
    for child in node.childNodes:
        if child.nodeName == "name":
            print "Book name:", child.childNodes[0].data
        elif child.nodeName == "author":
            print "Author:", child.childNodes[0].data
{{< /code >}}

{{< code title="実行結果" >}}
Book name: Book name1
Author: Author 1
Author: Author 2
Author: Author 3
Book name: Book name2
Author: Author 1
{{< /code >}}


ノードがテキストノードが調べる
----

```python
if node.nodeType == node.TEXT_NODE:
    print "This is a text node"
```


ノードに属性を追加する／属性の値を変更する
----

`Element` オブジェクトの __`setAttribute(self, attname, value)`__ メソッドを使用して、任意のノードの属性を設定することができます。

{{< code lang="python" title="id 属性の値を変更する" hl_lines="5" >}}
from xml.dom import minidom

doc = minidom.parseString('<books><book id="old"/></books>')
nodeList = doc.getElementsByTagName('book')
nodeList[0].setAttribute('id', '002')
print(doc.toxml())
{{< /code >}}

{{< code lang="xml" title="実行結果" >}}
<?xml version="1.0" ?><books><book id="new"/></books>
{{< /code >}}


テキストノードの値を変更する
----

`Element` オブジェクトの __`nodeValue`__ 属性の値を設定することで、テキストノードの値を変更することができます。

{{< code lang="python" title="テキストノードの値を変更する" hl_lines="6" >}}
from xml.dom import minidom

doc = minidom.parseString('<book>Old text</book>')
nodeList = doc.getElementsByTagName('book')
bookNode = nodeList[0]
bookNode.childNodes[0].nodeValue = 'New text'
print(doc.toxml())
{{< /code >}}

{{< code lang="xml" title="実行結果" >}}
<?xml version="1.0" ?><book>New text</book>
{{< /code >}}

