package com.mvw.china.doc;

import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfParagraphStyle;
import com.mvw.china.bus.annotation.ApiMethod;
import com.mvw.china.bus.annotation.ApiParam;
import com.mvw.china.bus.annotation.ApiService;
import com.mvw.china.common.JsonFormatUtils;
import com.mvw.china.common.ReflectUtils;
import com.mvw.china.common.ScannerUtils;

public class AutoBuildDoc {

	public static void build(String docPath, List<String> p) throws Exception {

		Document doc = new Document(PageSize.A4);
		RtfWriter2.getInstance(doc, new FileOutputStream(docPath));
		doc.open();

		Font font10 = getFont(12, Font.NORMAL);

		List<Class<?>> list = ScannerUtils.getServiceEntry(p);

		for (int i = 0; i < list.size(); i++) {

			Class<?> c = list.get(i);

			// h1
			ApiService apiService = c.getAnnotation(ApiService.class);
			doc.add(new Paragraph((i + 1) + " " + apiService.value(), RtfParagraphStyle.STYLE_HEADING_1));

			Method[] m = c.getDeclaredMethods();
			for (int j = 0; j < m.length; j++) {
				Method tm = m[j];
				if (Modifier.isPublic(tm.getModifiers()) && tm.getDeclaringClass() != Object.class) {

					// h2
					doc.add(new Paragraph(""));
					ApiMethod apiMethod = tm.getAnnotation(ApiMethod.class);
					if (apiMethod != null) {

						doc.add(new Paragraph((i + 1) + "." + (j + 1) + " " + apiMethod.desc(),
								RtfParagraphStyle.STYLE_HEADING_2));
						doc.add(new Paragraph(""));

						Table table = new Table(2);
						table.setWidth(32 * 3);
						table.setAlignment(Element.ALIGN_LEFT);
						table.setWidths(new float[] { 20f, 76f });

						// 方法编码
						Cell c1 = new Cell(new Phrase("服务编码", font10));
						table.addCell(c1);

						Cell c2 = new Cell(new Phrase(apiMethod.value(), font10));
						table.addCell(c2);

						// 输入
						JSONObject args = new JSONObject();
						Annotation[][] ps = tm.getParameterAnnotations();
						if (ps != null) {
							for (Annotation[] pa : ps) {
								for (Annotation p2 : pa) {
									if (p2 instanceof ApiParam) {
										ApiParam p3 = (ApiParam) p2;
										if(ReflectUtils.isBaseType(p3.type())){
											args.put(p3.value(),p3.desc());
										}else{
											args.put(p3.value(), ReflectUtils.allFields(p3.type()));
										}
									}
								}
							}
						}
						String paramStr = JsonFormatUtils.formatJson(args.toJSONString());
						Cell c3 = new Cell(new Phrase("输入", font10));
						table.addCell(c3);

						Cell c4 = new Cell(new Phrase(paramStr, font10));
						table.addCell(c4);

						// 输出
						Class<?> r = tm.getReturnType();
						JSONObject returnJson = ReflectUtils.allFields(r);
						String returnStr = JsonFormatUtils.formatJson(returnJson.toJSONString());

						Cell c5 = new Cell(new Phrase("输出", font10));
						table.addCell(c5);

						Cell c6 = new Cell(new Phrase(returnStr, font10));
						table.addCell(c6);

						doc.add(table);
					}
				}
			}
		}

		doc.close();
	}

	private static Font getFont(int fontSize, int fontStyle) {

		Font chineseFont = null;

		try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			chineseFont = new Font(bfChinese, fontSize, fontStyle);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chineseFont;
	}
}
