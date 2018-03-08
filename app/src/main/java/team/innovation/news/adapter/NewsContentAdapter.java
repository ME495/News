package team.innovation.news.adapter;
/**
 * Created by ME495 on 2018/3/7.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import team.innovation.news.R;
import team.innovation.news.business.NetworkUtil;
import team.innovation.news.entity.NewsContent;

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：新闻内容适配器
 */
public class NewsContentAdapter extends BaseAdapter {
    private Context context;
    private int itemLayout;
    private ArrayList<NewsContent> data;

    public NewsContentAdapter(Context context, int itemLayout, ArrayList<NewsContent> data) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemLayout, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.title);
            holder.image = convertView.findViewById(R.id.image);
            holder.newsContent = data.get(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsContent newsContent = data.get(position);
        holder.title.setText(newsContent.getTitle());
        new LoadImage().execute(holder);
//        holder.image.setImageBitmap(newsContent.getBitmap());
        convertView.setTag(holder);
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        ImageView image;
        NewsContent newsContent;
    }

    private class LoadImage extends AsyncTask<ViewHolder, Void, ViewHolder> {
        @Override
        protected ViewHolder doInBackground(ViewHolder... viewHolders) {
            ViewHolder holder = viewHolders[0];
            if (holder.newsContent.getBitmap() == null) {
                holder.newsContent.setBitmap(NetworkUtil.getBitmap(holder.newsContent.getImageUrl()));
            }
            return holder;
        }

        @Override
        protected void onPostExecute(ViewHolder viewHolder) {
            super.onPostExecute(viewHolder);
            viewHolder.image.setImageBitmap(viewHolder.newsContent.getBitmap());
        }
    }
}
