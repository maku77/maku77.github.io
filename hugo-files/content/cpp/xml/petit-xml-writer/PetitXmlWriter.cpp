#include "PetitXmlWriter.h"

const char *PetitXmlWriter::HEADER_TEMPLATE = "<?xml version='1.0' encoding='%s'?>";
const char *PetitXmlWriter::DEFAULT_ENCODING = "UTF-8";
const char *PetitXmlWriter::INDENT_STRING = "  ";


PetitXmlWriter::PetitXmlWriter(const char *fileName, bool indent, const char *encoding)
: m_indentMode(indent), m_prevNodeType(NODE_TYPE_NONE) {
    m_pFile = fopen(fileName, "w");
    WriteHeader(encoding);
}


PetitXmlWriter::~PetitXmlWriter() {
    fclose(m_pFile);
}


void PetitXmlWriter::BeginNode(const char *name) {
    if (m_prevNodeType == NODE_TYPE_BEGIN_TAG) {
        fprintf(m_pFile, ">");
    }
    if (m_indentMode && m_prevNodeType != NODE_TYPE_TEXT) {
        Indent();
    }
    fprintf(m_pFile, "<%s", name);
    m_tagStack.push_back(name);
    m_prevNodeType = NODE_TYPE_BEGIN_TAG;
}


void PetitXmlWriter::EndNode() {
    if (m_prevNodeType == NODE_TYPE_BEGIN_TAG) {
        fprintf(m_pFile, ">");
    }
    std::string endTag = m_tagStack.back();
    m_tagStack.pop_back();
    if (m_indentMode && m_prevNodeType != NODE_TYPE_TEXT) {
        Indent();
    }
    fprintf(m_pFile, "</%s>", endTag.c_str());
    m_prevNodeType = NODE_TYPE_END_TAG;
}


void PetitXmlWriter::AddAttr(const char *name, const char *value) {
    fprintf(m_pFile, " %s=\"%s\"", name, value);
}


void PetitXmlWriter::AddText(const char *text) {
    if (m_prevNodeType == NODE_TYPE_BEGIN_TAG) {
        fprintf(m_pFile, ">");
    }
    fprintf(m_pFile, text);
    m_prevNodeType = NODE_TYPE_TEXT;
}


void PetitXmlWriter::WriteHeader(const char *encoding) {
    fprintf(m_pFile, HEADER_TEMPLATE, encoding);
}


void PetitXmlWriter::Indent() {
    fprintf(m_pFile, "\n");
    for (TagStack::size_type i = 0; i < m_tagStack.size(); ++i) {
        fprintf(m_pFile, INDENT_STRING);
    }
}

