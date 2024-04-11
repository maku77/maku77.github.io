---
title: "Windows でパーミッションの違いで git diff が表示されてしまうのを防ぐ (core.filemode)"
url: "p/ijepy8e/"
date: "2014-04-22"
tags: ["Git"]
aliases: /git/settings/permission-diff.html
---

Linux と Windows ではファイルのパーミッションの扱いが異なるので、Linux で作成したスクリプトファイル（パーミッション: `0755`）を、Windows で `git diff` すると、パーミッションが `0644` に変わっているという結果が出てしまったりします。
これを防ぐには、Windows 側の `git config` で、__`core.filemode`__ を __`false`__ に設定します。

```
C:\> git config --global core.filemode false
```

うまくいかない場合は、ローカルスコープの設定（リポジトリごとの設定）で `core.filemode` が `true` になっている可能性があります。
以下のように一度設定をクリアしてから行うとうまくいくはずです。

```
C:\> git config --local --unset core.filemode
```

{{< code title="参考: core.fileMode の説明" >}}
core.fileMode
	If false, the executable bit differences between the index and the
	working copy are ignored; useful on broken filesystems like FAT.
	See git-update-index(1). True by default.
{{< /code >}}

