---
title: ディレクトリ内のファイルを列挙する
created: 2010-05-07
---

ディレクトリ内のファイル／ディレクトリを列挙する（一階層のみ）
====

Dir.foreach を使用する方法
----
```ruby
Dir.foreach('.') do |item|
    puts item
end
```

カレントディレクトリを示す `.` や親ディレクトリを示す `..` が含まれてしまうので、これらを除いて列挙したい場合は、下記のように除外する必要があります。

```ruby
Dir.foreach('.') do |item|
    next if item == '.' or item == '..'
    puts item
end
```

Dir.open を使用する方法
----
```ruby
Dir.open('.') do |dir|
    for item in dir
        puts item
    end
end
```

この方法では、`Dir.foreach` を用いた場合と同様の結果が得られます。

Dir.glob を使用する方法
----
```ruby
Dir.glob('*') do |item|
    puts item
end
```

`Dir.glob` では、ファイルパターンを指定してファイルを列挙することができます。
列挙される要素には `.` や `..` などのディレクトリや、ドットで始まるファイルは含まれません。


下位ディレクトリを再帰的に列挙する
====

Dir.glob を使用する方法
----
ディレクトリ内のファイルとディレクトリを再帰的に列挙したい場合は、`Dir.glob` を使用すると簡単です。
`Dir.glob` の検索パターンに `**` を含めると、深い階層のディレクトリに含まれるファイルまで検索してくれます。
下記のようにすると、カレントディレクトリ以下のファイルをすべて列挙します（ただし、ドットで始まるファイルは含まれません）。

```ruby
Dir.glob('**/*') do |item|
    puts item
end
```


find.rb を使用する方法
----
Ruby に標準で付属している `find.rb` が提供する `Find.find` メソッドを使用してファイルを再帰的に列挙する方法もあります。

`Find.find` メソッドの結果には、親ディレクトリ (`..`) は含まれませんが、自分自身のディレクトリ (`.`) や、ドットファイルは含まれます。

```ruby
require 'find'

Find.find('.') do |item|
  puts item
end
```

下記は、少しだけ応用して、

* スクリプトの引数でディレクトリを指定
* ファイルのベース名だけを表示
* ファイルの種類をファイル名の後ろに表示

するようにしたサンプルです。

```ruby
require 'find'

path = ARGV[0] ? ARGV[0] : '.'
Find.find(path) do |item|
    base = File.basename(item)
    puts "#{base}\t#{File.ftype(item)}"
end
```

自力で再帰処理を記述する方法
----

自力でディレクトリ内のファイルを再帰的に列挙すると、次のような感じになります。

```ruby
def enum_files(dir_path)
    Dir.foreach(dir_path) do |x|
        next if x == '.' or x == '..'
        new_path = File.join(dir_path, x)
        if File.directory?(new_path) then
            enum_files(new_path)
        else
            puts new_path
        end
    end
end

enum_files('.')
```

上記の `enum_files` メソッドで、ブロックパラメータを使用できるようにするには以下のように `yield` を使用します。

```ruby
def enum_files(dir_path)
    Dir.foreach(dir_path) do |x|
        next if x == '.' or x == '..'
        new_path = File.join(dir_path, x)
        if File.directory?(new_path) then
            enum_files(new_path) {|x| yield x }
        else
            yield new_path
        end
    end
end

enum_files('.') do |x|
    puts x
end
```

