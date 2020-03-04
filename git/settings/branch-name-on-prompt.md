---
title: "bash のプロンプトにカレントブランチ名を表示する (PS1)"
date: "2010-10-15"
---

下記のように bash のプロンプト設定 (`PS1`) を行っておくと、プロンプト表示に Git のカレントブランチ名を表示することができます。

~~~ bash
parse_git_branch() {
    git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/(\1)/'
}
PS1="\w\$(parse_git_branch) $ "
~~~

ちなみに、Git で現在チェックアウトしているブランチ名を取得するには、いろんな方法があります。

~~~
$ git name-rev HEAD 2> /dev/null | awk "{ print \$2 }"
$ git branch 2> /dev/null | cut -f2 -d\* -s
$ git branch 2> /dev/null | grep -e ^* | tr -d \*\ ←半角スペース
$ git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'
~~~

