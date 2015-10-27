---
title: ディポ上のファイルを検索する
created: 2006-06-16
---

ディポ上のファイルをすべて表示
====

```
$ p4 files //depot/dir/...
//depot/dir/file1.cpp#1 - add change 22657 (ktext)
//depot/dir/file2.cpp#1 - add change 22657 (ktext)
//depot/dir/file3.cpp#2 - edit change 22658 (text)
```

ファイル名だけを表示したい場合は、例えば `sed` でフィルタします。

```
$ p4 files //depot/dir/... | sed "s/\(.*\)#.*/\1/"
//depot/dir/file1.cpp
//depot/dir/file2.cpp
//depot/dir/file3.cpp
```

拡張子で絞り込み
====
サーバ上のファイルを検索しようとした場合に、検索結果が大量になると、

```
Request too large (over 40000); see 'p4 help maxresults'.
```

と表示されて検索結果が表示されないことがあります。
このような場合は、例えば次のように検索を絞り込みます。

#### 例: ディポ上の .h ファイルを表示
```
$ p4 files //depot/dir/...*.h
```

特定のファイルがどこにあるかは次のように grep すれば簡単に探せます。 

```
$ p4 files //depot/dir/...*.h | grep Hello.h
```

