package com.example.studentscoremanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.studentscoremanagement.Model.TaiKhoan;

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME="dbStudentScoreMn.db";
    private static int DB_VERSION=1;

    //bang Tai Khoan
    public static final String TB_TAIKHOAN="tbTaiKhoan";
    public static final String COL_TAIKHOAN_TEN="taiKhoan_ten";
    public static final String COL_TAIKHOAN_MATKHAU="taiKhoan_matKhau";
    public static final String COL_TAIKHOAN_SDT="taiKhoan_sdt";
    public static final String COL_TAIKHOAN_ANH="taiKhoan_anh";

    //bang Lop
    public static final String TB_LOP="tbLop";
    public static final String COL_LOP_MALOP="lop_maLop";
    public static final String COL_LOP_CHUNHIEM="lop_chuNhiem";

    //bang Hoc Sinh
    public static final String TB_HOCSINH="tbHocSinh";
    public static final String COL_HOCSINH_MAHOCSINH="hocSinh_maHocSinh";
    public static final String COL_HOCSINH_HO="hocSinh_ho";
    public static final String COL_HOCSINH_TEN="hocSinh_ten";
    public static final String COL_HOCSINH_PHAI="hocSinh_phai";
    public static final String COL_HOCSINH_NGAYSINH="hocSinh_ngaySinh";
    public static final String COL_HOCSINH_MALOP="hocSinh_maLop";

    //bang Mon Hoc
    public static final String TB_MONHOC="tbMonHoc";
    public static final String COL_MONHOC_MAMONHOC="monHoc_maMonHoc";
    public static final String COL_MONHOC_TENMONHOC="monHoc_tenMonHoc";
    public static final String COL_MONHOC_HESO="monHoc_heSo";

    //bang Diem
    public static final String TB_DIEM="tbDiem";
    public static final String COL_DIEM_MAHOCSINH="diem_maHocSinh";
    public static final String COL_DIEM_MAMONHOC="diem_maMonHoc";
    public static final String COL_DIEM_DIEM="diem_diem";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String scripTBTaiKhoan="CREATE TABLE "+ TB_TAIKHOAN+"("+
                COL_TAIKHOAN_TEN+" TEXT PRIMARY KEY,"+
                COL_TAIKHOAN_MATKHAU+" TEXT NOT NULL," +
                COL_TAIKHOAN_SDT+" TEXT NOT NULL," +
                COL_TAIKHOAN_ANH+" BLOB)";

        String scripTBLop="CREATE TABLE "+ TB_LOP+"("+
                COL_LOP_MALOP+" TEXT PRIMARY KEY,"+
                COL_LOP_CHUNHIEM+" TEXT NOT NULL)";

        String scripTBHocSinh="CREATE TABLE "+ TB_HOCSINH+"("+
                COL_HOCSINH_MAHOCSINH+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                COL_HOCSINH_HO+" TEXT NOT NULL,"+
                COL_HOCSINH_TEN+" TEXT NOT NULL,"+
                COL_HOCSINH_PHAI+" TEXT NOT NULL,"+
                COL_HOCSINH_NGAYSINH+" TEXT,"+
                COL_HOCSINH_MALOP+" TEXT,"+
                "FOREIGN KEY ("+COL_HOCSINH_MALOP+") REFERENCES "+TB_LOP+"("+COL_LOP_MALOP+"))";

        String scripTBMonHoc="CREATE TABLE "+ TB_MONHOC+"("+
                COL_MONHOC_MAMONHOC+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                COL_MONHOC_TENMONHOC+" TEXT NOT NULL,"+
                COL_MONHOC_HESO+" INTEGER)";

        String scripTBDiem="CREATE TABLE "+ TB_DIEM+"("+
                COL_DIEM_MAHOCSINH+" TEXT NOT NULL,"+
                COL_DIEM_MAMONHOC+" TEXT NOT NULL,"+
                COL_DIEM_DIEM+ " REAL,"+
                "PRIMARY KEY ("+COL_DIEM_MAHOCSINH+", "+COL_DIEM_MAMONHOC+"))";

        //Execute Create query
        db.execSQL(scripTBTaiKhoan);
        db.execSQL(scripTBLop);
        db.execSQL(scripTBHocSinh);
        db.execSQL(scripTBMonHoc);
        db.execSQL(scripTBDiem);

        //Execute Create Data
        addDataDefault(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TB_TAIKHOAN);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_LOP);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_HOCSINH);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_MONHOC);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_DIEM);

        onCreate(db);
    }

    //Truy v???n kh??ng tr??? v??? kq (update)
    public void QueryData(String sql){
        SQLiteDatabase database =getWritableDatabase();
        database.execSQL(sql);
        database.close();
    }

    //Truy v???n tr??? v??? k???t qu???
    public Cursor GetData(String sql){
        SQLiteDatabase database =getReadableDatabase();
        return database.rawQuery(sql, null);

    }




    public void themTK(TaiKhoan taiKhoan) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TAIKHOAN_TEN, taiKhoan.getTenTaiKhoan());
        values.put(COL_TAIKHOAN_MATKHAU, taiKhoan.getMatKhau());
        values.put(COL_TAIKHOAN_SDT, taiKhoan.getSdt());
        db.insert(TB_TAIKHOAN, null, values);
        db.close();
    }


    private void addDataDefault(SQLiteDatabase database){
        database.execSQL("INSERT INTO "+TB_TAIKHOAN+" VALUES ('truonglv','123456','0329385635',null)");
        database.execSQL("INSERT INTO "+TB_TAIKHOAN+" VALUES ('nghia','123456','0347360427',null)");
        database.execSQL("INSERT INTO "+TB_TAIKHOAN+" VALUES ('nam','123456','0334704530',null)");
        database.execSQL("INSERT INTO "+TB_TAIKHOAN+" VALUES ('vu','123456','0327551251',null)");
        database.execSQL("INSERT INTO "+TB_TAIKHOAN+" VALUES ('sang','123456','0933545121',null)");

        database.execSQL("INSERT INTO "+TB_LOP+" VALUES ('12A1','L?? V??nh Tr?????ng')");
        database.execSQL("INSERT INTO "+TB_LOP+" VALUES ('12A2','Nguy???n H???i Nam')");
        database.execSQL("INSERT INTO "+TB_LOP+" VALUES ('12A3','Nguy???n L????ng V??')");
        database.execSQL("INSERT INTO "+TB_LOP+" VALUES ('12A4','Hu???nh Ph?????c Sang')");
        database.execSQL("INSERT INTO "+TB_LOP+" VALUES ('12A5','B??i Tr???ng Ngh??a')");
        database.execSQL("INSERT INTO "+TB_LOP+" VALUES ('12A6','Nguy???n V??n Tu???n')");

        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (1,'Nguy???n V??n','T??o','Nam','20/20/2005','12A1')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (2,'Nguy???n V??n','Sang','Nam','20/20/2005','12A1')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (3,'Nguy???n Th???','Th???m','N???','20/20/2005','12A1')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (4,'L??u V??n','Tu???n','Nam','20/20/2005','12A1')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (5,'Nguy???n V??n','Tr?????ng','Nam','20/20/2005','12A1')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (6,'Ph???m V??n','T??','Nam','20/20/2005','12A1')");

        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (7,'Nguy???n V??n','T??o','Nam','20/20/2005','12A2')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (8,'Tr???n Qu???c','Tr?????ng','Nam','20/20/2005','12A2')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (9,'Nguy???n Th???','Th???m','N???','20/20/2005','12A2')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (10,'L??u V??n','Tu???n','Nam','20/20/2005','12A2')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (11,'Nguy???n V??n','Tr?????ng','Nam','20/20/2005','12A2')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (12,'Ph???m V??n','T??','Nam','20/20/2005','12A2')");

        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (13,'Nguy???n V??n','T??o','Nam','20/20/2005','12A3')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (14,'Nguy???n V??n','V??','Nam','20/20/2005','12A3')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (15,'Nguy???n Th???','Th???m','N???','20/20/2005','12A3')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (16,'L??u V??n','Tu???n','Nam','20/20/2005','12A3')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (17,'Nguy???n V??nh','Tr?????ng','Nam','20/20/2005','12A3')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (18,'Ph???m V??n','T??','Nam','20/20/2005','12A3')");

        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (19,'Nguy???n V??n','T??o','Nam','20/20/2005','12A4')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (20,'Nguy???n V??n','T??','Nam','20/20/2005','12A4')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (21,'Nguy???n Th???','Th???m','N???','20/20/2005','12A4')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (22,'L??u V??n','Tu???n','Nam','20/20/2005','12A4')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (23,'Nguy???n V??n','T??o','Nam','20/20/2005','12A4')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (24,'Ph???m V??n','T??','Nam','20/20/2005','12A4')");

        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (25,'Nguy???n V??n','T??o','Nam','20/20/2005','12A5')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (26,'Nguy???n V??n','T??o','Nam','20/20/2005','12A5')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (27,'Nguy???n Th???','Vi','N???','20/20/2005','12A5')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (28,'L??u V??n','Tu???n','Nam','20/20/2005','12A5')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (29,'Nguy???n V??n','T?????ng','Nam','20/20/2005','12A5')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (30,'Ph???m V??n','T??','Nam','20/20/2005','12A5')");

        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (31,'Nguy???n V??n','T??o','Nam','20/20/2005','12A6')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (32,'Nguy???n V??n','T??','Nam','20/20/2005','12A6')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (33,'Nguy???n Th???','Lan','N???','20/20/2005','12A6')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (34,'L??u V??n','Tu???n','Nam','20/20/2005','12A6')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (35,'Nguy???n V??n','Tr?????ng','Nam','20/20/2005','12A6')");
        database.execSQL("INSERT INTO "+TB_HOCSINH+" VALUES (36,'Ph???m V??n','T??','Nam','20/20/2005','12A6')");

        database.execSQL("INSERT INTO "+TB_MONHOC+" VALUES (1,'To??n','2')");
        database.execSQL("INSERT INTO "+TB_MONHOC+" VALUES (2,'V???t l??','2')");
        database.execSQL("INSERT INTO "+TB_MONHOC+" VALUES (3,'H??a','2')");
        database.execSQL("INSERT INTO "+TB_MONHOC+" VALUES (4,'V??n','2')");
        database.execSQL("INSERT INTO "+TB_MONHOC+" VALUES (5,'S???','1')");
        database.execSQL("INSERT INTO "+TB_MONHOC+" VALUES (6,'?????a','1')");
        database.execSQL("INSERT INTO "+TB_MONHOC+" VALUES (7,'GDQP','1')");

        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (1,1,7.5)");
        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (8,1,7.5)");
        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (16,1,7.5)");
        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (24,1,7.5)");
        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (32,1,7.5)");
        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (2,1,7.5)");
        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (9,1,7.5)");
        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (17,1,7.5)");
        database.execSQL("INSERT INTO "+TB_DIEM+" VALUES (33,1,7.5)");
    }
}
