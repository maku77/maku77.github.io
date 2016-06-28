---
title: Visitor パターン
created: 2011-04-10
---

Visitor パターンの特徴
====

* インタフェースの異なるオブジェクトを巡回することができる。Visitor オブジェクトは、各種要素を巡回しながら目的のデータを構築する。
* 巡回対象のクラスのカプセル化が破られることがある。
* 若干複雑。
* 単純なポリモーフィズムによる巡回ができる場合は、このパターンを使う必要はない。

Visitor パターンを使わない場合
====

例えば、HTML のノードリストを順番に処理しながら HTML テキストを構築することを考えてみます。
各ノード（テキストノードやリンクノードなど）を表すクラスは、保持するデータやインタフェースが異なるため、深く考えずに実装してしまうと次のような ```instanceof``` とキャストの組み合わせコードができてしまいます。

```java
public String extractHtmlText() {
    StringBuilder results = new StringBuilder();
    Node node = null;
    while ((node = nodeList.next()) != null) {
        if (node instanceof StringNode) {
            StringNode n = (StringNode) node;
            results.append("<P>");
            results.append(n.getText());
            results.append("</P>");
        } else if (node instanceof LinkNode) {
            LinkNode n = (LinkNode) node;
            results.append("<A href='"> + n.getUri() + "'>");
            results.append(n.getLinkName());
            results.append("</A>");
        } else if (...) {
            ...
        }
    }
    return results.toString();
}
```

各種ノードに、以下のような HTML テキストを返すための共通のインタフェースを用意すれば、```instanceof``` やキャストを行わずにポリモーフィックに ```Node``` を処理できると考えるかもしれません。

```java
public interface Node {
    public String getHtmlText();
}
```

しかし、```Node``` インタフェースにこのような出力メソッドの実装を強要してしまうと、各 ```Node``` サブクラスが HTML 出力のための知識を持つことになってしまいます。
HTML 意外の出力形式、例えば Latex に対応したくなったらどうしましょう？

```java
public interface Node {
    public String getHtmlText();
    public String getLatexText();
}
```

こういった追加があるごとに、全ての ```Node``` サブクラスを変更することになってしまいます。
```Node``` クラスには、本来のデータ提供のための責務を果たすのに専念してもらうべきで、いろんなフォーマットに対応した出力の機能は持たせたくありません。
できれば HTML や Latex 出力のためのコードは、```Node``` クラスから切り離された別のクラスで行いたいのです。

Visitor パターンを使う場合
====

Visitor クラスは、各要素を順番に処理し、任意のデータを構築します。

例えば、各種 ```Node``` サブクラスを処理することができる Visitor は以下のようなインタフェースで定義します。
各種 ```Node``` サブクラスごとに、その型に特化したメソッドを1つずつ用意します。
例えば、```StringNode``` のために ```visitStringNode(...)``` を用意します。

```java
public interface NodeVisitor {
    public void visitStringNode(StringNode node);
    public void visitLinkNode(LinkNode node);
    ...
}
```

```visitStringNode()``` メソッドは、```StringNode``` クラスから呼び出され、```visitLinkNode()``` メソッドは、```LinkNode``` クラスから呼び出されることになります。

Visitor パターンでは、各要素をポリモーフィックに処理できるようにするため、各要素が Visitor オブジェクトを受け取る共通の ```accept``` メソッドを実装します。

```java
public interface Node {
    public void accept(NodeVisitor visitor);
}
```

そして、各要素の ```accept``` メソッドの実装の中で、自分自身を処理してもらうように Visitor クラスに処理を委譲します。
ここで、自分自身の型とマッチする Visitor クラスのメソッドを呼び出すようにするのがポイントです。
ここが Visitor パターンの肝です。

```StringNode``` クラスは、自分自身の型が ```StringNode``` であると分かっているので、```Node``` オブジェクトからの ```StringNode``` オブジェクトへのキャスト処理が必要なく、また、適切な ```visitStringNode()``` メソッドを呼び出すことができます。

```java
public StringNode implements Node {
    ...
    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitStringNode(this);
    }
}
```

Visitor クラス側の実装では、すべての ```Node``` オブジェクトの ```accept``` メソッドを、自分自身をパラメータとして順番に呼び出していきます。
すると、各 ```Node``` オブジェクトから、```visitStringNode``` や ```visitLinkNode``` などの具体的な型に依存したメソッドが呼び出されるので、この中で目的のデータを構築していきます。

```java
public class HtmlTextExtractor implements NodeVisitor {
    private StringBuilder results;

    public String extractHtmlText() {
        results = new StringBuilder();
        Node node = null;
        while ((node = nodeList.next()) != null) {
            node.accept(this);
        }
        return results.toString();
    }

    @Override
    public void visitStringNode(StringNode node) {
        results.append("<P>");
        results.append(node.getText());
        results.append("</P>");
    }

    @Override
    public void visitLinkNode(LinkNode node) {
        results.append("<A href='"> + node.getUri() + "'>");
        results.append(node.getLinkName());
        results.append("</A>");
    }

    ...
}
```

Visitor クラスが各要素の ```accept``` メソッドを呼び出し、呼び出された各要素側の ```accept``` 内では、Visitor オブジェクトの適切なメソッドを呼び返しています。このように二段階で実際に呼び出されるメソッドが決定することを「ダブル・ディスパッチ」と呼びます。

上記は、HTML テキストを構築するための具象 Visitor クラスの実装になっていますが、もし、HTML 以外に Latex 形式でも出力したくなった場合は、
このような具象 Visitor クラスをもうひとつ用意するだけで済みます。
各 ```Node``` クラスに手をいれる必要はないのです。

参考文献
====

* 『Design Patterns: Elements of Reusable Object-Oriented Software』
* 『アジャイルソフトウェア開発の奥義』
* 『Agile Software Development, Principles, Patterns, and Practices』
* 『パターン思考リファクタリング入門』第10章 累積処理

