---
title: "Perlメモ: ファイルがテキストファイルかどうか調べる (-T)"
url: "p/iikadsk/"
date: "2008-07-09"
tags: ["perl"]
aliases: ["/perl/file/find-text-files.html"]
---

下記のようにすると、ディレクトリ内のファイルを列挙するときに、ファイルの種類がテキストであるものだけを抽出することができます。

{{< code lang="perl" title="例: カレントディレクトリの全てのテキストファイルを列挙" >}}
for (glob '*') {
    print "$_\n" if -T;
}
{{< /code >}}

