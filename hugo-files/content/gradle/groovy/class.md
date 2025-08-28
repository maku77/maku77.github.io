---
title: "Groovy でクラスを定義する"
url: "p/j5bvcq9/"
date: "2015-07-08"
tags: ["gradle, groovy"]
aliases: ["/gradle/groovy/class.html"]
---

クラスを定義する
----

Groovy では、クラスやメソッドの可視性はデフォルトで public になります。
下記の `Book` クラスは `title` と `authors` フィールドを持っており、両方とも外部から参照できます。

{{< code lang="groovy" title="sample.groovy" >}}
class Book {
    def title
    def authors = []
    def getLabel() {
        title + ', ' + authors
    }
    String toString() {
        getLabel()
    }
}

def book = new Book()
book.title = 'Title1'
book.authors << 'Author1' << 'Author2' << 'Author3'
println book
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ groovy sample.groovy
Title1, [Author1, Author2, Author3]
{{< /code >}}

上記の例では、`book.title` というプロパティに直接アクセスしているかのように見えますが、内部的には、自動的に生成された `book.getTitle()` という getter メソッドが呼び出されています。
同様に、`book.setTitle()` という setter メソッドも自動的に生成されています。

つまり、下記の 2 つのコードは同じ動作をします。

```groovy
book.title = 'Title1'
println book.title
```

```groovy
book.setTitle('Title1')
println book.getTitle()
```

名前付きパラメータによるコンストラクタ呼び出し
----

コンストラクタを呼び出すときに、名前付きパラメータを指定することで、任意のプロパティを初期化することができます。

```groovy
class Book {
    def title
    def authors
}

def book = new Book(title:'Title1', authors:['Author1', 'Author2'])
println book.authors
```

