---
title: "nvm で複数の Node.js バージョンを切り替えて使用する (Node Version Manager)"
url: "p/3x95seb/"
date: "2022-03-13"
lastmod: "2024-08-06"
tags: ["nodejs"]
changes:
  - 2024-08-06: コマンド体系が変わっていた（list -g がなくなっていた）ので更新
aliases: /nodejs/env/nvm.html
---

nvm (Node Version Manager) とは？
----

__`nvm`__ というコマンドラインツールを使うと、システム内に複数バージョンの Node.js 実行環境をインストールして、切り替えて使うことができるようになります。
`nvm` 実行のために、あらかじめ Node.js をインストールしておく必要はありません。

```console
$ nvm use 16  # node コマンドを version 16.x.x に切り替え（必要に応じてインストール）
$ nvm use 14  # node コマンドを version 14.x.x に切り替え（必要に応じてインストール）
```

上記のように `node` コマンドのバージョンを簡単に切り替えることができるため、新しいバージョンの Node.js を試してみたいときや、複数バージョンでテストを行いたい場合に便利です。
`nvm` はスタンドアローン版の Node.js とも共存できるので、安心してインストールすることができます。
`nvm` を使わずにインストールされた Node.js は、`system` という名前で参照できるようになっています。

```console
$ nvm use system  # node コマンドをスタンドアローン版に切り替え
```


nvm のインストール
----

`nvm` コマンドは次のように簡単にインストールできます。

### macOS や Linux の場合
{{< code lang="console" title="macOS や Linux の場合" >}}
$ curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash
{{< /code >}}

