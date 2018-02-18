---
title: "AppleScript でボタンによる選択肢を表示する"
date: "2018-02-18"
---

下記のサンプルでは、"Windows" "Mac" "Cancel" の３つのボタンを表示し、押したボタンによって異なるメッセージを表示します。

~~~
#!/usr/bin/env osascript

display dialog "Select your OS" buttons {"Mac", "Windows", "Cancel"} default button 3 with icon note
copy the result as list to {ret}

if the ret is "Windows" then
    display dialog "You are using Windows!"
else if the ret is "Mac" then
    display dialog "You are using Mac!"
end if
~~~

