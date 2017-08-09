---
title: Propertiesファイルから設定値を読み込む
created: 2011-04-07
---

アプリケーションの設定を以下のような properties ファイルに保存して、読み出す例です。

#### config.properties
~~~
data1 = Hello
~~~

次に示す `AppConfig` クラスでは、`config.properties` ファイルの内容を読み込んで `java.io.Properties` インスタンスを生成しています。
ファイルを読み込めない場合は、デフォルト値を使用するようにしています。

#### AppConfig.java

~~~ java
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Manages configuration values.
 */
public class AppConfig {
    private static final String FILE_NAME = "config.properties";
    private static final String DATA1_KEY = "data1";
    private static final String DATA1_DEFAULT = "default value";
    private Properties properties = new Properties();

    public AppConfig() {}

    /**
     * Load a configuration file.
     */
    public void load() throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);
        properties.load(fis);
        fis.close();
    }

    /**
     * Get the "data1" property value.
     */
    public String getData1() {
        return properties.getProperty(DATA1_KEY, DATA1_DEFAULT);
    }
}
~~~

上記のクラスは以下のような感じで使用します。

~~~ java
AppConfig config = new AppConfig();
try {
    config.load();
} catch (IOException e) {
    e.printStackTrace();
}
System.out.println("data1=" + config.getData1());
~~~

