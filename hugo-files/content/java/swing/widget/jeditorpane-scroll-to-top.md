---
title: "Javaメモ: Swing - JEditorPane で先頭部分を表示する"
url: "p/pur7zs5/"
date: "2011-03-21"
tags: ["java"]
aliases: ["/java/swing/widget/jeditorpane-scroll-to-top.html"]
---

`JEditorPane` に長いテキストを `setText()` でセットすると、そのテキストの最後の部分が表示された状態になります。
先頭部分を表示するには、`moveCaretPosition()` を実行します。

```java
// JEditorPane editorPane;
editorPane.setText(veryLongText);
editorPane.moveCaretPosition(0);
```
