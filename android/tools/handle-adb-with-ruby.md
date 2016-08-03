---
title: Ruby で adb コマンドの出力結果を処理する
created: 2016-07-06
---

下記は、Ruby スクリプトの中から `adb shell` コマンドを呼び出して、その出力をハンドルするサンプルです。

ここで定義している `adb_shell` メソッドは、受け取ったコマンド文字列を `adb shell` の後ろにくっつけた形のコマンドを実行します（`adb_shell('pm list packages')` とすると、`adb shell pm list packages` が実行される）。

#### sample.rb

```ruby
# Executes the command via 'adb shell'.
# Obtained result will be passed to the specified block line by line.
def adb_shell(command)
  result = `adb shell #{command}`
  result.gsub!("\n\n", "\n")  # for Windows
  result.lines { |line| yield line }
end

# Test
if $0 == __FILE__
  adb_shell('pm list packages') do |line|
    puts '==> ' + line
  end
end
```

#### 実行結果

```
$ ruby sample.rb
==> package:android
==> package:android.autoinstalls.config.google.fugu
==> package:com.android.backupconfirm
==> package:com.android.bluetooth
==> package:com.android.certinstaller
==> package:com.android.defcontainer
==> package:com.android.dreams.basic
...
```

応用として、パッケージ名部分だけを取り出したいのであれば、下記のように正規表現でフィルタしてしまうこともできますね。

```ruby
adb_shell('pm list package') do |line|
  if line =~ /package:((\w|.)+)/
    puts '==> ' + $1
  end
end
```

#### 実行結果

```
$ ruby sample.rb
==> android
==> android.autoinstalls.config.google.fugu
==> com.android.backupconfirm
==> com.android.bluetooth
==> com.android.certinstaller
==> com.android.defcontainer
==> com.android.dreams.basic
...
```

