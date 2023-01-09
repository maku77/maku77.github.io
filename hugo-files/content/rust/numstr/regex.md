---
title: "Rust で正規表現を扱う (regex)"
url: "p/r7sdwgy/"
date: "2023-01-08"
tags: ["Rust"]
---

Rust の [regex クレート](https://crates.io/crates/regex) を使うと、正規表現を使った様々な文字列処理を行うことができます。
Rust (Cargo) プロジェクト内で以下のように実行して `Cargo.toml` に依存関係を追加すれば `regex` の使用準備は完了です。

```console
$ cargo add regex
```

{{< code lang="toml" title="Cargo.toml" >}}
[dependencies]
regex = "1.7.0"
{{< /code >}}


パターンに一致するか調べる (is_match)
----

[Regex#is_match](https://rust-lang.github.io/regex/regex/struct.Regex.html#method.is_match) メソッドを使うと、引数で渡した文字列に、`Regex` のパターンに一致する部分文字列が含まれているかを調べることができます。

```rust
use regex::Regex;

let re = Regex::new(r"\d{4}-\d{2}-\d{2}").unwrap();
let input = "Today's date is 2023-01-07.";
if re.is_match(input) {
    println!("日付らしき文字列が見つかりました");
}
```

`Regex#is_match` メソッドは、パターンに一致する文字列が部分的にでも見つかれば `true` を返します。
文字列全体がパターンに一致するかどうかを調べたい場合は、パターンに `^`（行頭）と `$`（行末）を含めて、__`r"^\d{4}-\d{2}-\d{2}$"`__ のようにします。


パターンに一致した位置を調べる (find, find_iter)
----

[Regex#find](https://rust-lang.github.io/regex/regex/struct.Regex.html#method.find) メソッドは、パターンに一致する部分文字列が見つかったときに [regex::Match](https://rust-lang.github.io/regex/regex/struct.Match.html) オブジェクトを返します。
見つからない場合は `Option::None` を返します。
`Match` のメソッドを使って、実際に一致した部分文字列や、その位置を取得できます。

{{< code lang="rust" title="連続する数値を探す" >}}
let re = Regex::new(r"\d+").unwrap();
let input = "server: ok=100 changed=50 unreachable=0 failed=3";
match re.find(input) {
    Some(m) => println!("Found `{}` at {}-{}", m.as_str(), m.start(), m.end()),
    None => println!("Not found"),
}
{{< /code >}}

{{< code title="実行結果" >}}
Found `100` at 11-14
{{< /code >}}

`Regex#find` は最初に見つかった部分文字列だけを返しますが、複数回マッチさせたい場合は、代わりに [Regex#find_iter](https://rust-lang.github.io/regex/regex/struct.Regex.html#method.find_iter) メソッドで [regex::Matches](https://rust-lang.github.io/regex/regex/struct.Matches.html) オブジェクトを取得します。
`Matches` は `Iterator` を実装しているので、ループ処理が可能です。

```rust
let re = Regex::new(r"\d+").unwrap();
let input = "server: ok=100 changed=50 unreachable=0 failed=3";
for m in re.find_iter(input) {
    println!("Found `{}` at {}-{}", m.as_str(), m.start(), m.end());
}
```

{{< code title="実行結果" >}}
Found `100` at 11-14
Found `50` at 23-25
Found `0` at 38-39
Found `3` at 47-48
{{< /code >}}


グルーピングして部分文字列を抽出する (captures, captures_iter)
----

[Regex#captures](https://rust-lang.github.io/regex/regex/struct.Regex.html#method.captures) メソッドは、パターン中の括弧 (`()`) でグルーピングされた部分を一度に抽出して、[regex::Captures](https://rust-lang.github.io/regex/regex/struct.Captures.html) オブジェクトを返します。
`Captures` は `Vec` と同様にアクセス（`[]` や `get`）することができ、インデックス 0 には、パターンに一致した文字列全体が格納されています。

```rust
let re = Regex::new(r"(\d{4})-(\d{2})-(\d{2})").unwrap();
let input = "Eiichi Shibusawa was born on 1840-02-13 and died on 1931-11-11.";
match re.captures(input) {
    Some(caps) => {
        println!("年月日: {}", &caps[0]);
        println!("年: {}", &caps[1]);
        println!("月: {}", &caps[2]);
        println!("日: {}", &caps[3]);
    }
    None => println!("Not found"),
}
```

{{< code title="実行結果" >}}
年月日: 1840-02-13
年: 1840
月: 02
日: 13
{{< /code >}}

複数回マッチさせたいときは、`captures` の代わりに [captures_iter](https://rust-lang.github.io/regex/regex/struct.Regex.html#method.captures_iter) を使用します。

```rust
for caps in re.captures_iter(input) {
    println!("年: {}, 月: {}, 日: {}", &caps[1], &caps[2], &caps[3]);
}
```

{{< code title="実行結果" >}}
年: 1840, 月: 02, 日: 13
年: 1931, 月: 11, 日: 11
{{< /code >}}

正規表現パターンの中で、キャプチャグループに名前を付けるには __`(?P<name>exp)`__ というシンタックスを使用します。

```rust
let re = Regex::new(r"(?P<year>\d{4})-(?P<month>\d{2})-(?P<day>\d{2})").unwrap();
let input = "Eiichi Shibusawa was born on 1840-02-13 and died on 1931-11-11.";
for caps in re.captures_iter(input) {
    println!(
        "年: {}, 月: {}, 日: {}",
        &caps["year"], &caps["month"], &caps["day"]
    );
}
```


パターンに一致する部分を置換する (replace, replace_all)
----

[Regex#replace](https://rust-lang.github.io/regex/regex/struct.Regex.html#method.replace) メソッドを使用すると、正規表現パターンに一致した部分を任意の文字列に置換できます。
最初に一致した部分だけでなく、一致した部分をすべて置換したいときは、[Regex#replace_all](https://rust-lang.github.io/regex/regex/struct.Regex.html#method.replace_all) メソッドを使用します。

```rust
let re = Regex::new(r"[m|M]aku").unwrap();
let input = "I am maku. You are not Maku.";
let output = re.replace_all(input, "####");
println!("{}", output); //=> "I am ####. Your are not ####."
```

パターンの中で括弧 (`()`) を使ってキャプチャグループを構成しておけば、実際に一致した部分文字列を、置換文字列の中で __`$1`__ や __`$2`__ で参照できます。

{{< code lang="rust" title="ハイフン (-) で繋がった単語の前後を入れ替える" >}}
let re = Regex::new(r"\b(\w+)-(\w+)\b").unwrap();
let input = "AAA BBB-CCC DDD EEE-FFF GGG";
let output = re.replace_all(input, "$2-$1");
println!("{}", output); //=> "AAA CCC-BBB DDD FFF-EEE GGG"
{{< /code >}}

