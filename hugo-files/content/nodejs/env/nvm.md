---
title: "nvm で複数の Node.js バージョンを切り替えて使用する (Node Version Manager)"
url: "p/3x95seb/"
date: "2022-03-13"
tags: ["nodejs"]
aliases: /nodejs/env/nvm.html
---

nvm (Node Version Manager) とは？
----

__`nvm`__ というコマンドラインツールを使うと、システム内に複数バージョンの Node.js 実行環境をインストールして、切り替えて使うことができるようになります。

```console
$ nvm use 16  # node コマンドを version 16.x.x に切り替え
$ nvm use 14  # node コマンドを version 14.x.x に切り替え
```

新しいバージョンの Node.js を試してみたいときや、複数バージョンでテストを行いたい場合に便利です。
`nvm` はスタンドアローン版の Node.js とも共存できるので、安心してインストールすることができます。
`nvm` を使わずにインストールされた Node.js は、`system` という名前で参照できるようになっています。

```console
$ nvm use system  # node コマンドをスタンドアローン版に切り替え
```


nvm のインストール
----

`nvm` コマンドは次のように簡単にインストールできます。

### macOS や Linux の場合

```console
$ curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash
```

最新バージョンのインストール方法は [nvm-sh/nvm - GitHub](https://github.com/nvm-sh/nvm) の公式サイトで確認してください。
インストール後は、ターミナル（bash など）を再起動するか、`source ~/.bashrc` を実行すると `nvm` コマンドを実行できるようになります。

### Windows の場合

`nvm` はもともと Linux 系のコマンドラインツールとして作成されていますが、Windows でも [nvm-windows のインストーラー](https://github.com/coreybutler/nvm-windows/releases) を使えば簡単にインストールできます。


nvm の使い方
----

`nvm` が現在アクティブにしている Node.js のバージョンを調べるには、__`nvm current`__ コマンドを使用します。

```console
$ nvm current
system
```

上記のように `system` と表示される場合は、システムにすでにインストールされている（`nvm` を使わずにインストールした）Node.js がアクティブになっています。
まだ Node.js がインストールされていない環境の場合は `none` と表示されます。
システムにインストールされた Node.js 実行環境は必要なくなるので、不要なグローバルパッケージをアンインストールしておくとよいかもしれません（`npm root -g` で表示されたディレクトリをクリアする方法もあります）。

```console
$ npm list -g  # グローバルパッケージの一覧を表示
$ sudo npm uninstall -g XXXX  # 不要なパッケージをアンインストール
```

`nvm` で特定のバージョンの Node.js をインストールしたいときは、__`nvm install`__ コマンドを使用します。
次の例では、メジャーバージョン 16 の最新の Node.js をインストールしています。
最新の LTS （長期サポート）バージョンをインストールしたいときは、`--lts` オプションを指定します。
最新バージョンをインストールしたいときは、`node` というエイリアス名を使用します。


```console
$ nvm install 16  # メジャーバージョンを指定してインストールする場合
Downloading and installing node v16.14.0...

$ nvm install --lts  # 最新の LTS 版をインストールする場合
Installing latest LTS version.
v16.14.0 is already installed.

$ nvm install node  # 最新バージョンをインストールする場合
Downloading and installing node v17.7.1...
```

Node.js 環境を追加インストールすると、自動的にそのバージョンがアクティブになります。

```console
$ nvm current
v16.14.0

$ node -v
v16.14.0
```

使用する Node.js のバージョンを切り替えるには、__`nvm use`__ コマンドを使用します。

```console
$ nvm use system  # システムインストールされた Node.js を使う
$ nvm use 16      # nvm でインストールしたバージョン 16 の Node.js を使う
```

インストールされている Node.js の一覧を表示するには、__`nvm ls`__ コマンドを使用します。

```console
$ nvm ls
       v14.19.0
->     v16.14.0
         system
default -> 16 (-> v16.14.0)
...
stable -> 16.14 (-> v16.14.0) (default)
...
lts/gallium -> v16.14.0
```

現在アクティブになっているバージョンには、行頭に `->` が表示されています。
上記の例では、`v16.14.0` がアクティブになっていることが分かります。
`default -> 16` のように矢印の左側に表示されている名前は、具体的なバージョンのエイリアス名を示しており、`default` などのエイリアス名が自動的に生成されます。
このエイリアスの仕組みによって、次のコマンドはいずれも `v16.14.0` の切り替えを意味します。

```console
$ nvm use 16
$ nvm use 16.14
$ nvm use v16.14.0
$ nvm use default
$ nvm use stable
$ nvm use lts/gallium
$ nvm use --lts  （参考までに LTS はオプションでも指定可能）
```

`default` となっているのは、その名の通り、ターミナル起動時（bash シェル起動時）にデフォルトで有効になるバージョンとして扱われます。
__`nvm alias`__ コマンドで、デフォルトのバージョンを変更することができます（この `default` エイリアスの実体は、`~/.nvm/alias/default` というファイルです）。

```console
$ nvm alias default 14
```

ちなみに、各バージョンの Node.js 実行環境は、`~/.nvm` ディレクトリ以下にインストールされていきます。
`npm install -g` でグローバルインストールした NPM パッケージも、各バージョンごとに管理されます（`node_modules` ディレクトリの場所は `npm root -g` で確認できます）。
多くのバージョンの Node.js をインストールすると、ホームディレクトリのサイズが肥大化していくので注意してください（1 バージョンごとに 100 MB くらい消費していきます）。
必要のなくなったバージョンは適宜、__`nvm uninstall`__ で削除しましょう。

```console
$ nvm uninstall 14
```


（応用）最新環境に同じ NPM パッケージをインストールする
----

前述の通り、`nvm install node` コマンドを使用すると、最新の Node.js バージョンをインストールすることができますが、新しい環境の NPM グローバルパッケージはひとつずつ入れ直す必要があります。
現在アクティブになっている Node.js 環境にインストールされている NPM グローバルパッケージを、最新の Node.js 環境にすべてインストールするには次のように __`--reinstall-packages-from`__ オプションを使用します。

```console
$ nvm install node --reinstall-packages-from=current
```

このオプションには、任意のエイリアス名を指定できます。

```console
# 現在の最新環境 (node) にインストールされているパッケージを引き継ぐ
$ nvm install node --reinstall-packages-from=node

# 特定のバージョンにインストールされているパッケージを引き継ぐ
$ nvm install node --reinstall-packages-from=16
```

デフォルトでは NPM パッケージのバージョンはすべて引き継がれますが、__`--latest-npm`__ オプションを指定することで、最新の NPM パッケージをインストールすることができます。
新しい Node.js 環境では、新しいバージョンの NPM パッケージが必要になることも多いため、このオプションは指定しておいた方がいいかもしれません。

```console
$ nvm install node --reinstall-packages-from=node --latest-npm
```


（応用） .nvmrc ファイルで Node.js バージョンを揃える
----

Node.js プロジェクトのルートディレクトリに __`.nvmrc`__ ファイルを置くと、そのプロジェクトが想定する Node.js のバージョンを示すことができます。
例えば、プロジェクトで Node.js バージョン `14.19.x` を使用するように統一したい場合は、次のように `.nvmrc` ファイルを作成して、Git などにコミットしておきます。

```console
$ echo 14.19 > .nvmrc
```

このディレクトリより下のディレクトリで `nvm install` を引数なしで実行すると、`.nvmrc` に記述されたバージョンの Node.js がインストールされます。

```console
$ nvm install
Found '/Users/maku/myapp/.nvmrc' with version <14.19.0>
Downloading and installing node v14.19.0...
```

アクティブな Node.js バージョンを切り替える場合も、`nvm use` を引数なしで実行するだけで済みます。

```console
$ nvm use
Found '/Users/maku/myapp/.nvmrc' with version <14.19.0>
Now using node v14.19.0 (npm v6.14.16)
```

このように、`nvm` さえ入れておけば、プロジェクト全体で使用する Node.js のバージョンを簡単に揃えることができます。

