---
title: "Javaメモ: DOM プログラミング - 子要素のテキストノードの値を取得する"
url: "p/6n8pbn8/"
date: "2005-08-12"
tags: ["java"]
aliases: ["/java/file/dom-get-text.html"]
---

サンプルデータとして、下記のような XML ファイルを使用します。

{{< code lang="xml" title="sample.xml" >}}
<?xml version="1.0" ?>
<root>Hello World</root>
{{< /code >}}

この XML ファイルの中の `Hello World` というテキストノードは、`root` 要素の子ノードなので、`root` 要素に対して `getFirstChild()` を呼び出すことで参照することができます。

{{< code lang="java" title="サンプルコード" >}}
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Text;
// Document document;

    // ルート要素を取得
    Element root = document.getDocumentElement();

    // 子要素であるテキストノードを取得
    Text textNode = (Text) root.getFirstChild();
    System.out.println("TextNode value: " + textNode.getNodeValue());
{{< /code >}}

