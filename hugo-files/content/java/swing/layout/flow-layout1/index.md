---
title: "Javaメモ: Swing - FlowLayout でコンポーネントを左上から並べる"
url: "p/x9smwmt/"
date: "2011-01-15"
tags: ["java"]
aliases: ["/java/swing/layout/flow-layout1.html"]
---

FlowLayout は JPanel のデフォルトのレイアウトマネージャとして設定されており、コンポーネントを単純に左上から右方向に順番に「流れるように」並べていきます。
コンポーネント間には、デフォルトで適度な隙間が入ります。
ウィンドウのサイズを縮めると、内部に配置したコンポーネントは自動的に折り返して並べられます。

{{< image src="img-001.png" >}}
{{< image src="img-002.png" >}}

{{< code lang="java" title="サンプルコード" >}}
public class MyPanel extends JPanel {
    public MyPanel() {
        setLayout(new FlowLayout());
        add(new Button("Button 1"));
        add(new Button("Button 2"));
        add(new Button("Button 3"));
        add(new Button("Button 4"));
        add(new Button("Button 5"));
    }
}
{{< /code >}}

このレイアウトマネージャでは、大雑把なレイアウトしか行えないので、開発の初期の段階で、とりあえずコンポーネントを配置したいときに使用することになります（動的に増減するボタンを並べる、といった用途には使えるかもしれない）。
最終的なレイアウトでは、別の適切なレイアウトマネージャを設定する必要があります。
