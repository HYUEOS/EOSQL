package com.eos.eosql.for_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.eos.eosql.data.ContactData;

import java.util.ArrayList;

/**
 * Created by jyoung on 16. 8. 14..
 *
 * DB를 실질적으로 관리하는 클래스.
 * 실제 DB와의 connecting logic 이 모두 들어있습니다.
 */
public class DBManager {
    private Context context;

    private MySQLOpenHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;

        /** DB 관련 변수 셋팅.
         * 호출하는 곳에 따라 parameters 를 다르게 줘야 한다면
         * 해당 변수들을 이 클래스가 아닌 호출하는 위치에서 셋팅한 후,
         * 이 클래스의 멤버변수에 대입하는 방식으로 구현해야 합니다. **/
        helper = new MySQLOpenHelper(context, "eosql.db", null, 1);
        db = helper.getWritableDatabase(); // 읽고 쓸 수 있는 Database 셋팅
    }

    /**
     * 조건에 맞는 data list 를 db 에서 select 하여 반환합니다.
     *
     * @param orderBy 정렬 기준이 될 column
     * @return 선택된 list
     */
    public ArrayList<ContactData> select(String orderBy) {
        ArrayList<ContactData> contactList = new ArrayList<>();

        Cursor c = db.query("contact", null, null, null, null, null, orderBy); // "contact" table 에서 orderBy column 기준으로 정렬한 모든 값을 받아옵니다.

        while (c.moveToNext()) { // 다음 값이 있는 동안
            // 값을 받아와서 반환할 list 에 추가해줍니다.
            contactList.add(new ContactData(c.getInt(c.getColumnIndex("cid")), c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("nickname")), c.getString(c.getColumnIndex("phonenum")), c.getInt(c.getColumnIndex("age"))));
        }

        return contactList;
    }

    /**
     * Table 에 새 data 를 입력합니다.
     *
     * @param contact 입력할 data
     */
    public void insert(ContactData contact) {
        ContentValues values = new ContentValues();

        /** 각 column 의 값 셋팅 **/
        values.put("name", contact.getName());
        values.put("age", contact.getAge());
        values.put("phonenum", contact.getPhonenum());
        values.put("nickname", contact.getNickname());

        // 셋팅 된 값을 contact table 에 입력합니다.
        db.insert("contact", null, values);

        Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Table 의 특정 data 를 수정합니다.
     *
     * @param contact 수정할 data
     */
    public void update(ContactData contact) {
        ContentValues values = new ContentValues();

        /** 각 column 의 값 셋팅 **/
        values.put("name", contact.getName());
        values.put("age", contact.getAge());
        values.put("phonenum", contact.getPhonenum());
        values.put("nickname", contact.getNickname());

        // contact table 의 data 중 cid 값이 입력받은 contact data 의 id 값과 일치하는 data 의 값을 수정합니다.
        db.update("contact", values, "cid=?", new String[]{contact.getId() + ""});

        Toast.makeText(context, "수정되었습니다.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Table 의 특정 data 를 삭제합니다.
     *
     * @param id 수정할 data 의 id
     */
    public void delete(int id) {
        // contact table 의 data 중 cid 값이 입력받은 id 값과 일치하는 data 를 삭제합니다.
        db.delete("contact", "cid=?", new String[]{id + ""});

        Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }
}
