---
title: "コミット、チェックアウト時に改行コードを自動変換する"
date: "2014-03-21"
---

`git config` による設定で、`core.autocrlf` や `core.safecrlf` を設定しておくと、コミット時、チェックアウト時に改行コードを自動変換することができます。
変換設定として、下記のような設定を行うことができます。

- core.autocrlf
  - **false**: コミット時、チェックアウト時に改行コードの変換を行わない
  - **true**: コミット時に CRLF→LF の変換を行い、チェックアウト時に LF→CRLF の返還を行う。
  - **input**: コミット時に CRLF→LF の変換を行い、チェックアウト時には変換を行わない。
- core.safecrlf
  - **true**: ファイル内に複数の改行コードが混じっている場合に自動変換を行わない。

おすすめの設定は以下の通りです。

#### Windows の場合

```
$ git config --global core.autocrlf true  # コミット時: CRLF=>LF チェックアウト時: LF=>CRLF
$ git config --global core.safecrlf true  # ファイル内に複数の改行コードが混じっている場合に自動変換をしない
$ git config --global core.whitespace cr-at-eol  # git diff 時の ^M を抑制
```

#### Mac/Linux の場合

```
$ git config --global core.autocrlf input  # コミット時: CRLF=>LF チェックアウト時: 変換なし
$ git config --global core.safecrlf true   # ファイル内に複数の改行コードが混じっている場合に自動変換をしない
```

改行コード関連の変換を一切行ってほしくない場合は、単純に `core.autocrlf` を `false` に設定します。
複雑な構成管理を行っている環境では、結局この設定が一番安全だったりします。

```
$ git config --global core.autocrlf false
```

