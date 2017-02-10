---
title: Swing - JTree でノードを選択したときのイベントをハンドルする
created: 2011-02-12
---

JTree の各ノードが選択されたときのイベントをハンドルするには、TreeSelectionListner を設定します。

以下の例では、JTree のノードとして DefaultMutableTreeNode を設定してあると仮定して、選択されたノードの情報を取得しています。
例えば、DefaultMutableTreeNode のコンストラクタで Book オブジェクトをユーザーオブジェクトとして指定しておけば、以下のように `getUserObject()` メソッドを使用して取り出すことができます。

~~~ java
// final JTree tree = new JTree(createNodes());

tree.addTreeSelectionListener(new TreeSelectionListener() {
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null) {
            // Nothing is selected.
            return;
        }
        if (node.isLeaf()) {
            Book book = (Book) node.getUserObject();
            System.out.println(book.getTitle());
        }
    }
});
~~~

