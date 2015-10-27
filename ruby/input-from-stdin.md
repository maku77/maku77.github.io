---
title: 標準入力から読み込む
created: 2010-05-07
---

標準入力からの読み込み
====

下記のように、リダイレクトやパイプで渡されたテキスト（標準入力）を読み込みたい場合のお話です。

```
$ ./main.rb < input.txt
$ cat input.txt | ./main.rb
```


標準入力から全ての行を一行ずつ読み込む
----

標準入力から次の一行を読み込むには、`ARGF.gets` を使用します。
`ARGF.gets` は `gets` と省略できます。

#### 方法 (1) ARGF.gets

```ruby
while line = gets
    print '> ' + line
end
```

`ARGF.each` でループ処理させることもできます。

#### 方法 (2) ARGF.each

```ruby
ARGF.each do |line|
    print '> ' + line
end
```

インデックスを付けながら一行ずつ読み込む
----

`ARGF.each` の代わりに、`ARGF.each_with_index` を使用すると、インデックスを取得しながらループ処理を行うことができます。

```ruby
ARGF.each_with_index do |line, i|
  puts "#{i}: #{line}"
end
```


ARGF はフィルタプログラム作成用に便利
====

`ARGF` (`$<`) は、コマンドライン引数にファイル名を指定した場合は、ファイルの内容を読み出すための仮想ファイルハンドルとして働き、ファイル名を指定しなかった場合は標準入力から内容を読み出しために使用できます。

例えば、テキストに行番号を付加するフィルタプログラムは以下のように記述することができます。

```ruby
while line = ARGF.gets
  print $., ": ", line
end
```

コマンドライン引数に複数のファイル名を指定した場合も、`ARGF` は連なったテキストデータとして読み込んでくれます。

```
$ ruby sample.rb input1.txt input2.txt input3.txt
```

つまり、`ARGF` を使ったコーディングを行うことによって、テキストファイルや標準入力からの入力テキストを一気に加工して出力する、フィルタプログラムを簡単に作成することができるようになっています。

