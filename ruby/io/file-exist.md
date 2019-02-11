---
title: "ファイルやディレクトリが存在するか調べる (File.exist?, Dir.exist?)"
date: "2019-02-12"
---

ファイル・ディレクトリの有無を調べる (File.exist?)
----

指定したパスのファイルやディレクトリが存在するかどうかを調べるには、[File.exist?(path)](https://docs.ruby-lang.org/ja/latest/method/File/s/exist=3f.html) メソッドを使用します。

#### sample というファイル（あるいはディレクトリ）の有無を調べる

~~~ ruby
if File.exist?('sample')
  puts 'sample が見つかりました'
end
~~~

ディレクトリの確認もまったく同様ですが、`File.exist?` では、ファイルとディレクトリの区別ができないことに注意してください。
`sample` というファイルがある場合も、`sample` というディレクトリがある場合も、`File.exist?('sample')` は `true` を返します。


ファイルとディレクトリを区別して有無を調べる (File.file?, File.directory?, Dir.exist?)
----

`File.exist?` はファイルとディレクトリを区別せずに、指定したパスにファイルかディレクトリが存在しているかを調べるメソッドでした。
ファイルとディレクトリをちゃんと区別して、ファイルの有無やディレクトリの有無をチェックするには、[File.file?(path)](https://docs.ruby-lang.org/ja/latest/method/File/s/file=3f.html) や [File.directory?](https://docs.ruby-lang.org/ja/latest/method/File/s/directory=3f.html)（あるいは [Dir.exist?(path)](https://docs.ruby-lang.org/ja/latest/method/Dir/s/exist=3f.html)）を使用します。

#### ファイルの有無を調べる

~~~ ruby
File.file?('/bin/bash')      # => true
File.file?('/bin')           # => false
File.file?('/no_such_file')  # => false
~~~

#### ディレクトリの有無を調べる

~~~ ruby
File.directory?('.')            #=> true
File.directory?('/etc')         #=> true
File.directory?('/etc/passwd')  #=> false
~~~


パス内で特殊文字 (~) を使用する場合
----

ファイルやディレクトリの有無を確認するときのパスには、相対パスや絶対パスを指定することができますが、下記のようにホームディレクトリを示す `~` を含める場合は、あらかじめ `File.expand_path` でパスを展開しておく必要があります。

#### ホームディレクトリ以下の temp ディレクトリの有無を調べる

~~~ ruby
if Dir.exist?(File.expand_path('~/temp'))
  puts 'ホームディレクトリに temp ディレクトリが見つかりました'
end
~~~

