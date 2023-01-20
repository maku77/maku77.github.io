---
title: "Rust で Excel ファイルを読み込む (calamine)"
url: "p/4ye2eah/"
date: "2023-01-20"
tags: ["Excel", "Rust"]
---

calamine とは
----

Rust の __`calamine`__ クレートは、Excel ファイルを読み込むためのライブラリです。

- [calamine - Rust](https://docs.rs/calamine/latest/calamine/)
- [tafia/calamine: A pure Rust Excel/OpenDocument SpeadSheets file reader: rust on metal sheets](https://github.com/tafia/calamine)

読み込み専用 (read-only) のライブラリですが、ピュアな Rust 実装で軽量です。
作者の tafia (Johann Tuffe) 氏によると、「書き込み (write) はめっちゃ複雑だから対応しないよ。セルのアップデートくらいなら対応するかもね」とのこと。

Rust プロジェクトで次のように依存関係を追加すれば準備完了です。

```console
$ cargo add calamine
```

以下、Excel ファイルを読み込むサンプルコードです。


ワークシート名のリストを取得する (Xlsx#sheet_names)
----

__`open_workbook`__ で Excel ファイルを開いて、`Xlsx` インスタンスを取得するところがすべての始まりです。
__`Xlsx#sheet_names`__ メソッドで、ワークシート名の一覧を取得できます。

{{< code lang="rust" hl_lines="5" >}}
use calamine::{Reader, Xlsx};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let workbook: Xlsx<_> = calamine::open_workbook("sample.xlsx")?;
    let sheet_names = workbook.sheet_names();
    println!("{:?}", sheet_names);
    Ok(())
}
{{< /code >}}

{{< code title="出力例" >}}
["Sheet1", "Sheet2"]
{{< /code >}}


ワークシートを読み込む (Xlsx#worksheet_range, worksheet_range_at)
----

__`Xlsx#worksheet_range`__ メソッドで、ワークシート内のデータ（セル）を読み込むことができます。
このようにデータを読み込む場合は、`open_workbook` で Excel ファイルを開くときに、mutable 変数で受け取る必要があることに注意してください（ワークシートの情報を内部に展開するため）。

{{< code lang="rust" hl_lines="7" >}}
use calamine::{Reader, Xlsx};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let mut workbook: Xlsx<_> = calamine::open_workbook("sample.xlsx")?;

    // Sheet1 という名前のワークシートを読み込む
    if let Some(Ok(range)) = workbook.worksheet_range("Sheet1") {
        println!("{:?}", range);  // range は Range<DataType> インスタンス
    }
    Ok(())
}
{{< /code >}}

{{< code title="出力例（A1～B2 のセルがある場合）" >}}
Range { start: (0, 0), end: (1, 1), inner: [String("A1セル"), String("B1セル"), String("A2セル"), String("B2セル")] }
{{< /code >}}

ワークシート名ではなく、インデックスでワークシートを指定したいときは、`worksheet_range` の代わりに __`worksheet_range_at`__ メソッドを使います。

```rust
if let Some(Ok(range)) = workbook.worksheet_range_at(0) {
    println!("{:?}", range);
}
```


値を持つセルの範囲を取得する (Range#get_size)
----

__`Range#get_size`__ メソッドで、ワークシート内のどの範囲（行／列）にセルが存在するかを調べることができます。
上記方法で、`Range` オブジェクトを取得済みと想定しています。

```rust
// range は Range<DataType> インスタンス
println!("rows = {}", range.get_size().0);
println!("cols = {}", range.get_size().1);
```

{{< code title="出力例" >}}
1000
30
{{< /code >}}


1 行ずつループ処理する (Range#rows)
----

ここで使用する Excel ファイル (`sample.xlsx`) の内容は、次のようになっていると想定します。
（空）と書いてあるのは空白セルです。
つまり、3 行 x 3 列で、歯抜けのセルが含まれたワークシートです。

<table>
  <tr><td>A1セル</td><td>B1セル</td><td>（空）</td></tr>
  <tr><td>A2セル</td><td>B2セル</td><td>C2セル</td></tr>
  <tr><td>（空）</td><td>B3セル</td><td>（空）</td></tr>
</table>

`Range` オブジェクトの __`rows`__ メソッドを使って、次のように 1 行ずつループ処理することができます。
各行のカラム数は __`len()`__ メソッドで取得できますが、この値は最も多くのカラムを含む行に合わせた値になるようです（今回の `sample.xlsx` では、すべての行のカラム数が 3 になる）。

```rust
let mut workbook: Xlsx<_> = calamine::open_workbook("sample.xlsx")?;
if let Some(Ok(range)) = workbook.worksheet_range_at(0) {
    for row in range.rows() {
        println!("len={}, row={:?}, row[0]={:?}", row.len(), row, row[0]);
    }
}
```

{{< code title="出力例" >}}
len=3, row=[String("A1セル"), String("B1セル"), Empty], row[0]=String("A1セル")
len=3, row=[String("A2セル"), String("B2セル"), String("C2セル")], row[0]=String("A2セル")
len=3, row=[Empty, String("B3セル"), Empty], row[0]=Empty
{{< /code >}}

空のセルは、`DataType` 列挙型の `Empty` バリアントとして表現されます。

ループ時に行のインデックスが欲しければ、`enumerate` と組み合わせて次のようにします。

```rust
for (row_index, row) in range.rows().enumerate() {
    println!("{}: {:?}", row_index, row);
}
```

{{< code title="出力例" >}}
0: [String("A1セル"), String("B1セル"), Empty]
1: [String("A2セル"), String("B2セル"), String("C2セル")]
2: [Empty, String("B3セル"), Empty]
{{< /code >}}


すべてのセルを取り出す (Range#cells)
----

`Range` 内のセルを、行単位ではなくまとめて取得したいときは、__`Range#cells`__ メソッドを使用します。

```rust
let cells = range.cells();  // Cells<DataType>
println!("セルの数 = {}", cells.len());
for cell in cells {
    let row = cell.0;
    let col = cell.1;
    let data = cell.2;
    println!("[{}, {}] = {}", row, col, data);
}
```

{{< code title="出力例" >}}
セルの数 = 9
[0, 0] = A1セル
[0, 1] = B1セル
[0, 2] =
[1, 0] = A2セル
[1, 1] = B2セル
[1, 2] = C2セル
[2, 0] =
[2, 1] = B3セル
[2, 2] =
{{< /code >}}

各セルの値 (`cell.2`) は、`DataType` という列挙型で表現されており、各バリアントで異なるタイプのデータを保持するようになっています。
`match` 式を使えば、含まれているデータのタイプに応じて分岐処理することができます。

```rust
for cell in range.cells() {
    print!("[{}, {}] = ", cell.0, cell.1);  // セルの位置を表示
    match cell.2 {
        DataType::Empty => println!("空っぽのセルです"),
        DataType::String(s) => println!("{} という文字列セルです", s),
        DataType::Int(n) => println!("{} という整数値セルです", n),
        DataType::Float(f) => println!("{} という浮動小数点数セルです", f),
        DataType::Bool(b) => println!("{} という真偽値セルです", b),
        DataType::DateTime(d) => println!("{} という日時セルです", d),
        DataType::Error(e) => println!("ERROR: {}", e),
    }
}
```

{{< code title="出力例" >}}
[0, 0] = A1セル という文字列セルです
[0, 1] = B1セル という文字列セルです
[0, 2] = 空っぽのセルです
[1, 0] = A2セル という文字列セルです
[1, 1] = B2セル という文字列セルです
[1, 2] = C2セル という文字列セルです
[2, 0] = 空っぽのセルです
[2, 1] = B3セル という文字列セルです
[2, 2] = 空っぽのセルです
{{< /code >}}

空セルや文字列セルかどうかを調べる専用のメソッドも用意されています。

```rust
let data = cell.2;
println!("is_empty = {}", data.is_empty());
println!("is_string = {}", data.is_string());
```

文字列セルの場合だけ、その文字列を取り出しつつ処理したい場合は、__`DataType#get_string`__ メソッドで `Option<&str>` を取得します。

```rust
let data = cell.2;
if let Some(s) = data.get_string() {
    println!("文字列セルの値: {}", s);
}
```


単一のセルの値を取得する (Range#get_value, get)
----

__`Range#get_value`__ メソッドで、指定した行・列のセルの値を取得することができます。
戻り値は `Option<&DataType>` 型になっており、空セルの場合は `Option::None` が返されます。

```rust
let cell = range.get_value((0, 1));  // Option<&DataType>
println!("{:?}", cell);  //=> Some(String("Hello"))
```

