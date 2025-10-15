---
title: "Androidトラブルシューティング: repo init でエラーが出る場合"
url: "p/zh3w2tz/"
date: "2012-02-21"
tags: ["android"]
aliases: ["/android/trouble/repo-init-error.html"]
---

Proxy 環境で、repo コマンド実行時に下記のようなエラーが出る場合、

```console
$ repo init -u https://android.googlesource.com/platform/manifest
...
Get https://android.googlesource.com/tools/repo

fatal: branch 'stable' has not been signed
```

以下のように http でアクセスするように変更すればうまくいくことがあります。

{{< code lang="text" title="(1) repo ファイルの REPO_URL を https から http に変更" >}}
REPO_URL='https://android.googlesource.com/tools/repo'
↓
REPO_URL='http://android.googlesource.com/tools/repo'
{{< /code >}}

{{< code lang="bash" title="(2) プロキシ設定 （~/.bashrc とか）" >}}
$ export http_proxy=http://example.com:8080/
{{< /code >}}

{{< code lang="bash" title="(3) http のアドレスで repo init" >}}
$ repo init -u http://android.googlesource.com/platform/manifest
{{< /code >}}

