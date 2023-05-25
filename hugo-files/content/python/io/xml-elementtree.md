---
title: "Python で XML をパースする (ElementTree)"
url: "p/cp9q7n5/"
date: "2014-11-28"
tags: ["Python", "XML"]
aliases: /python/parse-xml-by-element-tree.html
---

ElementTree XML API
----

Python 2.5 以降には、XML を扱うための標準 API として、__ElementTree XML API__ が用意されています。

- [The ElementTree XML API](https://docs.python.org/3/library/xml.etree.elementtree.html)

`ElementTree` では、パースした XML を DOM オブジェクトとしてメモリ上に保持するため、XML データを読み込んだあとは、各 XML 要素へ柔軟にアクセスすることができます。


XML ファイルをパースする
----

`ElementTree` モジュールが提供している __`ElementTree.parse`__ 関数を使って、XML ファイルを読み込むことができます。
`ElementTree.parse` 関数は、読み込んだ XML をパースして [ElementTree](https://docs.python.org/3/library/xml.etree.elementtree.html#elementtree-objects) オブジェクトとして返します。
`ElementTree` オブジェクトは、XML ツリー全体を表現するオブジェクトです。
XML ツリーの先頭要素を表す [Element](https://docs.python.org/3/library/xml.etree.elementtree.html#element-objects) オブジェクトは、__`ElementTree#getroot()`__ メソッドで取得することができます。

{{< code lang="xml" title="input.xml（テスト用の XML ファイル）" >}}
<tree name="hello">
  <trunk>...</trunk>
</tree>
{{< /code >}}

{{< code lang="python" title="main.py" >}}
from xml.etree import ElementTree

# XML ファイルから ElementTree オブジェクトを生成
tree = ElementTree.parse('input.xml')

# 先頭要素を表す Element オブジェクトを取得
elem = tree.getroot()
print(elem.tag)     #=> tree
print(elem.attrib)  #=> {'name': 'hello'}
{{< /code >}}


XML 形式の文字列をパースする
----

XML ファイルではなく、XML 形式のテキストをパースしたいときは、__`ElementTree.fromstring(str)`__ 関数を使用します。
戻り値は、先頭要素を表す `Element` オブジェクトになります。

{{< code lang="python" title="main.py" >}}
from xml.etree import ElementTree

# XML 文字列をパースして Element オブジェクトを生成
elem = ElementTree.fromstring('<tree name="hello"><trunk>...</trunk></tree>')
print(elem.tag)     #=> tree
print(elem.attrib)  #=> {'name': 'hello'}
{{< /code >}}


Element オブジェクトから要素の情報を取得する
----

`Element` オブジェクトは 1 つの XML 要素を表現しており、下記のようなプロパティやメソッドが用意されています。
要素に含まれるテキストノードや属性を表すためのオブジェクトは存在せず、`Element` オブジェクトに付随する情報として管理されていることに注意してください。

| 属性 | 説明 |
| ---- | ---- |
| __`Element#tag`__ | タグ名を取得 |
| __`Element#text`__ | その要素の先頭にあるテキストノードを取得（`<b>これ</b>`） |
| __`Element#tail`__ | その要素の直後にあるテキストノードを取得（`<b>...</b>これ`） |
| __`Element#attrib`__ | dictionary 形式で属性をすべて取得 |
| __`Element#get(key, default=None)`__ | 指定したキーの属性を取得 |
| __`Element#items()`__ | (name, value) のペアで属性をすべて取得 |
| __`Element#keys()`__ | 属性のキーをすべて取得 |

下記の例では、各要素のタグ名や、属性、テキストノードを再帰的に出力しています。
子要素を取得するには、`Element` 要素自身を `for-in` ループでイテレートします。

{{< code lang="xml" title="books.xml" >}}
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
{{< /code >}}

{{< code lang="python" title="parse_books.py" >}}
from xml.etree import ElementTree

def dump_node(node, indent=0):
    print("{}{} {} {}".format('    ' * indent, node.tag, node.attrib, node.text.strip()))
    for child in node:
        dump_node(child, indent + 1)

if __name__ == '__main__':
    tree = ElementTree.parse('books.xml')
    dump_node(tree.getroot())
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ python parse_books.py
books {}
    book {'id': '001'}
        title {} Title 1
        author {} Author 1
    book {'id': '002'}
        title {} Title 2
        author {} Author 2
{{< /code >}}

