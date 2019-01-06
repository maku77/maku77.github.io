---
title: "メソッドの可視性を設定する（メソッドのアクセス制御）"
date: "2011-10-10"
---

**デフォルトでは Ruby のメソッドは public** になります（`initialize` メソッドだけは private）。
メソッドの可視性を設定するには、`public`、`protected`、`private` キーワードを使って以下のように指定します。

```ruby
class MyClass
  def method1
  end
  def method2
  end
  def method3
  end
  def method4
  end

  public :method1, :method2
  protected :method3
  private :method4
end
```

C++ のような構文で可視性を設定することもできます。

```ruby
class MyClass
  public  # 以下のメソッドは public
    def method1
    end
    def method2
    end

  protected  # 以下のメソッドは protected
    def method3
    end

  private  # 以下のメソッドは private
    def method4
    end
end
```

