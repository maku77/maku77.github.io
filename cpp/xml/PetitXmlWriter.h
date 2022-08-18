#ifndef PETIT_XML_WRITER_H_44485D3F2A05405DB58E9D44EE109983
#define PETIT_XML_WRITER_H_44485D3F2A05405DB58E9D44EE109983

#include <stdio.h>
#include <string>
#include <vector>

class PetitXmlWriter {
private:
    static const char *HEADER_TEMPLATE;
    static const char *DEFAULT_ENCODING;
    static const char *INDENT_STRING;
    FILE *m_pFile;
    typedef std::vector<std::string> TagStack;
    TagStack m_tagStack;
    bool m_indentMode;
    enum PrevNodeType {
        NODE_TYPE_BEGIN_TAG,
        NODE_TYPE_END_TAG,
        NODE_TYPE_TEXT,
        NODE_TYPE_NONE
    };
    PrevNodeType m_prevNodeType;

public:
    PetitXmlWriter(const char *fileName, bool indent = true, const char *encoding = DEFAULT_ENCODING);
    ~PetitXmlWriter();
    void BeginNode(const char *name);
    void EndNode();
    void AddAttr(const char *name, const char *value);
    void AddText(const char *text);

private:
    void WriteHeader(const char *encoding);
    void Indent();
};

#endif // PETIT_XML_WRITER_H_44485D3F2A05405DB58E9D44EE109983

