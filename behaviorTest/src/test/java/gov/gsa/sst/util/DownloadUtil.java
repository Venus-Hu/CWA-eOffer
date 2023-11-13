package gov.gsa.sst.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadUtil {
	private static Logger log = LoggerFactory.getLogger(DownloadUtil.class);
	
	/**
	 * Determine if test is being run locally or not
	 * 
	 * @return true if value of driver.location is "local"
	 */
	public static boolean isLocalEnvironment() {
		String localorRemote = LoadProperties.getProperty("driver.location");
		return localorRemote.equalsIgnoreCase("local");
	}
	
	/**
	 * Delete files from default data download location
	 * 
	 * @param fileName prefix of file(s) to be deleted
	 */
	public static void deleteFiles(String fileName) {
        File f = DownloadUtil.setUpDownloadLocation();
        FilenameFilter errorFiles = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(fileName);
            }
        };

        File[] files = f.listFiles(errorFiles);
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
	}
	
	/**
	 * Poll for file to exists every 1 second and wait until time-out
	 * 
	 * @param driver
	 * @param file name of file, e.g. 'temp.txt'
	 * @param timeOutInSeconds time out for waiting
	 * @return true if file was downloaded successfully
	 */
    public static boolean waitForDownload(String fileName, int timeOutInSeconds) {
    	boolean isDownloaded = false;
    	String filePath = DownloadUtil.setUpDownloadLocation().getPath();
        File file = new File(filePath + File.separator + fileName);
        
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeOutInSeconds*1000) {
        	if (file.exists()) {
        		log.info("File '" + file.getName() +"' detected. Done waiting.");
        		isDownloaded = true;
        		break;
        	} else {
        		log.info("File '" + file.getName() +"' not detected. Sleeping...");
        		sleep(1000);
        	}
        }
        return isDownloaded;
    }
    
    /**
     * Sleep method
     * 
     * @param milliseconds time to sleep in millisecs
     */
    public static void sleep(int milliseconds) {
    	try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.info(e.getMessage());
		}
	}

    /**
     * Whether a file exists in the default
     * download folder. 
     * 
     * @param fileName
     * @return
     */
	public static boolean isFileDownloaded(String fileName) {
		String filePath = DownloadUtil.setUpDownloadLocation().getPath();
        File file = new File(filePath + File.separator + fileName);
		return file.exists();
	}
	
	/**
	 * Create a test data download folder. If 'driver.filedownloadPath'
	 * is defined and the driver.location is 'remote' or not defined, download folder is
	 * Java 'user.dir'/testDataDownload. Otherwise, it will
	 * be the value in 'driver.filedownloadPath'
	 * 
	 * @return reference to download directory
	 */
	public static File setUpDownloadLocation() {
		String downloadDriverPath = LoadProperties.getProperty("driver.filedownloadPath");
		String driverLocation = LoadProperties.getProperty("driver.location");
		
		File downloadDir = null;
		if (downloadDriverPath != null) {
			if (driverLocation != null && driverLocation.toLowerCase().trim().contains("local")) {
				downloadDir = new File(downloadDriverPath);
			} else {
				downloadDir = new File(System.getProperty("user.dir") + File.separator + "testDataDownload");
			}
		} else {
			downloadDir = new File(System.getProperty("user.dir") + File.separator + "testDataDownload");
		}
		log.info("Data download folder " + downloadDir.getPath() + " will be created if it doesn't exist...");
		
		if (!downloadDir.exists()) {
			downloadDir.mkdir();
		}
		return downloadDir;
	}

	/**
	 * Delete files with given name from the given
	 * directory
	 * 
	 * @param fileName e.g. a file name
	 * @param downloadDir e.g. download directory
	 */
	public static void deleteFiles(String fileName, File downloadDir) {
        FilenameFilter errorFiles = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(fileName);
            }
        };

        File[] files = downloadDir.listFiles(errorFiles);
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
	}

	/**
	 * Wait for download
	 * 
	 * @param fileName
	 * @param downloadDir
	 * @param timeOutInSeconds
	 * @return true if file is found before time out
	 */
	public static boolean waitForDownload(String fileName, File downloadDir, int timeOutInSeconds) {
		boolean isDownloaded = false;
        File file = new File(downloadDir.getPath() + File.separator + fileName);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeOutInSeconds*1000) {
        	if (file.exists()) {
        		log.info("File '" + file.getName() +"' detected. Done waiting.");
        		isDownloaded = true;
        		break;
        	} else {
        		log.info("File '" + file.getName() +"' not detected. Sleeping...");
        		sleep(1000);
        	}
        }
        log.info("Is file " + fileName +" downloaded: " + isDownloaded);
        return isDownloaded;
	}
	
	/**
	 * Unzips a file with .zip format 
	 * 
	 * @param zipFileName
	 * @param downloadDir
	 * @throws IOException
	 */
	public static void unZipFile(String zipFileName, File downloadDir) throws IOException
	{
		byte[] buffer = new byte[1024];
		File file = new File(downloadDir.getPath() + File.separator + zipFileName);
		try{
			ZipInputStream zinput = new ZipInputStream(new FileInputStream(file));
			ZipEntry zEntry = zinput.getNextEntry();
			while(zEntry != null){
				String fileName = zEntry.getName();
				File newFile = new File(downloadDir + File.separator + fileName);
				FileOutputStream fout = new FileOutputStream(newFile);
				int len;
				while((len = zinput.read(buffer)) > 0){
					fout.write(buffer, 0, len);
				}
				fout.flush();
				fout.close();
				zEntry = zinput.getNextEntry();
			}
			zinput.closeEntry();
			zinput.close();	
		}catch(IOException e){
			log.error("File could not be unzipped - " + e.getMessage());
		}

	}
	
	public static File zipFile(List<String> fileNames, File downloadDir) throws IOException
	{
		/*byte[] buffer = new byte[1024];
		File file = new File(downloadDir.getPath() + File.separator + zipFileName);
		try{
			ZipInputStream zinput = new ZipInputStream(new FileInputStream(file));
			ZipEntry zEntry = zinput.getNextEntry();
			while(zEntry != null){
				String fileName = zEntry.getName();
				File newFile = new File(downloadDir + File.separator + fileName);
				FileOutputStream fout = new FileOutputStream(newFile);
				int len;
				while((len = zinput.read(buffer)) > 0){
					fout.write(buffer, 0, len);
				}
				fout.flush();
				fout.close();
				zEntry = zinput.getNextEntry();
			}
			zinput.closeEntry();
			zinput.close();	
		}catch(IOException e){
			log.error("File could not be unzipped - " + e.getMessage());
		}
		*/
		FileOutputStream fout = new FileOutputStream(downloadDir.getPath() + File.separator + "photos.zip");
		ZipOutputStream zout = new ZipOutputStream(fout);
		for(int i =0; i< fileNames.size(); i++)
		{
		    ZipEntry ze = new ZipEntry(fileNames.get(i));
		    zout.putNextEntry(ze);
		    //send data to zout;
		    zout.closeEntry();
		}
		zout.close();
		File file = new File(downloadDir.getPath() + File.separator + "photos.zip");
		return file;
	}
}
