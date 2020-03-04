---
title: "git config による設定を削除する (config --unset)"
date: "2010-07-17"
---

`git config` で設定した値を削除したい場合は、以下のように `--unset` オプションを指定して `git config` を実行します。

#### 例: user.email のローカル設定を削除する

~~~
$ git config --local --unset user.email
~~~

