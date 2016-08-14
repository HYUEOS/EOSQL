package com.eos.eosql.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eos.eosql.ContactDialog;
import com.eos.eosql.MainActivity;
import com.eos.eosql.R;
import com.eos.eosql.data.ContactData;
import com.eos.eosql.for_sql.DBManager;

import java.util.ArrayList;

/**
 * Created by jyoung on 16. 8. 14..
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<ContactData> dataList = new ArrayList<>();
    private Context context;

    private DBManager dbManager;

    public ContactAdapter(Context context, ArrayList<ContactData> dataList) {
        this.context = context;
        this.dataList = dataList;

        dbManager = new DBManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ContactData contact = dataList.get(position);

        if (contact != null) {
            holder.name.setText(contact.getName() + " (" + contact.getAge() + ")");
            holder.phoneNum.setText(contact.getPhonenum());
            holder.nickname.setText(contact.getNickname());

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(context)
                            .setCancelable(true) // 화면 밖이나 뒤로가기 버튼을 통해 dialog 를 취소할 수 있습니다.
                            .setItems(new String[]{"수정하기", "삭제하기"}, new DialogInterface.OnClickListener() { // dialog 에 선택할 list 를 셋팅합니다.
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) { // 특정 item 이 선택되면
                                    switch (i) { // 선택된 item 의 positin 값 (i) 에 따라 다르게 동작합니다.
                                        case 0: // 수정
                                            ContactDialog dialog = new ContactDialog(context, contact);
                                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(DialogInterface dialogInterface) {
                                                    ((MainActivity)context).dataChanged();
                                                }
                                            });
                                            dialog.show();
                                            break;
                                        case 1: // 삭제
                                            dbManager.delete(contact.getId());
                                            ((MainActivity)context).dataChanged();
                                            break;
                                    }

                                }
                            })
                            .show();
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phoneNum, nickname;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.contact_name);
            phoneNum = (TextView)itemView.findViewById(R.id.contact_phone);
            nickname = (TextView)itemView.findViewById(R.id.contact_nickname);
        }
    }
}
