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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public void addRule(int elementResource, String ruleName, boolean negative){
        Rule rule = RulesFactory.getRule(ruleName, negative);
        mRules.put(elementResource, rule);
    }

    public void addAction(int elementResource, String actionName){
        Action action = ActionFactory.getAction(actionName);
        mActions.put(elementResource, action);
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
        for(String view : viewHolder.getFields()) {
            viewHolder.checkRules(view);
            Object value = null;
            try {
                value = product.getValue(view);
            } catch (NoSuchMethodException e) {
                continue;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            viewHolder.setValue(view, value);
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

        void checkRules(String field){
            View view = mViews.get(field.toLowerCase());
            if(mRules.containsKey(view.getId())){
                Product product = mItems.get(getAdapterPosition());
                boolean visibility = mRules.get(view.getId()).check(product);
                int visibilityState = visibility ? View.VISIBLE : View.GONE;
                view.setVisibility(visibilityState);
            }
        }

        void setValue(String field, Object value){
            View view = mViews.get(field.toLowerCase());
            if(view == null){
                return;
            }

            if (view instanceof TextView){
                setText(value, (TextView) view);
            } else if (view instanceof ImageView){
                setImage(value, (ImageView) view);
            }
        }

        private void setImage(Object value, ImageView view) {
            if(value instanceof byte[]){
                byte[] data = (byte[]) value;
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                view.setImageBitmap(bitmap);
            }else if(value instanceof Integer){
                view.setImageResource((Integer) value);
            } else if (value instanceof Bitmap){
                view.setImageBitmap((Bitmap) value);
            }
        }

        private void setText(Object value, TextView view) {
            if(value instanceof String){
                view.setText((String)value);
            } else if(value instanceof Double) {
                view.setText(String.valueOf(value));
            }
        }

        private Integer getViewId(String name){
            View view = mViews.get(name.toLowerCase());
            if(view != null){
                return view.getId();
            }
            return null;
        }
    }
}
