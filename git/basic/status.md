---
title: コミット対象になっているファイルを確認する (git status)
date: "2010-07-17"
---

`git status` コマンドを使用すると、次回のコミットの対象になっているファイルや、コミットの対象にはなっていないけれど編集されているファイルなどを確認することができます。
出力は大体以下のような感じになります。

~~~
$ git status

# Changed to be committed:
#     new file:   aaa.txt
#     new file:   bbb.txt
#     modified:   ccc.txt
#
# Changed but not updated:
#     modified:   ddd.txt
#     midified:   eee.txt
#     deleted:    fff.txt
# 
# Untracked files:
#     ggg.txt
#     hhh.txt
#     iii.txt
~~~

３つのセクションに分けてファイルが表示されますが、それぞれ下記のような意味を持っています。

### Changed to be committed

次回の `git commit` コマンドでローカルリポジトリにコミットされるファイルの一覧です。
新しくローカルリポジトリに追加されるファイル (new file)、変更されるファイル（modified)、削除されるファイル (deleted) などの情報も示されます。
カラー出力設定してある場合、ファイル名は緑色で表示されます。

### Changed but not updated

ローカルリポジトリの最新と比べてファイルが更新されているが、ステージ (`git add`) されておらず、コミットの対象になっていないファイルの一覧です。
`git add` することでコミットの対象になり、次回のコミットでローカルリポジトリに変更が反映されます。
カラー出力設定してある場合、ファイル名は赤色で表示されます。

### Untracked files

Git の管理対象になっていないファイルの一覧です。
例えば、新しく作成したファイルがここに表示されます。
`git add` することでコミットの対象になり、次回のコミットで new file としてローカルリポジトリに追加されます。
カラー出力設定してある場合、ファイル名は赤色で表示されます。

