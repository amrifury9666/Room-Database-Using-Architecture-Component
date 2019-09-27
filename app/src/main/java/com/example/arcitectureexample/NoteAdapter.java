package com.example.arcitectureexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arcitectureexample.databinding.NoteItemBinding;
import com.example.arcitectureexample.db.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private Context context;
    private ItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note item = getItem(position);

        holder.bind(item);
    }


    public Note getNoteAt(int position) {

        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        NoteItemBinding mBinding;

        public NoteHolder(@NonNull NoteItemBinding binding) {
            super(binding.getRoot());

            this.mBinding = binding;
        }


        public void bind(final Note note) {

            mBinding.title.setText(note.getTitle());
            mBinding.description.setText(note.getDescription());
            mBinding.priority.setText(String.valueOf(note.getPriority()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface ItemClickListener {

        void onItemClick(Note note);
    }

    public void setListener(ItemClickListener clickListener) {
        this.listener = clickListener;
    }
}
