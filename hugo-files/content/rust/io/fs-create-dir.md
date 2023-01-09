---
title: "Rust でディレクトリを作成・削除する (std::fs::create_dir, create_dir_all, remove_dir, remove_dir_all)"
url: "p/zju5eow/"
date: "2022-12-30"
tags: ["Rust"]
---

ディレクトリを作成する (create_dir)
----

Rust でディレクトリを作成するには、[std::fs::create_dir 関数](https://doc.rust-lang.org/std/fs/fn.create_dir.html) を使用します。

{{< code lang="rust" title="カレントディレクトリに aaa ディレクトリを作成" >}}
use std::fs;

fn main() -> std::io::Result<()> {
    fs::create_dir("aaa")?;
    Ok(())
}
{{< /code >}}

すでに同名のディレクトリが存在している場合など、ディレクトリを作成できない場合はエラーが発生します。

```rust
use std::fs;

fn main() {
    match fs::create_dir("aaa") {
        Ok(_) => println!("ディレクトリを作成しました"),
        Err(_) => eprintln!("ディレクトリを作成できませんでした"),
    }
}
```


複数階層のディレクトリを作成する (create_dir_all)
----

深い階層のディレクトリを一気に作成するには、`create_dir` の代わりに [create_dir_all 関数](https://doc.rust-lang.org/std/fs/fn.create_dir_all.html) を使用します。
このメソッドは、__すでに存在しているディレクトリを指定してもエラーにならない__ ので、`create_dir` より使い勝手はよいかもしれません。

{{< code lang="rust" title="aaa/bbb/ccc ディレクトリが存在しなければ作成する" >}}
use std::fs;

fn main() {
    fs::create_dir_all("aaa/bbb/ccc").unwrap(); // 成功する前提で unwrap
}
{{< /code >}}



ディレクトリを削除する (remove_dir)
----

既存の（空の）ディレクトリを削除するには、[std::fs::remove_dir 関数](https://doc.rust-lang.org/std/fs/fn.remove_dir.html)を使用します。
この関数は、Linux の `rmdir` コマンドと同様、空のディレクトリしか削除できないことに注意してください（DirectoryNotEmpty エラーが発生します）。
存在しないディレクトリを削除しようとした場合もエラーが発生します。

{{< code lang="rust" title="ディレクトリ aaa を削除する" >}}
use std::fs;

fn main() -> std::io::Result<()> {
    fs::remove_dir("aaa")?;
    Ok(())
}
{{< /code >}}


空でないディレクトリを削除する (remove_dir_all)
----

ディレクトリ内に含まれているファイルやディレクトリも含めて丸ごと削除してしまうには、`remove_dir` 関数の代わりに [remove_dir_all 関数](https://doc.rust-lang.org/stable/std/fs/fn.remove_dir_all.html) を使用します。
この関数も、存在しないディレクトリを指定するとエラーになります。
次の例では、`Path#is_dir` 関数でディレクトリの存在を確認してから削除しています。

{{< code lang="rust" title="ディレクトリ aaa を中身も含めて削除する" >}}
use std::{fs, path::Path};

fn main() {
    let dir_name = "aaa";

    if Path::new(dir_name).is_dir() {
        // ディレクトリが存在するなら丸ごと削除
        match fs::remove_dir_all(dir_name) {
            Ok(_) => println!("Directory `{}` has been removed", dir_name),
            Err(e) => eprintln!("Failed to remove {}: {}", dir_name, e),
        }
    } else {
        // ディレクトリは存在しなかった
        println!("Directory {} does not exist", dir_name);
    }
}
{{< /code >}}

参考
----

- [ファイルやディレクトリの名前を変更する (`std::fs::rename`)](/p/raiqzbr/)

