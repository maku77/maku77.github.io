---
title: "Rust でファイルやディレクトリの名前を変更する (std::fs::rename)"
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
    fs::rename("a.txt", "b.txt")?;
    Ok(())
}
{{< /code >}}


ファイルを移動する
----

`rename` 関数の移動先パスとして、別のディレクトリのファイルパスを指定すれば、ファイルを移動することができます。

{{< code lang="rust" title="a.txt を foo ディレクトリに移動する" >}}
use std::{fs, io};

fn main() -> io::Result<()> {
    // 移動先のディレクトリがなければ作成しておく
    fs::create_dir_all("aaa/bbb")?;
    // ファイルを移動する
    fs::rename("a.txt", "aaa/bbb/a.txt")?;
    Ok(())
}
{{< /code >}}

移動先のディレクトリはあらかじめ存在していなければいけないので、上記では `create_dir_all` 関数で先にディレクトリを用意しています（すでに存在していれば無視されます）。

- 参考: [ディレクトリを作成・削除する (`std::fs::create_dir, create_dir_all, remove_dir, remove_dir_all`)](/p/zju5eow/)

