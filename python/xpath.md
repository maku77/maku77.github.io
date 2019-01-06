---
title: "Python で XPath を使用する"
date: "2014-11-28"
---

Python 2.5 以降に搭載されている ElementTree XML API は、XPath による要素アクセスをサポートしています。

* [The Element Tree XML API - 20.5.2. XPath support](https://docs.python.org/3/library/xml.etree.elementtree.html#xpath-support)

下記の例では、```country``` という名前の要素をすべて取得し、再帰的に子要素を表示しています。
パスの指定を ```./country``` から、```.``` に変更すれば、ルート要素からすべての要素を出力できます。

#### countries.xml（入力ファイル）
```xml
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
```

#### sample.py
```python
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
```

#### 実行結果
```bash
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
```
