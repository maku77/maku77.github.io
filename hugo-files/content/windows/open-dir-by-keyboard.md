---
title: "Windowsメモ: Windows でキーボード操作だけで様々なフォルダを素早く開く"
url: "p/acsfhxi/"
date: "2015-10-22"
tags: ["windows"]
aliases: /windows/open-dir-by-keyboard.html
---

下記のような感じで、キーボード操作だけで様々なフォルダを素早く開くことができます。

{{< code title="例: C ドライブを開く" >}}
1. Windows キー + R
2. C: と入力して Enter
{{< /code >}}

2 つ目のステップで入力する内容を変えることによって、色々なフォルダを開くことができます。

| 入力するテキスト | 開くフォルダ | 具体的なパスの例 |
| :--------------- | :----------- | :--------------- |
| **`C:`** | C ドライブ | C:\ |
| **`desktop`** | デスクトップ | C:\Users\maku\Desktop |
| **`%userprofile%`** | ユーザディレクトリ（Linux でいう HOME 的な場所） | C:\Users\maku |
| **`%appdata%`** | Application Data ディレクトリ | C:\Users\maku\AppData\Roaming |
| **`%windir%`** | Windows ディレクトリ | C:\Windows |
| **`%programfiles%`** | Program Files | C:\Program Files |
| **`shell:sendto`** | SendTo ディレクトリ | C:\Users\maku\AppData\Roaming\Microsoft\Windows\SendTo |
| **`shell:startup`** | スタートアップディレクトリ | C:\Users\maku\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup |

ちなみに、開いたフォルダを素早く閉じるには、`Ctrl + W` です。

