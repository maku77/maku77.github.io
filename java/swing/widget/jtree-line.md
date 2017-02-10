---
title: Swing - JTree で一階層目のノード間に水平線を表示する
created: 2011-02-11
---

![jtree-line.png](./jtree-line.png)

JTree オブジェクトに対して、以下のようにプロパティ設定を行うと、第一階層（ルートノードの直下）のノード間にラインを表示することができます。

~~~ java
UIManager.put("Tree.line", Color.GRAY);
JTree tree = new JTree();
tree.putClientProperty("JTree.lineStyle", "Horizontal");
~~~

ただし、選択しているルック＆フィールによっては、ラインが描画されないこともあります。

