---
title: 絶対パスと相対パスの変換
created: 2010-07-20
---

basename - ディレクトリ、ファイル名だけを抽出する
====

```bash
$ basename /aaa/bbb/ccc
ccc

$ basename /aaa/bbb/ccc/
ccc

$ basename aaa/bbb/ccc
ccc
```


dirname - そのディレクトリ、ファイルが格納されているディレクトリのパスを抽出する
====

```bash
$ dirname /aaa/bbb/ccc
/aaa/bbb

$ dirname /aaa/bbb/ccc/
/aaa/bbb

$ dirname aaa/bbb/ccc
aaa/bbb
```

