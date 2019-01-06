---
title: "stdio を使ったシンプルな XmlWriter クラス (PetitXmlWriter)"
date: "2007-06-06"
---

PetitXmlWriter クラスは、C の stdio を使用したシンプルな XML ファイル出力のサンプルです。

- [PetitXmlWriter.h](./PetitXmlWriter.h) / [PetitXmlWriter.cpp](./PetitXmlWriter.cpp)

サンプルコードを実行すると、以下のような XML ファイルが作成されます。

#### sample.xml

```xml
<?xml version='1.0' encoding='UTF-8'?>
<book>
  <category type="science">
    <book title="TITLE 1" author="AUTHOR 1">CONTENT 1</book>
    <book title="TITLE 2" author="AUTHOR 2">CONTENT 2</book>
  </category>
</book>
```

#### sample.cpp

```cpp
#include "PetitXmlWriter.h"

int main() {
    PetitXmlWriter writer("sample.xml");

    // Begin root node.
    writer.BeginNode("book");

    // Add "category" node.
    writer.BeginNode("category");
    writer.AddAttr("type", "science");

    // Add "book" node.
    writer.BeginNode("book");
    writer.AddAttr("title", "TITLE 1");
    writer.AddAttr("author", "AUTHOR 1");
    writer.AddText("CONTENT 1");
    writer.EndNode();

    // Add "book" node.
    writer.BeginNode("book");
    writer.AddAttr("title", "TITLE 2");
    writer.AddAttr("author", "AUTHOR 2");
    writer.AddText("CONTENT 2");
    writer.EndNode();

    // End "category" node.
    writer.EndNode();

    // End root node.
    writer.EndNode();
}
```


