---
title: "Perforceメモ: p4 sync 時のファイル上書き設定 (noclobber/clobber)"
url: "p/7kazgbv/"
date: "2006-06-16"
tags: ["perforce"]
aliases: ["/p4/clobber-settings.html"]
---

`p4 edit` でファイルを writable 状態にしているときは、`p4 sync` してもそのファイルは上書きされませんが、**OS の機能で直接ファイルを writable 状態に変更してしまった場合**（ファイルの書き込みフラグを ON にした場合）は、**クライアントの clobber 設定によって sync 時の振る舞いが変わります**。

| 設定 | 振る舞い |
| ---- | -------- |
| `Options: noclobber` | `p4 sync` でそのファイルは上書きされない。 |
| `Options: clobber` | `p4 sync` でそのファイルは上書きされる。 |

