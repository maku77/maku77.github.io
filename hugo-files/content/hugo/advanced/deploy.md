---
title: "hugo deploy コマンドで Azure などのクラウドサービス上にデプロイする"
url: "p/2ycmvbn/"
date: "2020-03-25"
tags: ["Hugo"]
description: "Hugo は、各種クラウド (GCS, AWS S3, Azure Storage) 上のストレージにデプロイするためのコマンドを標準で搭載しています。"
changes:
  - 2023-04-17: 設定ファイル名を config.toml から hugo.toml に変更
aliases: /hugo/advanced/deploy.html
---

Hugo は、各種クラウド (Azure Storage, Google Cloud Storage, AWS S3) 上のストレージにデプロイするためのコマンドを標準で搭載しています。

hugo deploy コマンドとは
----

静的なウェブサイトをホスティングするために、次のような有名どころのストレージサービスを使用している人は多いと思います。

- Microsoft の Azure Storage (BLOB Storage)
- Google の GSC (Google Cloud Storage)
- Amazon の AWS S3

Hugo が搭載している __`hugo deploy`__ コマンドを使用すると、プロジェクトの設定ファイル (`hugo.toml`) に記述されたデプロイ設定に基づき、これらのサービスへのデプロイを行えるようになります。
もちろん、各種ストレージサービス用の CLI（コマンドラインツール）を使ってもデプロイすることはできるのですが、`hugo deploy` を使うことでデプロイ方法が単純化（統一化）され、余計なコマンドラインツールをインストールしなくてもデプロイを実行できるという利点があります。

ここでは、Azure のストレージコンテナ（BLOB ストレージ）に Hugo で生成した Web コンテンツをデプロイする方法を説明します。


Azure のストレージコンテナにデプロイする
----

### hugo deploy の設定

[Azure Storage](https://azure.microsoft.com/ja-jp/services/storage/) は、簡単に静的な Web サイトをホスティングする機能を提供しています。
ここでは詳細な手順は記載しませんが、下記サイトのように、(1) ストレージアカウントの作成、(2) 静的な Web サイトを有効化、という 1 分くらいの手順で Web サイトを立ち上げることができます。
利用料金も、小規模なサイトであれば月額 100 円もかからないくらいリーズナブルです。

- 参考: [Azure Storage で静的 Web サイトをホスティングする｜まくろぐ](https://maku.blog/p/gkardu9/)

Azure Storage で静的な Web サイトを有効化すると、`$web` という名前のストレージコンテナが生成されるので、ここにデプロイするための設定を設定ファイル (__`hugo.toml`__) に追記します。

{{< code lang="toml" title="hugo.toml（追記）" >}}
[[deployment.targets]]
URL = "azblob://$web"
{{< /code >}}

あとはデプロイ先を示す Azure Storage のストレージアカウント名と、アクセス用のキーを、次のような環境変数で設定します。

- 環境変数 __`AZURE_STORAGE_ACCOUNT`__: ストレージアカウント名
- 環境変数 __`AZURE_STORAGE_KEY`__: ストレージキー（代わりに SAS トークンを使いたい場合は `AZURE_STORAGE_SAS_TOKEN` を設定します）

設定は最低限これだけしておけばよいので、とてもシンプルです。
さらに細かい設定については [公式サイト](https://gohugo.io/hosting-and-deployment/hugo-deploy/) を参照してください。

### hugo deploy の実行

あとは、次のように `hugo` コマンドで Web サイトをビルドし、`hugo deploy` コマンドでデプロイを実行できます。

```console
# サイトをビルド（public ディレクトリを生成）
$ hugo
Building sites …

# デプロイを実行（public ディレクトリの中身をアップロード）
$ hugo deploy
Deploying to target "" (azblob://$web)
Identified 38 file(s) to upload, totaling 6.0 MB, and 0 file(s) to delete.
Success!
```

今回はデプロイ先が 1 つなので、ターゲット名の設定を省略しており、ログにもターゲット名が出力されていません。
差分アップロードもちゃんとできているようです（`azcopy sync` に相当）。
コンテンツに更新がない場合は、次のようにアップロード処理をスキップしてくれます。

```console
$ hugo deploy
Deploying to target "" (azblob://$web)
No changes required.
```

ちなみに、プロキシ環境から `hugo deploy` を実行したいときは、`http_proxy` 環境変数を設定しておけば OK です。

### （おまけ）アップロード用のバッチファイル

下記は、`hugo` コマンドによるサイトのビルドと、`hugo deploy` によるデプロイをまとめて実行する Windows バッチファイルの例です。
`AZURE_STORAGE_ACCOUNT`、`AZURE_STORAGE_KEY` などの汎用的な環境変数をシステム全体に設定してしまうのが嫌な場合は、下記のように独自の環境変数に設定した値を伝搬させて設定するとよいでしょう。

{{< code lang="bat" title="deploy.bat" >}}
@echo off
setlocal
set AZURE_STORAGE_ACCOUNT=%MAKU_AZURE_STORAGE_ACCOUNT%
set AZURE_STORAGE_KEY=%MAKU_AZURE_STORAGE_KEY%
if defined MAKU_AZURE_STORAGE_PROXY (
    set http_proxy=%MAKU_AZURE_STORAGE_PROXY%
)
hugo
hugo deploy
{{< /code >}}

