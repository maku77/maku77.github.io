---
title: Git でコミットログの出力形式をカスタマイズする
date: "2010-07-19"
---

ログの出力形式をカスタマイズする (--pretty)
----

`git log` の `--pretty` オプションを指定することによってログの出力形式をカスタマイズすることができます。

```
# 各コミットを一行で表示する
$ git log --pretty=onelie

# 短い SHA1 ハッシュ値とコミットログの一行目を表示
$ git log --pretty=format:"%h %s"

# 赤色で commit date、青色で author、緑色で subject を表示
$ git log --pretty="format:%Cred%ci %Cblue%an %Cgreen%s"
```

フォーマットとして使用できる文字列は `git diff --help` の `PRETTY FORMATS` セクションで確認できます。


日時の表示形式をカスタマイズする (--date)
----

`git log` コマンドで出力される日時は、デフォルトでは以下のようなフォーマットで表示されます。

```
Tue Apr 14 13:20:45 2015 +0900
```

`git log` の `--date` オプションを使用すると、このフォーマットを変更できます。

```
$ git log --date=iso       # 2015-04-14 13:20:45 +0900 （ISO 8601 形式）★オススメ！
$ git log --date=rfc       # Tue, 14 Apr 2015 13:20:45 +0900（RFC 2822 形式）
$ git log --date=local     # Tue Apr 14 13:20:45 2015 （ローカルタイムゾーンにおけるローカルタイム）
$ git log --date=default   # Tue Apr 14 13:20:45 2015 +0900（commiter あるいは author のタイムゾーン）
$ git log --date=short     # 2015-04-14 （YYYY-MM-DD 形式の年月日のみ）
$ git log --date=relative  # 3 days ago （現在からの相対時間）
$ git log --date=raw       # 1428985245 +0900 （Git の内部形式）
```

