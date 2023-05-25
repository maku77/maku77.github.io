---
title: "Python で XML を扱う方法いろいろ"
url: "p/pt6fpx8/"
date: "2014-11-28"
tags: ["Python", "XML"]
aliases: /python/xml-in-python.html
---

Python には下記のように、標準の XML パーサが複数搭載されています。

- __`xml.etree.ElementTree`__ ... The ElementTree XML API
- __`xml.dom`__ ... The Document Object Model API
- __`xml.dom.minidom`__ ... Minimal DOM implementation
- __`xml.sax`__ ... Support for SAX2 parsers

通常はシンプルな `xml.etree.ElementTree` を使い、DOM 標準の API を使いたい場合は `xml.dom` を使用すればよいでしょう。
巨大な XML ファイルを効率的に読み込みたい場合は、SAX API を提供する `xml.sax` を選択できます。

- 参考: [20. Structured Markup Processing Tools](https://docs.python.org/3/library/markup.html)

