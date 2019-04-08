---
title: "Yaml ファイルを読み書きする (js-yaml)"
date: "2019-04-05"
description: "NPM のパッケージとして公開されている js-yaml を使用すると、Node.js のプログラムから簡単に Yaml ファイルを扱うことができるようになります。"
---

js-yaml パッケージのインストール
----

`js-yaml` パッケージは、`npm` を使ってインストールすることができます。

```
$ npm install js-yaml
$ npm install js-yaml --save  # package.json に依存情報を追記する場合
```

- [js-yaml (YAML1.2 parser/writer) - npm](https://www.npmjs.com/package/js-yaml)


js-yaml で Yaml ファイルを読み込む
----

例えば、ここでは下記のような Yaml ファイルを読み込んでみます（出典は[こちら](https://en.wikipedia.org/wiki/YAML)）。

#### data.yaml

```yaml
---
receipt:     Oz-Ware Purchase Invoice
date:        2012-08-06
customer:
    first_name:   Dorothy
    family_name:  Gale

items:
    - part_no:   A4786
      descrip:   Water Bucket (Filled)
      price:     1.47
      quantity:  4

    - part_no:   E1628
      descrip:   High Heeled "Ruby" Slippers
      size:      8
      price:     133.7
      quantity:  1
```

`js-yaml` モジュールの **`safeLoad`** 関数を使用すると、パラメータで渡した Yaml テキストを JavaScript のオブジェクトに変換することができます。
**`safeLoad` 関数には、ファイル名を渡すのではなく、Yaml 形式のテキストを渡す必要がある**ことに注意してください。
下記のサンプルでは、Yaml ファイルの内容を `fs.readFileSync()` で取得し、その結果を `js-yaml` の `safeLoad` 関数でパースしています。

#### main.js （Yaml ファイルの読み込み）

```js
/**
 * 指定されたパスの Yaml ファイルを読み込みます。
 */
function loadYamlFile(filename) {
  const fs = require('fs');
  const yaml = require('js-yaml');
  const yamlText = fs.readFileSync(filename, 'utf8')
  return yaml.safeLoad(yamlText);
}

// Entry point
if (require.main === module) {
  const path = require('path');

  try {
    const data = loadYamlFile(path.join(__dirname, 'data.yaml'));
    console.log(data);
    console.log(data.items[1].price);
  } catch (err) {
    console.error(err.message);
  }
}
```

#### 実行結果

```
$ node main.js
{ receipt: 'Oz-Ware Purchase Invoice',
  date: 2012-08-06T00:00:00.000Z,
  customer: { first_name: 'Dorothy', family_name: 'Gale' },
  items:
   [ { part_no: 'A4786',
       descrip: 'Water Bucket (Filled)',
       price: 1.47,
       quantity: 4 },
     { part_no: 'E1628',
       descrip: 'High Heeled "Ruby" Slippers',
       size: 8,
       price: 133.7,
       quantity: 1 } ] }
133.7
```

Yaml ファイルの内容が、正しく JavaScript のオブジェクトに変換されていることがわかります。


js-yaml で Yaml ファイルを書き出す
----

`js-yaml` モジュールの、**`dump`** 関数を利用すると、任意の JavaScript オブジェクトを Yaml 形式のテキストに変換することができます。
変換したテキストをそのままテキストファイルとして出力すれば、Yaml ファイルができあがります。

下記の例では、`books` オブジェクトの内容を Yaml 形式のテキストに変換し、`books.yaml` ファイルに保存しています。

#### main.js （Yaml ファイルへの保存）

```js
const fs = require('fs');
const yaml = require('js-yaml');
const books = [
  { name: 'タイトル1', authors: ['著者A', '著者B'] },
  { name: 'タイトル2', authros: ['著者C', '著者D'] }
];

const yamlText = yaml.dump(books);
fs.writeFile('books.yaml', yamlText, 'utf8', (err) => {
  if (err) {
    console.error(err.message);
    process.exit(1);
  }
  console.log('Yaml ファイルを保存しました');
});

// 同期書き込みなら
// const yamlText = yaml.dump(books);
// try {
//   fs.writeFileSync('books.yaml', yamlText, 'utf8');
// } catch (err) {
//   console.error(err.message);
//   process.exit(1);
// }
```

#### books.yaml （作成された Yaml ファイル）

```yaml
- name: タイトル1
  authors:
    - 著者A
    - 著者B
- name: タイトル2
  authros:
    - 著者C
    - 著者D
```

