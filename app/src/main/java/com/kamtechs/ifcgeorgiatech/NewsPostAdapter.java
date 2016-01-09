package com.kamtechs.ifcgeorgiatech;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Kamenator on 10/27/15.
 */

public class NewsPostAdapter extends RecyclerView.Adapter<CompleteListViewHolder> {

    private Activity mContext;
    private ArrayList<NewsPost> newsPosts;
    private LayoutInflater mLayoutInflater = null;


    public NewsPostAdapter(Activity context, ArrayList<NewsPost> newsPosts) {
        mContext = context;
        this.newsPosts = newsPosts;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return newsPosts.size();
    }
    /*@Override
    public Object getItem(int pos) {
        return newsPosts.get(pos);
    }*/
    @Override
    public long getItemId(int position) {
        return position;
    }
    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.news_post_list_layout_picture, null);
            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }



        NewsPost currentNewsPost = newsPosts.get(position);
        SimpleDateFormat formater = new SimpleDateFormat("MM-dd-yy");

        String dateString = formater.format(currentNewsPost.calendar.getTime());

        if (dateString.substring(0,1).equals("0")) {
            dateString = dateString.substring(1);
            if (dateString.substring(2,3).equals("0")) {
                dateString = dateString.substring(0,2) + dateString.substring(3, dateString.length());
            }
        } else {
            if (dateString.substring(3,4).equals("0")) {
                dateString = dateString.substring(0,3) + dateString.substring(4, dateString.length());
            }
        }

        if (currentNewsPost.hasPicture) {
            if (currentNewsPost.picture == null) {
                viewHolder.newsImage.setImageResource(R.drawable.loading);
            } else {
                viewHolder.newsImage.setImageBitmap(currentNewsPost.picture);
            }
        } else {
            viewHolder.newsImage.getLayoutParams().height = 0;
            viewHolder.paddingTextView.getLayoutParams().height = 0;
        }

        String messageToSet = "<b><font color=#0000FF>" +
                                dateString + ": </font></b><font>" +
                                currentNewsPost.message + "</font>";
        viewHolder.newsPost.setText(Html.fromHtml(messageToSet));

        return v;
    }*/

    public CompleteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.news_post_list_layout_picture, parent, false);
        CompleteListViewHolder holder = new CompleteListViewHolder(v);
        return holder;
    }

    public void onBindViewHolder(CompleteListViewHolder holder, int position) {
        NewsPost currentNewsPost = newsPosts.get(position);

        SimpleDateFormat formater = new SimpleDateFormat("MM-dd-yy");

        String dateString = formater.format(currentNewsPost.calendar.getTime());

        if (dateString.substring(0,1).equals("0")) {
            dateString = dateString.substring(1);
            if (dateString.substring(2,3).equals("0")) {
                dateString = dateString.substring(0,2) + dateString.substring(3, dateString.length());
            }
        } else {
            if (dateString.substring(3,4).equals("0")) {
                dateString = dateString.substring(0,3) + dateString.substring(4, dateString.length());
            }
        }

        String messageToSet = "<b><font color=#0000FF>" +
                dateString + ": </font></b><font>" +
                currentNewsPost.message + "</font>";
        holder.newsPost.setText(Html.fromHtml(messageToSet));

        if (currentNewsPost.hasPicture) {
            holder.newsImage.getLayoutParams().height = 250;
            holder.paddingTextView.getLayoutParams().height = 7;
            if (currentNewsPost.picture == null) {
                holder.newsImage.setImageResource(R.drawable.loading);
            } else {
                holder.newsImage.setImageBitmap(currentNewsPost.picture);
            }
        } else {
            holder.newsImage.getLayoutParams().height = 0;
            holder.paddingTextView.getLayoutParams().height = 0;
        }


    }
}


class CompleteListViewHolder extends RecyclerView.ViewHolder{
    public TextView newsPost;
    public ImageView newsImage;
    public TextView paddingTextView;

    public CompleteListViewHolder(View base) {
        super(base);
        newsPost = (TextView) base.findViewById(R.id.newsPostListText);
        newsImage = (ImageView) base.findViewById(R.id.imageView);
        paddingTextView = (TextView) base.findViewById(R.id.paddinTextView);
    }
}