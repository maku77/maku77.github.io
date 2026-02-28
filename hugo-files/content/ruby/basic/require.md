---
title: "Rubyメモ: require で他のライブラリを読み込む"
url: "p/f49nx8n/"
date: "2015-11-21"
tags: ["ruby"]
aliases: ["/ruby/basic/require.html"]
---

`require` メソッドを使用すると、システムにインストールされたライブラリや、相対パス指定でライブラリを読み込むことができます。

システムにインストールされたライブラリを読み込む (require)
----

下記は、システムにインストールされたライブラリを読み込む例です。
この構文では、組み込み変数の `$LOAD_PATH` に設定されたディレクトリ以下にインストールされたライブラリをロードできます。
読み込むファイルの拡張子 `.rb` は省略することができます。

```ruby
require 'sample'
```

参考: [ユーザライブラリの検索パスを調べる](/p/pgsu6vb/)


相対パスでライブラリを読み込む (require_relative)
----

例えば、下記のような `greet` メソッドを提供するライブラリがあるとします。

{{< code lang="ruby" title="mylib.rb" >}}
def greet
  puts 'Hello!'
end
{{< /code >}}

同じディレクトリにある別のスクリプトから、この `mylib.rb` というライブラリを読み込んで使用するには、以下のように `require_relative` メソッドでライブラリ名を指定します。


{{< code lang="ruby" title="sample.rb" >}}
require_relative 'mylib'   # 同じディレクトリ内の mylib.rb を読み込む
greet  # 上記のライブラリが提供する関数の呼び出し
{{< /code >}}

{{< code title="実行結果" >}}
$ ruby sample.rb
Hello!
{{< /code >}}

もちろん、上位のディレクトリのファイルや、下位のディレクトリのファイルも読み込むことができます。

```ruby
require_relative '../mylib'
require_relative 'foo/mylib'
```

### コラム: Ruby 1.8 以前の場合

Ruby 1.8 以前には、`require_relative` メソッドがなかったため、`require` メソッドを使用して、相対パスによるライブラリのロードなどを行っていました。
その際に、ありがちな間違いは、下記のように記述してしまうことです。

```ruby
require './mylib'
```

上記のようなパス指定をすると、そのファイルからの相対パスではなく、`ruby` コマンドを実行したディレクトリからの相対パスという意味になってしまうため、実行するディレクトリによって動作が変わってしまいます。
これを防ぐためには、下記のように記述しなければいけませんでした。

```ruby
require File.join(File.dirname(__FILE__), 'mylib')
```

Ruby 1.9 以降では、`require_relative` が使用できるため、このような面倒な記述は必要なくなりました。

