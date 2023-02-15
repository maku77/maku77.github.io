---
title: "Hugo のテーマを設定する"
url: "p/h2cku5d/"
date: "2017-08-25"
tags: ["Hugo"]
aliases: /hugo/basic/theme.html
---

テーマをダウンロードする
----

Hugo で Web サイトを生成するときは、外観のベースとなる __テーマ__ を設定する必要があります。
テーマを設定せずに `hugo` コマンドで Web サイトを出力しても、真っ白なページが表示されてしまうだけです。
テーマは自分で作成することもできますが、[Hugo Themes](https://themes.gohugo.io/) というサイトに、有志の人によって作られたテーマが公開されていますので、まずはここからダウンロードして使ってみるのがよいでしょう。
使用方法はテーマごとに少しずつことなるので、詳細はそれぞれのテーマの説明 (`README.md`) を読んでください。

### テーマの ZIP ファイルをダウンロードする方法

例えば、下記の bootstrap4-blog というテーマがよさそうであれば、__Download__ ボタンを押します。

{{< image border="true" w="800" src="img-001.png" >}}

通常は、GitHub のサイトに飛ぶので、__Clone or download__ のボタンを押してテーマをダウンロードしてください。

{{< image border="true" w="800" src="img-002.png" >}}

ダウンロードしたテーマは、プロジェクトの __`themes`__ ディレクトリに配置します。
このケースでは、`themes/bootstrap4-blog` ディレクトリとして配置します。

### テーマを Git で取得する方法

テーマは GitHub 上で管理されていますので、[Git](/git/) コマンドが使える環境であれば、下記のように `git clone` してしまうのが簡単です（もちろん先にリポジトリの URL は調べておく必要はありますが）。

```console
$ git clone https://github.com/alanorth/hugo-theme-bootstrap4-blog.git themes/bootstrap4-blog
```

もし、自分のサイトを Git 管理しているのであれば、次のようにテーマを [Git サブモジュール](/p/dsctaq7/) として取り込んでしまうがよいです。
Git サブモジュールのコマンド (`git submodule`) はちょっと難しいですが、テーマをサブモジュールとして管理すれば、テーマ側の更新を適宜反映できるようになります。

```console
# テーマをサブモジュールとして取り込む
$ git submodule add https://github.com/alanorth/hugo-theme-bootstrap4-blog.git themes/bootstrap4-blog

# テーマ側の更新を取り込むとき
$ git submodule update --remote --recursive
```


（必要に応じて）テーマごとのセットアップ
----

テーマによっては、外部コンポーネントに依存していて、初期設定が必要なものもあります。
上記の bootstrap4-blog テーマでは、下記のように関連モジュールをインストールしてくださいと説明がありますので、その通りに実行しておきます。

```console
$ npm install
$ npm run build
```

ちなみに、`npm` は Node.js 用のパッケージマネージャで、[Node.js](https://nodejs.org) をインストールすれば一緒にインストールされます。


使用するテーマを指定する
----

テーマは `themes` ディレクトリにいくつでもダウンロードしておくことができます。
その中から実際に使用するテーマは、Hugo の設定ファイルの中で以下のように設定します。

{{< code lang="toml" title="config.toml（抜粋）" >}}
theme = "bootstrap4-blog"
{{< /code >}}

テーマを設定したら、`hugo` コマンドで Web サイトを生成してみてください。
設定したテーマをもとに、__`public`__ ディレクトリ以下にアップロード用のファイル群が出力されるはずです。

```console
$ hugo
```

`hugo server` コマンドで、Web サーバを立ち上げる場合にも `config.toml` で設定したテーマが使用されます。

`hugo` コマンドを実行するときに、__`-t`__ オプションで使用するテーマを指定することもできます。

```console
$ hugo -t theme_name        （サイトを出力する場合）
$ hugo server -t theme_name （Web サーバを立ち上げる場合）
```

