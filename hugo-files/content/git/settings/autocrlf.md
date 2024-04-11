---
title: "Git でコミット、チェックアウト時に改行コードを自動変換する (core.autocrlf, core.safecrlf)"
url: "p/efmfnuy/"
date: "2014-03-21"
tags: ["Git"]
aliases: /git/settings/autocrlf.html
---

Git の設定 (`git config`) で、__`core.autocrlf`__ や __`core.safecrlf`__ を設定しておくと、コミットやチェックアウト時に改行コードを自動変換することができます。
変換設定として、下記のような設定を行うことができます。

- `core.autocrlf`
  - **`false`**: コミット時、チェックアウト時に改行コードの変換を行わない
  - **`true`**: コミット時に CRLF→LF の変換を行い、チェックアウト時に LF→CRLF の返還を行う。
  - **`input`**: コミット時に CRLF→LF の変換を行い、チェックアウト時には変換を行わない。
- `core.safecrlf`
  - **`true`**: ファイル内に複数の改行コードが混じっている場合に自動変換を行わない。

おすすめの設定は以下の通りです。
このように設定しておくと、Git サーバー側では `LF` で統一して管理され、チェックアウト後は各 OS の標準的な改行コード（Windows は CRLF、macOS は LF）でファイルを扱えます。

{{< code lang="console" title="Windows の場合" >}}
$ git config --global core.autocrlf true  # コミット時: CRLF=>LF チェックアウト時: LF=>CRLF
$ git config --global core.safecrlf true  # ファイル内に複数の改行コードが混じっている場合に自動変換をしない
$ git config --global core.whitespace cr-at-eol  # git diff 時の ^M を抑制
{{< /code >}}

{{< code lang="console" title="macOS/Linux の場合" >}}
$ git config --global core.autocrlf input  # コミット時: CRLF=>LF チェックアウト時: 変換なし
$ git config --global core.safecrlf true   # ファイル内に複数の改行コードが混じっている場合に自動変換をしない
{{< /code >}}

改行コード関連の変換を一切行ってほしくない場合は、単純に `core.autocrlf` を `false` に設定します。
複雑な構成管理を行っている環境では、__結局この設定が一番安全だったりします__。

{{< code lang="console" title="改行変換を一切行わない設定" >}}
$ git config --global core.autocrlf false  # 改行コードの自動変換をしない
{{< /code >}}

