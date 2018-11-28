package pl.szop.andrzejshop.adapters;

import android.content.Context;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.data.criteria.Sort;
import pl.szop.andrzejshop.utils.ResourceUtils;

public class SortingAdapter extends ArrayAdapter {

    private List<Sort> mItems;
    private Context mContext;
    private int mResource;

    public SortingAdapter(Context context, List<Sort> items, int resource){
        super(context, resource);
        mContext = context;
        mItems = items;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }
        Sort sort = mItems.get(position);
        String text = sort==null ? mContext.getString(R.string.lack) : buildSortingText(sort);
        viewHolder.setTex(text);
        return row;
    }

    @NonNull
    private String buildSortingText(Sort sort) {
        String name = ResourceUtils.getStringByName(sort.getName() + "_product", mContext);
        String order = sort.isDesc() ? ResourceUtils.getStringByName("desc", mContext) :
                ResourceUtils.getStringByName("asc", mContext);
        return name + " " + order;
    }

    static class ViewHolder{
        TextView textView;

        ViewHolder(View view){
            textView = view.findViewById(android.R.id.text1);
        }

        void setTex(String text){
            textView.setText(text);
        }
    }
}
