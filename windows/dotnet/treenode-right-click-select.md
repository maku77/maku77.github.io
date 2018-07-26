---
title: ".NET - ツリーノードを右クリックしたときにそのノードを選択状態にする"
date: "2006-07-01"
---

ツリーノードを右クリックしたときにそのノードを選択状態にするには、`TreeView` の `MouseDown` イベントをハンドルし、下記のようにします。

~~~ csharp
private void treeView1_MouseDown(object sender, MouseEventArgs e)
{
    if (e.Button == MouseButtons.Right) {
        treeView1.SelectedNode = treeView1.GetNodeAt(e.X, e.Y);
    }
}
~~~

