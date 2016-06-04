package cn.edu.fudan.anniversary.util;

/**
 *
 * @author xuri
 * @date 2014-11-5
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImage {

	public static File saveToFile(String destUrl) {
		File file = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			String[] strings = destUrl.split("/");
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream("WebRoot/images/"
					+ strings[strings.length - 1]);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
			file = new File("WebRoot/images/" + strings[strings.length - 1]);
			System.out.print("success\t");
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
		return file;
	}

	public static void main(String[] args) {
//
//		saveToFile("http://news.fudan.edu.cn//uploadfile/poster/1(210).jpg");
	}
}
