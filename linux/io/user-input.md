---
title: "シェルスクリプト: ユーザ入力を取得する (read)"
date: "2012-11-05"
---

ユーザ入力の基本
----

`read 変数名` とすると、ユーザからの一行分の入力を変数に取得することができます。

#### sample.sh

```bash
#!/bin/sh
echo -n "Enter your name: "
read name
echo "Hello, $name"
```

#### 実行結果

```
$ ./sample.sh
Enter your name: まくまく へむむ
Hello, まくまく へむむ
```


ユーザ入力を取得して y が入力された場合だけ処理を継続する
----

`read` コマンドの典型的な使用例として、ユーザに `y/n` の選択肢を入力させるものがあります。

#### sample.sh

```bash
#!/bin/bash

function remove_all {
  echo -n 'Are you sure? (y/n): '
  read input
  if [ "$input" = 'Y' -o "$input" = 'y' ]; then
    echo 'All things have been removed!'
  fi
}

remove_all
```

`if` の中で `$line` の内容を確認するときに、ダブルクォーテーションで囲んでいるのは、ユーザの入力がなかった場合に空文字 `""` を取得するためです。
これがないと、ユーザが何も入力せずにエンターした場合にエラーになってしまいます。

#### 実行例

```
$ ./sample.sh
Are you sure? (y/n): y
All things have been removed!
```


スペースで区切られたユーザ入力を別々の変数に取得する
----

`read` コマンドの後ろに複数の変数を指定すると、スペースで区切られたユーザ入力を別々の変数に取得することができます。

#### sample.sh
```bash
#!/bin/sh
read aaa bbb ccc
echo "aaa = $aaa"
echo "bbb = $bbb"
echo "ccc = $ccc"
```

#### 実行結果
```
$ ./sample.sh
100 200 300
aaa = 100
bbb = 200
ccc = 300
```

`read` に指定した変数の数よりも、入力が少ない場合は、変数が空になります。

#### 実行結果
```
$ ./sample.sh
100 200
aaa = 100
bbb = 200
ccc =
```

逆に、入力の方が多い場合は、`read` コマンドの最後に指定した変数にまとめて格納されます。

#### 実行結果
```
$ ./sample.sh
100 200 300 400 500
aaa = 100
bbb = 200
ccc = 300 400 500
```

