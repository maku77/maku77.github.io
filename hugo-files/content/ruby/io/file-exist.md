---
title: "Rubyメモ: ファイルやディレクトリが存在するか調べる (File.exist?, Dir.exist?)"
url: "p/dnav4hc/"
date: "2019-02-12"
tags: ["ruby"]
aliases: ["/ruby/io/file-exist.html"]
---

## ファイル・ディレクトリの有無を調べる (File.exist?)

指定したパスのファイルやディレクトリが存在するかどうかを調べるには、[File.exist?(path)](https://docs.ruby-lang.org/ja/latest/method/File/s/exist=3f.html) メソッドを使用します。

{{< code lang="ruby" title="sample というファイル（あるいはディレクトリ）の有無を調べる" >}}
if File.exist?('sample')
  puts 'sample が見つかりました'
end
{{< /code >}}

ディレクトリの確認もまったく同様ですが、`File.exist?` では、ファイルとディレクトリの区別ができないことに注意してください。
`sample` というファイルがある場合も、`sample` というディレクトリがある場合も、`File.exist?('sample')` は `true` を返します。

## ファイルとディレクトリを区別して有無を調べる (File.file?, File.directory?, Dir.exist?)

`File.exist?` はファイルとディレクトリを区別せずに、指定したパスにファイルかディレクトリが存在しているかを調べるメソッドでした。
ファイルとディレクトリをちゃんと区別して、ファイルの有無やディレクトリの有無をチェックするには、[File.file?(path)](https://docs.ruby-lang.org/ja/latest/method/File/s/file=3f.html) や [File.directory?](https://docs.ruby-lang.org/ja/latest/method/File/s/directory=3f.html)（あるいは [Dir.exist?(path)](https://docs.ruby-lang.org/ja/latest/method/Dir/s/exist=3f.html)）を使用します。

{{< code lang="ruby" title="ファイルの有無を調べる" >}}
File.file?('/bin/bash')      # => true
File.file?('/bin')           # => false
File.file?('/no_such_file')  # => false
{{< /code >}}

{{< code lang="ruby" title="ディレクトリの有無を調べる" >}}
File.directory?('.')            #=> true
File.directory?('/etc')         #=> true
File.directory?('/etc/passwd')  #=> false
{{< /code >}}

## パス内で特殊文字 (~) を使用する場合

ファイルやディレクトリの有無を確認するときのパスには、相対パスや絶対パスを指定できますが、下記のようにホームディレクトリを示す `~` を含める場合は、あらかじめ `File.expand_path` でパスを展開しておく必要があります。

{{< code lang="ruby" title="ホームディレクトリ以下の temp ディレクトリの有無を調べる" >}}
if Dir.exist?(File.expand_path('~/temp'))
  puts 'ホームディレクトリに temp ディレクトリが見つかりました'
end
{{< /code >}}
