package pl.szop.andrzejshop.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.szop.andrzejshop.actions.Action;
import pl.szop.andrzejshop.actions.ActionFactory;
import pl.szop.andrzejshop.models.Product;
import pl.szop.andrzejshop.rules.Rule;
import pl.szop.andrzejshop.rules.RulesFactory;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context mContext;
    private List<? extends Product> mItems;
    private int mResource;
    private AdapterListener mListener;

    private Map<Integer, Action> mActions;
    private Map<Integer, Rule> mRules;
    private ViewAdapter mViewAdapter = new ViewAdapter();

    public void addRule(int elementResource, String ruleName, boolean negative){
        mViewAdapter.addRule(elementResource, ruleName, negative);
    }

    public void addAction(int elementResource, String actionName){
        mViewAdapter.addAction(elementResource, actionName);
    }

    public interface AdapterListener{
        void onClick(Long productId);
    }

    public ProductsAdapter(@NonNull Context context, int resource, List<? extends Product> items, AdapterListener adapterListener) {
        mContext = context;
        mResource = resource;
        mItems = items;
        mListener = adapterListener;
        mActions = new HashMap<>();
        mRules = new HashMap<>();
    }

    public void setItems(List<? extends Product> items){
        mItems = items;
        notifyDataSetChanged();
    }

    public void setResource(int resource){
        mResource = resource;
    }

    private void setValues(ViewHolder viewHolder, Product product) {
        for(String viewName : viewHolder.getFields()) {
            View view = viewHolder.mViews.get(viewName.toLowerCase());
            mViewAdapter.bindView(view, product, false);
            mViewAdapter.checkRules(view, product);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(mResource, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Product product = mItems.get(i);
        viewHolder.getView().setOnClickListener((event)->{
            if(mListener != null){
                try {
                    Long id = (Long) mItems.get(i).getValue("id");
                    mListener.onClick(id);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        setValues(viewHolder, product);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private Map<String, View> mViews = new HashMap<>();
        private View mView;

        ViewHolder(View view){
            super(view);
            mView = view;
            ViewGroup viewGroup = (ViewGroup) view;
            for(int i=0; i<viewGroup.getChildCount(); i++){
                View childView = viewGroup.getChildAt(i);
                // set actions
                setActions(childView);
                String resourceName = getResourceName(childView);
                mViews.put(resourceName.toLowerCase(), childView);
            }
        }

        // TODO spróbować wykorzystac funkcję z ViewAdapter
        private void setActions(View childView) {
            if(isActionComponent(childView) && mActions.containsKey(childView.getId())){
                childView.setOnClickListener(e->{
                    Product product = mItems.get(getAdapterPosition());
                    mActions.get(childView.getId()).execute(product, mContext);
                });
            }
        }

        public View getView(){
            return mView;
        }

        private boolean isActionComponent(View childView) {
            return childView instanceof Button || childView instanceof ImageButton;
        }

        public Set<String> getFields(){
            return mViews.keySet();
        }

        private String getResourceName(View view){
            String resourceName = mContext.getResources().getResourceName(view.getId());
            int splashIndex = resourceName.indexOf('/');
            return resourceName.substring(splashIndex+1);
        }
    }
}
