「[天才まくまくノート](https://maku77.github.io)」のサイトを管理するためのリポジトリです。
プログラミングやシステムトレードなどに関する記事を書いています。Web サイトは下記から見ることができます。

- https://maku77.github.io/


セットアップ＆ビルド
----

```console
$ bundle install --path vendor/bundle
$ ./run.sh
```


Windows PC で Jekyll サーバーを立てる場合のメモ
----

ローカルの Windows PC で Jekyll サーバーを立てるときは、https://rubyinstaller.org/ から Ruby+Devkit をダウンロードして、とにかく全部インストール。
あとは `bundle install` すれば、`run.bat` でサーバー起動できます。

もし実行中に、

```
`require': cannot load such file -- webrick (LoadError)
```

などのエラー出たら、`bundle add` で追加します。

```
C:\> set http_proxy=http://proxy.example.com:7890/  （必要に応じて）
C:\> bundle add webrick
```

追加した内容は `Gemfile` に記録されるので、次回からは `bundle install` で OK。

