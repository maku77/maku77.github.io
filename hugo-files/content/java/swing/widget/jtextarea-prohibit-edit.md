---
title: "Javaメモ: Swing - JTextArea のテキストを編集不可にする"
url: "p/j9pq4vu/"
date: "2011-01-23"
tags: ["java"]
aliases: ["/java/swing/widget/jtextarea-prohibit-edit.html"]
---

テキストエリア内のテキストをユーザが編集できないようにするには、`setEditable()` メソッドで `false` を指定します。

```java
JTextArea textArea = new JTextArea(3, 20);
textArea.setEditable(false);
textArea.setText("AAA\nBBB\nCCC\nDDD");
```

テキストを編集不可に設定した JTextArea にテキストを設定するには、`setText()` メソッドを使用して、コードから明示的に設定する必要があります。
