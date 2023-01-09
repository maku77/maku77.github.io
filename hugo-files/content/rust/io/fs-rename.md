---
title: "Rust でファイルやディレクトリの名前を変更（移動）する (std::fs::rename)"
url: "p/raiqzbr/"
date: "2022-12-30"
tags: ["Rust"]
---

Rust でファイルやディレクトリの名前を変更するには、[std::fs::rename 関数](https://doc.rust-lang.org/std/fs/fn.rename.html) を使用します。
`rename` 関数は、ファイルの移動にも使用できます。


ファイルの名前を変更する
----

{{< code lang="rust" title="a.txt を b.txt にリネームする" >}}
use std::{fs, io};

fn main() -> io::Result<()> {
    fs::rename("a.txt", "b.txt")?; // 失敗時は io::Error を伝搬
    Ok(())
}
{{< /code >}}

ファイル名の代わりにディレクトリ名を指定すれば、ディレクトリ名を変更することができます。

`rename` 関数には [Path](https://doc.rust-lang.org/std/path/struct.Path.html) インスタンスを渡すこともできます。

```rust
use std::{fs, io, path::Path};

fn main() -> io::Result<()> {
    let path_from = Path::new("a.txt");
    let path_to = Path::new("b.txt");
    fs::rename(&path_from, &path_to)?; // 失敗時は io::Error を伝搬
    Ok(())
}
```


ファイルを移動する
----

`rename` 関数の移動先パスとして、ディレクトリ階層を含むファイルパスを指定すれば、ファイルをそのディレクトリに移動することができます。
このとき、ファイル名を変更すれば、移動とファイル名の変更が同時に行われます（逆に言うと、移動だけしたいのであれば、同じファイル名を指定する必要があります）。

{{< code lang="rust" title="a.txt を aaa/bbb/ccc.txt として移動する" >}}
use std::{fs, io};

fn main() -> io::Result<()> {
    // 移動先のディレクトリが存在しなければ先に作成しておく
    fs::create_dir_all("aaa/bbb")?;
    // ファイルを移動＆リネームする
    fs::rename("a.txt", "aaa/bbb/ccc.txt")?;
    Ok(())
}
{{< /code >}}

移動先のディレクトリはあらかじめ存在していなければいけないので、上記では `create_dir_all` 関数で先にディレクトリを用意しています（すでに存在していれば無視されます）。

- 参考: [ディレクトリを作成・削除する (`std::fs::create_dir, create_dir_all, remove_dir, remove_dir_all`)](/p/zju5eow/)

次の `move_file` ユーティリティ関数は、移動先のディレクトリが存在していないときに自動的に作成してくれます。

```rust
use std::{fs, io, path::Path};

/// from が示すファイル／ディレクトリを、to のパスへ移動（とリネーム）します。
/// to には移動後のファイル／ディレクトリ名を含んでいる必要があります。
/// to が親ディレクトリを持つ場合、そのディレクトリを先に作成します。
fn move_file(from: &Path, to: &Path) -> io::Result<()> {
    // 移動先のディレクトリが存在しなければ先に作成しておく
    if let Some(parent) = to.parent() {
        fs::create_dir_all(parent)?;
    }
    // ファイルを移動＆リネームする
    fs::rename(&from, &to)?;
    Ok(())
}

fn main() {
    let path_from = Path::new("a.txt");
    let path_to = Path::new("aaa/bbb/ccc.txt");
    match move_file(&path_from, &path_to) {
        Ok(_) => println!("Success!"),
        Err(err) => eprintln!("ERROR: {}", err),
    }
}
```

