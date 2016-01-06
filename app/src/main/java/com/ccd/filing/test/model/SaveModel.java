package com.ccd.filing.test.model;

import android.os.Environment;

import com.lv.tools.IDCard;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/1/5
 *         email 1179524193@qq.com
 *         </p>
 */
public class SaveModel {
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();

    public void saveIDCard(IDCard idCard) {
        /**
         * 创建文件
         */
        String filePath = SD_PATH + "/idcard.xls";
        try {
            createFile(filePath);

            WritableWorkbook book = Workbook.createWorkbook(new File(filePath));
            /** 创建工作单元 */
            WritableSheet sheet = book.createSheet(idCard.getCardNo(), 0);
            int i = 0;
            Label[][] labels = new Label[10][2];
            labels[0][0] = new Label(0, i++, "卡id");
            labels[1][0] = new Label(0, i++, "姓名");
            labels[2][0] = new Label(0, i++, "性别");
            labels[3][0] = new Label(0, i++, "民族");
            labels[4][0] = new Label(0, i++, "生日");
            labels[5][0] = new Label(0, i++, "家庭住址");
            labels[6][0] = new Label(0, i++, "身份证号码");
            labels[7][0] = new Label(0, i++, "签发机关");
            labels[8][0] = new Label(0, i++, "开始时间");
            labels[9][0] = new Label(0, i++, "结束时间");
            int j=0;
            labels[0][1] = new Label(1, j++, idCard.getCardId());
            labels[1][1] = new Label(1, j++, idCard.getName());
            labels[2][1] = new Label(1, j++, idCard.getSex());
            labels[3][1] = new Label(1, j++, idCard.getNation());
            labels[4][1] = new Label(1, j++, idCard.getBirthday());
            labels[5][1] = new Label(1, j++, idCard.getAddress());
            labels[6][1] = new Label(1, j++, idCard.getCardNo());
            labels[7][1] = new Label(1, j++, idCard.getIssuingAuthority());
            labels[8][1] = new Label(1, j++, idCard.getBeginDate());
            labels[9][1] = new Label(1, j++, idCard.getEndDate());


            for (Label[] label : labels) {
                sheet.addCell(label[0]);
                sheet.addCell(label[1]);
            }
            book.write();
            book.close();

        } catch (IOException e) {
            return;
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建文件
     */
    private void createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.isFile()) {

        } else {
            file.createNewFile();
        }
    }

//    private int id;
//    private String cardId; //卡id
//    private String name;//姓名
//    private String sex; //性别
//    private String nation;//民族
//    private String birthday; // 生日
//    private String address;//家庭住址
//    private String cardNo; // 身份证号码
//    private String issuingAuthority;//签发机关
//    private String beginDate;//开始时间
//    private String endDate; //结束时间
//    private byte[] wlt; //照片byte码
//    protected Bitmap photo; // bitmap格式照片
//    private String photoBase64; //base64格式的bitmap
}
