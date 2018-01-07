---
title: Hugo で新規の Web サイトを作成する
date: "2017-08-23"
---

ここでは、Hugo を使用して、新しく Web サイトを構築するための手順を示します。

![create-site.png](./create-site.png){: .center}

サイトのスケルトンを生成する
----

まずは [Hugo をインストールして](install.html)、`hugo` コマンドを使用できる状態にしてください。
下記のように `hugo new site` コマンドを使用して、Web サイトのひな型を作成することができます。

#### 新しい Web サイト (my_site) を作成する

~~~
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
~~~

生成されるファイル群はものすごくシンプルで、下記のようにファイル２つと、いくつかのディレクトリだけです。

~~~
my_site/
  +-- config.toml （サイト全体の設定）
  +-- archetypes/ （新しいページを作成するときのひな形）
  |    +-- default.md
  +-- content/ （記事を格納。Markdown 形式のファイルを置いていく）
  +-- data/    （サイト生成時に利用するデータ）
  +-- layouts/  (レイアウトテンプレート）
  +-- static/  （画像ファイルなど、サイト生成時にそのままコピーするファイルを置く）
  +-- themes/  （テーマディレクトリを配置。実際にどのテーマを使用するかは config.toml で指定）
~~~


サイトのテーマをインストール、設定する
----

- 参考: [Hugo のテーマを設定する](theme.html)

Hugo で新規に Web サイトを生成したら、外観を定義するためのテーマを１つ設定します（何らかのテーマを設定しておかないと、生成されたサイトは空っぽ（真っ白）になってしまいます）。
`hugo new site` によるサイト生成では、テーマまでは生成されないので、[ネット上に公開されているテーマ](https://themes.gohugo.io/)をダウンロードしてきます。

ここでは、[mainroad というテーマ](https://themes.gohugo.io/mainroad/) を使用してみましょう。

Hugo のテーマは、`themes` ディレクトリの下に、テーマ用のディレクトリを作って保存します。
テーマ別にディレクトリを分けて保存するので、複数のテーマをあらかじめダウンロードしておくことができます（実際に使用するテーマは簡単に切り替えられます（後述））。
テーマは GitHub で配布されているので、[Git](/git/) がインストールされているのであれば、下記のように簡単にインストールできます。

~~~
$ cd themes
$ git clone https://github.com/vimux/mainroad
~~~

`themes/mainroad` というディレクトリが作成されれば OK です。
あとは、実際にこのテーマを使用するように、Hugo の設定ファイル (`config.toml`) に下記の一行を追加してください。

#### config.toml

~~~ toml
theme = "mainroad"
~~~

これでテーマの設定は完了です。


サイトのタイトルと言語を設定する
----

サイトの設定ファイルでは、テーマ設定以外にも、サイトタイトルや、全体で使用する言語の設定を行っておきます。

#### config.toml

~~~ toml
baseURL = "http://example.org/"
languageCode = "ja-jp"
title = "My New Hugo Site"
theme = "mainroad"
~~~

<div class="note">
ここで設定した値は、テンプレートファイルの中から、<code>{{ "{{" }} .Site.Title }}</code>、<code>{{ "{{" }} .Site.BaseURL }}</code>、<code>{{ "{{" }} .Site.LanguageCode }}</code> のように参照することができます。
</div>


記事を作成する
----

- 参考: [Hugo で記事を作成する](create-page.html)

ここでは、サンプル記事を１つだけ作成しておきましょう。
記事ファイルは下記のように `hugo new` コマンドを使用すれば、`content` ディレクトリ以下に自動作成されます（コマンドはプロジェクトのルートで実行してください）。

~~~
$ hugo new post/sample.md
~~~

このコマンドによって、`content/post/sample.md` というファイルが生成されるので、下記のような感じになるように修正してください。

#### content/post/sample.md

~~~ markdown
---
title: "記事のタイトル"
date: 2017-08-25T22:20:24+09:00
---

記事の本文
記事の本文
記事の本文
~~~

ヘッダ部分に、`draft: true` という行があると、ページが出力されなくなってしまうので、その行は削除してください（参考: [Hugo でドラフトページを作成する](draft.html)）。


Hugo の Web サーバーを起動する
----

Web サイトのテーマ設定や記事作成が終わったら、`hugo server` コマンドを実行して Web サーバーを起動します（実際には、Web サーバーは起動したままで記事の作成を行っていけます）。

~~~
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
~~~

Hugo サーバーの起動にかかった時間は、たったの 8ms です。爆速ですね。

Hugo サーバが起動した状態で、Web ブラウザから `http://localhost:1313/` にアクセスすると、サイトのトップページが表示されます。

![create-site.png](./create-site.png){: .center}

あとは、`hugo new` コマンドを使って記事をどんどん作成していくだけです。
Hugo サーバは、`content` ディレクトリ内の変更を監視しており、記事が作成されたり修正されたりした場合に自動的にリロードしてくれるので、Hugo サーバは一度立ち上げたら立ち上げっぱなしで大丈夫です。

記事のカスタマイズ方法は、テーマごとに異なりますので、詳しくは各テーマのサイトを参照してください（[mainroad テーマ](https://themes.gohugo.io/mainroad/)）。

