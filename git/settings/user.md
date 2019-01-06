---
title: "コミット時に使用するユーザ名とメールアドレスを設定する"
date: "2010-07-17"
---

Git を使い始めるときは、最初に、コミット時に使用されるユーザ名とメールアドレスを設定しておきます。

~~~
$ git config --global user.name "Your Name"
$ git config --global user.email "YourName@example.org"
~~~

上記のようにグローバルな設定値として設定した値は、`$HOME/.gitconfig` に保存され、以下のコマンドで設定内容を確認できます。

~~~
$ git config --global --list
user.name=Your Name
user.email=YourName@example.org
~~~

`git help config` によると、上記の２つの設定は、環境変数 `GIT_AUTHOR_NAME`、`GIT_AUTHOR_EMAIL` でも設定できるとされており、環境変数の値が優先されるようです。

