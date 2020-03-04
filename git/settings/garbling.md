---
title: "git diff や git status での日本語の文字化けを防ぐ (core.page, core.quotepath)"
date: "2017-08-19"
lastmod: "2019-10-18"
---

git diff での日本語の文字化け
----

`git diff` の出力に日本語が含まれているときに、下記のように２桁の数値ばかりの表示に文字化けしてしまうことがあります。

~~~
$ git diff
diff --git a/README.txt b/README.txt
index 0005c4e..5d6ea9c 100644
--- a/README.txt
+++ b/README.txt
@@ -1 +1 @@
-<E3><81><82><E3><81><84><E3><81><86><E3><81><88><E3><81><8A>
+<E3><81><8B><E3><81><8D><E3><81><8F><E3><81><91><E3><81><93>
~~~

このような場合は、ページャの設定 (`core.pager`) で下記のように `LESSCHARSET` を設定するようにします。

~~~
$ git config --global core.pager "LESSCHARSET=utf-8 less"
~~~

あるいは、システムの環境変数で Git のページャ設定を行っておくこともできます。


#### ~/.bash_profile

~~~
export GIT_PAGER="LESSCHARSET=utf-8 less"
~~~

これで正しく日本語の差分が表示されるようになります。

~~~
$ git diff
diff --git a/README.txt b/README.txt
index 0005c4e..5d6ea9c 100644
--- a/README.txt
+++ b/README.txt
@@ -1 +1 @@
-あいうえお
+かきくけこ
~~~

Windows の場合は、環境変数 **LANG** を設定しておくとよいようです。

~~~
C:\> set LANG=ja_JP.UTF-8
~~~


git status
----

`git status` の表示で、日本語のファイル名が下記のように文字化けしてしまうことがあります。

~~~
$ git status
On branch master
Untracked files:
  (use "git add <file>..." to include in what will be committed)

        "\343\201\202\343\201\202\343\201\202.txt"

nothing added to commit but untracked files present (use "git add" to track)
~~~

このような場合は、下記のように `core.quotepath` 設定を `false` に設定します。

~~~
$ git config --global core.quotepath false
~~~

これで正しく日本語のファイル名が表示されるようになります。

~~~
$ git status
On branch master
Untracked files:
  (use "git add <file>..." to include in what will be committed)

        あああ.txt

nothing added to commit but untracked files present (use "git add" to track)
~~~


設定の確認と削除
----

上記で設定した値を確認するには下記のようにします。

~~~
$ git config --global --list
core.pager=LESSCHARSET=utf-8 less
core.quotepath=false
~~~

設定した値をクリアするには下記のようにします。

~~~
$ git config --global --unset core.pager
$ git config --global --unset core.quotepath
~~~

