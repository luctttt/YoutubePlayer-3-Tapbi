package vn.tapbi.youtubeplayer3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.tapbi.youtubeplayer3.data.model.comment.ItemComment;
import vn.tapbi.youtubeplayer3.databinding.ItemCommentBinding;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewMainHolder> {

    private List<ItemComment> mSnippets;
    private final Context context;

    public CommentAdapter(List<ItemComment> mSnippets, Context context) {
        this.mSnippets = mSnippets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding itemComment=  ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewMainHolder(itemComment);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMainHolder holder, int position) {
        ItemComment item = mSnippets.get(position);

        Glide.with(context).load(item.getSnippet().getTopLevelComment().getSnippets().getAuthorProfileImageUrl())
                .centerCrop()
                .into(holder.commentBinding.imgAuthorProfileImageUrl);

        holder.commentBinding.txtComment.setText(item.getSnippet().getTopLevelComment().getSnippets().getTextDisplay());
    }

    @Override
    public int getItemCount() {

        if (mSnippets == null)
            return 0;
        return mSnippets.size();
    }

    public static class ViewMainHolder extends RecyclerView.ViewHolder {
        private final ItemCommentBinding commentBinding;

        public ViewMainHolder(ItemCommentBinding commentBinding) {
            super(commentBinding.getRoot());
            this.commentBinding = commentBinding;
        }
    }
}
