---
title: "Javaメモ: Swing - JDialog のウィンドウサイズを変更できるようにする"
url: "p/utkwe8j/"
date: "2011-02-02"
tags: ["java"]
aliases: ["/java/swing/basic/change-dialog-size.html"]
---

JDialog は JFrame とは異なり、デフォルトではウィンドウサイズを変更できないようになっています。
ダイアログの端をドラッグすることでウィンドウのサイズを変更できるようにするには、以下のように設定します。

```java
dialog.setSizeable(true);
```
