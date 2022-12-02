---
title: "Git で管理するシェルスクリプトに実行権限（パーミッション）を付ける（chmod +x 相当）"
url: "p/xwxdv6y/"
date: "2015-10-21"
tags: ["Git"]
aliases: /git/file-permission.html
---

Git はファイルの所有者情報なのどメタ情報を管理しませんが、__ファイルの実行可能ビットだけは記録する__ ようになっています（Linux の `chmod +x` で付加するビット）。

例えば、Windows 上で Linux のシェルスクリプトを作成していて、Git リポジトリにコミットするときは、次のように __`git update-index`__ コマンドで実行可能ビットを付加してからコミットしなければいけません。

{{< code lang="console" title="シェルスクリプトに実行可能ビットを付ける" >}}
$ git update-index --add --chmod=+x <filename>
$ git commit
{{< /code >}}

Linux 上で作業する場合は、`chmod +x` で付加した情報が反映されるので、通常通り `git add` でファイルを追加するだけで OK です。

