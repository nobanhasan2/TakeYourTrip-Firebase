package com.wix.norsoftbd.takeyourtrip.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wix.norsoftbd.takeyourtrip.Interface.Itemclicklistener;
import com.wix.norsoftbd.takeyourtrip.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MNH on 11/1/2017.
 */

public class postViewholder extends RecyclerView.ViewHolder implements  View.OnClickListener {
public TextView title,location;public CircleImageView profile_icon;
public Itemclicklistener itemclicklistener;
public postViewholder(View itemView) {
        super(itemView);
        profile_icon=(CircleImageView) itemView.findViewById(R.id.profile_recyler_image);
        title=(TextView)itemView.findViewById(R.id.recyler_title);
        location=(TextView)itemView.findViewById(R.id.recycler_location);

        itemView.setOnClickListener(this);

        }

    public void setItemclicklistener(Itemclicklistener itemclicklistener) {
        this.itemclicklistener = itemclicklistener;
        }

@Override
    public void onClick(View view) {
        itemclicklistener.onClick(view,getAdapterPosition(),false);
        }


        }
