package com.lv.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.RemoteException;
import android.util.Log;

import com.synjones.bluetooth.DecodeWlt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lvzhongyi
 *         description 用来解析整个身份证信息的类
 *         date 2015/10/23 0023
 *         email 1179524193@qq.cn
 */
public class ParseCardInfo {
    /**
     * 解析身份证信息
     *
     * @param readResult 从读卡器中读取到的原始byte数据
     * @return {@link IDCard}返回一个对象
     * @throws Exception 解析错误的异常
     */
    public static IDCard parse(byte[] readResult) throws Exception {
        long startTime = System.currentTimeMillis();
        IDCard idCard = new IDCard();
        short textlen = (short) (readResult[0] * 256 + readResult[1]);
        short wltlen = (short) (readResult[2] * 256 + readResult[3]);

        byte[] name = new byte[30];
        System.arraycopy(readResult, 4, name, 0, name.length);
        idCard.setName(new String(name, "UTF-16LE").trim());
        byte[] sex = new byte[2];
        System.arraycopy(readResult, 34, sex, 0, sex.length);
        idCard.setSex(new String(sex, "UTF-16LE"));
        if (idCard.getSex().equalsIgnoreCase("1"))
            idCard.setSex("男");
        else
            idCard.setSex("女");
        byte[] nation = new byte[4];
        System.arraycopy(readResult, 36, nation, 0, nation.length);
        idCard.setNation(idCard.getNationName(new String(nation, "UTF-16LE")));
        byte[] birthday = new byte[16];
        System.arraycopy(readResult, 40, birthday, 0, birthday.length);
        idCard.setBirthday(addLine(new String(birthday, "UTF-16LE")));
        byte[] address = new byte[70];
        System.arraycopy(readResult, 56, address, 0, address.length);
        idCard.setAddress(new String(address, "UTF-16LE").trim());
        byte[] cardNo = new byte[36];
        System.arraycopy(readResult, 126, cardNo, 0, cardNo.length);
        idCard.setCardNo(new String(cardNo, "UTF-16LE"));
        byte[] issuingAuthority = new byte[30];
        System.arraycopy(readResult, 162, issuingAuthority, 0, issuingAuthority.length);
        idCard.setIssuingAuthority(new String(issuingAuthority, "UTF-16LE").trim());
        byte[] beginDate = new byte[16];
        System.arraycopy(readResult, 192, beginDate, 0, beginDate.length);
        idCard.setBeginDate(addLine(new String(beginDate, "UTF-16LE")));
        byte[] endDate = new byte[16];
        System.arraycopy(readResult, 208, endDate, 0, endDate.length);
        idCard.setEndDate(addLine(new String(endDate, "UTF-16LE").trim()));
        byte[] cc = new byte[36];
        System.arraycopy(readResult, 224, cc, 0, cc.length);
        String tt = new String(cc, "UTF-16LE").trim();
        byte[] wlt = new byte[1024];
        System.arraycopy(readResult, textlen + 4, wlt, 0, wlt.length);
        byte[] bmpBytes = decode(wlt);
        if (bmpBytes != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bmpBytes, 0, bmpBytes.length);
            idCard.setPhoto(bmp);
            idCard.setWlt(bmpBytes);
            idCard.setPhotoBase64(BitmapBase64.bitmapToBase64(bmp));
        }
        Log.v("card_time",System.currentTimeMillis()-startTime+"");
        return idCard;
    }

    /**
     * 将加密的照片byte数据通过jni解析
     *
     * @param wlt 解密前
     * @return 解密后
     * @throws RemoteException 解密错误
     */
    public static byte[] decode(byte[] wlt) throws RemoteException {
//        String bmpPath = "/data/data/com.cjy.filing/files/photo.bmp";
//        String wltPath = "/data/data/com.cjy.filing/files/photo.wlt";

        String bmpPath = Environment.getExternalStorageDirectory().getPath() + "/photo.bmp";
        String wltPath = Environment.getExternalStorageDirectory().getPath() + "/photo.wlt";

        File wltFile = new File(wltPath);
        File oldBmpPath = new File(bmpPath);
        if (oldBmpPath.exists() && oldBmpPath.isFile()) {
            oldBmpPath.delete();
        }


        try {
            FileOutputStream fos = new FileOutputStream(wltFile);
            fos.write(wlt);
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DecodeWlt dw = new DecodeWlt();

        int result = dw.Wlt2Bmp(wltPath, bmpPath);
        byte[] buffer = null;
        FileInputStream fin;
        try {
            File bmpFile = new File(bmpPath);
            fin = new FileInputStream(bmpFile);
            int length = fin.available();
            buffer = new byte[length];
            fin.read(buffer);
            fin.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 将日期加上斜线
     *
     * @param string 处理前    例如20150103
     * @return 处理后          2015/02/03
     */
    private static String addLine(String string) {
        String targetString = "";
        if (string.length() > 7) {
            targetString += string.substring(0, 4);
            targetString += "/";
            targetString += string.substring(4, 6);
            targetString += "/";
            targetString += string.substring(6);
        }
        return targetString;
    }
}
