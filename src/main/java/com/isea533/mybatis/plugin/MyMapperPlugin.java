package com.isea533.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import tk.mybatis.mapper.generator.MapperPlugin;

import java.util.Iterator;
import java.util.List;

/**
 * @author yahuakang
 * @since 2020/8/6
 */
public class MyMapperPlugin extends MapperPlugin {

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        this.generateSqlBaseColumns(document, introspectedTable);
        return true;
    }

    private void generateSqlBaseColumns(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        XmlElement sqlElement = new XmlElement("sql");
        Attribute attr = new Attribute("id", "Base_Column_List");
        sqlElement.addAttribute(attr);
        TextElement comment = new TextElement("<!-- WARNING - @mbg.generated -->");
        StringBuilder columnsBuilder = new StringBuilder();
        List columnList = introspectedTable.getAllColumns();
        Iterator columns = columnList.iterator();
        while(columns.hasNext()) {
            IntrospectedColumn content = (IntrospectedColumn)columns.next();
            columnsBuilder.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + '.' + content.getActualColumnName()).append(", ");
        }
        String columns1 = columnsBuilder.substring(0, columnsBuilder.length() - 2);
        TextElement content1 = new TextElement(columns1);
        sqlElement.addElement(comment);
        sqlElement.addElement(content1);
        rootElement.addElement(new TextElement(""));
        rootElement.addElement(sqlElement);
        rootElement.addElement(new TextElement(""));
    }
}