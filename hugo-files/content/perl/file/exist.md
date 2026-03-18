---
title: "Perlメモ: ファイルが存在するかどうか調べる (if -e)"
url: "p/odmfzd5/"
date: "2008-03-24"
tags: ["perl"]
aliases: ["/perl/file/exist.html"]
---

`if -e 'ファイル名'` とすると、そのファイルがすでに存在しているかを調べることができます。

{{< code lang="perl" title="例: 指定した名前のファイル (`$filename`) が存在していたら終了する" >}}
die "'$filename' already exists.\n" if -e $filename;
{{< /code >}}

