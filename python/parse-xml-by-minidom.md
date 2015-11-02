---
title: Python で XML をパースする (minidom)
created: 2012-06-04
---

Python 2.0 以降では、Document Object Model インタフェース (DOM API) の最小の実装として、**xml.dom.minidom** が搭載されています。
DOM 標準の API を使って操作する必要のない場合は、より使いやすい `ElementTree` モジュールを使用した実装を検討するのがよいでしょう。

Document オブジェクト (xml.dom.minidom.Document) の取得
====

minidom を使用して、XML の各要素にアクセスするためには、まずは DOM の `Document` オブジェクトを取得する必要があります。

#### XML ファイルをパースする場合
```python
#!/usr/bin/env python
from xml.dom import minidom

doc = minidom.parse('input.xml')
print(doc.toxml())
```

#### XML 文字列をパースする場合
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
print(doc.toxml())
```

タグ名を指定して要素を取得する (getElementsByTagName)
====

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

