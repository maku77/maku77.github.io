---
title: "Rust でディレクトリ内のファイルを列挙する (fs::read_dir)"
url: "p/2kv6eub/"
date: "2022-12-30"
tags: ["Rust"]
---

read_dir 関数の基本
----

Rust の標準モジュール `std::fs` の [read_dir 関数](https://doc.rust-lang.org/std/fs/fn.read_dir.html) を使うと、ディレクトリ内のファイルやディレクトリを列挙することができます。

{{< code lang="rust" title="std::fs::read_dir 関数" >}}
pub fn read_dir<P: AsRef<Path>>(path: P) -> Result<ReadDir>
{{< /code >}}

列挙結果には、カレントディレクトリ (`.`) や親ディレクトリ (`..`) は含まれないので、自然な列挙が可能です。

{{< code lang="rust" title="src/main.rs" >}}
use std::fs;

fn main() {
    let entries = fs::read_dir(".").unwrap(); // ReadDir を取得

    // ループで Result<DieEntry, Error> をひとつずつ処理
    for entry in entries {
        // DirEntry#file_name() でファイル名（ディレクトリ名）を取得できる
        println!("{:?}", entry.unwrap().file_name());
    }
}
{{< /code >}}

{{< code title="実行結果" >}}
"Cargo.toml"
"target"
"Cargo.lock"
".gitignore"
".git"
"src"
{{< /code >}}


エラーチェックする
----

上記ではコードを簡素化するために `Result#unwrap` メソッドを使っていますが、万が一 `Err` 値が返された場合は panic が発生してしまうので、プロダクトコードでは `unwrap` メソッドは使うべきではありません。
`Result` が `Ok` 値を持っているかを調べつつ、その値を取り出すには、次のように __`if let`__ 構文を使用できます。

{{< code lang="rust" title="他の実装例" >}}
use std::fs;

fn main() {
    if let Ok(entries) = fs::read_dir(".") {
        for entry in entries {
            if let Ok(entry) = entry {
                println!("{:?}", entry.file_name());
            }
        }
    }
}
{{< /code >}}


Path インスタンスを取得する
----

`DirEntry#file_name()` でファイル名 (`OsString`) を取得する代わりに、__`DirEntry#path()`__ を使って __`PathBuf`__ オブジェクトを取得できます。
`PathBuf` は、パス情報を扱ういろいろなメソッドを提供しています。

```rust
let path = entry.path(); // PathBuf を取得

println!("file_name = {:?}", entry.file_name());
println!("is_file = {}", path.is_file());
println!("is_dir = {}", path.is_dir());
println!("is_absolute = {}", path.is_absolute());
```

- 参考: [ファイルやディレクトリの存在を調べる (`std::io::Path`, `PathBuf`)](/p/fbkt3ah/)
- 参考: [ファイルやディレクトリのパス文字列を構築／分割する (`std::path::Path`, `PathBuf`)](/p/36hr2bj/)


ディレクトリ内のファイルリストを Vec で取得する
----

前述のように、Rust でディレクトリ内のファイルを列挙しようとすうと、少し面倒なコードになります。
下記のユーティリティ関数 `read_dir_entries` を使うと、指定したディレクトリに含まれているファイルやディレクトリの名前 (`PathBuf`) を `Vec` 形式でまとめて取得できます。

```rust
use std::{
    fs, io,
    path::{Path, PathBuf},
};

/** 指定したディレクトリ内のすべてのファイル（ディレクトリ）のパス情報を取得します。 */
fn read_dir_entries<P: AsRef<Path>>(path: P) -> io::Result<Vec<PathBuf>> {
    let mut entries = fs::read_dir(path)?
        .map(|res| res.map(|e| e.path()))
        .collect::<Result<Vec<_>, io::Error>>()?;

    entries.sort();
    Ok(entries)
}

fn main() {
    match read_dir_entries(".") {
        Ok(entries) => println!("{:?}", entries),
        Err(e) => eprintln!("ERROR: {}", e),
    }
}
```

{{< code title="実行結果" >}}
["./.git", "./.gitignore", "./Cargo.lock", "./Cargo.toml", "./src", "./target"]
{{< /code >}}

