---
title: Swing - JTree で選択されているノードを取得する
created: 2011-02-12
---

選択中のノードに対して、新しいノードを追加したいことがあります。
以下のメソッドは、JTree で選択中のノード（DefaultMutableTreeNode オブジェクト）を取得します。
どのノードも選択されていない場合は、ルートノードを返します。

~~~ java
/**
 * Returns the selected node of the tree.
 *
 * @return the selected node, returns the root node if there is no selection
 */
private DefaultMutableTreeNode getSelectedNode(JTree tree) {
    TreePath path = tree.getSelectionPath();
    if (path == null) {
        return (DefaultMutableTreeNode) tree.getModel().getRoot();
    }
    return (DefaultMutableTreeNode) path.getLastPathComponent();
}
~~~

こちらの実装でも OK。

~~~ java
private DefaultMutableTreeNode getSelectedNode(JTree tree) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    if (node != null) {
        return node;
    }
    return (DefaultMutableTreeNode) tree.getModel().getRoot();
}
~~~

