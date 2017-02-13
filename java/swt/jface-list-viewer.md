---
title: JFace - JFace の ListViewer を使用する
created: 2010-10-17
---

本格的なことをやるなら List じゃなくて ListViewer
----

SWT/JFace で使用できるリストコンポーネントには、List (SWT) と ListViewer (JFace) があります。

- SWT: `org.eclipse.swt.widgets.List`
- JFace: `org.eclipse.jface.viewers.ListViewer`

基本的には、Tree と TreeViewer の関係と同じで、List が単純に要素を追加するだけの機能しかないのに比べ、ListViewer は MVC アーキテクチャに基いた設計が可能です。

ListViewer に要素を追加する場合には、データを１つずつ `add` していくのではなく、IStructuredContentProvider インタフェースを実装したオブジェクトとしてまとめて渡します（何度も `add` を呼ぶ必要がない）。
多くの場合、独自クラスのオブジェクトを配列、または ArrayList として渡すことになります。

また、ListViwer は TreeViewer などと同様に、表示内容を定義するために ILabelProvider インタフェースをセットすることができます。
セットしない場合は、`IStructuredContentProvider#getElements()` の返すオブジェクトの `toString()` が使われることになります。

~~~ java
org.eclipse.jface.viewers.ILabelProvider              // 表示方法の定義
org.eclipse.jface.viewers.IStructuredContentProvider  // 保持するデータの定義
~~~

単純にデータの一覧を表示するくらいのことなら List クラスでも十分ですが、少し本格的なことをやろうとすると ListViewer を使わなければいけません。
まず、List クラスには、各要素に対応する任意のオブジェクトを割り当てることができないのがイタイです。
ある要素を選択したときに、選択したインデックスか、その要素の文字列くらいしか取得できません。
これに比べ、ListViwer には以下のような、指定したインデックスに対応するオブジェクトを取得するメソッドがあります。

~~~
Object ListViewer#getElementAt(int index)
~~~

また、List クラスがサブクラス化できないのに対し、ListViewer はサブクラス化が可能なので、ListViewer オブジェクトから独自クラスのオブジェクトを取得できるようにカスタマイズすることができます。


ListView を使ってみる
----

ここでは、独自クラスである Person クラスのオブジェクトを ListViewer で表示してみます。
MVC の M (Model) に当たる部分です。

#### Person.java

~~~ java
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}
~~~

独自のオブジェクトを ListViewer に渡せるようにするには、ListViewer に対して、そのクラスを扱えるようにするための IStructuredContentProvider をセットする必要があります。
ちなみに、TreeViewer の場合には、ITreeContentProvider でしたが、それと比べると IStructuredContentProvider で実装しなければならないメソッドは少ないです。

Object[] getElements(Object inputElement)
: リストに表示するオブジェクトの配列を返すように実装する。パラメータには、`ListView#setInput()` で渡されたオブジェクトが渡される。

void dispose()
: この ContentProvider で割り当てたリソースを破棄するように実装する。ListView が破棄されるときに呼び出される。破棄するリソースが何もない場合は、実装は空でよい。

void inputChanged(Viewer viewer, Object oldInput, Object newInput)
: `ListView#setInput()` で ListView にセットされたデータが変更されるときに呼び出される。最初に `setInput()` を呼び出した際にも呼び出される。このとき、`oldInput` は `null` になり、`newInput` には `setInput()` に渡したデータと同じものが渡される。このメソッドは ListView が破棄される直前にも呼び出される。このとき、`newInput` は `null` になる。データが変更されたときにすることが何もない場合は、実装は空でよい。

ここでは、ListViewer に Person オブジェクトの配列を渡すとして IStructuredContentProvider を実装します。

#### PersonContentProvider.java

~~~ java
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PersonContentProvider implements IStructuredContentProvider {
    @Override
    public Object[] getElements(Object inputElement) {
        Person[] persons = (Person[]) inputElement;
        return persons;
    }

    @Override
    public void dispose() {}

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
}
~~~

次に、Person オブジェクトの内容をどのように表示するかを定義するための ILabelProvider を実装します。
ILabelProvider にはインタフェースがたくさんあるので、ここではアダプタクラスである LabelProvider を継承して、`getText()` メソッドだけを定義することにします。

#### PersonLabelProvider.java

~~~ java
import org.eclipse.jface.viewers.LabelProvider;

public class PersonLabelProvider extends LabelProvider {
    @Override
    public String getText(Object element) {
        Person p = (Person) element;
        return p.getName() + " (" + p.getAge() + ")";
    }
}
~~~

これで Person 配列を ListViewer にセットするための PersonContentProvider と PersonLabelProvider ができたので、あとは以下のように実行するだけです。
データを扱う部分や、表示のためのロジックが別クラスに分離されているので、メインコードはとてもすっきりします。

#### MyComposite.java

~~~ java
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class MyComposite extends Composite {
    MyComposite(Composite parent) {
        super(parent, SWT.NONE);
        addComponents();
    }

    private void addComponents() {
        setLayout(new FillLayout(SWT.HORIZONTAL));

        // ListViewer に表示するデータ
        Person[] persons = new Person[] {
                new Person("Yabuki", 10),
                new Person("Nishi", 20),
                new Person("Tange", 30),
        };

        // ListViewer を作成して上記のデータをセット
        ListViewer listViewer = new ListViewer(this, SWT.SINGLE);
        listViewer.setContentProvider(new PersonContentProvider());
        listViewer.setLabelProvider(new PersonLabelProvider());
        listViewer.setInput(persons);
    }
}
~~~

