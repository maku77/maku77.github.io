---
title: 一時ディレクトリ／一時ファイルを使用する
created: 2015-12-08
---

一時ディレクトリ（テンポラリディレクトリ）
====

OS の一時ディレクトリのパスを取得する
----

`tmpdir` ライブラリが提供する `Dir.tmpdir` メソッドを使用すると、現在のシステム上で使用可能なテンポラリディレクトリのパスを取得することができます。

```ruby
require 'tmpdir'
puts Dir.tmpdir  #=> 'C:/Users/maku/AppData/Local/Temp'
```

一時ディレクトリを作成する
---

`Dir.mktmpdir` メソッドを使用すると、適切なパスにテンポラリディレクトリを作成し、そのパスを返してくれます。

```ruby
require 'tmpdir'
puts Dir.mktmpdir  #=> C:/Users/maku/AppData/Local/Temp/d20151208-8924-qjbjiq
```

ただし、上記のように単純に `Dir.mktmpdir` を呼び出してしまうと、後片付けをするためには自力でそのディレクトリを削除しなければいけません。
下記のようにブロックを渡してやると、そのブロックを抜けた時に自動的にテンポラリディレクトリを削除してくれます。

```ruby
require 'tmpdir'

Dir.mktmpdir do |dir|
  # dir の示すディレクトリの中で一時作業を行う
  puts dir
  $path = dir
end

puts Dir.exist?($path)  #=> false（きれいさっぱり消されている）
```


一時ファイル（テンポラリファイル）
====

`tempfile` ライブラリの提供する `Tempfile` クラスを使用すると、テンポラリファイルを簡単に作成することができます。
通常の `Tempfile.open` メソッドの戻り値は、通常の `File` オブジェクトと同じように扱うことができます。

```ruby
require 'tempfile'

file = Tempfile.open('foo')
begin
  # テンポラリファイルを使用した処理
  puts file.path  #=> 'C:/Users/maku/AppData/Local/Temp/foo20151208-7572-u08rd5'
  file.puts 'Hello'
ensure
  file.close
  file.unlink  # 必要なくなったら削除するのが望ましい
end
```

上記の例では `unlink` メソッドでテンポラリファイルを削除していますが、`unlink` せずに `close` だけしておくと、後から再び `open` して、テンポラリファイルに書き込んだ内容を読み出すことができます。

```ruby
require 'tempfile'

file = Tempfile.open('foo')

# テンポラリファイルに書き込む
begin
  file.puts 'AAA'
  file.puts 'BBB'
  file.puts 'CCC'
ensure
  file.close  # 閉じるだけで unlink はしない
end

# テンポラリファイルの内容を読み込む
file.open.each do |line|
  puts line
end
```

