package com.mvw.china.doc;

import java.io.FileOutputStream;
import java.io.OutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
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

public class Test2 {
	
	private static Test2 export = null;
	
	private Test2() {}
	
	public static Test2 newInstance() {
		if(export == null) {
			export = new Test2();
		}
		return export;
	}
	
	public void exportWordDocument(OutputStream out) throws Exception {
        Document doc = new Document(PageSize.A4);
        RtfWriter2.getInstance(doc, out);
        doc.open();
        
        //Font titleFont = getChineseFont(16, Font.NORMAL);
        //Font txtFont = getChineseFont(12, Font.NORMAL);
        
        //IText中用文本块(Chunk)、短语(Phrase)和段落(paragraph)处理文本
        //paragraph
        doc.add(new Paragraph("Paragraph"));
        
        //Chunk
        doc.add(new Chunk("Chunk"));
        
        doc.add(new Chapter(new Paragraph("Miu的介绍"), 1));
        
        //标题
        doc.add(new Paragraph("1", RtfParagraphStyle.STYLE_HEADING_1));
        doc.add(new Paragraph("2", RtfParagraphStyle.STYLE_HEADING_2));
        doc.add(new Paragraph("3", RtfParagraphStyle.STYLE_HEADING_3));
        
        /*
			RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
			rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
			rtfGsBt2.setStyle(Font.NORMAL);
			rtfGsBt2.setSize(12);
			Paragraph title = new Paragraph("测试");
			title.setAlignment(Element.ALIGN_CENTER);
			title.setFont(titleFont);
			document.add(title);
			//正文
			title = new Paragraph("1.第一章");
			title.setFont(rtfGsBt1);
			document.add(title); 
         */
        
        //table
        Table table = initReportTableData();
        doc.add(table);
        doc.close();
	}
	
	private Table initReportTableData() throws Exception {
		
		Font font10 = getChineseFont(10, Font.NORMAL);
		
		Table table = new Table(2);
		table.setWidth(32*3);
		table.setAlignment(Element.ALIGN_LEFT);
		
		table.setWidths(new float[]{20f, 76f});

		//正文
		for(int i = 0;i<5; i ++) {
			Cell c1 = new Cell(new Phrase("服务编码", font10));
    		//c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        	//c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		table.addCell(c1);
    		
    		Cell c2 = new Cell(new Phrase("--", font10));
    		//c2.setHorizontalAlignment(Element.ALIGN_LEFT);
        	//c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		table.addCell(c2);
	    }
		
		return table;
	}
	
	private static Font getChineseFont(int fontSize, int fontStyle) {
		
		Font chineseFont = null;
		
		try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			chineseFont = new Font(bfChinese, fontSize, fontStyle);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return chineseFont;
	}
	
	public static void main(String[] args) throws Exception {
		FileOutputStream out = new FileOutputStream("myWord.doc");
		Test2.newInstance().exportWordDocument(out);
	}
}
