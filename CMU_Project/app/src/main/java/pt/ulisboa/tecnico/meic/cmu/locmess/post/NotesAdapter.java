package pt.ulisboa.tecnico.meic.cmu.locmess.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.meic.cmu.locmess.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Akilino on 16/03/2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{

    private ArrayList<Post> listPosts;
    private Context mContext;
    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback{
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView messageTextView, senderTextView, timestampTextView,locationTextView;
        private Button statusButton;
        //private View container;

        public ViewHolder(View itemView) {
            super(itemView);

            messageTextView = (TextView) itemView.findViewById(R.id.messageEditText);
            senderTextView = (TextView) itemView.findViewById(R.id.senderTextView);
            timestampTextView = (TextView) itemView.findViewById(R.id.timestampTextView);
            locationTextView = (TextView) itemView.findViewById(R.id.locationTextView);
            statusButton = (Button) itemView.findViewById(R.id.statusButton);
            statusButton.setOnClickListener(this);
            //container = itemView.findViewById(R.id.layout);
            //container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.statusButton){
                Log.d(TAG, "onClick: ItemClickedNote " + getAdapterPosition());
                //getAdapterPosition();
                itemClickCallback.onItemClick(getAdapterPosition());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.post_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(postView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = listPosts.get(position);

        TextView messageTextView = holder.messageTextView;
        TextView senderTextView = holder.senderTextView;
        TextView timestampTextView = holder.timestampTextView;
        TextView locationTextView = holder.locationTextView;
        Button statusButton = holder.statusButton;

        messageTextView.setText(post.getMessage());
        senderTextView.setText(post.getUser());
        timestampTextView.setText(post.getMode());
        locationTextView.setText(post.getLocation());
        statusButton.setText("unpost");
    }

    @Override
    public int getItemCount() {
        if(listPosts==null)
            return 0;
        return listPosts.size();
    }

    public NotesAdapter(Context context, ArrayList<Post> posts){
        listPosts = posts;
        mContext = context;
    }

    private Context getContext(){
        return mContext;
    }
}
