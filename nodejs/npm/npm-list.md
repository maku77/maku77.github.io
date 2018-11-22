---
title: "npm コマンドでインストールされている Node.js 用パッケージの一覧を表示する (npm list, ll)"
date: "2018-11-22"
---

`npm install` によってインストールされた Node パッケージの一覧は、**`npm list`** コマンドで確認することができます（エイリアスとして `npm ls` も使用可能）。

~~~
$ npm list      # ローカルモードでインストールしたパッケージを一覧表示
$ npm list -g   # グローバルモードでインストールしたパッケージを一覧表示
~~~

各パッケージが依存しているパッケージに関しても、階層構造ですべて表示してくれます。

#### 実行例（require パッケージがインストールされている場合）

~~~
D:\y\sandbox> npm list
D:\y\sandbox
`-- require@2.4.20
  +-- std@0.1.40
  `-- uglify-js@2.3.0
    +-- async@0.2.10
    +-- optimist@0.3.7
    | `-- wordwrap@0.0.3
    `-- source-map@0.1.43
      `-- amdefine@1.0.1
~~~

`npm list` の代わりに、**`npm la`** や **`npm ll`** を使用すると、より詳細な情報を表示することができます。

~~~
D:\y\sandbox> npm ll

| D:\y\sandbox
|
`-- require@2.4.20
  | javascript module management! brings node's require statement to the browser
  | git://github.com/marcuswestin/require.git
  | https://github.com/marcuswestin/require
  +-- std@0.1.40
  |   javascript standard library
  |   git://github.com/marcuswestin/std.js.git
  |   https://github.com/marcuswestin/std.js#readme
  `-- uglify-js@2.3.0
    | JavaScript parser, mangler/compressor and beautifier toolkit
    | git+https://github.com/mishoo/UglifyJS2.git
    | http://lisperator.net/uglifyjs
    +-- async@0.2.10
    |   Higher-order functions and common patterns for asynchronous code
    |   git+https://github.com/caolan/async.git
    |   https://github.com/caolan/async#readme
    +-- optimist@0.3.7
    | | Light-weight option parsing with an argv hash. No optstrings attached.
    | | git+ssh://git@github.com/substack/node-optimist.git
    | | https://github.com/substack/node-optimist#readme
    | `-- wordwrap@0.0.3
    |     Wrap those words. Show them at what columns to start and stop.
    |     git://github.com/substack/node-wordwrap.git
    |     https://github.com/substack/node-wordwrap#readme
    `-- source-map@0.1.43
      | Generates and consumes source maps
      | git+ssh://git@github.com/mozilla/source-map.git
      | https://github.com/mozilla/source-map
      `-- amdefine@1.0.1
          Provide AMD's define() API for declaring modules in the AMD format
          git+https://github.com/jrburke/amdefine.git
          http://github.com/jrburke/amdefine
~~~

