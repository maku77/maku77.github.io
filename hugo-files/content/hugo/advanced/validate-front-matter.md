---
title: "Python で Markdown ファイルのフロントマターの記述が正しいかチェックする"
url: "p/bes7iu7/"
date: "2019-10-22"
tags: ["Hugo"]
aliases: /hugo/advanced/validate-front-matter.html
---

下記の Python スクリプト (`check-front-matter.py`) は、Markdown ファイルに記載された YAML フロントマターの内容が正しいかどうかを調べるサンプルスクリプトです。
確認内容は、`validate()` 関数の中に記載されていますので、用途に応じてこの内容を書き換えてください。
下記のサンプルコードでは、フロントマターに `title` と `date` が記載されているかをチェックしています。


使い方
----

PyYAML というライブラリを使用しているので、下記のようにインストールしておく必要があります。

{{< code lang="console" title="PyYAML のインストール" >}}
$ pip install pyyaml
{{< /code >}}

次のように実行すると、カレントディレクトリ以下の全ての Markdown ファイル (.md) の内容を検証できます。

```console
$ python check-front-matter.py
content/title3.md: Missing title or date
content/title4.md: Missing title or date
content/title5.md: Missing title or date
```

この例では、3 つの Markdown ファイルのフロントマターが正しく記述されていないことが検出されています。


サンプルスクリプト
----

{{< code lang="python" title="check-front-matter.py" >}}
#!/usr/bin/env python3
import yaml

class YamlFrontMatter:
    """YAML ファイルからフロントマターを取得するクラスです。"""

    def __init__(self, filename):
        self.filename = filename

    def load_frontmatter(self):
        """
        Markdown ファイルのフロントマターを読みこんで、
        Python のオブジェクトとして返します。
        """
        with open(self.filename, encoding='utf-8') as file:
            text = self.__load_front_matter_text(file)
            return yaml.safe_load(text)

    def __load_front_matter_text(self, file):
        """
        ファイルオブジェクトからフロントマター部分のテキストを抽出します。
        セパレータの "---" は含みません。
        """
        line = file.readline().rstrip('\r\n')
        if line != '---':
            raise Exception(file.name + ': Front matter must begin with "---"')

        result = []
        for line in file:
            line = line.rstrip('\r\n')
            if line == '---':
                return '\n'.join(result)
            result.append(line)

        raise Exception(file.name + ': Front matter must end with "---"')


# カレントディレクトリ以下のすべての Markdown ファイルの
# フロントマターの記述が正しいかチェックします。
if __name__ == '__main__':
    import glob
    import sys

    def validate(filename, fm):
        """
        フロントマターの内容 (fm) が正しいかチェックします。
        ここの実装は自由に変更してください。
        """
        if ('title' in fm) and ('date' in fm):
            # print(filename + ': OK')
            pass
        else:
            print(filename + ': Missing title or date')

    for filename in glob.iglob('**/*.md', recursive=True):
        try:
            fm = YamlFrontMatter(filename).load_frontmatter()
            validate(filename, fm)
        except Exception as e:
            print(e, file=sys.stderr)
            sys.exit(1)
{{< /code >}}

