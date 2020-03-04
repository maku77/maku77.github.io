---
title: "Git の local 設定と global 設定と system 設定の違い"
date: "2014-05-25"
---

Git 設定の 3 つのスコープ
----

`git config` による設定のスコープは 3 種類あり、スコープが狭くなるほど参照時の優先度は高くなります。
下記はそれぞれのスコープでの設定方法を、優先度の高い順に示しています。
カッコの中のファイル名は、コマンドを実行したときの設定値の保存先です。

~~~
git config --local ...   # 各リポジトリごとの設定 (.git/config)（優先度:高）
git config --global ...  # 現在のユーザの共通設定 (~/.gitconfig)
git config --system ...  # システム内の共通設定 (/etc/gitconfig など)（優先度:低）
~~~

例えば、global 設定で `user.name` が `Ichiro` になっていても、local 設定が `Jiro` になっていれば、`Jiro` の方が優先的に使用されます。

プロジェクトごとに固有の設定をする場合は、local なスコープで設定を行うとよいでしょう。
この場合、プロジェクトの作業ツリーのトップにある `.git/config` に設定が保存されます。


（コラム）Windows の場合のホームディレクトリ
----

global スコープの設定は、ユーザのホームディレクトリの `.gitconfig` ファイルに設定値が保存されますが、Windows の場合のホームディレクトリは、デフォルトで `%USERPROFILE%` 環境変数で取得できるディレクトリが使用されます。

```
C:\> echo %USERPROFILE%
C:\Users\maku
```

例えば、上記の場合は `C:\Users\maku` が Git のホームディレクトリとして使用されるので、global スコープの設定は、`C:\Users\maku\.gitconfig` に保存されます。

ただし、`HOME` 環境変数が設定されている場合は、そちらのディレクトリがホームディレクトリとして使用されます。

- 参考: [Git の設定値がどのファイルで設定されているか調べる (config --show-origin)](./show-origin.html)

