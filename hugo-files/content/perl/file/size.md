---
title: "Perlメモ: ファイルサイズを取得する (-s)"
url: "p/an7xw2r/"
date: "2008-03-24"
tags: ["perl"]
aliases: ["/perl/file/size.html"]
---

指定したファイル名（下記の例では `$filename`）のファイルのサイズを調べるには以下のようにします。
得られる値の単位は**バイト**です。

{{< code lang="perl" title="例: ファイルサイズ（バイト数）を取得する" >}}
my $filesize = -s $filename;
{{< /code >}}

`-s` を比較演算子と一緒に使用すると、ファイルサイズによる絞込み検索を行えます。

{{< code lang="perl" title="例: 拡張子が .cpp で、かつ、ファイルサイズが 1024byte 以下のファイルを探す" >}}
my @files = glob '*';
my @files2 = grep { (-s) < 1024 } @files;
{{< /code >}}

