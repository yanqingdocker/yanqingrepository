package cn.com.caogen.service;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import cn.com.caogen.util.DateUtil;
import com.lowagie.text.DocumentException;
//import com.nudms.server.nurse.servlet.CompressDataServlet;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import java.awt.*;

/**
 * 打印表单
 * @author
 *
 */
public class PrintServiceImp {

    public void printmenu(String filepath, String printName) throws  IOException,DocumentException, PrinterException{
        int j=0;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if(services.length == 0){
            System.out.println("not found printer");
        }
        PrinterJob job = PrinterJob.getPrinterJob();
        for(PrintService ps: services){
            if(ps.getName().equals(printName)){
                job.setPrintService(services[j]);
            }else {
                j++;
            }
        }

        FileInputStream fis = new FileInputStream(filepath);
        byte[] pdfContent = new byte[fis.available()];
        fis.read(pdfContent, 0, fis.available());
        ByteBuffer buf = ByteBuffer.wrap(pdfContent);
        PDFFile pdfFile = new PDFFile(buf);
        Image image;

        image=Toolkit.getDefaultToolkit().getImage("2.png");
        image=Toolkit.getDefaultToolkit().getImage("D:\\2.png");


        Book bk = new Book();

        int num = pdfFile.getNumPages();
        for(int i=0; i<num; i++){
            PDFPage page = pdfFile.getPage(i+1);
            PageFormat pf = job.defaultPage();
            bk.append(new Printable() {
                          @Override
                          public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                              if(pageIndex>0){
                                  return NO_SUCH_PAGE;
                              }
                              int yIndex = 30;
                              Image image;

                              image=Toolkit.getDefaultToolkit().getImage("D:\\2.png");
                              Graphics2D graphics2D = (Graphics2D) graphics;
                              graphics2D.setStroke(new BasicStroke(0.5f));
                              Font font = new Font("宋体", Font.CENTER_BASELINE, 10);
                              graphics2D.setFont(font);
                              Color defaultColor = graphics2D.getColor();
                              graphics2D.setFont(new Font("宋体",Font.CENTER_BASELINE, 2));
                              graphics2D.setColor(defaultColor);
                              graphics2D.drawImage(image, -20, 50, 620,664,null);
                              yIndex = drawString(graphics2D, ".", 260, 50, 250, 30);
                              graphics2D.setFont(new Font("宋体",Font.CENTER_BASELINE, 10));
                              yIndex = drawString(graphics2D, DateUtil.getDate().split("-")[0], 112, 135, 250, 30);
                              yIndex = drawString(graphics2D, DateUtil.getDate().split("-")[1], 162, 135, 250, 30);
                              yIndex = drawString(graphics2D, DateUtil.getDate().split("-")[2], 202, 135, 250, 30);
                              yIndex = drawString(graphics2D, "111111", 355, 135, 250, 30);
                              graphics2D.setFont(new Font("宋体",Font.CENTER_BASELINE, 12));
                              yIndex = drawString(graphics2D, "我取款", 161, 158, 250, 30);
                              yIndex = drawString(graphics2D, "南方国际钱庄", 404, 158, 250, 30);
                              yIndex = drawString(graphics2D, "网上交易", 161, 185, 250, 30);
                              yIndex = drawString(graphics2D, "壹仟壹佰壹元壹分壹角", 161, 253, 250, 30);
                              yIndex = drawString(graphics2D, "101010101010101", 161, 280, 250, 30);
                              graphics2D.setFont(new Font("宋体",Font.CENTER_BASELINE, 14));
                              yIndex = drawString(graphics2D, "1", 312, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 335, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 358, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 381, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 404, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 427, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 450, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 473, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 496, 240, 250, 30);
                              yIndex = drawString(graphics2D, "1", 519, 240, 250, 30);


                              Stroke stroke = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0,new float[]{4, 4},0);
                              graphics2D.setStroke(stroke);
                              graphics2D.drawRect(1, 411, 841, 1);


                              return PAGE_EXISTS;
                          }
                      }
                    , pf);

            Paper paper = pf.getPaper();
            double x = 0;
            double y = 0;

            if(page.getAspectRatio()<1){
                double width = page.getBBox().getWidth();
                double height = page.getBBox().getHeight();

                paper.setImageableArea(x, y, width, height);

                pf.setOrientation(PageFormat.PORTRAIT);
            }else{

                double width = page.getBBox().getHeight();
                double height = page.getBBox().getWidth();

                paper.setImageableArea(x, y, width, height);

                pf.setOrientation(PageFormat.LANDSCAPE);
            }
            pf.setPaper(paper);
            System.out.println();
        }
        job.setPageable(bk);
        job.setJobName("My book");
        try {
            job.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int drawString(Graphics2D graphics2D, String text, int x, int y, int lineWidth, int lineHeight){
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        if(fontMetrics.stringWidth(text)<lineWidth){
            graphics2D.drawString(text, x, y);
            return y;
        } else{
            char[] chars = text.toCharArray();
            int charsWidth = 0;
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<chars.length; i++){
                if((charsWidth + fontMetrics.charWidth(chars[i]))>lineWidth){
                    graphics2D.drawString(sb.toString(), x, y);
                    sb.setLength(0);
                    y = y + lineHeight;
                    charsWidth = fontMetrics.charWidth(chars[i]);
                    sb.append(chars[i]);
                } else{
                    charsWidth = charsWidth + fontMetrics.charWidth(chars[i]);
                    sb.append(chars[i]);
                }
            }
            if(sb.length()>0){
                graphics2D.drawString(sb.toString(), x, y);
                y = y + lineHeight;
            }
            return y - lineHeight;
        }
    }
    public static void main(String  args[]) throws IOException, DocumentException, PrinterException
    {
        PrintServiceImp pic = new PrintServiceImp();
        pic.printmenu("D:\\Java Printing.pdf", "Java Printing.pdf");

    }


}

