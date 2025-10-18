---
title: "Linuxメモ: 今日の日付から YYYYMMDD のような文字列を作成する (date)"
url: "p/7aj35xe/"
date: "2005-04-21"
tags: ["linux"]
aliases: /linux/time/date-string.html
---

Linux の **`date`** コマンドを使用すると、現在時刻を表す文字列を自由なフォーマットで取得することができます。

```console
$ date +%Y%m%d-%H%M%S
20050521-231345
```

時刻入りの簡単なログを出力したいときや、バックアップを作成するときのファイル名構成のために使用することができます。

{{< code lang="console" title="例: 現在日時をファイル名に付けてファイルコピー（バックアップ）" >}}
$ cp sample.txt sample.txt.`date +%Y%m%d`.back
$ ls
sample.txt    sample.txt.20050102.back
{{< /code >}}

