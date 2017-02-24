---
title: JFace の TableViewer を使用してテーブルにデータを表示する
created: 2005-07-23
---

JFace の TableViewer で独自オブジェクトを表示する
----

TableViewer に表示するためのデータを保持するクラスで、ITableLabelProvider インタフェースを実装しておくと、そのデータをどのようにテーブルに表示するかを指示することができます。
ITableLabelProvider インスタンスは `TableViewer#setLabelProvider()` メソッドを使用してセットします。

ITableLabelProvider を利用してテーブルにデータを表示する手順は次のようになります。


### (1) ドメインオブジェクトを用意する

ここではサンプルとして、ウェブサイトのタイトルと URL を保持するドメインオブジェクトを作成します。

~~~ java
public class SiteInfo {
    public String title;
    public String url;

    public SiteInfo(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
~~~


### (2) ITableLabelProvider を実装する

ITableLabelProvider を実装することにより、独自のドメインオブジェクトをテーブルに表示する方法を定義します。
例えば、`ITableLabelProvider#getColumnText()` はテーブルの各セルに表示するテキストを返すように実装します。

~~~ java
private class SiteInfoLabelProvider implements ITableLabelProvider {
    // テキストの表示内容
    public String getColumnText(Object element, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return ((SiteInfo) element).title;
        case 1:
            return ((SiteInfo) element).url;
        }
        return null;
    }
    ...
}
~~~

第 1 引数の `element` で独自のドメインオブジェクトを受け取ります。
第 2 引数の `columnIndex` で列の番号が渡されます (0 base)。
`columnIndex` はテーブルに設定された列の数 (N) に応じて 0 〜 N-1 の値が渡されます（作成された TableColumn の数がテーブルの列数になります）。


### (3) TableViewer に ITableLabelProvider をセットする

~~~ java
TableViewer viewer = new TableViewer(table);
viewer.setLabelProvider(new SiteInfoLabelProvider());
~~~


### (4) テーブルにドメインオブジェクトを追加する

テーブルにドメインオブジェクトを追加する時は以下のメソッドを使用します。

- `TableViewer#add(Object obj)`
- `TableViewer#add(Object[] objArray)`

このメソッドを実行すると、`setLabelProvider` で設定した ITableLabelProvider のメソッドが呼び出され、テーブルの各要素が表示されます。

~~~ java
viewer.add(new SiteInfo("Google", "http://google.com/"));
viewer.add(new SiteInfo("Sun Microsystems", "http://sun.com/"));
~~~


### まとめ

以上のコードをまとめると次のようになります。

#### SiteInfo.java

~~~ java
/**
 * 独自のドメインオブジェクト（ウェブサイトの情報）
 */
public class SiteInfo {
    public String title;
    public String url;

    public SiteInfo(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
~~~

#### SiteInfoLabelProvider.java

~~~ java
/**
 * ドメインオブジェクト (SiteInfo) の表示方法を定義
 */
public class SiteInfoLabelProvider implements ITableLabelProvider {
    // テキストの表示内容
    public String getColumnText(Object element, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return ((SiteInfo) element).title;
        case 1:
            return ((SiteInfo) element).url;
        }
        return null;
    }

    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
    }
}
~~~

#### Main.java

~~~ java
public class Main {
    private Shell shell = null;
    private Table table = null;

    /**
     * テーブルの作成とデータの追加
     */
    private void createTable() {
        // テーブルオブジェクトを作成
        table = new Table(shell, SWT.NONE);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setBounds(new org.eclipse.swt.graphics.Rectangle(0,0,400,100));

        // テーブルのヘッダを作成
        TableColumn header1 = new TableColumn(table, SWT.RIGHT);
        header1.setText("Title");
        header1.setWidth(200);
        TableColumn header2 = new TableColumn(table, SWT.RIGHT);
        header2.setText("URL");
        header2.setWidth(200);

        // ITableLabelProvider を TableViewer にセット．
        TableViewer viewer = new TableViewer(table);
        viewer.setLabelProvider(new SiteInfoLabelProvider());

        // テーブルにデータを追加（ITableLabelProvider により表示される）
        viewer.add(new SiteInfo("Google", "http://google.com/"));
        viewer.add(new SiteInfo("Sun Microsystems", "http://sun.com/"));
    }

    ...(snip)...
}
~~~


下記は JFace のテーブルの操作に関するメモです。

JFace のテーブルの列にタイトル（ヘッダ）を表示する
----

テーブルの各列の見出しは、`TableColumn#setText` で設定できます。

~~~ java
// テーブルの列の見出しを設定
TableColumn tc1 = new TableColumn(table, SWT.CENTER);
TableColumn tc2 = new TableColumn(table, SWT.CENTER);
TableColumn tc3 = new TableColumn(table, SWT.CENTER);
tc1.setText("Header A");
tc2.setText("Header B");
tc3.setText("Header C");
tc1.setWidth(100);
tc2.setWidth(100);
tc3.setWidth(100);
~~~

TableColumn で列の見出しを設定しても、Table オブジェクトの `headerVisible` プロパティが `false` になっていると、見出し自体が表示されないので注意です。

~~~ java
// テーブルの列の見出しを可視化
table.setHeaderVisible(true);
~~~


JFace のテーブルにアイテムを設定する
----

テーブルにアイテムを追加する前に、テーブルの「列」の幅などを設定します。
列ごとに Tablecolumn を作成して設定します。

~~~ java
// テーブルの列のレイアウト設定
TableColumn tc1 = new TableColumn(table, SWT.RIGHT);
TableColumn tc2 = new TableColumn(table, SWT.RIGHT);
TableColumn tc3 = new TableColumn(table, SWT.RIGHT);
tc1.setWidth(100);
tc2.setWidth(100);
tc3.setWidth(100);
~~~

Table にアイテムを追加するには、TableItem を使用します。
TableItem は 1 行分のアイテムを表しています。

~~~ java
// Table に行を追加
TableItem item = new TableItem(table, SWT.NONE);
item.setText(0, "Item 1-A");
item.setText(1, "Item 1-B");
item.setText(2, "Item 1-C");

// 2 行目を追加
item = new TableItem(table, SWT.NONE);
item.setText(0, "Item 2-A");
item.setText(1, "Item 2-B");
item.setText(2, "Item 2-C");
~~~


JFace のテーブルでアイテムを区切る罫線を表示／非表示にする
----

~~~ java
// org.eclipse.swt.widgets.Table table;

table.setLinesVisible(true);   // 罫線を表示
table.setLinesVisible(false);  // 罫線を非表示
~~~

