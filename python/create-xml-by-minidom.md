---
title: "Python で XML を構築する (minidom)"
date: "2012-06-08"
---

下記は、Python の minidom モジュールが提供する DOM インタフェースを使用して XML を構築し、XML ファイルとして保存するサンプルです。

#### sample.py
```python
#!/usr/bin/env python
import codecs
from xml.dom import minidom

def create_xmldoc():
    doc = minidom.Document()
    tree = doc.createElement('tree')
    doc.appendChild(tree)

    branch = doc.createElement('branch')
    tree.appendChild(branch)

    leaf = doc.createElement('leaf')
    leaf.setAttribute('id', '001')
    branch.appendChild(leaf)

    text = doc.createTextNode('This is a leaf')
    leaf.appendChild(text)

    return doc

def save_xmldoc(xmldoc, filepath):
    f = codecs.open(filepath, 'wb', encoding='utf-8')  # Text encoding
    xmldoc.writexml(f, '', ' '*4, '\n', encoding='UTF-8')  # XML header's encoding
    f.close()

if __name__ == '__main__':
    doc = create_xmldoc()
    save_xmldoc(doc, 'output.xml')
```

`codecs.open()` で指定する `encoding` パラメータは、テキスト書き込み時に使用するエンコーディング形式で、 `Document#writexml()` で指定する `encoding` パラメータは、XML のヘッダに書き込まれるエンコーディング形式です。
ここを混同しないように注意してください。

#### 実行結果 (output.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<tree>
    <branch>
        <leaf id="001">This is a leaf</leaf>
    </branch>
</tree>
```

