---
title: ".NET - ツリーノードのドラッグ＆ドロップの流れ"
date: "2006-07-01"
---

処理の流れ
----

TreeView の中のツリーノードをドラッグ＆ドロップするときの処理の流れは下記のようになります。

1. ツリーノードのドラッグを開始しようとすると `TreeView#ItemDrag` イベントが発生する。
2. このタイミングで `Control#DoDragDrop()` を呼び出してドラッグ＆ドロップ開始（`Control#DoDragDrop()` メソッドは、ドラッグ＆ドロップが終了するまでブロックします）。
3. ドロップ可能な TreeView コントロールの上にマウスカーソルが入ると、`Control#DragEnter` イベントが発生する。
4. ドロップすると、`Control#DragDrop` イベントが発生する。
5. `Control#DoDragDrop()` から戻る。


実装
----

### 1. ドラッグ開始時のイベントを追加＆実装

`TreeView#ItemDrag` イベントとして `ItemDragEventHandler`（イベント・ハンドラ）を追加します。

~~~ csharp
public Form1() {
    ...
    treeView1.ItemDrag += new System.Windows.Forms.ItemDragEventHandler(treeView1_ItemDrag);
    ...
}

// ツリーアイテムのドラッグを開始
private void treeView1_ItemDrag(object sender, ItemDragEventArgs e) {
    ((Control) sender).DoDragDrop(e.Item, DragDropEffects.Move);
}
~~~

ユーザが TreeView 上のツリーノードのドラッグを開始しようとするとき、`TreeView#ItemDrag` イベントが発生します。
ItemDragEventHandler デリゲートの 1 番目の引数 (`object sender`) には、ドラッグを開始させようとしたコントロールのオブジェクトが渡されます。
ここでは TreeView コントロールのオブジェクトが渡されます。
ItemDragEventHandler デリゲートの 2 番目の引数 (`ItemDragEventArgs e`) の `ItemDragEventArgs#Item` プロパティを参照することで、ドラッグしようとしている TreeNode を取得することができます。

この ItemDrag イベントが発生したタイミングで、`Control#DoDragDrop()` メソッドを呼び出すことで、実際にツリーアイテムのドラッグ＆ドロップの処理が開始されます。
`Control#DoDragDrop()` の 1 番目の引数では、どのアイテムをドラッグするかを指定します。
ここで指定したアイテムをドロップ先で取得できるようになります。
`Control#DoDragDrop()` の 2 番目の引数では、どのドラッグ操作を行えるかを決定します。
ここでは、ノードの移動を許可することを示す `DragDropEffects.Move` を指定しています。

ドラッグ開始の実装は以上で完了です。
ドラッグ開始の実装はシンプルですが、ちょっと面倒なのはドロップの実装の方です。


### 2. ドロップを受け入れる許可をする

コントロールに対してドロップを可能にするためには、`Control#AllowDrop` プロパティを True にしておく必要があります。
ここでは、TreeView コントロールへのドロップを許可します。

~~~ csharp
public Form1() {
    ...
    treeView1.AllowDrop = true;
    ...
}
~~~


### 3. ドロップ可能なアイテムだけを判別して受け入れるようにする

~~~ csharp
public Form1() {
    ...
    treeView1.DragEnter += new System.Windows.Forms.DragEventHandler(treeView1_DragEnter);
    ...
}

// アイテムをドラッグ中のマウスカーソルがドロップ可能なコントロールに入った
private void treeView1_DragEnter(object sender, DragEventArgs e) {
    if (e.Data.GetDataPresent(typeof(TreeNode))) {
        e.Effect = DragDropEffects.Move;
    }
    else {
        e.Effect = DragDropEffects.None;
    }
}
~~~

`Control#DoDragDrop()` によりドラッグを開始したアイテム（マウスカーソル）が、ドロップ可能なコントロール（`Control#AllowDrop` プロパティが True である Control）の領域に入ると、ドロップ先コントロールの `Control#DragEnter` イベントが発生します。

DragEventHandler の第 2 引数 (DragEventArgs) の `DragEventArgs#Data` プロパティを参照すると、このイベントに関連付けられているデータを含む IDataObject を取得できます。
ここでは `IDataObject#GetDataPresent()` メソッドを使用して、ドロップされたアイテムが TreeNode 型であるかどうかを判別しています。
TreeNode 型でない場合は、ドロップを禁止するために `DragEventArgs#Effect` プロパティを `DragDropEffects.None` に設定します。


### 4. ドロップしたときの処理を記述する

~~~ csharp
public Form1() {
    ...
    this.treeView1.DragDrop += new System.Windows.Forms.DragEventHandler(this.treeView1_DragDrop);
    ...
}

private void treeView1_DragDrop(object sender, DragEventArgs e) {
    // ドロップされたオブジェクトが TreeNode でなければ終了
    if (!e.Data.GetDataPresent(typeof(TreeNode))) {
        return;
    }

    // ドラッグ元の TreeNode 取得
    TreeNode srcNode = (TreeNode)e.Data.GetData(typeof(TreeNode));

    // ドラッグ先の TreeNode の取得
    Point pt = ((TreeView)sender).PointToClient(new Point(e.X, e.Y));
    TreeNode dstNode = ((TreeView)sender).GetNodeAt(pt);
}
~~~

ツリーノードをドロップすると、`Control#DragDrop` イベントが発生します。
`DragEventArgs#Data` プロパティを参照して IDataObject オブジェクトを取得し、`IDataObject#GetData()` メソッドによってドラッグ元の TreeNode オブジェクトを取得することができます。
ドロップ先の TreeNode は、イベント発生時のマウスカーソル位置などから求める必要があります。

ドラッグ元のオブジェクト、ドロップ先のオブジェクトが取得できたら、そこで任意の処理を行えば OK です。


ドラッグ＆ドロップ操作中に実行された最終的な効果を調べる
----

`Control#DoDragDrop` メソッドの戻り値を調べると、ドラッグ＆ドロップ操作中に実行された最終的な効果を表す DragDropEffects 列挙体の値を取得することができます。

~~~ csharp
private void treeView1_ItemDrag(object sender, ItemDragEventArgs e) {
    DragDropEffects result = ((Control) sender).DoDragDrop(e.Item, DragDropEffects.Move);
    MessageBox.Show(result.ToString());
}
~~~

例えば上記のように実装しておくと、ドラッグ＆ドロップ終了時に、その結果に応じて "Move" などの値がメッセージボックスで表示されます。

