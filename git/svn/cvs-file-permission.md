---
title: "CVS のファイルのパーミッションの扱いについて"
date: "2005-01-08"
---

CVS のリポジトリはファイルの実行属性だけは保存してくれます。
この属性は `cvs add` する時点で設定されていなければいけません。

`cvs update` した場合は、リポジトリに置いてあるファイルのパーミッションの方が優先されます。
パーミッションを変更したい場合は、リポジトリ内の RCS ファイルのパーミッションを直接変更しなければいけません。

