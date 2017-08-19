---
title: sed で自分自身のファイルを書き換えるときの注意
created: 2002-08-20
---

`sed` コマンドによる出力結果を、自分自身のファイルに書き戻そうとしてリダイレクトを使うと、ファイルの中身が消えてしまいます（これは `sed` に限ったことではなく、Linux の一般的な振る舞いですが）。

例えば、下記のように実行すると、input.txt ファイルの内容は空っぽになってしまいます。

#### 間違ったリダイレクト（ファイルの内容が消えてしまう）
```
$ sed 's/aaa/bbb/' input.txt > input.txt
```

このようなことを行いたい場合は、`sed` コマンドの `-i` オプションを指定すると自分自身のファイルに結果を書き戻すことができます。

#### 自分自身のファイルに書き戻す

```
$ sed -i 's/aaa/bbb' input.txt
```

元のファイルのバックアップを取っておきたい場合は、`-i` オプションに続けて拡張子を指定します。

#### 自分自身のファイルに書き戻す（バックアップ付き）
```
$ sed -i.bak 's/aaa/bbb' input.txt
$ ls
input.txt  input.txt.bak
```

拡張子のドットは省略できません。また、`-i` の後ろにスペースを入れることもできないので注意してください。
