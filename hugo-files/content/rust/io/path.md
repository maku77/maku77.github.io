---
title: "ファイルやディレクトリのパス文字列を構築／分割する (std::path::Path, PathBuf)"
url: "p/36hr2bj/"
date: "2023-01-01"
tags: ["Rust"]
---

Path と PathBuf
----

Rust には、ファイルやディレクトリのパス情報を扱うための [std::path::Path](https://doc.rust-lang.org/std/path/struct.Path.html) struct が用意されています。
`Path` の mutable 版（可変）である、[std::path::PathBuf](https://doc.rust-lang.org/std/path/struct.PathBuf.html) struct を使うと、パス情報を動的に組み立てていくことができます。

次の例では、パス形式の文字列リテラルから `Path` と `PathBuf` のインスタンスを生成しています。

```rust
use std::path::{Path, PathBuf};

fn main() {
    let path = Path::new("/dir1/dir2/file.txt");
    let path_buf = PathBuf::from("/dir1/dir2/file.txt");

    println!("{:?}", path);     //=> "/dir1/dir2/file.txt"
    println!("{:?}", path_buf); //=> "/dir1/dir2/file.txt"
}
```

{{% note title="パスを文字列で取り出す" %}}
`Path` や `PathBuf` インスタンスから、パスを表現する文字列を取得したければ次のように記述できます。

```rust
let path = Path::new("/aaa/bbb/ccc");
let s = path.to_string_lossy();

assert_eq!(s, "/aaa/bbb/ccc");
```

ただ、実際のプロダクトコード内でパス情報を扱うときは、`Path` オブジェクトのまま扱った方が都合がよいので、実際にこのような処理が必要になることはあまりありません。
例えば、ファイルを扱う [std::fs](https://doc.rust-lang.org/stable/std/fs/) モジュールの各種関数は、パスを表す文字列と `Path` インスタンスのどちらも扱えるようになっています。
{{% /note %}}


パスを分解する
----

`Path` や `PathBuf` の、次のようなメソッドを使うことで、`/dir1/dir2/file.txt` のようなパス文字列から、親ディレクトリ名や、ベースネーム、拡張子名などを抽出することができます。

| メソッド | 戻り値の型 | 意味 |
| ---- | ---- | ---- |
| __`Path#parent()`__ | `Option<&Path>` | 親ディレクトリ |
| __`Path#file_name()`__ | `Option<&OsStr>` | ファイル名 |
| __`Path#file_stem()`__ | `Option<&OsStr>` | ベースネーム（ファイル名から拡張子を除いたもの） |
| __`Path#extension()`__ | `Option<&OsStr>` | 拡張子 |

{{< code lang="rust" title="例: フルパスの場合" >}}
use std::ffi::OsStr;
use std::path::Path;

fn main() {
    let path = Path::new("/dir1/dir2/file.txt");

    assert_eq!(path.parent(), Some(Path::new("/dir1/dir2")));
    assert_eq!(path.file_name(), Some(OsStr::new("file.txt")));
    assert_eq!(path.file_stem(), Some(OsStr::new("file")));
    assert_eq!(path.extension(), Some(OsStr::new("txt")));
}
{{< /code >}}

拡張子が存在しない場合、`Path#extension()` の戻り値は（`Option` enum の）__`None`__ になります。

{{< code lang="rust" title="例: ファイル名だけの場合" >}}
use std::ffi::OsStr;
use std::path::Path;

fn main() {
    let path = Path::new("file");

    assert_eq!(path.parent(), Some(Path::new("")));
    assert_eq!(path.file_name(), Some(OsStr::new("file")));
    assert_eq!(path.file_stem(), Some(OsStr::new("file")));
    assert_eq!(path.extension(), None);
}
{{< /code >}}


パスを構築する
----

### パスを結合する

`PathBuf` struct は、パスを動的に組み立てていくための `push`、`pop`、`set_file_name`、`set_extension` といったメソッドを備えています。
__`PathBuf::push`__ メソッドを連続して使うと、各階層のディレクトリ名、ファイル名を末尾に繋げていくことができます。

```rust
use std::path::PathBuf;

fn main() {
    let mut path = PathBuf::new();
    path.push("/");
    path.push("aaa");
    path.push("bbb");
    path.push("ccc.txt");

    assert_eq!(path, PathBuf::from("/aaa/bbb/ccc.txt"));
}
```

`Path` struct は immutable（不変）なので、自分自身を変更する `push` メソッドは備えていませんが、代わりに、パス結合後の結果を戻り値として返す __`Path#join`__ メソッドを備えています。

```rust
use std::path::{Path, PathBuf};

fn main() {
    let path = Path::new("/aaa/bbb");
    let path_buf = path.join("ccc.txt");

    assert_eq!(path_buf, PathBuf::from("/aaa/bbb/ccc.txt"));
}
```

### Path から PathBuf を生成する (to_path_buf)

__`Path#to_path_buf`__ メソッドを使うと、同一のパスを表す `PathBuf` を生成することができます。
次の例では、`Path` から `PathBuf` を生成し、さまざまな編集メソッドを使って新しいパス情報を構築しています。

```rust
use std::path::{Path, PathBuf};

fn main() {
    let path = Path::new("/aaa/bbb/ccc.txt");
    let mut path_buf = path.to_path_buf(); // PathBuf を生成

    path_buf.pop(); // 親ディレクトリへ ("/aaa/bbb")
    path_buf.pop(); // 親ディレクトリへ ("/aaa")
    path_buf.push("xxx"); // パスを結合 ("/aaa/xxx")
    path_buf.push("yyy"); // パスを結合 ("/aaa/xxx/yyy")
    path_buf.set_extension("txt"); // 拡張子をセット ("/aaa/xxx/yyy.txt")

    assert_eq!(path_buf, PathBuf::from("/aaa/xxx/yyy.txt"));
}
```


### ファイル名／拡張子を置換する (PathBuf::set_file_name, Path::with_file_name)

パスのファイル名部分だけを変更したいときは、__`PathBuf::set_file_name`__ メソッドを使います。
このメソッドは、パスの末尾がファイル名であるか、ディレクトリ名であるかは考慮しないので注意してください。

```rust
use std::path::PathBuf;

fn main() {
    let mut path_buf = PathBuf::from("/aaa/bbb/ccc");
    path_buf.set_file_name("XXX.txt");

    assert_eq!(path_buf, PathBuf::from("/aaa/bbb/XXX.txt"));
}
```

ファイル名部分の置換には、__`Path::with_file_name`__ を使うこともできます。
`Path` インスタンスは immutable（不変）なので、置換結果は戻り値として返されます。

```rust
use std::path::{Path, PathBuf};

fn main() {
    let path = Path::new("/aaa/bbb/ccc");
    let path_buf = path.with_file_name("XXX.txt");

    assert_eq!(path_buf, PathBuf::from("/aaa/bbb/XXX.txt"));
}
```

同様に使えるメソッドとして、拡張子部分だけを置換する __`PathBuf::set_extension`__ や __`Path::with_extension`__ も用意されています。

