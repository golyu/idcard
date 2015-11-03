package com.lv.tools;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;


/**
 * @author lvzhongyi
 * @description
 * @date 2015/10/23 0023
 * @email 1179524193@qq.cn
 */
public class IDCard extends DataSupport {
    private String cardId; //卡id
    private String name;//姓名
    private String sex; //性别
    private String nation;//民族
    private String birthday; // 生日
    private String address;//家庭住址
    private String cardNo; // 身份证号码
    private String issuingAuthority;//签发机关
    private String beginDate;//开始时间
    private String endDate; //结束时间
    private byte[] wlt; //照片byte码
    protected Bitmap photo; // bitmap格式照片
    private String photoBase64; //base64格式的bitmap



    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public byte[] getWlt() {
        return wlt;
    }

    public void setWlt(byte[] wlt) {
        this.wlt = wlt;
    }



    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public String getNationName(String nation) {
        int nationcode = Integer.parseInt(nation);
        if (nationcode >= 1 && nationcode <= 56) {
            this.nation = nations[nationcode];
        } else if (nationcode == 97) {
            this.nation = "其他";
        } else if (nationcode == 98) {
            this.nation = "外国血统";
        } else {
            this.nation = "编码错";
        }

        return this.nation;
    }

    final protected String[] nations = {
            "解码错",            // 00
            "汉",            // 01
            "蒙古",            // 02
            "回",            // 03
            "藏",            // 04
            "维吾尔",            // 05
            "苗",            // 06
            "彝",            // 07
            "壮",            // 08
            "布依",            // 09
            "朝鲜",            // 10
            "满",            // 11
            "侗",            // 12
            "瑶",            // 13
            "白",            // 14
            "土家",            // 15
            "哈尼",            // 16
            "哈萨克",            // 17
            "傣",            // 18
            "黎",            // 19
            "傈僳",            // 20
            "佤",            // 21
            "畲",            // 22
            "高山",            // 23
            "拉祜",            // 24
            "水",            // 25
            "东乡",            // 26
            "纳西",            // 27
            "景颇",            // 28
            "柯尔克孜",        // 29
            "土",            // 30
            "达斡尔",            // 31
            "仫佬",            // 32
            "羌",            // 33
            "布朗",            // 34
            "撒拉",            // 35
            "毛南",            // 36
            "仡佬",            // 37
            "锡伯",            // 38
            "阿昌",            // 39
            "普米",            // 40
            "塔吉克",            // 41
            "怒",            // 42
            "乌孜别克",        // 43
            "俄罗斯",            // 44
            "鄂温克",            // 45
            "德昴",            // 46
            "保安",            // 47
            "裕固",            // 48
            "京",            // 49
            "塔塔尔",            // 50
            "独龙",            // 51
            "鄂伦春",            // 52
            "赫哲",            // 53
            "门巴",            // 54
            "珞巴",            // 55
            "基诺",            // 56
            "编码错",            // 57
            "其他",            // 97
            "外国血统"            // 98
    };
}
