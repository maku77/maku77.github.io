---
title: "ファイルが存在するかどうか調べる (if -e)"
date: "2008-03-24"
---

`if -e 'ファイル名'` とすると、そのファイルがすでに存在しているかを調べることができます。

#### 例: 指定した名前のファイル ($filename) が存在していたら終了する

~~~ perl
die "'$filename' already exists.\n" if -e $filename;
~~~

