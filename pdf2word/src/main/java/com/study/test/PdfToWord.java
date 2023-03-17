package com.study.test;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.widget.PdfPageCollection;

import java.io.File;


public class PdfToWord {

	// �漰����·��
	// 1��pdf���ڵ�·������ʵ�������Ǵ��ⲿ�����

	// 2������Ǵ��ļ�����Ҫ�����з֣��������pdf·��
	 String splitPath = "./split/";
	
	// 3������Ǵ��ļ�����Ҫ����pdf�ļ�һ��һ������ת��
	 String docPath = "./doc/";

	public  String pdftoword(String  srcPath) {
		// 4���������ɵ�doc���ڵ�Ŀ¼��Ĭ���Ǻ������һ���ط�����Դʱ�����ṩ���صĽӿڡ�
		String desPath = srcPath.substring(0, srcPath.length()-4)+".docx";
		boolean result = false;
		try {
			// 0���ж�������Ƿ���pdf�ļ�
			//��һ�����ж�������Ƿ�Ϸ�
			boolean flag = isPDFFile(srcPath);
			//�ڶ������������·�����½��ļ���
			boolean flag1 = create();
			
			if (flag && flag1) {
				// 1������pdf
				PdfDocument pdf = new PdfDocument();
				pdf.loadFromFile(srcPath);
				PdfPageCollection num = pdf.getPages();
				
				// 2�����pdf��ҳ��С��11����ôֱ�ӽ���ת��
				if (num.getCount() <= 10) {
					pdf.saveToFile(desPath, com.spire.pdf.FileFormat.DOCX);
				}
				// 3�����������ҳ���Ƚ϶࣬�Ϳ�ʼ�����з���ת��
				else {	
					// ��һ������������з�,ÿҳһ��pdf
					pdf.split(splitPath+"test{0}.pdf",0);
					
					// �ڶ��������зֵ�pdf��һ��һ������ת��
					File[] fs = getSplitFiles(splitPath);
					for(int i=0;i<fs.length;i++) {
						PdfDocument sonpdf = new PdfDocument();
						sonpdf.loadFromFile(fs[i].getAbsolutePath());
						sonpdf.saveToFile(docPath+fs[i].getName().substring(0, fs[i].getName().length()-4)+".docx",FileFormat.DOCX);
					}
					//����������ת����doc�ĵ����кϲ����ϲ���һ�����word
					try {
						result = MergeWordDocument.merge(docPath, desPath); 
						System.out.println(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			} else {
				System.out.println("����Ĳ���pdf�ļ�");
				return "����Ĳ���pdf�ļ�";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//4���Ѹոջ����split��docɾ��
			if(result==true) {
				new FileDeleteTest().clearFiles(splitPath);
				new FileDeleteTest().clearFiles(docPath);
			}
		}
		return "ת���ɹ�";
	}


	private  boolean create() {
		File f = new File(splitPath);
		File f1 = new File(docPath);
		if(!f.exists() )  f.mkdirs();
		if(!f.exists() )  f1.mkdirs();
		return true;	    
	}

	// �ж��Ƿ���pdf�ļ�
	private  boolean isPDFFile(String srcPath2) {
		File file = new File(srcPath2);
		String filename = file.getName();
		if (filename.endsWith(".pdf")) {
			return true;
		}
		return false;
	}

	// ȡ��ĳһ·�������е�pdf
	private  File[] getSplitFiles(String path) {
		File f = new File(path);
		File[] fs = f.listFiles();
		if (fs == null) {
			return null;
		}
		return fs;
	}

}
