---
title: "Hugo で新規の Web サイトを作成する"
url: "p/bt5enw6/"
date: "2017-08-23"
tags: ["Hugo"]
aliases: /hugo/basic/create-site.html
---

ここでは、Hugo を使用して、新しく Web サイトを構築するための手順を示します。

{{< image border="true" src="img-001.png" title="Hugo で生成したサイトの例" >}}

サイトのスケルトンを生成する
----

まずは [Hugo をインストールして](/p/r8ufyk5/)、`hugo` コマンドを使用できる状態にしてください。
下記のように __`hugo new site`__ コマンドを使用して、Web サイトのひな型を作成することができます。

{{< code lang="console" title="新しい Web サイト (my_site) を作成する" >}}
$ hugo new site my_site
Congratulations! Your new Hugo site is created in C:\Users\maku\my_site.

Just a few more steps and you're ready to go:

1. Download a theme into the same-named folder.
   Choose a theme from https://themes.gohugo.io/, or
   create your own with the "hugo new theme <THEMENAME>" command.
2. Perhaps you want to add some content. You can add single files
   with "hugo new <SECTIONNAME>\<FILENAME>.<FORMAT>".
3. Start the built-in live server via "hugo server".

Visit https://gohugo.io/ for quickstart guide and full documentation.
{{< /code >}}

生成されるファイル群はものすごくシンプルで、下記のようにファイル２つと、いくつかのディレクトリだけです。

```
my_site/
  +-- config.toml （サイト全体の設定）
  +-- archetypes/ （新しいページを作成するときのひな形）
  |    +-- default.md
  +-- content/ （記事を格納。Markdown 形式のファイルを置いていく）
  +-- data/    （サイト生成時に利用するデータ）
  +-- layouts/ （レイアウトテンプレート）
  +-- static/  （画像ファイルなど、サイト生成時にそのままコピーするファイルを置く）
  +-- themes/  （テーマディレクトリを配置。実際にどのテーマを使用するかは config.toml で指定）
```


サイトのテーマをインストール、設定する
----

- 参考: [Hugo のテーマを設定する](/p/h2cku5d/)

Hugo で新規に Web サイトを生成したら、外観を定義するためのテーマを１つ設定します（何らかのテーマを設定しておかないと、生成されたサイトは空っぽ（真っ白）になってしまいます）。
`hugo new site` によるサイト生成では、テーマまでは生成されないので、[ネット上に公開されているテーマ](https://themes.gohugo.io/)をダウンロードしてきます。

ここでは、[mainroad というテーマ](https://themes.gohugo.io/themes/mainroad/) を使用してみましょう。

Hugo のテーマは、__`themes`__ ディレクトリの下に、テーマ用のディレクトリを作って保存します。
テーマ別にディレクトリを分けて保存するので、複数のテーマをあらかじめダウンロードしておくことができます（実際に使用するテーマは簡単に切り替えられます（後述））。
テーマは GitHub で配布されているので、[Git](/git/) がインストールされているのであれば、下記のように簡単にインストールできます。

```console
$ cd themes
$ git clone https://github.com/vimux/mainroad
```

`themes/mainroad` というディレクトリが作成されれば OK です。
あとは、実際にこのテーマを使用するように、Hugo の設定ファイル (__`config.toml`__) に下記の一行を追加してください。

{{< code lang="toml" title="config.toml（抜粋）" >}}
theme = "mainroad"
{{< /code >}}

これでテーマの設定は完了です。


サイトのタイトルと言語を設定する
----

サイトの設定ファイルでは、テーマ設定以外にも、サイトタイトル (__`title`__) や、全体で使用する言語 (__`languageCode`__) の設定を行っておきます。

{{< code lang="toml" title="config.toml" >}}
baseURL = "http://example.org/"
languageCode = "ja-jp"
title = "My New Hugo Site"
theme = "mainroad"
{{< /code >}}

{{% note %}}
ここで設定した値は、テンプレートファイルの中から、`{{ .Site.Title }}`、`{{ .Site.BaseURL }}`、`{{ .Site.LanguageCode }}` のように参照することができます。
{{% /note %}}


記事を作成する
----

- 参考: [Hugo で記事を作成する](/p/q7sdwgy/)

ここでは、サンプル記事を 1 つだけ作成しておきましょう。
次のように __`hugo new`__ コマンドを実行すると、__`content`__ ディレクトリ以下に記事ファイル（Markdown ファイル）が自動作成されます。
コマンドはプロジェクトのトップディレクトリで実行してください。

```console
$ hugo new post/sample.md
```

これで `content/post/sample.md` というファイルが生成されるので、下記のような感じになるように修正してください。

{{< code lang="md" title="content/post/sample.md" >}}
---
title: "記事のタイトル"
date: 2017-08-25T22:20:24+09:00
---

記事の本文
記事の本文
記事の本文
{{< /code >}}

ヘッダ部分に、`draft: true` という行があると、ページが出力されなくなってしまうので、その行は削除してください（参考: [Hugo でドラフトページを作成する](/p/m2oatdw/)）。


Hugo の Web サーバーを起動する
----

Web サイトのテーマ設定や記事作成が終わったら、__`hugo server`__ コマンドを実行して Web サーバーを起動します（実際には、Web サーバーは起動したままで記事の作成を行っていけます）。

```console
$ hugo server
Started building sites ...
Built site for language en:
0 draft content
0 future content
0 expired content
1 regular pages created
6 other pages created
0 non-page files copied
1 paginator pages created
0 tags created
0 categories created
total in 8 ms
Watching for changes in C:\Users\maku\my_site\{data,content,layouts,static,themes}
Serving pages from memory
Web Server is available at http://localhost:1313/ (bind address 127.0.0.1)
Press Ctrl+C to stop
```

Hugo サーバーの起動にかかった時間は、たったの 8ms です。爆速ですね。

Hugo サーバが起動した状態で、Web ブラウザから `http://localhost:1313/` にアクセスすると、サイトのトップページが表示されます。

{{< image border="true" src="img-001.png" >}}

あとは、`hugo new` コマンドを使って記事をどんどん作成していくだけです。
Hugo サーバは、`content` ディレクトリ内の変更を監視しており、記事が作成されたり修正されたりした場合に自動的にリロードしてくれるので、Hugo サーバは一度立ち上げたら立ち上げっぱなしで大丈夫です。

記事のカスタマイズ方法は、テーマごとに異なりますので、詳しくは各テーマのサイトを参照してください。

