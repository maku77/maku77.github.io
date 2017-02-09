---
title: Swing - JList に動的に要素を追加／削除する
created: 2011-01-29
---

ListModel を利用した JList への要素の動的な追加
----

JList は要素の表示のために、内部的なデータモデルとして ListModel オブジェクトを使用します。
そのため、JList クラス自体には要素の追加、削除のためのメソッドは用意されていません。

下記は JList のデータとなる ListModel インタフェースです。

~~~ java
public interface ListModel<E> {
   int getSize()
   E getElementAt(int index);
   void addListDataListener(ListDataListener l)
   void removeListDataListener(ListDataListener l)
}
~~~

ここで注目すべきは、データの追加、削除のためのメソッドは定義されていないということです。
つまり、ListModel オブジェクトに対してデータを追加、削除するためのメソッドは自分で用意する必要があります。

~~~ java
public MyListModel implements ListModel {
    private Vector<MyData> dataList = new Vector<MyData>();

    public void addData(MyData data) {
        dataList.add(data);
        ...
    }
    ...
}
~~~

もうひとつ気にしなければいけないのは、ListModel オブジェクトに対して要素を追加した場合に、JList はそれをどうやって知り、表示を更新するのかということです。
このために使用されるのが、`ListModel.addListDataListener()` で追加される `ListDataListener` です。
JList は、設定された ListModel オブジェクトに対して、自分自身を ListDataListener として登録します。
これにより、JList は ListModel の変更を監視しています。

ListModel に対して要素が追加されたとき、削除したとき、変更したときは、ListModel が保持している全ての ListDataListener に対して、以下のような通知を行う必要があります。

~~~ java
public interface ListDataListener extends EventListener {
    void intervalAdded(ListDataEvent e);    // ListModel に要素を追加したときの通知
    void intervalRemoved(ListDataEvent e);  // ListModel の要素を削除したときの通知
    void contentsChanged(ListDataEvent e);  // ListModel の要素を変更したときの通知
}
~~~

よって、独自の ListModel に要素を追加するためのコードは、以下のような感じで実装することになります。

~~~ java
public MyListModel implements ListModel {
    private Vector<MyData> dataList = new Vector<MyData>();
    private Vector<ListDataListener> listeners = new Vector<ListDataListener>();

    public void addData(MyData data) {
        int index = dataList.size();
        dataList.add(data);

        // Notify listeners a change event.
        for (ListDataListener l : listeners) {
            ListDataEvent event = new ListDataEvent(this,
                    ListDataEvent.INTERVAL_ADDED, index, index);
            l.intervalAdded(event);
        }
    }

    void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    ...
}
~~~

このように ListDataListner への通知をちゃんと実装した ListModel に対して要素を追加すれば、JList の表示に自動的に反映されます。


AbstractListModel、DefaultListModel を使う
----

要素の動的な変更を可能にする ListModel を実装する場合は、要素を変更したあとに ListModel が保持しているすべての ListDataListener に対して変更通知を行う必要がありました。

ListModel の実装するときに、AbstractListModel を継承するようにすると、このあたりの実装を簡単に行うことができます。
AbstractListModel は以下の実装を提供してくれます。

- ListDateListener のリストの実体
- ListModel インタフェースの以下の実装
  - `void addListDataListener(ListDataListener l)`
  - `void removeListDataListener(ListDataListener l)`
- 要素変更時の ListDataListener への変更通知のためのショートカットメソッド
  - `void fireIntervalAdded(Object source, int index0, int index1)`
  - `void fireIntervalRemoved(Object source, int index0, int index1)`
  - `void fireContentsChanged(Object source, int index0, int index1)`

つまり、AbstractListModel を利用して ListModel を実装することにより、ListDataListener に関する部分の実装はほとんどしなくてよくなります。

~~~ java
public MyListModel extends AbstractListModel<MyData> {
    private Vector<MyData> dataList = new Vector<MyData>();

    public void addData(MyData data) {
        int index = dataList.size();
        dataList.add(data);
        fireIntervalAdded(this, index, index);
    }
    ...
}
~~~

さらに、ListModel で内部的に保持するデータのリストが Vector でよい場合は、この実装まで提供する DefaultListModel クラスを使用することができます。
DefaultListModel クラスは、内部で保持する Vector に対して要素を追加するためのメソッドを提供するので、そのままインスタンス化して使用することができます。
DefaultListModel は AbstractListModel を継承して実装されています。

#### 例: DefaultListModel の使用例

~~~ java
// javax.swing.DefaultListModel;

DefaultListModel<String> listModel = new DefaultListModel<String>();
listModel.addElement("AAA");
listModel.addElement("BBB");

// JList 作成後に要素を追加しても、動的に表示が更新される
JList list = new JList(model);
listModel.addElement("CCC");
listModel.addElement("DDD");
~~~

AbstractListModel と DefaultListModel は以下のように使い分ければよいでしょう。

- `AbstractListModel` -- すでに元になるデータコンテナが存在し、その内容を JList で表示したい場合。
- `DefaultListModel` -- 元になるデータコンテナが存在せず、動的に要素を追加して JList で表示したい場合。

例えば、JDBC でデータベースから取得するときに使用する ResultSet は一時的なオブジェクトなので、別の ArrayList か何かに取得内容を保存する必要があります。
このような場合は、DefaultListModel を使用すれば以下のように簡単に表示できます。

~~~ java
ResultSet resultSet = statement.executeQuery("SELECT name FROM mytable");
DefaultListModel<String> listModel = new DefaultListModel<String>();
while (resultSet.next()) {
    listModel.addElement(result.getString(1));
}
~~~

DefaultListModel に対して要素を追加、削除、変更するためのメソッドとして以下のようなものが用意されています。

- 要素の追加
  - `void addElement(E element)`
  - `void insertElementAt(E element, int index)`
- 要素の削除
  - `boolean removeElement(Object obj)`
  - `void removeElementAt(int index)`
  - `void removeAllElements()`
  - `void removeRange(int fromIndex, int toIndex)`
- 要素の変更
  - `void setElementAt(E element, int index)`

