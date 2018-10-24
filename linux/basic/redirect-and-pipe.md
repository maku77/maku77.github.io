---
title: "リダイレクトとパイプ処理のまとめ"
date: "2005-05-24"
---

リダイレクトとパイプの役割
----

デフォルトでは、標準出力 (stdout) の出力先はモニタで、標準入力の入力元はキーボードになっています。
リダイレクトやパイプは、これを変更する役割を持っています。

* `command > file` -- command の標準出力の内容をファイルへ出力する
* `command < file` -- ファイルの内容を command の標準入力へ入力する
* `command1 | command2` -- command1 の標準出力を command2 の標準入力へ繋ぐ

#### 例: list.txt の内容を sort コマンドの標準入力へ渡す

```
$ sort -r < list.txt      # リダイレクトを使う場合
$ cat list.txt | sort -r  # パイプを使う場合
```


標準出力のリダイレクト
----

### 標準出力と標準エラー出力をリダイレクトする

```
bash$ command &> output.txt
```

以下のように実行する方法もあり、Linux (bash) も Windows も共通の指定方法で実行できます。

```
bash$ command > output.txt 2>&1
```

'2>&1' は、STDERR(2) を STDOUT(1) に結合するという指示です。


### 標準出力と標準エラー出力を別々にリダイレクトする

```
bash$ command 1> stdout.txt 2> stderr.txt
```


### 標準エラー出力だけをリダイレクト

```
bash$ command 2> stderr.txt
```

#### 例: find コマンドを実行するときにエラー出力を抑制する（/dev/null へのリダイレクト）

```
$ find / -name ifconfig 2> /dev/null
```


標準エラーのパイプ
----

### 標準出力と標準エラー出力をパイプ

```
bash$ command1 2>&1 | command2
```

### 標準エラー出力だけをパイプ（標準出力は破棄）

```
bash$ (command1 1> /dev/null) 2>&1 | command2
```

