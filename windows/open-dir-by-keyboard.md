---
title: Windows でキーボード操作だけで様々なフォルダを素早く開く
created: 2015-10-22
---

下記のような感じで、キーボード操作だけで様々なフォルダを素早く開くことができます。
（タイピングがある程度早いこと前提ですが ^^;）

#### 例: C ドライブを開く
1. Windowsキー + R
2. `C:` と入力して Enter

2 つ目のステップで入力する内容を変えることによって、色々なフォルダを開くことができます。

### Windows 7 で確認
| 入力するテキスト | 開くフォルダ | 具体的なパスの例 |
| ---------------- | ------------ | ---------------- |
| C: | C ドライブ | C:\ |
| desktop | デスクトップ | C:\Users\maku\Desktop |
| %userprofile% | ユーザディレクトリ（Linux でいう HOME 的な場所） | C:\Users\maku |
| %appdata% | Application Data ディレクトリ | C:\Users\maku\AppData\Roaming |
| %windir% | Windows ディレクトリ | C:\Windows |
| %programfiles% | Program Files | C:\Program Files |
| shell:sendto | SendTo ディレクトリ | C:\Users\maku\AppData\Roaming\Microsoft\Windows\SendTo |

ちなみに、開いたフォルダを素早く閉じるには、`Ctrl + W` です。

