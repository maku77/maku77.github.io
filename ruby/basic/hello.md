---
title: "HelloWorld テンプレート"
date: "2019-01-12"
description: "Ruby の HelloWorld テンプレートです。簡単なスクリプトを作成するときに、このコードをコピペして作り始めると楽です。"
---

main 関数と show_usage 関数のテンプレート
----

```ruby
#!/usr/bin/env ruby
#
# 説明をここに。
#
# Usage:
#   $ ruby hello.rb <your_name>
#

def show_usage()
  file = File.basename($0)
  puts "Usage: #{file} <your_name>"
  exit(1)
end

def main(name)
  # ここにスクリプトのメイン部分を記述していく
  puts "Hello, #{name}!"
end

if $0 == __FILE__
  show_usage() if ARGV.size < 1
  name = ARGV[0]
  main(name)
end
```

クラスを使うとき
----

```ruby
#!/usr/bin/env ruby
#
# 説明をここに。
#
# Usage:
#   $ ruby hello.rb <your_name>
#

def show_usage()
  file = File.basename($0)
  puts "Usage: #{file} <your_name>"
  exit(1)
end

# このクラスにスクリプトのメイン部分を記述していく
class Hello
  def initialize(name)
    @name = name
  end

  def greet
    puts "Hello, #{@name}!"
  end
end

if $0 == __FILE__
  show_usage() if ARGV.size < 1
  name = ARGV[0]
  obj = Hello.new(name)
  obj.greet
end
```

