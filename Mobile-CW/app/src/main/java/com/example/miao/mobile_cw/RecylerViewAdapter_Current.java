package com.example.miao.mobile_cw;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * Created by Miao Zhang on 12/03/2018.
 * StudentID: S1402087
 */
class RecyclerViewHolder_Current extends  RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener


{
   public TextView textTitle,textDes,textPubDate;
    private ItemClickLisentner itemClickLisentner;

    public RecyclerViewHolder_Current(View itemView) {
        super( itemView );

        textTitle = (TextView) itemView.findViewById( R.id.textViewTitle );
       // textDes = (TextView) itemView.findViewById( R.id.textViewDes );
        textPubDate = (TextView) itemView.findViewById( R.id.textViewPubDate );

        itemView.setOnClickListener( this );
        itemView.setOnLongClickListener( this );
    }

    public void setItemClickLisentner(ItemClickLisentner itemClickLisentner){

this.itemClickLisentner = itemClickLisentner;

    }

    @Override
    public void onClick(View v) {
              itemClickLisentner.onClick( v,getAdapterPosition() );
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickLisentner.onClick( v,getAdapterPosition() );
        return false;
    }
}

public class RecylerViewAdapter_Current extends RecyclerView.Adapter <RecyclerViewHolder_Current> {
    private Context myCtx;
    private LinkedList<Item> itemList = new LinkedList<>(  );

    public RecylerViewAdapter_Current(Context myCtx, LinkedList<Item> itemList) {
        this.myCtx = myCtx;
        this.itemList = itemList;
    }

    @Override
    public RecyclerViewHolder_Current onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( myCtx );
        View view = inflater.inflate( R.layout.recycle_view_item,parent, false );


        return new RecyclerViewHolder_Current( view );
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_Current holder, int position) {
        final Item item = itemList.get( position );

        //Here can modify the information disiplayed on the Apdapter that has been invokered (currentIncidents, or Planned Road Worrk)
        holder.textTitle.setText( item.getTitle() );
        holder.textPubDate.setText(item.getPubDateString());


        holder.setItemClickLisentner( new ItemClickLisentner() {
            @Override
            public void onClick(View view, int position) {

                //Can send any information to itemDetails class- the activity that trigered by click the items that displayed on the currentIncidents Screen
                SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
                Intent intent = new Intent( myCtx,ItemDetails_Current.class );
                intent.putExtra( "Title",item.getTitle() );
                intent.putExtra( "Des",item.getDescription() );
                intent.putExtra("GesPoint",item.getGeorssPoint());
                intent.putExtra( "PubDate",sdf.format(item.getPublishDate()) );

                myCtx.startActivity( intent );

            }

        });

    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setFilter(LinkedList<Item> newList){
        itemList = new LinkedList<>();
        itemList = newList;
        notifyDataSetChanged();


    }


}
