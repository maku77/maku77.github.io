---
title: "Perforceメモ: 編集中のファイルがどのチェンジリストに入っているか調べる"
url: "p/9s4njfm/"
date: "2006-08-22"
tags: ["perforce"]
aliases: ["/p4/which-changelist.html"]
---

## 編集中のファイルがどのチェンジリストで管理されているか調べる

編集中のファイルを調べる `p4 opened` コマンドの出力を見ると、それらのファイルがどのチェンジリストで管理されているかを知ることができます。

```console
$ p4 opened
//depot/hoge/sample1.txt#1 - edit default change (ktext)
//depot/hoge/sample2.txt#1 - edit change 12345 (ktext)
```

上記の例では、sample1.txt はデフォルトのチェンジリスト、sample2.txt はチェンジリスト 12345 番に入っていることが分かります。


## 指定したチェンジリストに入っているファイルを調べる

チェンジリスト番号を指定して `p4 opened` コマンドを実行すれば、そのチェンジリスト内で編集状態になっているファイルの一覧だけを表示できます。

```console
$ p4 opened -c 12345
```

デフォルトのチェンジリストに入っているファイルを調べるには、チェンジリスト番号として **default** を指定します。

```console
$ p4 opened -c default
```
