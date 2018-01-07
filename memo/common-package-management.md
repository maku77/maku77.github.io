---
title: パッケージ管理ツールいろいろ
date: "2014-05-25"
---

Mac OSX 用のパッケージ管理
====

| ツール名 | コマンド | パッケージインストール先 | メモ |
| ---- | ---- | ---- | ---- |
| MacPorts | port | /opt/usr | システムにインストールされているコマンドに依存しないようにパッケージ管理するため、依存関係が多いとインストールに時間がかかる。パッケージインストールには管理者権限が必要 (sudo)。 |
| Homebrew | brew | /usr/local | システムにインストールされているコマンドをなるべく使用するように動く。一般ユーザ権限でパッケージインストール可能。Ruby で実装されている。|
| Fink | fink | /sw | Debian の apt をベースにしている。 |
| Gentoo Prefix | emerge | ~/Gentoo | Gentoo の portage をベースにしている。Mac OSX 専用ではない。管理者権限不要。 |


各言語のバージョン切り替え
====

| ツール名 | コマンド | 環境インストール先 | メモ |
| ---- | ---- | ---- | ---- |
| GVM (Groovy enVironment Manager) | gvm | ~/.gvm | 複数バージョンの Groovy を切り替えて使用するためのツール（Gaiden/Groovy/Grails/Griffon/Gradle などの切り替えもサポート）。その名の通り、RVM にインスパイアされて作られた。|
| RVM (Ruby Version Manager) | rvm | ~/.rvm | 複数バージョンの Ruby を切り替えて使用するためのツール。|


各言語のモジュール（パッケージ）管理
====

| ツール名 | コマンド | パッケージインストール先 | メモ |
| ---- | ---- | ---- | ---- |
| NPM (Node Package Manager) | npm | ./node_modules | Node.js で使用するパッケージの管理ツール。Node をインストールすると一緒にインストールされる。|
| Python Package Index | pip | site-packages | setuptools の easy_install を置き換えるもの。アンインストールなど簡単にできる。 |

