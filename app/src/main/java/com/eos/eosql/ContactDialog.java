package com.eos.eosql;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eos.eosql.data.ContactData;
import com.eos.eosql.for_sql.DBManager;

public class ContactDialog extends Dialog implements View.OnClickListener {
    private DBManager dbManager;

    private EditText name, age, nickname, phoneNum;
    private Button saveBtn;

    private ContactData contact;
    private boolean isUpdate = false;

    /**
     * Constructor.
     *
     * @param context
     * @param contact 수정의 목적으로 dialog 가 호출된 경우 수정할 data 의 값이 들어오지만, 새로운 값을 생성하기 위해 dialog 가 호출된 경우 null 값이 들어온다.
     */
    public ContactDialog(Context context, ContactData contact) {
        super(context);
        this.contact = contact;
        setContentView(R.layout.contact_dialog);

        /** initialize variables related to db manager **/
        dbManager = new DBManager(context);

        /** initialize vies **/
        name = (EditText)findViewById(R.id.input_name);
        age = (EditText)findViewById(R.id.input_age);
        nickname = (EditText)findViewById(R.id.input_nickname);
        phoneNum = (EditText)findViewById(R.id.input_phone);

        saveBtn = (Button)findViewById(R.id.input_save_btn);

        if (contact != null) { // if this dialog is called for editing
            isUpdate = true;

            /** set origin data **/
            setValue(name, contact.getName());
            setValue(age, contact.getAge() + "");
            setValue(nickname, contact.getNickname());
            setValue(phoneNum, contact.getPhonenum());
        } else { // if this dialog is called for creating
            this.contact = new ContactData();
        }

        saveBtn.setOnClickListener(this);
    }

    private void setValue(EditText editText, String value) {
        editText.setText(value);
        editText.setHint(value);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input_save_btn:
                if (name.getText().length() == 0 || age.getText().length() == 0 || nickname.getText().length() == 0 || phoneNum.getText().length() == 0) {
                    Toast.makeText(getContext(), "모든 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                /** set input data **/
                contact.setName(name.getText().toString());
                contact.setAge(Integer.parseInt(age.getText().toString()));
                contact.setNickname(nickname.getText().toString());
                contact.setPhonenum(phoneNum.getText().toString());

                if (isUpdate) {
                    dbManager.update(contact);
                } else {
                    dbManager.insert(contact);
                }

                dismiss();

                break;
        }
    }
}
