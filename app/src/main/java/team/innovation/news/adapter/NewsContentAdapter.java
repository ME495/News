package team.innovation.news.adapter;
/**
 * Created by ME495 on 2018/3/7.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import team.innovation.news.NavigationActivity;
import team.innovation.news.R;
import team.innovation.news.business.MyDatabaseHelper;
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
        final NewsContent newsContent = data.get(position);
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemLayout, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.title);
            holder.image = convertView.findViewById(R.id.image);
            holder.star = convertView.findViewById(R.id.star);
            holder.star.setTag(newsContent);
            //监听器，点击star，改变收藏状态
            holder.star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ChangeStar().execute(v);
                }
            });
            //设置star的初始状态
            new SetStar().execute(holder.star);
            holder.newsContent = newsContent;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(newsContent.getTitle());
        //载入图片
        new LoadImage().execute(holder);
//        holder.image.setImageBitmap(newsContent.getBitmap());
        convertView.setTag(holder);
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        ImageView image,star;
        NewsContent newsContent;
    }

    //获取并设置star的状态
    private class SetStar extends AsyncTask<View, Void, Boolean> {
        private ImageView imageView;
        private MyDatabaseHelper dbHelper;
        @Override
        protected Boolean doInBackground(View... views) {
            Log.e("change","ok");
            imageView = (ImageView) views[0];
            NewsContent newsContent = (NewsContent) imageView.getTag();
            //从数据库获取star状态，flag为true表示已经被收藏
            dbHelper = new MyDatabaseHelper(context, MyDatabaseHelper.FILENAME, null, 1);
            boolean flag = dbHelper.isExist(newsContent);
            return flag;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            //设置star的状态
            if (aBoolean) {
                imageView.setImageResource(R.drawable.star2);
            } else {
                imageView.setImageResource(R.drawable.star1);
            }
        }
    }
    private class ChangeStar extends AsyncTask<View, Void, Boolean> {

        private ImageView imageView;
        private MyDatabaseHelper dbHelper;
        @Override
        protected Boolean doInBackground(View... views) {
            Log.e("change","ok");
            imageView = (ImageView) views[0];
            NewsContent newsContent = (NewsContent) imageView.getTag();
            dbHelper = new MyDatabaseHelper(context, MyDatabaseHelper.FILENAME, null, 1);
            boolean flag = dbHelper.isExist(newsContent);
            if (flag) {
                dbHelper.deleteNewsContent(newsContent);
//                imageView.setImageResource(R.drawable.star1);
            } else {
                dbHelper.insertNewsContent(newsContent);
//                imageView.setImageResource(R.drawable.star2);
            }
            return flag;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                imageView.setImageResource(R.drawable.star1);
            } else {
                imageView.setImageResource(R.drawable.star2);
            }
        }
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
