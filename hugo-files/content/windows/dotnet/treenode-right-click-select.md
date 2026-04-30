---
title: "Windowsメモ: .NET - ツリーノードを右クリックしたときにそのノードを選択状態にする"
url: "p/7njuocg/"
date: "2006-07-01"
tags: ["windows"]
aliases: /windows/dotnet/treenode-right-click-select.html
---

ツリーノードを右クリックしたときにそのノードを選択状態にするには、`TreeView` の `MouseDown` イベントをハンドルし、下記のようにします。

```csharp
private void treeView1_MouseDown(object sender, MouseEventArgs e)
{
    if (e.Button == MouseButtons.Right) {
        treeView1.SelectedNode = treeView1.GetNodeAt(e.X, e.Y);
    }
}
```
