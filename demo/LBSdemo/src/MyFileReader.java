

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyFileReader{
	private static final String delimiterSharp = "#";
	private static final String delimiter4Term =":";
	private static final String delimiter = " ";
	private static final String delimiterS = "\\s+";
	private FileInputStream fis = null;
	private InputStreamReader isr = null;
	private BufferedReader br = null;
	private File fInput = null;
	private String charset = "utf-8";

	public MyFileReader(File fInput){
		this.fInput = fInput;
	}

	public MyFileReader(String charset, File fInput){
		this.charset = charset;
		this.fInput = fInput;
	}

	public MyFileReader(File dirInput, String fileName){
		this(dirInput, fileName, "utf-8");
	}

	public MyFileReader(File dirInput, String fileName, String charset){
		this.fInput = new File(dirInput, fileName);
		this.charset = charset;
	}

	public static void main(String []args){
		File dir = new File("D:/test");
		MyFileReader mfw = new MyFileReader(dir,"udi.txt");
		mfw.open();
		System.out.println(mfw.readLine());
		mfw.close();
	}

	public void open(){
		try{
			fis = new FileInputStream(fInput);
			isr = new InputStreamReader(fis,charset);
			br = new BufferedReader(isr);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public String readLine(){
		String line = null;
		try{
			line = br.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		return line;
	}

	public void close(){
		try{
			br.close();
			isr.close();
			fis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}//*/
}
