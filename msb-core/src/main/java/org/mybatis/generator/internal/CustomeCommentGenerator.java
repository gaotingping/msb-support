package org.mybatis.generator.internal;


import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class CustomeCommentGenerator extends DefaultCommentGenerator {

	@Override //method
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {}

	@Override //字段注释
	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		String remark = introspectedColumn.getRemarks();
		field.addJavaDocLine("@ApiDescribe(\""+remark+"\")");
	}

	@Override //getter
	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {}

	@Override //setter
	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {}

	@Override //xml方法注释
	public void addComment(XmlElement xmlElement) {}
}
