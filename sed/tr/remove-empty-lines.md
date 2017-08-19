---
title: tr コマンドで空白行を削除する
created: 2009-04-05
---

```
$ cat hoge.txt | tr -s "\n"
```

`tr` コマンドの `-s` オプションは、指定した文字が連続して現れた場合に、1 度だけ出力することを表します。
上記の例では、連続する「改行」が出現した場合に 1 つにするため、実際には空行を削除していることと同様になります。

* 参考: [sed で空白行を削除する](../sed/remove-empty-lines.html)
