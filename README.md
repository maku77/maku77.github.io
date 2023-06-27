Origin:「[天才まくまくノート](https://maku77.github.io)」


セットアップ ＆ Jekyll サーバー起動
----

```console
# 最初の一回だけ
$ bundle config set --local path 'vendor/bundle'
$ bundle install

# 次のパス指定方法は deprecated
# bundle install --path vendor/bundle

# Jekyll サーバー起動
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

