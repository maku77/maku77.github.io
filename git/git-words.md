---
title: "Git 関連用語"
date: "2010-07-17"
---

### 作業ツリー

Git の管理下に入ったローカルのディレクトリツリーを作業ツリーと呼びます。git init コマンドなどで作成される「.git」ディレクトリが存在するディレクトリが、作業ツリーのルートになります。Subversion や CVS では「作業コピー」と呼んでいました。

### リポジトリ

変更履歴などを管理するリポジトリで、Git では必ずローカルディレクトリに存在します。他のマシン上にあるリポジトリも、ローカルにあるリポジトリも対等の関係にあり、各リポジトリにコミットされた変更内容を互いにやりとりすることでプロジェクト全体の変更の一貫性を保ちます。一般的には、ある一つのマシンのリポジトリを中央リポジトリとし、そこから最終的なプロジェクトの成果物を生成します。リポジトリの実体は、作業ツリーのルートにある .git ディレクトリです。

### ステージする

あるファイルを次回のコミット対象に含めることを、ファイルをステージするといいます。Perforce を使ったことがある人は、p4 edit などによる Perforce サーバへの変更通知と近い概念なので分かりやすいと思います。Git では、git add や git rm コマンドでファイルを指定すると、その情報が Git の index に登録され、次回のコミット対象に入ります。

### ハンク (Hunk)

ファイル内の連続した変更箇所のこと。１つのファイル内に連続した変更箇所が複数あれば、ハンクも複数存在することになります。Git ではファイル単位で変更を追跡するのではなく、もっと細かいハンク単位で追跡します。つまり、ファイル内の変更の一部だけをコミットするという作業が行えます。

### チェックアウト

チェックアウトされているブランチとは、現在の作業ツリーに反映されているブランチのことで、作業対象になっているブランチのことをいいます。git init した後の初期状態で何かファイルをコミットすると、デフォルトで master というブランチが作成され、チェックアウトされた状態になります。

