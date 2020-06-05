package com.gamma.tools;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.ResourceUtils;  
/** 
 * 将文件夹下面的文件 
 * 打包成zip压缩文件 
 *  
 * @author admin 
 * 
 */  
public final class FileTools {  
	protected static Logger logger =  Logger.getLogger(FileTools.class);
     
	  public static void fileToZip(String sourceFileName,String zipFileName)
	    {
	        ZipOutputStream out = null;
	        BufferedOutputStream bos = null;
	        File sourceFile = null;
	        
	        //调用函数
	        try {
	        	
	        	   //创建zip输出流
		        out = new ZipOutputStream( new FileOutputStream(zipFileName));
		        
		        //创建缓冲输出流
		        bos = new BufferedOutputStream(out);
		        
		        sourceFile = new File(sourceFileName);
	        	
				compress(out,bos,sourceFile,sourceFile.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				
			 	try {
			 		bos.flush();
					bos.close();
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}
			       
			}
	        
	    }
	    
	    private static void compress(ZipOutputStream out,BufferedOutputStream bos,File sourceFile,String base) throws Exception
	    {
	        //如果路径为目录（文件夹）
	        if(sourceFile.isDirectory())
	        {
	        
	            //取出文件夹中的文件（或子文件夹）
	            File[] flist = sourceFile.listFiles();
	            
	            if(flist.length==0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
	            {
	                out.putNextEntry(  new ZipEntry(base+"/") );
	            }
	            else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
	            {
	                for(int i=0;i<flist.length;i++)
	                {
	                    compress(out,bos,flist[i],base+"/"+flist[i].getName());
	                }
	            }
	        }
	        else//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
	        {
	            out.putNextEntry( new ZipEntry(base) );
	            FileInputStream fos = new FileInputStream(sourceFile);
	            
                int length = 0;
                while ((length = fos.read()) != -1) {
                	out.write(length);
                }
 
	            fos.close();
	            
	        }
	    }      
	    
	    
    public static boolean deleteDir(File dir) {

    	if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	     }
	     // 目录此时为空，可以删除
	     return dir.delete();
    }
    

	public static void downloadFIle(HttpServletResponse response, String filePath, String fileName) {
		InputStream in = null;
		OutputStream out = null;
		try {
			
			response.setHeader("content-disposition", "attachment;filename="  
	                + URLEncoder.encode(fileName, "UTF-8")); 
			  // 创建读取文件的流  
	        in = new FileInputStream(filePath);  
	        // 创建输出文件的流，也就是response的OutputStream  
	        out = response.getOutputStream();  
	        // 进行读取并写出，是以前学习io的模版代码  
	        byte[] buffer = new byte[1024];  
	        int len = 0;  
	        while ((len = in.read(buffer)) > 0) {  
	            out.write(buffer, 0, len);  
	        }  
	         
		} catch (IOException e) {
			 logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			
				try {
					
					if(in != null){
						in.close();
					}
					
					if(out != null){
						out.close();
					}
					
				} catch (IOException e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				} 
			
		}
		
	}  
	
	public static void readToWrite(String srcFile, String toFile) {
	    	 
		Writer w = null; 
	    Scanner in = null;
	      
	    try {
	    	   String result = "";
	    	    in = new Scanner(new File(srcFile));
		        while (in.hasNextLine()) {
		            result += in.nextLine() + "\r\n";
		        }
		        w = new FileWriter(new File(toFile));
		        w.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
			    in.close();
				w.flush();
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	        
	}
	
	public static void readFileToWriter(File srcFile, BufferedWriter bw) {
   	 
	    Scanner in = null;
	    try {
	    	  
	    	    in = new Scanner(srcFile);
		        while (in.hasNextLine()) {
		        	bw.write(in.nextLine());
		        	bw.newLine();
		        }
		       
		} catch (IOException e) {
			 logger.info(e.getMessage());
			e.printStackTrace();
		}finally {
			if(in != null){
				in.close();
			}
			
		}
	}
	
	
	public static void readFileToWriter(String urlName, BufferedWriter bw) {
		
		InputStream is = null;
	    try {
	    	URL url = ResourceUtils.getURL(urlName);
	    	
	    	if(url == null){
	    		return;
	    	}
	    	is = url.openStream();
	    	byte[] b = new byte[1024];
	    	 for (int n; (n = is.read(b)) != -1;) {
	    		 bw.write(new String(b, 0, n,"UTF-8"));
	    		 bw.newLine();
	        }
	    	 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					 logger.info(e.getMessage());
				}
			}
			
		}
		
	}
	
	
	public static BufferedWriter writerFiterByUTF8(String filePath,String fileName) throws UnsupportedEncodingException, FileNotFoundException{
		File controllerFile = new File(filePath,fileName+".java");
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(controllerFile), "UTF-8"));
	}

	public static BufferedWriter writerFiterToXML(String filePath,String fileName) throws UnsupportedEncodingException, FileNotFoundException{
		File controllerFile = new File(filePath,fileName+".xml");
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(controllerFile), "UTF-8"));
	}
			
	    
    public static void main(String[] args) throws Exception{  
        String sourceFilePath = "F:\\code_generator\\spring_boot_v1\\gamma\\src\\main\\webapp\\20171429111414";  
        String zipFilePath = "F:\\code_generator\\spring_boot_v1\\gamma\\src\\main\\webapp\\12700153file.zip";  
        FileTools.fileToZip(sourceFilePath, zipFilePath);  
    }

	

      
}  