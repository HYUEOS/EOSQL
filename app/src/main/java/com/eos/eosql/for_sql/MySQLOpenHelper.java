package com.eos.eosql.for_sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jyoung on 16. 8. 14..
 * 이 클래스는 그냥 한글로 쓸래.. 쓸게 많아...... 허무하게 끝난 짧은 일탈 ㅎㅎ
 *
 * 이름처럼 SQLite DB 사용을 도와주는 클래스입니다.
 * DB를 생성 or 업그레이드 하는 역할과 함께, DB 생성시에 실행될 쿼리 등을 설정할 수 있다.
 * (이상이 주로 쓰는 기능들이고, 아래의 메서드들을 잘 활용하면 더 심화된 방식으로도 사용할 수 있어요!)
 */
public class MySQLOpenHelper extends SQLiteOpenHelper {

    /**
     * Constructor
     *
     * @param context
     * @param name db file name
     * @param factory set null to use default cursor
     * @param version version number
     */
    public MySQLOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * this method is called When DB is created.
     * this is suitable to initialize db (create table, insert default values ...)
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table contact (" + // 'contact' 란 이름의 table을 생성합니다. 이 table 은
                "cid integer primary key autoincrement, " + // primary key (일단 db 에서의 식별자라고 생각해주세요) 역할을 할 cid 라는 integer 형 column (autoincrement : 값을 직접 입력하지 않아도 자동으로 1씩 증가된 값이 저장되도록 하는 옵션)
                "name text, " + // name 이란 이름의 text 형 column
                "age integer, " + // age 란 이름의 integer 형 column
                "phonenum text, " + // phonenum 이란 이름의 text 형 column
                "nickname text);"; // nickname 이란 이름의 text 형 column 을 가지고 있습니다.

        db.execSQL(query); // 작성한 쿼리문 실행
    }

    /**
     * 현재 사용자의 device 에 해당 db 가 이미 있는데 version 이 다를 경우 실행됩니다.
     * DB schema 에 변경 사항이 있을 경우, 해당 변경 사항을 적용하기 위해 많이 이용됩니다.
     * 이 앱은 예제이기 때문에 단순하게 Table 전체를 Drop(제거) 하고 onCreate 를 다시 실행하는 코드를 넣어두었습니다.
     *
     * @param db
     * @param oldVersion 현재 사용자의 device 에 저장된 db version number
     * @param newVersion 위 constructor 에서 입력받은 version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists contact";

        db.execSQL(query);

        onCreate(db);
    }
}
