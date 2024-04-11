---
title: "Git でコミット時に使用するユーザ名とメールアドレスを設定する (user.name, user.email)"
url: "p/gr3v7r8/"
date: "2010-07-17"
tags: ["Git"]
aliases: /git/settings/user.html
---

Git を使い始めるときは、最初に、コミット時に使用されるユーザ名とメールアドレスを設定しておきます。

{{< code lang="console" title="ユーザー名とメールアドレスの設定" >}}
$ git config --global user.name "Your Name"
$ git config --global user.email "YourName@example.org"
{{< /code >}}

上記のようにグローバルな設定値として設定した値は、__`~/.gitconfig`__ に保存され、以下のコマンドで設定内容を確認できます。

{{< code lang="console" title="グローバル設定の確認" >}}
$ git config --global --list
user.name=Your Name
user.email=YourName@example.org
{{< /code >}}

{{% note title="環境変数による設定" %}}
`git help config` によると、上記の 2 つの設定は、環境変数 __`GIT_AUTHOR_NAME`__、__`GIT_AUTHOR_EMAIL`__ でも設定できるとされており、環境変数の値が優先されるようです。
{{% /note %}}

