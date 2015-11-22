---
title: ruby-mp3info で MP3 ファイルのタグ情報を取得／修正する
created: 2015-10-28
---

MP3 ファイルの ID3 タグについて
====

MP3 の ID3 タグ (ID3v1/ID3v2) に関しては、下記の Wikipedia のサイトがわかりやすいです。

* [ID3タグ - Wikipedia](https://ja.wikipedia.org/wiki/ID3%E3%82%BF%E3%82%B0)

タグ内のエンコーディング形式
----
* ID3v1 -- 定義なし
* ID3v1.1 -- 定義なし
* ID3v2.2 -- UTF-16
* ID3v2.3 -- UTF-16
* ID3v2.4 -- UTF-16 および UTF-8

ID3v1 規格の頃のエンコーディング形式には規定がなく、昔 Windows で作成した MP3 ファイルが Shift_JIS でタグ情報を保存していたりすると、Unicode を前提とした音楽プレイヤーなどで文字化けが発生したりします。
現状では、最も普及している ID3v2.3 を使い、UTF-16 で保存しておくのがよいようです。


ruby-mp3info のインストール
====
moumar さんの **ruby-mp3info** という Gem パッケージを使用すると、MP3 ファイルのタグ情報を簡単に扱うことができます。

* [Documentation for ruby-mp3info](http://www.rubydoc.info/github/moumar/ruby-mp3info/)

Ruby スクリプトから `require 'mp3info'` とするためには、まずは Gem パッケージをインストールしておく必要があります。

```
$ sudo gem install mp3info
```

タグ情報の取得
====

`Mp3Info` クラスを使って MP3 ファイルをオープンすると、タグ情報（ID3v1 および ID3v2）にアクセスすることができます。

タグの有無を調べる
----
`Mp3Info#hastag1?` メソッド、および、`Mp3Info#hastag2` メソッドを使用すると、その MP3 ファイルに ID3v1 タグ、ID3v2 タグが含まれているかを調べることができます。

```ruby
require 'mp3info'

Mp3Info.open('sample.mp3') do |mp3|
  if mp3.hastag1?
    puts 'ID3v1 情報が見つかりました'
  end
  if mp3.hastag2?
    puts 'ID3v2 情報が見つかりました'
  end
end
```

ID3v1 タグの情報を取得する
----
はるか昔に使用されていた ID3v1 タグの情報は、`Mp3Info#tag1` ハッシュを参照することで取得することができます。
ID3v1 規格で定義されているタグ情報は、mp3info ライブラリでは下記のようなプロパティ名で取得できるようになっています。

| プロパティ名 | 規格上の定義 |
| ---- | ---- |
| title | 曲名文字列 |
| artist | アーティスト文字列 |
| album | アルバム文字列 |
| year | 日付文字列（4 バイトなので年のみ）|
| comments | コメント文字列 |
| tracknum | トラック番号 |
| genre※ | ジャンル番号 |

※ジャンル名に関しては、ID3v1 規格上では 1 バイトのジャンル ID ですが、`genre` プロパティの代わりに、`genre_s` プロパティを参照することで、テキスト表現のジャンル名を取得することができます。

#### sample.rb
```ruby
require 'mp3info'

Mp3Info.open('sample.mp3') do |mp3|
  mp3.tag1.each do |key, val|
    puts "#{key}: #{val}"
  end
end
```

ID3v2 タグの情報を取得する
----
ID3v2 規格のタグ情報は、`Mp3Info#tag2` ハッシュを参照することで取得できます。
ID3v2 規格では、それぞれのフィールドごとに**フレーム ID** という識別子が定義されているため、そのフレーム ID をキーとしてアクセスすることができます（フレーム ID は ID3v2.2 の場合は 3 文字、ID3v2.3 と ID3v2.4 の場合は 4 文字の英数字です）。


#### sample.rb
```ruby
require 'mp3info'

Mp3Info.open('sample.mp3') do |mp3|
  mp3.tag2.each do |key, val|
    puts "#{key}: #{val}"
  end
end
```

#### 実行例
```
$ ruby sample.rb
TPE1: 高須光聖, 長谷川朝二
TIT2: 「松本人志の放送室」CD-ROMリリース記念特別番組
TALB: 「松本人志の放送室」CD-ROMリリース記念特別番組
TSSE: Lavf52.67.0
COMM: 2013年03月27日
TCON: TOKYO FM
TDRC: 2013
```

バージョンを意識せずにタグ情報を取得する
----
ID3 タグのバージョンを意識せずにタグ情報を取得したいときは、`Mp3Info#tag` ハッシュを参照します。
ファイルに、ID3v1 と ID3v2 の両方の情報がある場合は、ID3v2 の情報が優先的に取得されます（一般的が音楽プレーヤーと同じ振る舞いです）。
`Mp3Info#tag` で参照可能な情報は、ID3v1 の頃から定義されていたもの限られるため、下記のような情報に限られています。

| プロパティ名 | 内容 | 対応する IDv2.2 のフレーム ID | 対応する IDv2.3/v2.4 のフレーム ID |
| ---- | ---- | ---- | ---- |
| title | 曲名 | "TT2" | "TIT2" |
| artist | アーティスト名 | "TP1" | "TPE1" |
| album | アルバム名 | "TAL" | "TALB" |
| year | 年 | "TYE" | "TYER" |
| tracknum | トラック番号 | "TRK" | "TRCK" |
| comments | コメント | "COM" | "COMM" |
| genre_s | ジャンル名 | "TCO" | "TCON" |
| genre | ジャンル番号 (ID3v1のみ) | ─ | ─ |

```ruby
require 'mp3info'

Mp3Info.open('sample.mp3') do |mp3|
  mp3.tag.each do |key, val|
    puts "#{key}: #{val}"
  end
end
```

なお、`Mp3Info#tag` ハッシュ内のプロパティ名が、どのフレーム ID にマッピングされるかは、`Mp3Info::TAG_MAPPING_2_3` というテーブルで調べることができます。

```ruby
puts Mp3Info::TAG_MAPPING_2_3["title"]  # => "TIT2"
```


MP3 のタグ情報を編集する
====
`Mp3Info.open` で MP3 ファイルを開いたあとに、`Mp3Info#tag1` および `Mp3Info#tag2` ハッシュの値を書き換えると、ファイルを閉じたタイミングでタグ情報が書き換えられます。
例えば、下記のようにすると、MP3 ファイルのタイトル、および、アーティスト名が書き換えられます。

```ruby
require 'mp3info'

Mp3Info.open('sample.mp3') do |mp3|
  mp3.tag2.TIT2 = 'タイトル'
  mp3.tag2.TPE1 = 'アーティスト'
end
```

`tag` プロパティは読み取り専用なので、タグ情報を書き換えるときは、`tag1` か `tag2` プロパティを使わないといけないことに注意しましょう。通常は上記のように `tag2` を使えば OK です。

`Mp3Info::TAG_MAPPING_2_3` テーブルから得られるフレーム ID を利用して、下記のように書くこともできます。

```ruby
require 'mp3info'

Mp3Info.open('sample.mp3') do |mp3|
  mp3.tag2[Mp3Info::TAG_MAPPING_2_3.title] = 'タイトル'
  mp3.tag2[Mp3Info::TAG_MAPPING_2_3.artist] = 'アーティスト'
end
```

ID3v1 の情報を ID3v2 のタグ情報としてコピーする
----

昔の MP3 ファイルには ID3v1 情報しか入っていないことがあります。
下記のコードは、ID3v2 情報がない場合に、対応する ID3v1 情報をコピーして格納します。

#### update-mp3-info.rb
```ruby
require 'mp3info'

def update_info(mp3)
  mp3.tag.each do |key, val|
    if Mp3Info::TAG_MAPPING_2_3.has_key?(key)
      next if mp3.tag2.has_key?(key)
      frame = Mp3Info::TAG_MAPPING_2_3[key]
      mp3.tag2[frame] = val
    end
  end
end

Mp3Info.open('sample.mp3') do |mp3|
  update_info mp3
end
```

ID3v1 から ID3v2 にコピーされる情報は、`Mp3Info::TAG_MAPPING_2_3` テーブルに定義されている情報のみ、つまり、「タイトル」、「アーティスト」、「アルバム」、「年」、「トラック番号」、「コメント」、「ジャンル名」です。
すでに ID3v2 タグとして情報を持っている場合は、コピーしません。

