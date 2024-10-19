---
title: "Vim のコマンドモードで入力補完する (Ctrl-D, Tab)"
url: "p/ow6rvbz/"
date: "2007-02-21"
tags: ["vim"]
aliases: /vim/basic/candidate.html
---

Vim のコマンドモードでコマンドを入力している最中に、<kbd>Ctrl-D</kbd> や <kbd>Tab</kbd> を押すと、入力候補の一覧表示や、入力の補完を行うことができます。

{{< code title="例: add を含んでいるパラメータの入力候補を表示" >}}
:help add<Ctrl-D>
{{< /code >}}

{{< code title="例: add で始まるパラメータの入力を補完" >}}
:help add<TAB>
{{< /code >}}

