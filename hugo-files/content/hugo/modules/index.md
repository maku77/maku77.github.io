---
title: "Hugo Modules の仕組みで Hugo サイト用のコンテンツを分割して管理する"
url: "p/bqar8o6/"
date: "2023-04-01"
tags: ["Hugo"]
---

Hugo Modules とは
----

[Hugo Modules](https://gohugo.io/hugo-modules/) の仕組みを使うと、異なるディレクトリに配置されたファイル群をまとめて、1 つの Hugo サイトを構成することができます。
Hugo Module の実体は、Hugo サイト用のファイルが格納されたディレクトリです。
Hugo Module は、ローカル PC 上のディレクトリであってもよいし、GitHub リポジトリ内のディレクトリであっても構いません（通常は GitHub で管理することになるでしょう）。

{{< mermaid >}}
graph RL
  subgraph my-hugo-module-1
    mod1("Hugo Module")
  end
  subgraph my-hugo-module-2
    mod2("Hugo Module")
  end
  subgraph my-hugo-site
    mod1_imported("Hugo Module")
    mod2_imported("Hugo Module")
  end
  mod1 -- import --> mod1_imported
  mod2 -- import --> mod2_imported

  classDef classMod fill:#36f,color:white,stroke:none
  class mod1,mod2,mod1_imported,mod2_imported classMod
{{< /mermaid >}}

この仕組みは、一見すると、Hugo テーマを Git サブモジュールとして取り込むのと同じように見えますが、Hugo Modules はより柔軟で、コンテンツを含むほとんどのファイルをインポートできます。
現状、下記の 7 つのディレクトリに格納されるファイルを、Hugo Module からインポートすることができます。

- __`archetypes`__ ... 複数サイトでコンテンツ作成用テンプレート (`.md`) を共有できます。
- __`assets`__ ... 複数サイトでビルド用のアセット（`.scss` など）を共有できます。
- __`content`__ ... 複数サイトでコンテンツファイル (`.md`) を共有できます。
- __`data`__ ... 複数サイトでデータ（`.yml`、`.json` など）を共有できます。複数の Hugo Module で定義されたデータをマージできます。
- __`i18n`__ ... 複数サイトで翻訳データ (`.yml`、`.json` など）を共有できます。複数の Hugo Module で定義された翻訳データをマージできます。
- __`layouts`__ ... 複数サイトでレイアウトやショートコード (`.html`) を共有できます。
- __`static`__ ... 複数サイトで静的なファイル（`.js` ファイルなど）を共有できます。

Hugo Modules の仕組みでテーマ系のファイル（`assets`、`layouts`、`static` など）をインポートするようにすれば、__もう Git サブモジュールの仕組みを使う必要はありません__。
Hugo Modules の特徴をまとめると次のような感じになります。
主に、従来の Git サブモジュールによるテーマ導入の仕組みとの比較です。

- Hugo Modules を使えば、前述のような様々なファイルを柔軟に組み合わせることができます。テーマのように完全な `layouts` を用意する必要はなく、必要なファイルだけを個別にインポートできます。可能性は無限大です。
- Hugo Modules を使えば、Git サブモジュールの複雑なコマンド (`git submodule`) を使う必要がありません。代わりに `hugo mod` コマンドを使う必要はありますが、Git サブモジュールの更新管理に比べれば分かりやすいです。
- Git サブモジュールを使用している場合、`git clone` 時に `--recurse-submodules` フラグを指定するなどしてサブモジュールの取り込みを忘れないよう注意する必要がありますが、Hugo Modules の場合は、単純に `git clone` して `hugo serve` するだけで、自動的に依存モジュールをインポートしてくれます（GitHub などから取得してくれる）。
- Hugo Module 内の各ディレクトリ（Golang 用語ではパッケージ）を、Hugo プロジェクト内のどのディレクトリに展開（マウント）するかを指定できます。Git サブモジュールの場合は、リポジトリ単位でしか展開先を指定できません。
- Hugo Modules を使うには、Golang の環境をインストールしておく必要があります。Golang の Go Modules という仕組みをベースにしているので、Golang を使ったことがないと、若干のとっつきにくさはあるかもしれません。とはいえ、基本的には Golang の知識は必要ありません。


事前準備
----

### Golang のインストール

Hugo Modules の仕組みは、Golang（Go 言語）の Go Modules という仕組みを利用して提供されています。
Hugo Modules の仕組みを使用するには、__Golang の実行環境をインストール__ しておく必要があります。

- [Download and install - The Go Programming Language](https://go.dev/doc/install)

Golang のインストールが完了すると、Hugo Modules 用のサブコマンド (__`hugo mod`__) などが正しく動作するようになります。
逆に、Golang がインストールされていない環境で Hugo Modules を使用する Hugo サイトをビルドしようとすると、`go` コマンドがないというエラーになります（例: `Error: failed to download modules: binary with name "go" not found`）。

### Hugo プロジェクトの作成

テスト用に Hugo サイト（プロジェクト）を作成しておきます。

```console
$ cd ~
$ hugo new site my-hugo-site
```


Hugo サイトを Hugo Module 化する
----

既存の Hugo サイトで Hugo Modules の仕組みを使用するには、__`hugo mod init`__ コマンドで、その Hugo サイト自体も Hugo Module として初期化しておく必要があります。
これにより、他の Hugo Module を GitHub などから自動取得できるようになります。

Hugo Module には、（Go Modules の仕組み上）__一意のモジュール名__ を設定しておく必要があり、通常は GitHub リポジトリのパスを __`github.com/USER/REPO`__ のような感じで指定します（`USER` と `REPO` の部分は適切に置き換えてください）。

{{< code lang="console" title="Hugo サイトを Hugo Module 化">}}
$ cd ~/my-hugo-site
$ hugo mod init github.com/USER/my-hugo-site
{{< /code >}}

{{% note title="実はモジュール名は何でもよい" %}}
上記の説明では、Hugo Module には `github.com/<USER>/<REPO>` のような一意の名前を付ける必要があると記述しましたが、正確に言うと、これはインポートされる側の Hugo Module の要件です。
なので、実際には Hugo サイト側（他の Hugo Module をインポートする側）のモジュール名は何でもよかったりします（単純に `mysite` など）。
{{% /note %}}

Hugo Module 化といっても、このコマンドは、次のような __`go.mod`__ というファイルを生成するだけです。
最初は、自身のモジュール名と使用した Golang バージョンくらいしか記述されていませんが、このあと Hugo Module を追加するたびにこのファイルに依存関係が追加されていきます。

{{< code title="go.mod" >}}
module github.com/USER/my-hugo-site

go 1.18
{{< /code >}}


Hugo Modules の仕組みでテーマをインポートする
----

[GitHub 上で公開されている Hugo テーマ](https://themes.gohugo.io/) を Hugo Module としてインポートするには、Hugo の設定ファイル（`hugo.toml` や `config.toml`）内に次のような __`module`__ セクションを追加します。
ここでは、[PaperMod](https://github.com/adityatelange/hugo-PaperMod/) というテーマを使ってみます。

{{< code lang="toml" title="hugo.toml に追加" >}}
[module]
  [[module.imports]]
    path = 'github.com/adityatelange/hugo-PaperMod'
{{< /code >}}

これにより、GitHub リポジトリ上のファイル群が Hugo サイトにマージされてビルドされることになるので、`hugo.toml` 内のテーマ名指定（`theme = 'PaperMod'` など）は必要ありません。
もっと言うと、`themes` ディレクトリ自体必要ありません。

インポート対象の GitHub リポジトリ（Hugo テーマ）は、Hugo Module として初期化 (`hugo mod init`) されていなくてもインポートできるようになっています。
つまり、現在公開されている Hugo テーマは、すべてこの仕組みでインポートできます。
ただし、依存関係を正しく管理するため、Hugo テーマは Hugo Module として初期化しておく（つまり、`go.mod` を配置しておく）ことが推奨されています。

Hugo Module のインポート設定ができたら、Hugo サーバーを起動して正しく Hugo Module がダウンロードされるか確認します。

{{< code lang="console" title="Hugo サーバーの起動" >}}
$ hugo serve
{{< /code >}}

`http://localhost:1313` にアクセスして、次のように正しくテーマが反映されていれば成功です。

{{< image border="true" w="500" src="img-001.png" title="Hugo Modules の仕組みでテーマをインポートできた" >}}


コンテンツを提供する Hugo Module を作ってみる
----

今度は、コンテンツ（`.md` ファイル）を提供する Hugo Module を新しく作成してインポートしてみます。
ここでは、最終的に GitHub の `https://github.com/USER/my-hugo-module` というリポジトリで管理するものとして、モジュール名を付けておきます。

{{< code lang="console" title="共有可能な Hugo Module を作成する" >}}
$ mkdir ~/my-hugo-module
$ cd ~/my-hugo-module
$ hugo mod init github.com/USER/my-hugo-module
{{< /code >}}

次に、この Hugo Module が提供するコンテンツを作成します。

{{< code lang="yaml" title="~/my-hugo-module/content/posts/hello.md" >}}
---
title: "Hello"
date: "2023-04-01"
---

Hello, Hugo Modules!
{{< /code >}}

これだけで、Hugo Module は完成です。
Hugo サイトの本体側 (`my-hugo-site`) から、この Hugo Module をインポートするよう設定します。

{{< code lang="toml" title="~/my-hugo-site/hugo.toml（抜粋）" hl_lines="4 5" >}}
[module]
  [[module.imports]]
    path = 'github.com/adityatelange/hugo-PaperMod'
  [[module.imports]]
    path = 'github.com/USER/my-hugo-module'
{{< /code >}}

この時点では、この Hugo Module はまだ GitHub 上で管理していないので、Hugo サイトをビルドしようとすると Hugo Module をダウンロードできないというエラーになります。

```console
$ cd ~/my-hugo-site
$ hugo
...
	remote: Repository not found.
	fatal: repository 'https://github.com/USER/my-hugo-module/' not found
```

そこで、一時的に GitHub の URL（モジュールパス）をローカル PC のディレクトリパスに置き換える、Go Modules の __replacement__ 機能を使用します。
置換内容は、Hugo 専用の __`HUGO_MODULE_REPLACEMENTS`__ 環境変数で設定できます。

{{< code lang="console" title="ローカル PC 上の Hugo Module を参照して起動" >}}
$ export HUGO_MODULE_REPLACEMENTS="github.com/USER/my-hugo-module -> /Users/maku/my-hugo-module"
$ hugo serve
{{< /code >}}

複数の置換設定を行いたいときは、カンマで区切って指定すれば OK です。
これで、正しくすべての Hugo Module をインポートできるようになり、Hugo サイトのビルドに成功するはずです。
今回のケースでは、`http://localhost:1313/posts/hello/` にアクセスして記事が表示されれば成功です。

{{< image border="true" w="550" src="img-002.png" title="コンテンツ用の Hugo Module もインポートできた" >}}

ちなみに、replacement の置換内容は、[go.mod ファイル内の replace ディレクティブ](https://go.dev/ref/mod#go-mod-file-replace) でも設定可能ですが、最終的にはこの設定は削除して、正しく GitHub 上のリポジトリを参照するように戻さなければいけないので、`HUGO_MODULE_REPLACEMENTS` 環境変数を使った方が都合がよいと思います。


Hugo Module の依存関係を確認する
----

__`hugo mod graph`__ コマンドを使用すると、（Hugo Module 化した）Hugo サイトが依存している Hugo Module の一覧を確認できます。

{{< code lang="console" title="Hugo Module の依存グラフを表示" >}}
$ cd ~/my-hugo-site
$ hugo mod graph
github.com/USER/my-hugo-site github.com/adityatelange/hugo-PaperMod@v0.0.0-20230331160356-2210bf20b365
github.com/USER/my-hugo-site /Users/maku/my-hugo-module
{{< /code >}}

この例では、Hugo サイト (`my-hugo-site`) が、2 つの Hugo Module（`hugo-PaperMod` と `my-hugo-module`）に依存していることが分かります。


Hugo Module の更新を取り込む
----

GitHub 上で管理されている Hugo Module のファイルが更新された場合に、それを Hugo サイト側に反映するには、__`hugo mod get -u`__ コマンドを使用します。

{{< code lang="console" title="Hugo Module の更新" >}}
$ hugo mod get -u ./...  # すべての Hugo Module を再帰的に更新
$ hugo mod get -u        # すべての Hugo Module を更新
$ hugo mod get -u github.com/USER/my-hugo-module  # 指定した Hugo Module を更新
$ hugo mod get -u github.com/USER/my-hugo-module@v.1.0.2  # バージョンを指定
{{< /code >}}


（応用）各ディレクトリのマウント先を変更する
----

Hugo Modules は、複数のディレクトリに散らばったファイルを集めて 1 つの Hugo サイトとしてビルドする仕組みなので、どうしてもファイル名の競合が発生してしまう可能性があります。
`content` ディレクトリ以下の記事ファイルや、`static` ディレクトリ以下の静的ファイルの名前が、複数の Hugo Module の間で競合してしまった場合、最初に見つかったファイルがビルド対象になります。

例えば、Hugo サイト本体 (`my-hugo-site`) から、2 つの Hugo Module（`my-hugo-module-1`、`my-hugo-module-2`）をインポートしているとして、それぞれが次のように競合する名前（パス）のファイルを持っているとすると、採用されるのは Hugo サイト本体に置かれたファイルだけです。

- `my-hugo-site/static/js/hello.js` ... `http://YOUR-SITE/js/hello.js` で見えるのはこのファイル
- `my-hugo-module-1/static/js/hello.js` ... このファイルは見えなくなる
- `my-hugo-module-2/static/js/hello.js` ... このファイルも見えなくなる

このように、インポートした Hugo Module のファイルが見えなくなると都合が悪い場合は、Hugo サイトの設定ファイル（`hugo.toml` や `config.toml`）の __`[[module.imports.mounts]]`__ セクションで、ディレクトリ単位でマウント先を変更します。

{{< code lang="toml" title="hugo.toml（抜粋）" >}}
[module]
  [[module.imports]]
    path = 'github.com/USER/my-hugo-module-1'
    [[module.imports.mounts]]
      source = 'static/js'
      target = 'static/mod1/js'
  [[module.imports]]
    path = 'github.com/USER/my-hugo-module-2'
    [[module.imports.mounts]]
      source = 'static/js'
      target = 'static/mod2/js'
{{< /code >}}

これで、各 Hugo Module のディレクトリが別々のディレクトリにマウントされ、次のような URL でアクセスできるようになります。

- `http://YOUR-SITE/js/hello.js` ... Hugo サイト本体の JS ファイル
- `http://YOUR-SITE/mod1/js/hello.js` ... my-hugo-module-1 の JS ファイル
- `http://YOUR-SITE/mod2/js/hello.js` ... my-hugo-module-2 の JS ファイル

上記の設定例では、`static/js` ディレクトリのマウント先を変更していますが、親ディレクトリの `static` ディレクトリごとマウント先を変更してしまっても OK です。


（応用）GitHub Actions の設定
----

GitHub Actions を使って Hugo サイトをビルドしている場合は、ワークフロー内で Golang 環境をセットアップするよう指定しておく必要があります。
このセットアップを忘れると、`hugo` コマンドによるビルド時に次のようなエラーになります（サイト自体を Hugo Module として初期化していない場合も同様のエラーが発生します）。

```
Error: module "github.com/USER/my-hugo-module" not found;
either add it as a Hugo Module or store it in "/home/runner/work/my-hugo-site/my-hugo-site/themes".:
module does not exist
```

ワークフローファイルの中で、[actions/setup-go](https://github.com/actions/setup-go) アクションを指定するだけで、Golang の実行環境は簡単にインストールすることができます。
あとは、今まで通り Hugo によるビルドを行うだけで、依存する Hugo Module を自動でダウンロードしてビルドしてくれます。

{{< code lang="yaml" title=".github/workflows/hugo-build.yml" hl_lines="16-19" >}}
name: Build Hugo site

on:
  push:
    branches: [main, master]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
      with:
        submodules: 'recursive'  # Fetch Hugo themes (true OR recursive)
        fetch-depth: 0  # Fetch all history for .GitInfo and .Lastmod

    - name: Setup Go environment
      uses: actions/setup-go@v4.0.0
      with:
        go-version: '^1.20'

    - name: Setup Hugo environment
      uses: peaceiris/actions-hugo@v2.6.0
      with:
        hugo-version: '0.111.3'
        extended: true  # Enable scss

    - name: Build Hugo site
      run: hugo --minify

    # ...
{{< /code >}}


{{% private %}}
- （応用）Git サブモジュールで追加している Hugo テーマを Hugo Module に置き換える
{{% /private %}}

