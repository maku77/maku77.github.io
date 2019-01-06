---
title: "Swing - JTextField でテキストの先頭／末尾部分を強制的に表示する"
date: "2011-02-06"
---

JTextField に長いテキストを格納した場合は、テキスト全体が表示されなくなります。
表示位置をずらして、テキストの末尾の部分を強制的に表示するには下記のようにします。

~~~ java
BoundedRangeModel model = textField.gethorizontalVisibility();
int extent = model.getExtent();
textField.setScrollOffset(extent);
~~~

逆にテキストの先頭を表示したい場合は、`setScrollOffset` で 0 を指定します。

~~~ java
textField.setScrollOffset(0);
~~~

