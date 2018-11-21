package pl.szop.andrzejshop.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
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

import pl.szop.andrzejshop.actions.Action;
import pl.szop.andrzejshop.actions.ActionFactory;
import pl.szop.andrzejshop.models.Image;
import pl.szop.andrzejshop.models.Product;
import pl.szop.andrzejshop.rules.Rule;
import pl.szop.andrzejshop.rules.RulesFactory;

// TODO przerobić to na klase niestatyczna
public class ViewAdapter {

    private Map<Integer, Action> mActions = new HashMap<>();
    private Map<Integer, Rule> mRules = new HashMap<>();

    public void addRule(int elementResource, String ruleName, boolean negative){
        Rule rule = RulesFactory.getRule(ruleName, negative);
        mRules.put(elementResource, rule);
    }

    public void addAction(int elementResource, String actionName){
        Action action = ActionFactory.getAction(actionName);
        mActions.put(elementResource, action);
    }

    public void bindView(View view, Product product) {
        bindView(view, product, true);
    }

    public void bindView(View view, Product product, boolean recursive){
        if(recursive && hasChildrenViews(view)){
            ViewGroup viewGroup = (ViewGroup) view;
            View childView;
            for(int i =0; i <viewGroup.getChildCount(); i++){
                childView = viewGroup.getChildAt(i);
                bindView(childView, product);
            }
        } else {
            setViewValue(view, product);
            checkRules(view, product);
            setAction(view, product);
        }

    }

    private void setViewValue(View view, Product product) {
        String viewName = getResourceName(view);
        try{
            Object value = isCustomObject(viewName) ? getValueFromCustomObject(viewName, product) :
                    product.getValue(viewName);
            setValue(view, value);
        } catch (Exception ignored){
            // method getValue can generate exception when not found correct method in object
        }
    }

    private boolean isCustomObject(String view) {
        return view.contains(".");
    }

    private Object getValueFromCustomObject(String viewId, Product product) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String[] fields = viewId.split("\\.");
        Product lastProduct = product;
        for(String field : fields){
            Object object = lastProduct.getValue(field);
            if(object instanceof Product){
                lastProduct = (Product) object;
            } else {
                return object;
            }
        }
        return null;
    }

    public void checkRules(View view, Product product){
        if(mRules.containsKey(view.getId())){
            boolean state = mRules.get(view.getId()).check(product);
            Rule.Action action = mRules.get(view.getId()).getAction();
            switch (action){
                case VISIBLE:
                    int visibilityState = state ? View.VISIBLE : View.GONE;
                    view.setVisibility(visibilityState);
                    break;
                case CHECKING:
                    // TODO dodać sprawdzanie typu
                    ((CheckBox)view).setChecked(state);
                    break;
            }
        }
    }

    public void setAction(View view, Product product){
        if(isActionComponent(view) && mActions.containsKey(view.getId())){
            view.setOnClickListener(e->{
                mActions.get(view.getId()).execute(product, view.getContext());
            });
        }
    }

    private boolean isActionComponent(View childView) {
        return childView instanceof Button || childView instanceof ImageButton;
    }

    private boolean hasChildrenViews(View view) {
        return view instanceof ViewGroup;
    }

    private void setValue(View view, Object value){
        if(view == null){
            return;
        }

        if (view instanceof TextView){
            setText(value, (TextView) view);
        } else if (view instanceof ImageView){
            setImage(value, (ImageView) view);
        } else if (view instanceof ViewPager){
//            ImageAdapter adapter = new ImageAdapter(view.getContext(), (List<Image>) value);
//            ((ViewPager)view).setAdapter(adapter);
        } else {
            System.out.println();
        }
        // TODO serve another types
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

    private String getResourceName(View view){
        String resourceName = view.getContext().getResources().getResourceName(view.getId());
        int splashIndex = resourceName.indexOf('/');
        return resourceName.substring(splashIndex+1);
    }
}
