---
title: "Python で XPath を使って XML 要素を参照する (ElementTree)"
url: "p/fufwevc/"
date: "2014-11-28"
tags: ["Python", "XML"]
aliases: /python/xpath.html
---

Python 2.5 以降に搭載されている ElementTree XML API は、XPath による要素アクセスをサポートしています。

- [The Element Tree XML API - XPath support](https://docs.python.org/3/library/xml.etree.elementtree.html#xpath-support)

次の例では、`country` という名前の要素をすべて取得し、再帰的に子要素を表示しています。

{{< code lang="xml" title="countries.xml（入力ファイル）" >}}
<?xml version="1.0"?>
<data>
    <country name="Liechtenstein">
        <rank>1</rank>
        <year>2008</year>
        <gdppc>141100</gdppc>
        <neighbor name="Austria" direction="E"/>
        <neighbor name="Switzerland" direction="W"/>
    </country>
    <country name="Singapore">
        <rank>4</rank>
        <year>2011</year>
        <gdppc>59900</gdppc>
        <neighbor name="Malaysia" direction="N"/>
    </country>
    <country name="Panama">
        <rank>68</rank>
        <year>2011</year>
        <gdppc>13600</gdppc>
        <neighbor name="Costa Rica" direction="W"/>
        <neighbor name="Colombia" direction="E"/>
    </country>
</data>
{{< /code >}}

{{< code lang="python" title="sample.py" hl_lines="11" >}}
from xml.etree import ElementTree

def dump_node(node, indent=0):
    print('    ' * indent, node.tag, node.attrib)
    for child in node.getchildren():
        dump_node(child, indent + 1)

if __name__ == '__main__':
    tree = ElementTree.parse('countries.xml')
    root = tree.getroot()
    for node in root.findall('./country'):
        print('-' * 60)
        dump_node(node)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ python sample.py
------------------------------------------------------------
 country {'name': 'Liechtenstein'}
     rank {}
     year {}
     gdppc {}
     neighbor {'name': 'Austria', 'direction': 'E'}
     neighbor {'name': 'Switzerland', 'direction': 'W'}
------------------------------------------------------------
 country {'name': 'Singapore'}
     rank {}
     year {}
     gdppc {}
     neighbor {'name': 'Malaysia', 'direction': 'N'}
------------------------------------------------------------
 country {'name': 'Panama'}
     rank {}
     year {}
     gdppc {}
     neighbor {'name': 'Costa Rica', 'direction': 'W'}
     neighbor {'name': 'Colombia', 'direction': 'E'}
{{< /code >}}

パスの指定を `./country` から、`.` に変更すると、ルート要素からすべての要素を出力できます。

