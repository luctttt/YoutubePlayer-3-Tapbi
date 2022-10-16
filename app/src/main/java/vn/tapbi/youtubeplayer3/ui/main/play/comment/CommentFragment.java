package vn.tapbi.youtubeplayer3.ui.main.play.comment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.model.comment.ItemComment;
import vn.tapbi.youtubeplayer3.databinding.ItemFullCommentBinding;
import vn.tapbi.youtubeplayer3.ui.adapter.CommentAdapter;

public class CommentFragment extends BottomSheetDialogFragment {
    private List<ItemComment> listComments;
    private String commentCount;

    ItemFullCommentBinding binding;
    boolean isShowComment = false ;


    public CommentFragment() {
    }

    public void setDataComment(List<ItemComment> listComments, String commentCount) {
        this.commentCount = commentCount;
        this.listComments = listComments;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setCancelable(false);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.item_full_comment,null , false);

        bottomSheetDialog.setContentView(binding.getRoot());
        binding.txtCommentCount.setText(commentCount);

        binding.recyclerviewCommentCount.setLayoutManager(new LinearLayoutManager(getContext()));
        CommentAdapter commentAdapter = new CommentAdapter(listComments, getContext());
        binding.recyclerviewCommentCount.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();

        binding.btnExitComment.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
            isShowComment = true ;
        });     // click

        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) binding.getRoot().getParent());
        behavior.setSkipCollapsed(false);
        behavior.setDraggable(false);
        behavior.setPeekHeight(1000);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        binding.recyclerviewCommentCount.addItemDecoration(itemDecoration);

        return bottomSheetDialog;
    }

    public boolean checkStateCommentFragment() {
        return isShowComment;
    }

}
