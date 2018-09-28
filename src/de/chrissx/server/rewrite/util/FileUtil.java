package de.chrissx.server.rewrite.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	
	public static void zipFiles(File[] files, File zipFile) throws IOException { 
		byte[] b = new byte[16*1024];
		ZipOutputStream zout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
		for(int i=0;i<files.length;i++)
		{
		    FileInputStream fin = new FileInputStream(files[i]);
		    zout.putNextEntry(new ZipEntry(files[i].getName()));
		    int length;
		    while((length=fin.read())>0)
		    {
		        zout.write(b,0,length);
		    }
		    zout.closeEntry();
		    fin.close();
		}
		zout.close();
    }
	
	public static void unzipFile(File zipFile, File outputDir) throws IOException {
		if(!outputDir.exists()) {
			outputDir.mkdir();
		}
		ZipInputStream zip = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
		ZipEntry e;
		while((e = zip.getNextEntry()) != null) {
			String eN = outputDir + File.separator + e.getName();
			if(!e.isDirectory()) {
				unzipSingleFileOnlyForUnzipFileMethod(zip, eN);
			}else {
				File d = new File(eN);
				d.mkdir();
			}
			zip.closeEntry();
		}
		zip.close();
	}
	
	private static void unzipSingleFileOnlyForUnzipFileMethod(ZipInputStream zip, String outputFile) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
		byte[] bytes = new byte[16*1024];
		int currentSize = 0;
		while((currentSize = zip.read(bytes, 0, bytes.length)) != -1) {
			out.write(bytes, 0, currentSize);
		}
		out.close();
	}
	
	public static void write(File f, String s, boolean append) throws IOException {
		if(!f.exists()) {
			Files.createFile(f.toPath());
		}
		if(s.length() == 0 || s == null) {
			return;
		}
		BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(f, append));
		String[] strs = s.split("\n");
		writer.write(strs[0]);
		writer.flush();
		for(int i = 1; i < strs.length; i++) {
			writer.newLine();
			writer.flush();
			writer.write(strs[i]);
			writer.flush();
		}
		writer.close();
	}
	
	public static void write(File f, String[] s, boolean append) throws IOException {
		if(!f.exists()) {
			Files.createFile(f.toPath());
		}
		if(s.length == 0 || s == null) {
			return;
		}
		BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(f, append));
		writer.write(s[0]);
		writer.flush();
		for(int i = 1; i < s.length; i++) {
			writer.newLine();
			writer.flush();
			writer.write(s[i]);
			writer.flush();
		}
		writer.close();
	}
	
	public static void write(File f, List<String> s, boolean append) throws IOException {
		if(!f.exists()) {
			Files.createFile(f.toPath());
		}
		if(s.size() == 0 || s == null) {
			return;
		}
		BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(f, append));
		writer.write(s.get(0));
		writer.flush();
		for(int i = 1; i < s.size(); i++) {
			writer.newLine();
			writer.flush();
			writer.write(s.get(i));
			writer.flush();
		}
		writer.close();
	}
	
	public static List<String> read(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> out = new ArrayList<String>();
		String s = "";
		while((s = reader.readLine()) != null) {
			out.add(s);
		}
		reader.close();
		return out;
	}
	
	public static void deleteFiles(File f) {
		for(File ff : f.listFiles()) {
			if(ff.isDirectory()) {
				deleteFiles(ff);
				ff.delete();
			}else {
				ff.delete();
			}
		}
	}
}