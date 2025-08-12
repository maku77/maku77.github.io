---
title: "意外と知られていない XML 記述のルール"
url: "p/cht2cpg/"
date: "2012-08-24"
tags: ["memo", "xml"]
aliases: ["/memo/xml/xml-rules.html"]
---

XML 文書を記述するときの意外と知られていないルールとして、下記のような定義があります。

* XML 宣言の `encoding` 属性は省略できる。省略すると、`encoding="UTF-8"` として扱われる。
* `xml` で始まる名前は使ってはいけない（NG 例: `<xmlRoot>`, `<XmlData>`）。要素名にも属性名にも使ってはいけない。大文字を混ぜてもダメ。
* タグ名に全角数字、半角カタカナは使ってはいけない。
* 文字参照で使うコード番号は、正確には Unicode ではなく ISO/IEC 10646 である。ただし、BMP (Basic Multilingual Plane) 領域は Unicode と同じなので、基本的に Unicode コードポイントとみなして構わない。

