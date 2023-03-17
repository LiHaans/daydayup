package com.study.test;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.io.File;

public class MergeWordDocument {
	
    public static boolean merge(String docPath,String desPath){

    	File[] fs = getSplitFiles(docPath);
    	System.out.println(docPath);
		Document document = new Document(docPath+"test0.docx");
		
		for(int i=1;i<fs.length;i++) {
			 document.insertTextFromFile(docPath+"test"+i+".docx",FileFormat.Docx_2013);
		}
		//���Ĳ����Ժϲ���doc���б���2
		document.saveToFile(desPath);
		return true;       
    }
	// ȡ��ĳһ·�������е�pdf
	private static File[] getSplitFiles(String path) {
		File f = new File(path);
		File[] fs = f.listFiles();
		if (fs == null) {
			return null;
		}
		return fs;
	}

}
