---
title: "親ディレクトリのパスを取得する"
date: "2017-08-07"
---


`Dir::pwd` を使用すると、カレントディレクトリの絶対パスを取得することができます。
それで取得したディレクトリパスを、`File::dirname` に渡してやると、カレントディレクトリの親ディレクトリを取得することができます。

例えば、カレントディレクトリが `C:\Users\maku\sample` であるときに、下記を実行すると、`C:\Users\maku` が表示されます。

~~~ ruby
puts File::dirname(Dir::pwd)
~~~

下記のサンプルでは、カレントディレクトリから、上位のディレクトリを順番に辿りながら出力しています。

#### sample.rb

~~~ ruby
path = Dir::pwd
while true
  puts path
  temp = File::dirname(path)
  break if path == temp
  path = temp
end
~~~

#### 実行例（D:\users\maku\sample ディレクトリ内から実行した場合）

~~~
D:\Users\maku\sample> ruby sample.rb
D:/Users/maku/sample
D:/Users/maku
D:/Users
D:/
~~~

