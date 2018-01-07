---
title: Swing - Container と JComponent と JFrame の関係
date: "2011-01-09"
---

クラス階層
----

~~~
java.awt.Container
  +-- javax.swing.JFrame （Swing のトップレベルコンテナ）
  +-- javax.swing.JDialog（Swing のトップレベルコンテナ）
  +-- javax.swing.JApplet（Swing のトップレベルコンテナ）
  +-- javax.swing.JComponent（各種 Swing コンポーネントの抽象基底クラス）
        +-- javax.swing.JPanel（レイアウトの入れ子を作るための箱になるクラス）
        +-- javax.swing.JButton（代表的なコンポーネントの一つ）
        +-- javax.swing.JLabel（代表的なコンポーネントの一つ）
        +-- javax.swing.JTextField（代表的なコンポーネントの一つ）
        +-- ...
~~~

java.awt.Container
----

Swing における GUI の構成要素は、すべて、`java.awt.Container`（コンテナ）のサブクラスであり、これらがツリー構造を成すことによって GUI が表現されています。
ボタンなどの各種 GUI コンポーネントを実際に配置していく対象となる上位コンテナは content pane と呼ばれており、この content pane への参照を取得するには、Swing のトップレベルコンテナである JFrame、JDialog、JApplet などが提供している `getContentPane()` メソッドを使用します。

~~~
Container RootPaneContainer#getContentPane()
~~~

JFrame、JDialog、JApplet はヘルパーメソッドとして `add`、`remove`、`setLayout` などのメソッドを提供しており、これらのメソッドを使用することで、content pane を間接的に操作することができるようになっています。
例えば、下記ように各種コンポーネントを直接配置していくことができます（`getContentPane()` で content pane を取得する必要がない）。

~~~ java
JFrame frame = new JFrame();
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

frame.setLayout(new FlowLayout());
frame.add(new JLabel("Press the button!"));
frame.add(new JButton("OK"));

frame.pack();
frame.setVisible(true);
~~~


javax.swing.JFrame / JDialog / JApplet
----

JFrame や JDialog、JApplet は、Swing のトップレベルコンテナと呼ばれています。
画面上に GUI を表示する場合は、少なくともこれらのいずれかをインスタンス化し、そこに各種コンポーネントを配置していくことになります。


javax.swing.JComponent
----

Swing の各種 GUI コンポーネント（ボタンなど）の基底クラスです。
カスタムコンポーネントを作成する場合にも基底クラスとして使用します。
一方で、Swing のトップレベルコンテナとなる JFrame、JDialog、JApplet などは JComponent を継承していません。


javax.swing.JPanel
----

JButton や JLabel などと同様に Swing コンポーネントを表現する JComponent のサブクラスですが、このクラスだけは特殊で、別の Swing コンポーネントを格納するための箱としての役割を果たします。
複数の JPanel オブジェクトに異なるレイアウトマネージャを設定し、それらを入れ子に配置することで様々なレイアウトを行うことができます。

JPanel の親クラスである JComponent を使っても、JComponent の入れ子構造を作れそうな気がしますが、JComponent は abstract class として定義されているので JPanel を使わないとインスタンス化できません。

