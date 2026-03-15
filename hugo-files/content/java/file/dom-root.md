---
title: "Javaメモ: DOM プログラミング - XML のルート要素を取得する"
url: "p/wxhedjx/"
date: "2005-08-12"
tags: ["java"]
aliases: ["/java/file/dom-root.html"]
description: "Java で XML データを扱う時に、DOM オブジェクトとして操作する場合は、まずは XML 全体を表す Document オブジェクトを取得します。"
---

XML 全体を表す `Document` オブジェクトは、`DocumentBuilder#parse()` で取得することができます。
ここでは最初のサンプルとして、下記のような XML ファイルのルート要素 (`<root>...</root>`) を取得してみます。

{{< code lang="xml" title="sample.xml（サンプルファイル）" >}}
<?xml version="1.0" encoding="UTF-8" ?>
<root></root>
{{< /code >}}

{{< code lang="java" title="DomTest.java（DOM を使った XML パースの例）" >}}
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class DomTest {
    private static Document document;

    public static void main(String[] args)
            throws ParserConfigurationException, SAXException, IOException {
        // 引数で XML を指定
        String xmlUri = args[0];

        // XML ファイルをパースして Document オブジェクトを取得
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlUri);

        // ルート要素を取得
        Element root = document.getDocumentElement();
        System.out.println("NodeName of root element: " + root.getNodeName());
    }
}
{{< /code >}}

{{< code lang="console" title="コンパイル" >}}
$ javac DomTest.java
{{< /code >}}

{{< code lang="console" title="実行" >}}
$ java DomTest sample.xml
NodeName of root element: root
{{< /code >}}


