---
title: "Swing - JTextArea でテキストの先頭部分を表示する"
date: "2011-05-05"
---

`JTextArea` の `setText` メソッドで、長いテキストをセットすると、テキストエリアにテキストの末尾部分が表示されます。
セットしたテキストの先頭部分を表示したい場合は、`setCaretPosition` を利用して表示領域を移動させます。

~~~ java
// import javax.swing.JTextArea;

textArea.setText(longText);
textArea.setCaretPosition(0);
~~~

