---
title: ディポ上のファイルを標準出力に出力する／ローカルファイルに保存する (p4 print)
created: 2006-03-24
---

`p4 print` コマンドでサーバ上のファイル内容を出力できます。
`p4 print` コマンドに `-q` オプションを付けると、出力の先頭行にファイルのパスが表示されるのを抑制できます。

```
$ p4 print -q //depot/dir/filename
```

ディポ上のファイルをローカルファイルとして保存したい場合は、
`p4 print` コマンドに `-o` オプションを指定します。

```
$ p4 print -o localfile.txt //depot/dir/filename
```

