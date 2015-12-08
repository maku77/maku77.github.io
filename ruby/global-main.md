---
title: グローバルスコープな実行コードを記述しない
created: 2015-12-08
---

下記は、`greet` メソッドだけを持つ簡単なクラスです。

#### hello.rb

```ruby
class Hello
  def greet; puts 'Hello!'; end
end

obj = Hello.new
obj.greet
```

一見何の問題もないように見えますが、グローバルなスコープに実行可能なメインロジックが含まれているため、下記のような問題があります。

* 他のコードからこのクラスを `require_once` で読み込んだときにメインロジックが実行されてしまう
* ユニットテストコードからこのクラスを読み込んだ場合もメインロジックが実行されてしまう

これを防ぐには、下記のようにメインロジックを `if $0 == __FILE__` という条件で囲んでやります。

```ruby
class Hello
  def greet; puts 'Hello!'; end
end

if $0 == __FILE__
  obj = Hello.new
  obj.greet
end
```

こうしておくと、`ruby hello.rb` としたときだけメインロジックが実行され、別のファイルから `require_relative` で読み込まれた場合には実行されないようになります。
`$0` の値は `ruby` コマンドに渡したファイル名になり、`__FILE__` の値は記述されているスクリプトファイル自身の名前になるため、上記のようにしておけば、別ファイル経由で `require_relative` した場合に、条件式が一致しなくなるという仕組みです。

| 実行するコマンド | `$0` の値  | `__FILE__` の値 |
| ---------------- | -------- | ------------- |
| `ruby hello.rb`  | hello.rb | hello.rb      |
| `ruby other.rb`  | other.rb | hello.rb      |

Ruby だけでなく、Perl や Python など、他のスクリプト言語でもほぼ同様の書き方をします。

