---
title: JFace の CheckboxTreeViewer を使用する
created: 2005-07-27
---

JFace の CheckboxTreeViewer を使用すると、チェックボックス付きのツリーを表示することができます。


ツリーアイテムのチェックボックスのチェック状態が変更された時のイベント
----

ツリー要素のチェック状態の変化を監視するには、

```
CheckboxTreeViewer#addCheckStateListener(ICheckStateListener listener)
```

を使ってイベントリスナを登録します。

#### 実装例

~~~ java
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.widgets.Tree;

...(snip)...

private Tree tree = null;

public void test() {
    // CheckboxTreeViewer に ICheckStateListener を登録します
    CheckboxTreeViewer tv = new CheckboxTreeViewer(tree);
    tv.addCheckStateListener(new MyCheckStateListener());

    // ツリーに任意のデータをセットします
    tv.setLabelProvider(...);
    tv.setContentProvider(...);
    tv.setInput(...);
}

public class MyCheckStateListener implements ICheckStateListener {
    /**
     * ツリーノードのチェックボックスの状態が変更された時。
     */
    public void checkStateChanged(CheckStateChangedEvent e) {
        // チェックされたか、チェックが外されたかを取得
        boolean checked = e.getChecked();

        // チェック状態が変更されたノードを取得
        MyTreeNode = (MyTreeNode) e.getElement();
    }
}
~~~


チェックの付いたツリーアイテムをすべて取得する
----

CheckboxTreeViewer 上でチェックされている項目を取得するには下記のメソッドを使用します。

```
Object[] CheckboxTreeViewer#getCheckedElements()
```

#### 実装例

~~~ java
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.swt.widgets.Tree;

...(snip)...

private Tree tree = null;
private CheckboxTreeViewer treeViewer = null;

// ツリーに任意のデータをセットします
public void createTreeViewer() {
    treeViewer = new CheckboxTreeViewer(tree);
    treeViewer.setLabelProvider(...);
    treeViewer.setContentProvider(...);
    treeViewer.setInput(...);
}

// チェックが付いているノードを取得する
public void processCheckedElements() {
    Object[] objs = treeViewer.getCheckedElements();
    for (int i = 0; i < objs.length; ++i) {
        MyClass m = (MyClass) objs[i];
        // ここで m を使った処理
    }
}
~~~

