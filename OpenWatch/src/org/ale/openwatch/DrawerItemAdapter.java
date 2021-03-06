package org.ale.openwatch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.ale.openwatch.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by davidbrodsky on 6/20/13.
 */
public class DrawerItemAdapter extends ArrayAdapter {
    Context c;
    static int layoutId = R.layout.drawer_item;
    ArrayList<String> data = null;
    ArrayList<String> feeds = null;
    HashMap dataToIcon;
    String userThumbnailUrl;

    public DrawerItemAdapter(Context context, ArrayList<String> data, HashMap<String, Integer> dataToIcon, ArrayList<String> feeds, String userThumbnailUrl) {
        super(context, layoutId, data);
        c = context;
        this.data = data;
        this.dataToIcon = dataToIcon;
        this.feeds = feeds;
        this.userThumbnailUrl = userThumbnailUrl;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        View view = convertView;
        ViewCache viewCache;
        if(view == null)
        {
            LayoutInflater inflater = ((Activity)c).getLayoutInflater();
            view = inflater.inflate(layoutId, parent, false);

            viewCache = new ViewCache();
            viewCache.icon = (ImageView)view.findViewById(R.id.icon);
            viewCache.title = (TextView)view.findViewById(R.id.title);
            viewCache.divider = view.findViewById(R.id.divider);

            view.setTag(R.id.list_item_cache, viewCache);
        }
        else
        {
            viewCache = (ViewCache) view.getTag(R.id.list_item_cache);
        }

        if(data.get(position).compareTo("divider") == 0){
            viewCache.divider.setVisibility(View.VISIBLE);
            viewCache.icon.setVisibility(View.GONE);
            viewCache.title.setVisibility(View.GONE);
            view.setTag(R.id.list_item_model, data.get(position));
            return view;
        }else{
            viewCache.divider.setVisibility(View.GONE);
            viewCache.icon.setVisibility(View.VISIBLE);
            viewCache.title.setVisibility(View.VISIBLE);
        }

        view.setId(R.id.standardRow);
        if(dataToIcon.containsKey(data.get(position))){
            viewCache.title.setText(data.get(position));
            viewCache.icon.setVisibility(View.VISIBLE);
            if(data.get(position).compareTo("Profile")==0){
                view.setId(R.id.profileRow);
                if(userThumbnailUrl != null){
                    ImageLoader.getInstance().displayImage(userThumbnailUrl, viewCache.icon);
                }else{
                    viewCache.icon.setImageResource((Integer)dataToIcon.get(data.get(position)));
                }
            }else
                viewCache.icon.setImageResource((Integer)dataToIcon.get(data.get(position)));
            //view.setTag(R.id.list_item_model, data.get(position));
        }else if(feeds.contains(data.get(position))){
            viewCache.title.setText(Constants.FEED_TO_TITLE.get(data.get(position)));
            //view.setTag(R.id.list_item_model, data.get(position));
            viewCache.icon.setVisibility(View.GONE);

        }else{
            viewCache.title.setText("# " + data.get(position));
            viewCache.icon.setVisibility(View.GONE);
        }

        view.setTag(R.id.list_item_model, data.get(position));
        return view;
    }

    static class ViewCache {
        ImageView icon;
        TextView title;
        View divider;
    }
}
