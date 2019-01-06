---
title: "Swing - スプリッターの分割方向を動的に変更する"
date: "2011-01-23"
---

JSplitPane の分割方向は、以下のメソッドを使用することで動的に変更することができます。

~~~ java
void JSplitPane.setOrientation(int orientation)
~~~

#### orientation = `JSplitPane.HORIZONTAL_SPLIT`

![splitpane-horizontal.png](./splitpane-horizontal.png)

#### orientation = `JSplitPane.VERTICAL_SPLIT`

![splitpane-vertical.png](./splitpane-vertical.png)

#### 使用例

~~~ java
// JSplitPane splitPane = new JSplitPane(...);

splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
~~~

