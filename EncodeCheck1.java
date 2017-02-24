package ianus.image.resize;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EncodingAndDecodingUseToCheckFileAndFolderName {
	
	
	public static List<String> scanFileFolderNameEncoding(String dataFolderPathName) throws Exception{
		
		List<String> wrongEncodingList = new ArrayList<String>();
		
		File dataFolder = new File(dataFolderPathName);
		
		wrongEncodingList = pullDirectoryContents(dataFolder, wrongEncodingList);
		
		return wrongEncodingList;
	}
	

	private static List<String> pullDirectoryContents(File dataFolder, List<String> wrongEncodingList) throws IOException{
		File [] files = dataFolder.listFiles();
		
		if(null == files){
			return wrongEncodingList;
		}
		
		for(File child : dataFolder.listFiles()){
			if(!encodingUTF8(child.getName())){
				wrongEncodingList.add(child.getAbsolutePath());
			}
			if(child.isDirectory()){
				pullDirectoryContents(child, wrongEncodingList);
			}
		} 
		return wrongEncodingList;
	}
	
	private static boolean encodingUTF8(String name) throws UnsupportedEncodingException{
		
			 String encode = URLEncoder.encode(name, "UTF-8"); 
			 String decode  = URLDecoder.decode(name, "UTF-8");
		
		return encode.equals(decode) ? true : false;
	}

	//	String selectedFolder;
	//	byte[] bytes = selectedFolder.getBytes("ISO-8859-1");
	//	selectedFolder = new String(bytes, "UTF-8");
	
	// ###### OR #######
	//	selectedFolder = URLEncoder.encode(selectedFolder, "ISO-8859-1"); // H%C3%A9l%C3%A8ne
	//	selectedFolder = URLDecoder.decode(selectedFolder, "UTF-8"); // and finally : testüß
	//
	
	

	public static void main (String ...args) throws Exception{
		List<String> wrongEncodingList = scanFileFolderNameEncoding("/Users/mostafizur/Desktop/test");
		for(String eco : wrongEncodingList){
			System.out.println(eco.toString());
		}
	}

}
