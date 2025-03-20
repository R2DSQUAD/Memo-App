package com.example.sqlite_program.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sqlite_program.activity.New_Activity;
import com.example.sqlite_program.db.DBHelper;
import com.example.sqlite_program.MemoItem;
import com.example.sqlite_program.R;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{

    ArrayList<MemoItem> mMemoItems;
    Context mContext;
    DBHelper mDBHelper;


    public CustomAdapter(ArrayList<MemoItem> mMemoItems, Context mContext) {
        this.mMemoItems = mMemoItems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);


    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(mMemoItems.get(position).getTitle());
        holder.tv_content.setText(mMemoItems.get(position).getContent());
        holder.tv_writeDate.setText(mMemoItems.get(position).getWriteDate());
    }

    @Override
    public int getItemCount() {
        return mMemoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_writeDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_writeDate = itemView.findViewById(R.id.tv_date);


            itemView.setOnClickListener(view -> {
                //아이디를 활용해 메모 리턴
                Intent intent = new Intent(mContext, New_Activity.class);
                int curPos = getBindingAdapterPosition(); //id 가져오기
                MemoItem memoItem = mMemoItems.get(curPos);
                int id = memoItem.getId();

                intent.putExtra(DBHelper.NOTE_INTENT, mMemoItems.get(curPos));
                intent.putExtra(DBHelper.IS_NEW_NOTE_INTENT, false);
                intent.putExtra("ID", id);
                mContext.startActivity(intent);
            });

            itemView.setOnLongClickListener(view -> {
                int curPos = getBindingAdapterPosition(); //길게 눌렀을 때 제거 버튼
                MemoItem memoItem = mMemoItems.get(curPos);

                String[] strChoiceItems = {"삭제"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("삭제하시겠습니까?");
                builder.setItems(strChoiceItems, (dialogInterface, position) -> {


                    if (position == 0) {
                        String beforeTime = memoItem.getWriteDate();
                        mDBHelper.deleteMemo(beforeTime);
                        MemoItem remove = mMemoItems.remove(curPos);
                        notifyItemRemoved(curPos);
                        Toast.makeText(mContext, "메모가 제거되었습니다!", Toast.LENGTH_SHORT).show();
                    }

                    else {
                    }
                });
                builder.show();
                return true;
            });
        }
    }



    // 액티비티에서 호출되는 함수이며, 현재 어댑터에 새로운 게시글 아이템을 전달받아 추가하는 목적이다.
    public void addItem(MemoItem _item) {
        mMemoItems.add(0, _item);
        notifyItemInserted(0);

    }



}
