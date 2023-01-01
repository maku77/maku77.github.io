---
title: "ファイルやディレクトリの存在を調べる (std::io::Path, PathBuf)"
url: "p/fbkt3ah/"
date: "2023-01-01"
tags: ["Rust"]
---

[Path](https://doc.rust-lang.org/std/path/struct.Path.html) や [PathBuf](https://doc.rust-lang.org/std/path/struct.PathBuf.html) 構造体が備えている __`is_file`__ メソッドや __`is_dir`__ メソッドを使うと、そのそのパスに対応するファイルやディレクトリが存在するかを調べることができます。
他にも、パスが絶対パスか相対パスかを調べるメソッドなどが用意されています。

| メソッド名 | 戻り値の型 | 意味 |
| ---- | ---- | ---- |
| __`Path::is_file()`__ | `bool` | そのパスが示す __ファイルが存在するか__ を調べます。シンボリックリンクの場合は、リンク先のファイルが存在するかを調べます。ファイルのアクセス権がない場合は、`false` を返します。 |
| __`Path::is_dir()`__ | `bool` | そのパスが示す __ディレクトリが存在するか__ を調べます。シンボリックリンクの場合は、リンク先のディレクトリが存在するかを調べます。ディレクトリのアクセス権がない場合は、`false` を返します。 |
| __`Path::is_symlink()`__ | `bool` | そのパスが示す __シンボリックファイルが存在するか__ を調べます。リンク先のファイルやディレクトリが存在するかまではチェックしません（シンボリックリンクが壊れていても `true` を返します）。 |
| __`Path::is_absolute()`__ | `bool` | そのパスが __絶対パスか__ を調べます。 |
| __`Path::is_relative()`__ | `bool` | そのパスが __相対パスか__ を調べます。 |
| __`Path::has_root()`__ | `bool` | そのパスが __ルートセパレーターを持つか__ を調べます（ほぼ `is_absolute` と同義）。 |

```rust
use std::path::Path;

fn main() {
    // 存在するファイル（相対パス指定）の場合
    let path = Path::new("Cargo.toml");
    assert_eq!(path.is_file(), true);
    assert_eq!(path.is_dir(), false);
    assert_eq!(path.is_symlink(), false);
    assert_eq!(path.is_absolute(), false);
    assert_eq!(path.is_relative(), true);
    assert_eq!(path.has_root(), false);

    // 存在しないファイル（絶対パス指定）の場合
    let path = Path::new("/nonexisting/file/path");
    assert_eq!(path.is_file(), false);
    assert_eq!(path.is_dir(), false);
    assert_eq!(path.is_symlink(), false);
    assert_eq!(path.is_absolute(), true);
    assert_eq!(path.is_relative(), false);
    assert_eq!(path.has_root(), true);
}
```
