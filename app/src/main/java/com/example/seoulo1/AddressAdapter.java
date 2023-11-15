package com.example.seoulo1;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private ArrayList<String> addressList;

    private AppDatabase database;

    public AddressAdapter(AppDatabase database, ArrayList<String> addressList) {
        this.database = database; // AppDatabase 객체 초기화
        this.addressList = addressList;
    }


    // 뷰 홀더 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    // 뷰 홀더에 데이터 바인딩
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = addressList.get(position);
        holder.txtAddress.setText(data);

        // 삭제 버튼 클릭 이벤트 처리
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    addressList.remove(adapterPosition); // 목록에서 해당 위치의 항목 삭제
                    notifyItemRemoved(adapterPosition);  // RecyclerView에 항목 삭제 알림
                    notifyItemRangeChanged(adapterPosition, addressList.size());  // 삭제 이후 항목 위치 변경 알림

                    // 데이터베이스에서도 해당 주소 삭제
                    deleteAddressFromDatabase(data);
                }
            }
        });
    }

    // 데이터베이스에서 주소를 삭제하는 메서드
    private void deleteAddressFromDatabase(String address) {
        AsyncTask.execute(() -> {
            try {
                // 이 부분에 데이터베이스에서 주소를 삭제하는 코드를 작성
                database.placeDao().deleteAddress(address);

                // 성공적으로 삭제되었음을 로그에 출력
                Log.d("Database", "Address deleted: " + address);
            } catch (Exception e) {
                // 삭제 중 오류가 발생했음을 로그에 출력
                Log.e("Database", "Error deleting address: " + address, e);
            }
        });
    }

    // 아이템 개수 반환
    @Override
    public int getItemCount() {
        return addressList.size();
    }


    // 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAddress;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddress = itemView.findViewById(R.id.txt_address);
            btnDelete = itemView.findViewById(R.id.btn_delete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        addressList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, addressList.size());
                    }
                }
            });
        }
    }
}