package com.example.miao.mobile_cw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * Created by Miao Zhang on 12/03/2018.
 * StudentID: S1402087
 */
class RecyclerViewHolderPlanned extends  RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener


{
   public TextView textTitle,textDes,txtDaysOfLength;
    private ItemClickLisentner itemClickLisentner;
public RelativeLayout relativeLayout;
    public RecyclerViewHolderPlanned(View itemView) {
        super( itemView );

        textTitle = (TextView) itemView.findViewById( R.id.textViewTitle );
       // textDes = (TextView) itemView.findViewById( R.id.textViewDes );
        txtDaysOfLength = (TextView) itemView.findViewById( R.id.textViewPubDate );
        relativeLayout = (RelativeLayout)itemView.findViewById(R.id.RelativeView);
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

public class RecylerViewAdapter_Planned extends RecyclerView.Adapter <RecyclerViewHolderPlanned> {
    private Context context;
    private LinkedList<Item> itemList = new LinkedList<>(  );

    public RecylerViewAdapter_Planned(Context myCtx, LinkedList<Item> itemList) {
        this.context = myCtx;
        this.itemList = itemList;
    }

    @Override
    public RecyclerViewHolderPlanned onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate( R.layout.recycle_view_item,parent, false );


        return new RecyclerViewHolderPlanned( view );
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderPlanned holder, int position) {
        final Item item = itemList.get( position );

        //Here can modify the information disiplayed on the Apdapter that has been invokered (currentIncidents, or Planned Road Worrk)


        // holder.textDes.setText( item.getDescription() );

        holder.textTitle.setText(item.getTitle());




        try {

                if(item.getDateRmain()<=3)
                {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#7BD02E"));

                }else if (item.getDateRmain()>3 &&item.getDateRmain()<=10)
                {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#EEBE77"));
                }else
                    {holder.relativeLayout.setBackgroundColor(Color.parseColor("#FCBDB0"));
                    }


            holder.txtDaysOfLength.setText("Lenth of Days :"+String.valueOf(item.getDateRmain()));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.setItemClickLisentner( new ItemClickLisentner() {
            @Override
            public void onClick(View view, int position) {

                //Can send any information to itemDetails class- the activity that trigered by click the items that displayed on the currentIncidents Screen
                SimpleDateFormat sdf = new SimpleDateFormat("E,   dd  MMM  yyyy - HH:mm");
                Intent toPlanned = new Intent(context,ItemDetails_Planned.class );
                toPlanned.putExtra( "Title",item.getTitle() );
                toPlanned.putExtra( "StartTime",sdf.format(item.getStartDate()) );
                toPlanned.putExtra( "EndTime",sdf.format(item.getEndDate()));
                toPlanned.putExtra( "Location",item.getTypeAndLocation() );
                toPlanned.putExtra( "Geo",item.getGeorssPoint() );

                context.startActivity( toPlanned );

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
