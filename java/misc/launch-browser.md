---
title: "既定の Web ブラウザで URL を開く"
date: "2011-05-05"
---

OS の規定の Web ブラウザとして設定されているアプリを起動するには、`java.awt.Desktop#browse()` メソッドが使用できます。
例えば、既定の Web ブラウザとして Firefox が設定されている場合に、下記のコードを実行すると Firefox が立ち上がります。

~~~ java
// import java.awt.Desktop;
// import java.io.IOException;
// import java.net.URI;
// import java.net.URISyntaxException;

Desktop desktop = Desktop.getDesktop();
try {
    desktop.browse(new URI("https://google.com/"));
} catch (IOException e) {
    e.printStackTrace();
} catch (URISyntaxException e) {
    e.printStackTrace();
}
~~~

`Desktop` クラスは、Web ブラウザ以外にも、URI やファイルの拡張子に関連づけられたアプリケーションを開く機能を備えています。
`Desktop` クラスには、以下のような便利そうなメソッドが用意されています。

~~~
void browse(URI uri)
void edit(File file)
void mail()
void mail(URI mailtoURI)
void open(File file)
void print(File file)
~~~

