---
title: "Vim のインサートモードを抜けるときに自動で IME をオフにする (macOS, Karabiner-Elements)"
url: "p/bbkb63f/"
date: "2025-05-06"
tags: ["neovim", "vim", "Karabiner-Elements"]
---

何をするか？
----

[Karabiner-Elements](https://karabiner-elements.pqrs.org/) は、macOS 用のキーボードカスタマイズツールです。
ここでは、Vim のインサートモードを抜けるときに IME を自動でオフにするための、Karabiner-Elements の設定を紹介します。


Karabiner-Elements の設定
----

### 設定方法

Karabiner-Elements のメニューから、

- `Complex Modifications` ⇨ `Add your own rules`

を選択し、下記の JSON を貼り付ければ設定完了です。

{{< code lang="json" title="vim-auto-ime-off.json" >}}
{
    "description": "Vim - Auto IME Off",
    "manipulators": [
        {
            "from": { "key_code": "escape" },
            "to": [
                { "key_code": "japanese_eisuu" },
                { "key_code": "escape" }
            ],
            "type": "basic"
        },
        {
            "from": {
                "key_code": "open_bracket",
                "modifiers": { "mandatory": ["left_control"] }
            },
            "to": [
                { "key_code": "japanese_eisuu" },
                { "key_code": "escape" }
            ],
            "type": "basic"
        },
        {
            "from": {
                "key_code": "caps_lock",
                "modifiers": { "optional": ["any"] }
            },
            "to": [
                {
                    "set_variable": {
                        "name": "caps_lock_pressed",
                        "value": 1
                    }
                },
                {
                    "key_code": "caps_lock",
                    "lazy": true
                }
            ],
            "to_after_key_up": [
                {
                    "set_variable": {
                        "name": "caps_lock_pressed",
                        "value": 0
                    }
                }
            ],
            "type": "basic"
        },
        {
            "conditions": [
                {
                    "name": "caps_lock_pressed",
                    "type": "variable_if",
                    "value": 1
                }
            ],
            "from": { "key_code": "open_bracket" },
            "to": [
                { "key_code": "japanese_eisuu" },
                { "key_code": "escape" }
            ],
            "type": "basic"
        }
    ]
}
{{< /code >}}

### 解説

この設定では、下記のようなインサートモードを抜けるキー入力があった場合に、すべて **`英数` → `ESC`** と入力したかのように振る舞うようにしています。

- `ESC`
- `Control + [`
- `CapsLock + [`

Mac では、`英数` キーを押すと IME がオフ、`かな` キーを押すと IME がオンになります。
Karabiner-Elements では、`英数` キーは `japanese_eisuu` という名前のキーコードとして定義されており、このキーコードを送ることで IME をオフにすることができます。

3 つ目の `CapsLock + [` というキーコンビネーションについては注意が必要です。
`CapsLock` は修飾キーではなく通常のキーとして扱われてしまうので、特殊な処理方法で記述しなければいけません。
上記の設定例では、`CapsLock` キーを押している間だけ `caps_lock_pressed` という変数に 1 をセットして、この変数が 1 の状態で `[` を押したときに `英数` → `ESC` と振る舞うようにしています。

あと、Karabiner-Elements でこれ以外にもキー設定を行っている場合は、`from` プロパティがコンフリクトしていないかの注意も必要です。
Karabiner-Elements の仕様として、同じキーコンビネーションを表す `from` プロパティが見つかると、最初に見つかったものが優先して使われます。
どうしても設定が重複してしまう場合は、設定自体をうまくマージするか、コンフリクト部分だけを抽出してマージした設定を新しく作り、その設定を最初に読み込むようにします（マージ済みの設定を優先的に使うようにします）。


参考リンク
----

- [左手小指だけで IME 操作｜まくろぐ](https://maku.blog/p/s3subpp/)

