---
title: "コマンドライン引数によるオプションに対応する (optparse)"
date: "2016-04-18"
---

OptionParser の基本
----

`OptionParser` クラスを使用すると、`-a` や `--add` といった形式のコマンドラインオプションを簡単に扱うことができます。

`OptionParser` でハンドルすべきオプションは、下記の `on` メソッドで設定します。

```ruby
on(short, long, desc = "") {|v| ... }
on(short, desc = "") {|v| ... }
on(long, desc = "") {|v| ... }
```

`short` はショートオプションを表し、`-a` や `-d` のように指定します。
`long` はロングオプションを表し、`--add` や `--delete` のように指定します。
`desc` はそのオプションの説明文で、`--help` オプションを指定して実行した場合に表示される説明文を設定します。
後ろのブロックには、そのオプションが指定された場合に実行されるコードを記述します。
オプションごとのパラメータを受け取ることもできます。

すべてのオプションについて `on` メソッドによる定義を完了したら、`parse(ARGV)` を実行すれば、指定したオプションに一致するブロックが実行されていきます。


簡単なサンプル
----

下記の例では、`OptionParser` を使用して、`-a (--add)` オプションと、`-d (--delete)` オプションをハンドルしています。

```ruby
require 'optparse'

opt = OptionParser.new
opt.on('-a', '--add', 'add an item') { puts 'Added' }
opt.on('-d', '--del', 'delete an item') { puts 'Deleted' }
opt.parse(ARGV)
```

下記は、それぞれのオプションを指定してプログラムを実行したときの実行例です。

```
$ ruby sample.rb -a
Added

$ ruby samle.rb -d
Deleted

$ ruby sample.rb -a -d
Added
Deleted
```

`OptionParser` を使用すると、デフォルトで `-h (--help)` オプションが定義され、`on` メソッドの `desc` オプションで指定した説明文が表示されます。

```
$ ruby sample.rb --help
Usage: sample [options]
    -a, --add                        add an item
    -d, --del                        delete an item
```


オプションごとにパラメータを受け取れるようにする
----

`on` メソッドでオプション名を指定するときに、その後ろにパラメータ名を指定すると、そのオプションにはパラメータ指定が必要であることを示すことができます。

```ruby
require 'optparse'

opt = OptionParser.new
opt.on('-a', '--add ITEM', 'add an item') { |v| puts "Add #{v}" }
opt.on('-d', '--del ITEM', 'delete an item') { |v| puts "Delete #{v}" }
opt.parse(ARGV)
```

次のようにオプションごとにパラメータを受け取ることができるようになります。
ロングオプションのパラメータの前は、スペースで区切っても、`=` で区切っても構いません。

```
$ ruby sample.rb -a 100
Add 100

$ ruby sample.rb --add 200
Add 200

$ ruby sample.rb --del=300
Delete 300
```

この例のように、各オプションに対してパラメータを単純に追記すると、そのパラメータ指定は必須だとみなされます。
例えば、下記のようにパラメータ指定を忘れるとエラーが発生します。

```
$ ruby sample.rb -d
sample.rb:6:in `<main>': missing argument: -d (OptionParser::MissingArgument)
```


オプションのパラメータを省略可能にする
----

オプションごとのパラメータ名を `[` で始めると、そのパラメータは省略可能になります。

```ruby
require 'optparse'

opt = OptionParser.new
opt.on('-a', '--add [ITEM]', 'add an item') do |val|
  if val != nil
    puts "Add #{val}"
  else
    puts "Add something"
  end
end
opt.parse(ARGV)
```

下記のように、パラメータを省略して実行してもエラーが発生しなくなります。

```
$ ruby sample.rb --add 100
Add 100

$ ruby sample.rb --add
Add something
```


ヘルプオプション ('-h', '--help') の説明を表示する
----

`OptionParser` を使用すると、自動的に `-h` と `--help` オプションが定義されるのですが、それによるヘルプメッセージには `-h` と `--help` オプションの説明が表示されません。

```
$ ruby sample.rb --help
Usage: sample [options]
    -a, --add                        add an item
```

ヘルプメッセージに `-h` と `--help` の説明も表示するには、他のオプションと同じように `on` メソッドでオプションを定義します。
これらのハンドラの中では、`OptionParser` オブジェクト自体を出力するようにすると、ヘルプメッセージを表示することができます。

```ruby
require 'optparse'

opt = OptionParser.new
opt.on('-a', '--add', 'add an item') { puts "Add" }
opt.on('-h', '--help', 'show this help') { puts opt; exit }
opt.parse(ARGV)
```

```
$ ruby sample.rb --help
Usage: sample [options]
    -a, --add                        add an item
    -h, --help                       show this help
```

オプションではない引数も扱う
----

例えば、下記のようにコマンドライン引数を指定したとします。

```
$ ruby sample.rb -n 100 hoge
```

最後に指定した `hoge` という引数は、オプションのパラメータとしてはみなされないので、`OptionParser#on('-n' ...)` によるハンドラの中で取得することができません。
このような場合は、`OptionParser#parse!` を使用してコマンドライン引数をパースすることで、`ARGV` の中に `hoge` だけが残るようにします。

```ruby
require 'optparse'

opt = OptionParser.new
opt.on('-n VALUE', 'do something') { |v| puts "do something with #{v}" }
opt.parse!(ARGV)

# ARGV の中にオプションとしてパースされなかった残りの引数が残っている
puts "ARGV = #{ARGV}"
```

```
$ ruby sample.rb -n 100 hoge
do something with 100
ARGV = ["hoge"]
```


オプションの取得部分をクラス化する
----

`OptionParser` クラスを使用して、そのアプリケーションに必要なコマンドライン引数をパースするためのクラスを作成しておくと、オプションの扱うコードの見通しがよくなります。
下記の例では、内部的に `OptionParser` を使用するクラス (`AppOption`) を作成しています。

```ruby
class AppOption
  require 'optparse'

  # インスタンス化と同時にコマンドライン引数をパース
  def initialize
    @options = {}
    OptionParser.new do |o|
      o.on('-a', '--add ITEM [OPT]', 'add item') { |v| @options[:add] = v }
      o.on('-d', '--delete ITEM', 'delete item') { |v| @options[:del] = v }
      o.on('-h', '--help', 'show this help') {|v| puts o; exit }
      o.parse!(ARGV)
    end
  end

  # オプションが指定されたかどうか
  def has?(name)
    @options.include?(name)
  end

  # オプションごとのパラメータを取得
  def get(name)
    @options[name]
  end

  # オプションパース後に残った部分を取得
  def get_extras
    ARGV
  end
end

# 使用例
option = AppOption.new
if option.has?(:add)
  puts option.get(:add)   # --add オプションのパラメータを取得
  puts option.get_extras  # 残りのコマンドライン引数を取得
end
```

#### 実行結果

```
$ ruby sample.rb --add AAA BBB
AAA
BBB
```

