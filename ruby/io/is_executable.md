---
title: "外部プログラムを実行可能か調べる／外部プログラムの絶対パスを取得する"
date: "2016-07-04"
---

コマンドが実行可能か調べる Which クラス
----

下記の `Which` クラスは、指定した任意のコマンド（`ruby` など）に対して下記のようなことを調べるメソッドを提供します。

* `get_absolute_path(command)` -- そのコマンドの絶対パスを調べる
* `command_exists?(command)` -- そのコマンドが実行可能か調べる

#### which.rb

```ruby
class Which
  def initialize()
    # PATH の通ったディレクトリのリスト
    @paths = ENV['PATH'].split(File::PATH_SEPARATOR)

    # OS がサポートしている拡張子のリスト
    @extensions = ['']
    @extensions += ENV['PATHEXT'].split(';') if ENV['PATHEXT']
  end

  # 指定したコマンドの絶対パスを調べる（見つからない場合は nil）
  def get_absolute_path(command)
    @paths.each do |path|
      @extensions.each do |ext|
        exe = File.join(path, "#{command}#{ext}")
        return exe if executable?(exe)
      end
    end
    return nil
  end

  # 指定したコマンドが実行可能か調べる（パスが通っているか調べる）
  def command_exists?(command)
    !!get_absolute_path(command)
  end

  private
    def executable?(path)
      File.executable?(path) && !File.directory?(path)
    end
end
```

下記は `Which` クラスの使用例です。`ruby`、`python`、`perl` コマンドの絶対パスと、それぞれのコマンドが実行可能かを調べています。


#### main.rb

```ruby
require_relative 'which'

which = Which.new
puts which.command_exists?('ruby')      #=> true
puts which.command_exists?('python')    #=> true
puts which.command_exists?('perl')      #=> false
puts which.get_absolute_path('ruby')    #=> 'C:\Ruby\bin\ruby.exe'
puts which.get_absolute_path('python')  #=> 'C:\Python35\python.exe'
puts which.get_absolute_path('perl')    #=> nil
```

Which クラスの実践的な使用例
----

下記は、任意のコマンドがある場合（ここでは `adb` コマンドが使用できる場合）にのみ処理を継続するというプログラムの例です。

#### main.rb

```ruby
require_relative 'which'

ADB = 'adb'

# `adb` コマンドが見つからない場合は警告表示して終了
if not Which.new.command_exists?(ADB)
  warn "Could not find '#{ADB}' command"
  exit 1
end

# 'adb' コマンドが見つかったので実際に実行して出力を取得
result = `#{ADB} get-serialno`
puts '===> ' + result
```

#### 実行結果

```
$ ruby main.rb
===> 8f9e98002130454
```

