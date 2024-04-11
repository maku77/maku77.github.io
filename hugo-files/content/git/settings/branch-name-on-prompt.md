---
title: "bash のプロンプトに Git のカレントブランチ名を表示する (PS1)"
url: "p/2mb4a94/"
date: "2010-10-15"
tags: ["Git"]
aliases: /git/settings/branch-name-on-prompt.html
---

下記のように bash のプロンプト設定 (`PS1`) を行っておくと、プロンプト表示に Git のカレントブランチ名を表示することができます。

{{< code lang="bash" title="~/.bashrc" >}}
parse_git_branch() {
    git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/ (\1)/'
}
PS1="\w\$(parse_git_branch) $ "
{{< /code >}}

こんな感じで表示されます。

```
~/gitwork/myapp (master) $
```


ちなみに、Git で現在チェックアウトしているブランチ名を取得するには、いろんな方法があります。

```console
$ git name-rev HEAD 2> /dev/null | awk "{ print \$2 }"
$ git branch 2> /dev/null | cut -f2 -d\* -s
$ git branch 2> /dev/null | grep -e ^* | tr -d \*\ ←半角スペース
$ git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'
```

