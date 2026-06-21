# maku77.github.io

「[天才まくまくノート](https://maku77.github.io)」のコンテンツを管理するためのリポジトリです。
主にプログラミングやシステムトレードなどに関する記事を書いています。
Web サイトは下記の URL で公開しています。

- https://maku77.github.io

## ビルド

[Hugo](https://gohugo.io/) を使用した静的サイトとしてビルドしています。
テーマは Hugo Module として管理しており、[hugo-module-maku-common](https://github.com/maku77/hugo-module-maku-common) および本サイト専用のテーマを組み合わせて使用しています。

サイトをビルドするには、あらかじめ [Hugo (extended)](https://gohugo.io/installation/) と [Go 言語](https://go.dev/) をインストールしておく必要があります。
次のように Hugo ローカルサーバーを起動して表示を確認できます。

```bash
cd hugo-files
hugo server
```

ブラウザで `http://localhost:1313` を開くとサイトを確認できます。

## ディレクトリ構成

```
hugo-files/    # Hugo プロジェクトのルート
  content/     # 記事コンテンツ（Markdown）
  assets/      # CSS・画像などのアセット
  static/      # 静的ファイル
  themes/      # ローカルテーマ
  hugo.toml    # Hugo 設定ファイル
```

