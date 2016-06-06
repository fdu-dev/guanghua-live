package cn.edu.fudan.anniversary.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class FileTypeUtils {
	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	private FileTypeUtils() {
	}

	static {
		getAllFileType(); // 初始化文件类型信息
	}

	/**
	 * Created on 2013-1-21
	 * <p>
	 * Discription:[getAllFileType,常见文件头信息]
	 * 
	 * @author:shaochangfu
	 */
	private static void getAllFileType() {
		FILE_TYPE_MAP.put("jpg", "FFD8FF"); // JPEG (jpg)
		FILE_TYPE_MAP.put("png", "89504E47");// PNG (png)
		FILE_TYPE_MAP.put("gif", "47494638");// GIF (gif)
		FILE_TYPE_MAP.put("bmp", "424D"); // Windows Bitmap (bmp)
		FILE_TYPE_MAP.put("zip", "504B0304");// zip 压缩文件
	}

	public static void main(String[] args) throws Exception {
		File f = new File("C:/Users/tom/Desktop/创业/dream/backend/WebRoot/images/event/1383648694628test.jpg");
		File f2 = new File("C:/Users/tom/Desktop/创业/dream/backend/WebRoot/images/event/1383640113124test.png");
		//File f3 = new File("/home/newpro/桌面/Screenshot.zip");
		//File f4 = new File("/home/newpro/桌面/svn插件1.6.zip");

		// 判断是否是iamge,是image 并且后缀名 是 JPEG,PNG,GIF BMP
		//System.out.println(isImage(f) + "is image");
		//System.out.println(isImage(f2) + "is image");
		System.out.println(getImageFileType(f) + " is image");
		System.out.println(getImageFileType(f2) + " is image");

		// 判断是否是zip
		//System.out.println(getFileByFile(f3) + " is zip");
		//System.out.println(getFileByFile(f4) + " is zip");

	}

	/**
	 * Created on 2013-1-21
	 * <p>
	 * Discription:[getImageFileType,获取图片文件实际类型,若不是图片则返回null]
	 * </p>
	 * 
	 * @param File
	 * @return fileType
	 * @author:shaochangfu
	 */
	public final static String getImageFileType(File f) {
		if (isImage(f)) {
			try {
				ImageInputStream iis = ImageIO.createImageInputStream(f);
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				if (!iter.hasNext()) {
					return null;
				}
				ImageReader reader = iter.next();
				iis.close();
				return reader.getFormatName();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * Created on 2013-1-21
	 * <p>
	 * Discription:[getFileByFile,获取文件类型,包括图片,若格式不是已配置的,则返回null]
	 * </p>
	 * 
	 * @param file
	 * @return fileType
	 * @author:shaochangfu
	 */
	public final static String getFileByFile(File file) {
		String filetype = null;
		byte[] b = new byte[50];
		try {
			InputStream is = new FileInputStream(file);
			is.read(b);
			filetype = getFileTypeByStream(b);
			is.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return filetype;
	}

	/**
	 * Created on 2013-1-21
	 * <p>
	 * Discription:[getFileTypeByStream]
	 * </p>
	 * 
	 * @param b
	 * @return fileType
	 * @author:shaochangfu
	 */
	public final static String getFileTypeByStream(byte[] b) {
		String filetypeHex = String.valueOf(getFileHexString(b));
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP
				.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue();
			if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Created on 2013-1-21
	 * <p>
	 * Discription:[isImage,判断文件是否为图片]
	 * </p>
	 * 
	 * @param file
	 * @return true 是 | false 否
	 * @author:shaochangfu
	 */
	public static final boolean isImage(File file) {
		boolean flag = false;
		try {
			BufferedImage bufreader = ImageIO.read(file);
			int width = bufreader.getWidth();
			int height = bufreader.getHeight();
			if (width == 0 || height == 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (IOException e) {
			flag = false;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * Created on 2013-1-21
	 * <p>
	 * Discription:[getFileHexString]
	 * </p>
	 * 
	 * @param b
	 * @return fileTypeHex
	 * @author:shaochangfu
	 */
	public final static String getFileHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		if (b == null || b.length <= 0) {
			return null;
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
