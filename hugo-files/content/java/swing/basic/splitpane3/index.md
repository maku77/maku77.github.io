---
title: "Javaメモ: Swing - スプリッターの分割方向を動的に変更する"
url: "p/hp76jeg/"
date: "2011-01-23"
tags: ["java"]
aliases: ["/java/swing/basic/splitpane3.html"]
---

JSplitPane の分割方向は、以下のメソッドを使用することで動的に変更することができます。

```java
void JSplitPane.setOrientation(int orientation)
```

{{< image title="orientation = JSplitPane.HORIZONTAL_SPLIT" src="img-001.png" >}}
{{< image title="orientation = JSplitPane.VERTICAL_SPLIT" src="img-002.png" >}}

{{< code lang="java" title="使用例" >}}
// JSplitPane splitPane = new JSplitPane(...);

splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
{{< /code >}}
