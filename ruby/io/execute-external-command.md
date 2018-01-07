---
title: 外部プログラムを呼び出す
date: "2011-07-29"
---

バッククォートを使用する方法
====

下記は Ruby プログラムの中から `/bin/ls` を実行して、その出力結果を取得するサンプルです。

~~~ ruby
#!/usr/bin/ruby
result = `/bin/ls`
puts result
~~~

外部プログラムに対してパラメータを渡すこともできます。

~~~ ruby
#!/usr/bin/ruby
result = `/bin/ls #{ARGV.join(' ')}`
puts result
~~~

外部プログラムの出力結果を 1 行ずつ処理することもできます。

~~~ ruby
#!/usr/bin/ruby
result = `/bin/ls`
result.each_line do |line|
  puts '===> ' + line
end
~~~

実行するコマンドを文字列変数に格納している場合は、下記のように実行時に展開できます。

~~~ ruby
command = 'date /t'
result = `#{command}`
~~~

system を使用する方法（出力が必要ない場合）
====

ただ単に、外部のプログラムを呼び出すだけでよい場合は、`system` を使用するのが手っ取り早いです。

#### ruby_version.rb

~~~ ruby
system 'ruby -v'
~~~

`system` は与えられたプログラムを、新しいプロセスで実行します。
自分自身のプロセスを置き換える形でプログラムを実行したい場合は、`Process.exec` を使用します。

#### my_ls.rb

~~~ ruby
Process.exec('ls', *ARGV)
~~~

自分自身のプロセスで外部プログラムを実行すれば、その実行を `Ctrl-C` で強制終了することができます。
（ただし、「MSDOS 環境の場合、command はサブシェル経由で実行されます。」という記載がありました。結局 Windows の場合は別のプロセスが起動してしまうっぽい・・・むむぅ。。。）


IO.popen を使用する方法
====

~~~ ruby
IO.popen('dir').each do |line|
  puts '===> ' + line
end
~~~

