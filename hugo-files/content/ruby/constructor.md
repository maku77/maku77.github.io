---
title: "Rubyメモ: コンストラクタを定義する"
url: "p/wiegfcm/"
date: "2011-10-07"
tags: ["ruby"]
aliases: ["/ruby/constructor.html"]
---


コンストラクタの定義は initialize
----

Ruby のクラスでコンストラクタを定義したいときは、`initialize` というメソッドを使用します。
ここでインスタンス変数などを初期化します。

{{< code lang="ruby" title="クラスの定義" >}}
class Person
  def initialize(name)
    @name = name
  end
end
{{< /code >}}


インスタンスの生成は new
----

`initialize` メソッド自体は private メソッドとして定義されるので、インスタンスを作成するときは暗黙的に定義されている `new` メソッドを呼び出します。
`new` を呼び出すと、Ruby 内部では、初期化されていないオブジェクトが生成されて、`initialize` メソッドが呼び出されます。

{{< code lang="ruby" title="インスタンスの生成" >}}
p = Person.new("makkuma")
puts p.inspect    # => #<Person:0x10016a160 @name="makkuma">
{{< /code >}}

上記の例では、Object クラスに定義されている `inspect` メソッドを使ってオブジェクトの内容を取得しています。

