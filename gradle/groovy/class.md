---
title: "Groovy でクラスを定義する"
date: "2015-07-08"
---

Groovy では、クラスやメソッドの可視性はデフォルトで `public` になります。
下記の例では、2 つのフィールドと、2 つのメソッドを持つ `Book` クラスを作成しています。
2 つのメソッドのうち 1 つは、`toString()` をオーバライドしていいます。

#### sample.groovy

```groovy
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
```

#### 実行例

```sh
$ groovy sample.groovy
Title1, [Author1, Author2]
```

上記の例では、`book.title` というプロパティに直接アクセスしているかのように見えますが、内部的には、自動的に生成された `book.getTitle()` という getter メソッドが呼び出されています。
同様に、`book.setTitle()` とい setter メソッドも自動的に生成されています。

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

