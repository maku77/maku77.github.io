---
title: "Swing - JTree で指定したノードを選択する"
date: "2011-02-12"
---

JTree のあるノードを選択状態にするには、以下のメソッドを使用します。
指定したノードが閉じられた状態の場合は、親ノードが自動的に展開されます。

~~~
void JTree#setSelectionPath(TreePath path)
~~~

フォーカスを移動する必要がなく、指定したノードがユーザに見える状態になるだけでよい場合は、以下のメソッドを使用します。このメソッドを使用する場合は、JTree が JScrollPane に含まれている必要があります。

~~~
void JTree#scrollPathToVisible(TreePath path)
~~~

上記のメソッドは、TreePath を引数に取りますが、ツリーノードを追加した直後は、追加した DefaultMutableTreeNode を選択状態にできると便利です。
以下のメソッドは、指定した DefaultMutableTreeNode を選択状態にします。

~~~ java
/**
 * Make sure the user can see the specified node.
 *
 * @param tree the tree that includes the node
 * @param node the node to be selected
 */
private void selectNode(JTree tree, DefaultMutableTreeNode node) {
    TreePath path = new TreePath(node.getPath());
    tree.setSelectionPath(path);
}
~~~

