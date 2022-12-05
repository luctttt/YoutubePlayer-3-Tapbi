package vn.tapbi.youtubeplayer3.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.common.ModelHomeSetting;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

    private List<ModelHomeSetting> listFunctionModelHomeSettings;
    private OnClickItemSetting interfaceSetting;

    public SettingAdapter(List<ModelHomeSetting> listFunctionModelHomeSettings, OnClickItemSetting interfaceSetting) {
        this.listFunctionModelHomeSettings = listFunctionModelHomeSettings;
        this.interfaceSetting = interfaceSetting;
    }

    @NonNull
    @Override
    public SettingAdapter.SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_fragment, parent, false);

        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.SettingViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> interfaceSetting.OnClickFunctionSetting(position,v));

        holder.txt_nameSetting.setText(listFunctionModelHomeSettings.get(position).getText());
        holder.img_iconSetting.setImageResource(listFunctionModelHomeSettings.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        if (listFunctionModelHomeSettings == null)
            return 0;
        return listFunctionModelHomeSettings.size();
    }

    public static class SettingViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nameSetting;
        ImageView img_iconSetting;

        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameSetting = itemView.findViewById(R.id.txt_title);
            img_iconSetting = itemView.findViewById(R.id.img_icon);
        }
    }

    public interface OnClickItemSetting {
        void OnClickFunctionSetting(int position , View view);
    }
}
