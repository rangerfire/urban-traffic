

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MyFileWriter{
	private static final String delimiterSharp = "#";
	private static final String delimiter4Term =":";
	private static final String delimiter = " ";
	private static final String delimiterS = "\\s+";
	private FileOutputStream fos = null;
	private OutputStreamWriter osw = null;
	private BufferedWriter bw = null;
	private File fOutput = null;
	private boolean append = false;
	private String charset = "utf-8";
	
	public MyFileWriter(File fOutput){
		this.fOutput = fOutput;
		this.append = false;
	}

	public MyFileWriter(String charset, File fOutput){
		this.fOutput = fOutput;
		this.append = false;
		this.charset = charset;
	}

	public MyFileWriter(File fOutput, boolean append){
		this.fOutput = fOutput;
		this.append = append;
	}

	public MyFileWriter(File fOutput, boolean append, String charset){
		this.fOutput = fOutput;
		this.append = append;
		this.charset = charset;
	}

	public MyFileWriter(File dirOutput, String fileName){
		this.fOutput = new File(dirOutput,fileName);
		this.append = false;
	}

	public MyFileWriter(File dirOutput, String fileName, String charset){
		this.fOutput = new File(dirOutput,fileName);
		this.append = false;
		this.charset = charset;
	}

	public MyFileWriter(File dirOutput, String fileName, boolean append){
		this.fOutput = new File(dirOutput,fileName);
		this.append = append;
	}

	public MyFileWriter(File dirOutput, String fileName, boolean append, String charset){
		this.fOutput = new File(dirOutput,fileName);
		this.append = append;
		this.charset = charset;
	}

	public static void main(String []args){
		File dir = new File("D:/test");
		MyFileWriter mfw = new MyFileWriter(dir,"jingdong1.txt");
		mfw.open();
		mfw.write("test");
		mfw.close();
	}

	public void open(){
		try{
			fos = new FileOutputStream(fOutput,append);
			osw = new OutputStreamWriter(fos,charset);
			bw = new BufferedWriter(osw);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void write(String line){
		try{
			bw.write(line);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void writeln(String line){
		try{
			bw.write(line);
			bw.newLine();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void newLine(){
		writeln("");
	}

	public void close(){
		try{
			bw.close();
			osw.close();
			fos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}//*/
}