最新バージョンのインストール方法は [nvm-sh/nvm - GitHub](https://github.com/nvm-sh/nvm) の公式サイトで確認してください。
インストール後は、ターミナル（bash など）を再起動するか、`source ~/.bashrc` を実行すると `nvm` コマンドを実行できるようになります。

### Windows の場合

`nvm` はもともと Linux 系のコマンドラインツールとして作成されていますが、Windows でも [nvm-windows のインストーラー](https://github.com/coreybutler/nvm-windows/releases) を使えば簡単にインストールできます。


nvm の使い方
----

`nvm` が現在アクティブにしている Node.js のバージョンを調べるには、__`nvm current`__ コマンドを使用します。

{{< code lang="console" title="アクティブになっている Node.js バージョンを確認" >}}
$ nvm current
system
{{< /code >}}

上記のように `system` と表示される場合は、システムにすでにインストールされている（`nvm` を使わずにインストールした）Node.js がアクティブになっています。
まだ Node.js がインストールされていない環境の場合は `none` や `No current version.` のように表示されます。
使用する Node.js を `nvm` で切り替える場合、スタンドアローン版としてインストールされた Node.js 実行環境は必要なくなるので、不要なグローバルパッケージをアンインストールしておくとよいかもしれません（`npm root -g` で表示されたディレクトリをクリアする方法もあります）。

{{< code lang="console" title="（必要があれば）スタンドアローン版の Node.js のグローバルパッケージを削除" >}}
$ npm list -g  # グローバルパッケージの一覧を表示
$ sudo npm uninstall -g XXXX  # 不要なパッケージをアンインストール
{{< /code >}}

`nvm` でインストール可能な Node.js バージョンの一覧は __`nvm list available`__ コマンドで確認できます。

{{< code lang="console" title="インストール可能なバージョンの一覧" >}}
$ nvm list available

|   CURRENT    |     LTS      |  OLD STABLE  | OLD UNSTABLE |
|--------------|--------------|--------------|--------------|
|    22.5.1    |   20.16.0    |   0.12.18    |   0.11.16    |
|    22.5.0    |   20.15.1    |   0.12.17    |   0.11.15    |
|    22.4.1    |   20.15.0    |   0.12.16    |   0.11.14    |
|    22.4.0    |   20.14.0    |   0.12.15    |   0.11.13    |
|    22.3.0    |   20.13.1    |   0.12.14    |   0.11.12    |
|    22.2.0    |   20.13.0    |   0.12.13    |   0.11.11    |
|    22.1.0    |   20.12.2    |   0.12.12    |   0.11.10    |
|    22.0.0    |   20.12.1    |   0.12.11    |    0.11.9    |
|    21.7.3    |   20.12.0    |   0.12.10    |    0.11.8    |
|    21.7.2    |   20.11.1    |    0.12.9    |    0.11.7    |
|    21.7.1    |   20.11.0    |    0.12.8    |    0.11.6    |
|    21.7.0    |   20.10.0    |    0.12.7    |    0.11.5    |
|    21.6.2    |    20.9.0    |    0.12.6    |    0.11.4    |
|    21.6.1    |   18.20.4    |    0.12.5    |    0.11.3    |
|    21.6.0    |   18.20.3    |    0.12.4    |    0.11.2    |
|    21.5.0    |   18.20.2    |    0.12.3    |    0.11.1    |
|    21.4.0    |   18.20.1    |    0.12.2    |    0.11.0    |
|    21.3.0    |   18.20.0    |    0.12.1    |    0.9.12    |
|    21.2.0    |   18.19.1    |    0.12.0    |    0.9.11    |
|    21.1.0    |   18.19.0    |   0.10.48    |    0.9.10    |

This is a partial list. For a complete list, visit https://nodejs.org/en/download/releases
{{< /code >}}

上記の例では、最新バージョンは 22.5.1 で、LTS（長期サポート）バージョンが 20.16.0 であることが分かります。
特定のバージョンの Node.js をインストールしたいときは、__`nvm install <ver>`__ コマンドを使用します。
次の例では、メジャーバージョン __`22`__ の最新の Node.js をインストールしています。
最新バージョンをインストールするときは、バージョン番号の代わりに __`nvm install latest`__ と指定することも可能です。
最新の LTS （長期サポート）バージョンをインストールしたいときは、__`nvm install lts`__ と指定します。

{{< code lang="console" title="nvm で Node.js をインストールする" >}}
$ nvm install 20  # メジャーバージョンを指定してインストールする場合
Downloading node.js version 22.5.1 (64-bit)...

$ nvm install latest  # 最新バージョンをインストールする場合
Downloading node.js version 22.5.1 (64-bit)...

$ nvm install lts  # 最新の LTS 版をインストールする場合
Downloading node.js version 20.16.0 (64-bit)...
{{< /code >}}

現在インストールされている Node.js バージョンの一覧は __`nvm list`__ コマンドで確認できます。 

{{< code lang="console" title="インストール済みの Node.js バージョンの確認" >}}
$ nvm list
    22.5.1
    20.16.0
{{< /code >}}

使用する Node.js のバージョンを切り替えるには、__`nvm use`__ コマンドを使用します。

```console
$ nvm use 22      # nvm でインストールしたバージョン 22 の Node.js を使う
$ nvm use system  # スタンドアローン版としてインストールされた Node.js を使う
```

`nvm ls` コマンドの結果には、次のように各種エイリアス名が表示されることがあります。

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

現在アクティブになっているバージョンには、行頭に `->` が表示されています（あるいは  `*`）。
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

ちなみに、各バージョンの Node.js 実行環境は、`~/.nvm` ディレクトリ以下にインストールされていきます（`nvm root` でディレクトリパスを確認可能）。
`npm install -g` でグローバルインストールした NPM パッケージも、各バージョンごとに管理されます（`node_modules` ディレクトリの場所は `npm root -g` で確認できます）。
多くのバージョンの Node.js をインストールすると、ホームディレクトリのサイズが肥大化していくので注意してください（1 バージョンごとに 100 MB くらい消費していきます）。
必要のなくなった Node.js バージョンは、適宜 __`nvm uninstall`__ で削除しましょう。

```console
$ nvm uninstall 14
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

