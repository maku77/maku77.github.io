---
title: "Perlメモ: ファイルがしばらく更新されていないかどうか調べる (if -M)"
url: "p/45dtqeb/"
date: "2008-03-24"
tags: ["perl"]
aliases: ["/perl/file/last-mod.html"]
---

ファイルハンドル `FILE` があるときに、そのファイルがしばらく更新されていないかどうかを調べるには以下のようにします。

{{< code lang="perl" title="ファイルが 60 日以上更新されていないか確認する" >}}
if (-M FILE > 60) {
    # ファイルが 60 日以上更新されていない
}
{{< /code >}}

