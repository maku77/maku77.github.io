---
title: "DOM プログラミング - 同じタグ名を持つ全ての子要素をループ処理する"
date: "2005-08-12"
---


#### サンプル XML (sample.xml)

~~~ xml
<?xml version="1.0" encoding="Shift_JIS" ?>
<MyTool>
  <Command name="ShowPage" class="mytool.command.ShowPageCommand" />
  <Command name="EditPage" class="mytool.command.EditPageCommand" />
</MyTool>
~~~

上記の XML ファイルでは、`MyTool` 要素の子要素として複数の `Command` 要素があります。
この `Command` 要素をループで処理するには、

~~~
NodeList list = Element#getElementsByTagName("子要素のタグ名");
~~~

として、同じタグ名を持つ子要素を取得します。

#### サンプルコード (DomTest.java)

~~~ java
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.IOException;
import org.xml.sax.SAXException;

public class DomTest {
    public static void main(String[] args)
            throws ParserConfigurationException, SAXException, IOException {
        // XML ファイルをパースして Document オブジェクトを取得
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("sample.xml");

        // ルート要素を取得
        Element root = document.getDocumentElement();

        // ルート要素の下にある <Command> 要素をすべて取得
        NodeList list = root.getElementsByTagName("Command");

        // 要素数だけループ
        for (int i = 0; i < list.getLength(); ++i) {
            Element element = (Element) list.item(i);

            // 属性値を取得して表示
            String nameAttr = element.getAttribute("name");
            String classAttr = element.getAttribute("class");
            System.out.println("Command[" + i + "]");
            System.out.println("  name: " + nameAttr + ", class: " + classAttr);
        }
    }
}
~~~

#### 実行例

~~~
D:\> java DomTest
Command[0]
  name: ShowPage, class: mytool.command.ShowPageCommand
Command[1]
  name: EditPage, class: mytool.command.EditPageCommand
~~~

