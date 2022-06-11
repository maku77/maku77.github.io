---
title: "Docker メモ"
layout: category-index
---

Docker をはじめる（インストールと設定）
----
* [Docker をインストールする (Docker Desktop / Docker Engine)](install)
* [Docker のプロキシ設定](/p/w69cfim/)
* [一般ユーザから docker コマンドを実行できるようにする](run-docker-without-root.html)


Docker の基本的な使い方
----
* [Docker で Hello World 〜 イメージの取得からコンテナの起動まで (docker image pull, docker container run)](/p/z8behko/)
* [Docker コンテナで起動したシェルに接続する (docker container run/start/exec/attach)](/p/y8cfimp/)
* [Docker コンテナをデーモンとして動作させる (docker container run -d, docker container logs)](/p/dmpsvz3/)
* [Docker チートシート](cheatsheet.html)


Docker コマンドによる操作
----

### イメージ (docker image)

* [Docker イメージを作成する (docker image build, docker container commit)](create-image.html)
* [Docker イメージを削除する (docker image rm)](/p/8fjnqtw/)
  * [（応用）リポジトリ名のついていない Docker イメージをすべて削除する (docker image rm)](remove-unnamed-images.html)
* [Docker イメージを Docker Hub に登録する](register-image-to-dockerhub.html)
* [Docker のマルチステージビルドで軽量のアプリ実行用イメージを作成する](multistage-build.html)

### コンテナ (docker container)
* [すべての Docker コンテナを停止／削除する (docker container stop/rm)](/p/6ehmpsv/)
* [Docker コンテナ内で動作しているプロセスの一覧を表示する (docker container top)](/p/s3m4jyg/)
* [Docker コンテナの詳細情報を表示する (docker container inspect)](inspect-container.html)
* [Docker コンテナ側の公開ポートがホスト側のどのポートにマッピングされているか調べる (docker container port)](/p/ow258be/)

### マウント（ボリューム、バインドマウント、tmpfs マウント）
* [Docker のマウント機能でファイルを永続化する（ボリューム、バインドマウント、tmpfsマウント）](mount)
* [Docker コンテナとホスト PC の間でファイルをコピーする (docker cp)](docker-cp.html)


トラブルシューティング
----
* [Docker で apt-get install するときに TERM 系のエラーが出る](term-error.html)
* [Dockerfile からの apt-get install で zlib1g-dev:i386 がインストールできないとき](zlib-error.html)


その他
----
* [Android ビルド環境を構築するための Dockerfile](dockerfile-for-android.html)

