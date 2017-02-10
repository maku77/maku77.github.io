---
title: Swing - JTree で同時に1つのノードしか選択できないようにする
created: 2011-02-12
---

JTree のノードは、デフォルトで複数のノードを選択できるようになっています。
一度にひとつのノードしか選択できないようにするには、下記のように選択モードを設定します。

~~~ java
tree.getSelectionModel().setSelctionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
~~~

