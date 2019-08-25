---
title: "macOS でシェルスクリプトをアイコンのダブルクリックで起動する"
date: "2019-08-25"
tags: ["mac"]
---

macOS では、シェルスクリプトファイルに `.command` という拡張子を付けておくと、Finder 上からファイルのアイコンをダブルクリックするだけで起動できるようになります。

例えば、下記のように簡単な `echo` 出力を行うだけのシェルスクリプトがあるとします。

#### sample.sh

```sh
#!/bin/sh
echo AAA
sleep 1
echo BBB
sleep 1
echo CCC
sleep 1
```

このファイルをダブルクリックで実行できるようにするには、下記のようにします。
`chmod +x` しておくのを忘れずに。

```
$ chmod +x sample.sh
$ mv sample.sh sample.command
```

この仕組みはスクリプトファイルであれば、どんな言語にでも適用できます。
例えば、先頭のシェバング部分を `#!/usr/bin/env python` などに変更すれば、Python スクリプトをダブルクリックで実行できるようになります。

