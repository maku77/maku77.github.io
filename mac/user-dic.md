---
title: "Mac のユーザ辞書をバックアップ、リストアする"
date: "2018-10-20"
---

「システム環境設定」の「キーボード」→「ユーザ辞書」タブで登録したユーザ辞書のエントリは下記のようにしてバックアップ＆リストアすることができます。

ユーザ辞書のバックアップ
----

1. 単語リストの一覧で <kbd>Ctrl-A</kbd> を押して全項目を選択する。
2. Finder の任意のフォルダへドラッグ＆ドロップ。

これで、**ユーザ辞書.plist** というファイル名でユーザ辞書が保存されます。


ユーザ辞書のリストア
----

1. **ユーザ辞書.plist** ファイルを単語リストの一覧へドラッグ＆ドロップ。

すでに登録されている用語に関しては重複登録はされないので、安心して上書き追加できます。


plist ファイルのフォーマット
----

ちなみに、plist ファイルのフォーマットは、下記のような XML 形式になっており、直接編集することが可能です。

~~~ xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<array>
	<dict>
		<key>phrase</key>
		<string>暗槓</string>
		<key>shortcut</key>
		<string>あんかん</string>
	</dict>
	<dict>
		<key>phrase</key>
		<string>暗刻</string>
		<key>shortcut</key>
		<string>あんこう</string>
	</dict>
	<dict>
		<key>phrase</key>
		<string>一翻</string>
		<key>shortcut</key>
		<string>いーはん</string>
	</dict>
	<dict>
		<key>phrase</key>
		<string>一翻</string>
		<key>shortcut</key>
		<string>いーふぁん</string>
	</dict>
</array>
</plist>
~~~

