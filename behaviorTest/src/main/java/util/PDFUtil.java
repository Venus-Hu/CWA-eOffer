package util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
/**
 * Created by skumar on 2/7/2017.
 */
public class PDFUtil {

    public static boolean isTextPresentInPDF(URL dataFileURL,String text){
        boolean value = false;
        try {
            File file = new File(dataFileURL.getFile());
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            value = pdfStripper.getText(pdDoc).contains(text);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean isTextPresentInPDF(String filePath,String text){
        boolean value = false;
        try {
            File file = new File(filePath);
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            value = pdfStripper.getText(pdDoc).contains(text);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean isTextPresentInPDFByPageNo(URL dataFileURL,String text,int startPageNo,int endPageNo){
        boolean value = false;
        try {
            File file = new File(dataFileURL.getFile());
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPageNo);
            pdfStripper.setEndPage(endPageNo);
            value = pdfStripper.getText(pdDoc).contains(text);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return value;
    }
    public static boolean isTextPresentInPDFByPageNo(String filePath,String text,int startPageNo,int endPageNo){
        boolean value = false;
        try {
            File file = new File(filePath);
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPageNo);
            pdfStripper.setEndPage(endPageNo);
            value = pdfStripper.getText(pdDoc).contains(text);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return value;
    }

    public static String getTextByArea(URL dataFileURL,int pageNo,int x,int y,int width,int height){
        String text = "";
        try{
        File file = new File(dataFileURL.getFile());
        PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripperByArea pdfStripperArea = new PDFTextStripperByArea();
            pdfStripperArea.setSortByPosition(true);
            Rectangle rect = new Rectangle(x,y,width,height);
            pdfStripperArea.addRegion("regionX",rect);
            PDPage docPage = pdDoc.getPage(pageNo);
            pdfStripperArea.extractRegions(docPage);
            text = pdfStripperArea.getTextForRegion("regionX");
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextByArea(URL dataFileURL,int pageNo,int width,int height){
        String text = "";
        try{
            File file = new File(dataFileURL.getFile());
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripperByArea pdfStripperArea = new PDFTextStripperByArea();
            pdfStripperArea.setSortByPosition(true);
            Rectangle rect = new Rectangle(width,height);
            pdfStripperArea.addRegion("region1",rect);
            PDPage docPage = pdDoc.getPage(pageNo);
            pdfStripperArea.extractRegions(docPage);
            text = pdfStripperArea.getTextForRegion("region1");
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextFromDoc(URL dataFileURL){
        String text = "";
        try {
            File file = new File(dataFileURL.getFile());
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(pdDoc);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextFromDocByPageNo(URL dataFileURL,int startPageNo,int endPageNo){
        String text = "";
        try {
            File file = new File(dataFileURL.getFile());
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPageNo);
            pdfStripper.setEndPage(endPageNo);
            text = pdfStripper.getText(pdDoc);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextFromDocByPageNo(URL dataFileURL,int startPageNo){
        String text = "";
        try {
            File file = new File(dataFileURL.getFile());
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPageNo);
            text = pdfStripper.getText(pdDoc);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextByArea(String filePath,int pageNo,int x,int y,int width,int height){
        String text = "";
        try{
            File file = new File(filePath);
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripperByArea pdfStripperArea = new PDFTextStripperByArea();
            pdfStripperArea.setSortByPosition(true);
            Rectangle rect = new Rectangle(x,y,width,height);
            pdfStripperArea.addRegion("region1",rect);
            PDPage docPage = pdDoc.getPage(pageNo);
            pdfStripperArea.extractRegions(docPage);
            text = pdfStripperArea.getTextForRegion("region1");
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextByArea(String filePath,int pageNo,int width,int height){
        String text = "";
        try{
            File file = new File(filePath);
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripperByArea pdfStripperArea = new PDFTextStripperByArea();
            pdfStripperArea.setSortByPosition(true);
            Rectangle rect = new Rectangle(width,height);
            pdfStripperArea.addRegion("region1",rect);
            PDPage docPage = pdDoc.getPage(pageNo);
            pdfStripperArea.extractRegions(docPage);
            text = pdfStripperArea.getTextForRegion("region1");
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextFromDoc(String filePath){
        String text = "";
        try {
            File file = new File(filePath);
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(pdDoc);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextFromDocByPageNo(String filePath,int startPageNo,int endPageNo){
        String text = "";
        try {
            File file = new File(filePath);
            PDDocument pdDoc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPageNo);
            pdfStripper.setEndPage(endPageNo);
            text = pdfStripper.getText(pdDoc);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String getTextFromDocByPageNo(String filePath,int startPageNo){
        String text = "";
        try {
            File file = new File(filePath);
            PDDocument pdDoc = PDDocument.load(file);
            pdDoc.close();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPageNo);
            text = pdfStripper.getText(pdDoc);
            pdDoc.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public BufferedImage convertPDFToImageByPageNo(String filePath,int DPI,int pageNo){
        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
                BufferedImage bim = pdfRenderer.renderImageWithDPI(pageNo, DPI, ImageType.RGB);
            document.close();
            return bim;
        }catch (IOException io){
            io.printStackTrace();
        }
        return null;
    }

    public BufferedImage convertPDFToImageByPageNo(URL dataFileURL,int DPI,int pageNo){
        try {
            PDDocument document = PDDocument.load(new File(dataFileURL.getFile()));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(pageNo, DPI, ImageType.RGB);
            document.close();
            return bim;
        }catch (IOException io){
            io.printStackTrace();
        }
        return null;
    }
}
