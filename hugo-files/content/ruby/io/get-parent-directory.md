---
title: "Rubyメモ: 親ディレクトリのパスを取得する"
url: "p/xqwzqn4/"
date: "2017-08-07"
tags: ["ruby"]
aliases: ["/ruby/io/get-parent-directory.html"]
---

`Dir::pwd` を使用すると、カレントディレクトリの絶対パスを取得できます。
取得したディレクトリパスを `File::dirname` に渡すと、カレントディレクトリの親ディレクトリを取得できます。

例えば、カレントディレクトリが `C:\Users\maku\sample` であるときに、下記を実行すると、`C:\Users\maku` が表示されます。

```ruby
puts File::dirname(Dir::pwd)
```

下記のサンプルでは、カレントディレクトリから上位のディレクトリを順番にたどりながら出力しています。

{{< code lang="ruby" title="sample.rb" >}}
path = Dir::pwd
while true
  puts path
  temp = File::dirname(path)
  break if path == temp
  path = temp
end
{{< /code >}}

{{< code title="実行例（D:\\users\\maku\\sample ディレクトリ内から実行した場合）" >}}
D:\Users\maku\sample> ruby sample.rb
D:/Users/maku/sample
D:/Users/maku
D:/Users
D:/
{{< /code >}}
