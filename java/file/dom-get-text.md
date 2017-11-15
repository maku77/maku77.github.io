---
title: "DOM プログラミング - 子要素のテキストノードの値を取得する"
created: 2005-08-12
---

サンプルデータとして、下記のような XML ファイルを使用します。

#### sample.xml

~~~ xml
<?xml version="1.0" ?>
<root>Hello World</root>
~~~

この XML ファイルの中の `Hello World` というテキストノードは、`root` 要素の子ノードなので、`root` 要素に対して `getFirstChild()` を呼び出すことで参照することができます。

#### サンプルコード

~~~ java
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Text;
// Document document;

    // ルート要素を取得
    Element root = document.getDocumentElement();

    // 子要素であるテキストノードを取得
    Text textNode = (Text) root.getFirstChild();
    System.out.println("TextNode value: " + textNode.getNodeValue());
~~~

