---
title: "Linuxメモ: 実行中のシェルスクリプトのファイル名を取得する ($0)"
url: "p/6vkj2pi/"
date: "2017-08-20"
tags: ["linux"]
aliases: /linux/path/path-of-script.html
---

シェルスクリプト内から、自身のファイル名を取得するには **`$0`** という特殊変数を参照します。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash
echo "$0"
{{< /code >}}

正確には、`$0` に格納されているパスは、そのスクリプト実行時に実際に指定したパスになります。
そのため、どのようにスクリプトを起動したかによって結果が変わってきます。

```console
# 絶対パスで起動した場合
$ /Users/maku/sample.sh
/Users/maku/sample.sh

# 相対パスで起動した場合
$ cd /Users
$ maku/sample.sh
maku/sample.sh

# カレントディレクトリのスクリプトを起動した場合
$ ./sample.sh
./sample.sh

# sh コマンドのパラメータで起動した場合
$ sh sample.sh
sample.sh
```

確実にファイル名（ベース名）だけを取得したい場合や、絶対パスを取得したい場合は以下のようにするのがよいでしょう。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

MY_BASENAME=$(basename $0)
MY_ABS_PATH=$(cd $(dirname $0); pwd)/$MY_BASENAME

echo "$MY_BASENAME"
echo "$MY_ABS_PATH"
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ~/sample.sh
sample.sh
/Users/maku/sample.sh
{{< /code >}}

