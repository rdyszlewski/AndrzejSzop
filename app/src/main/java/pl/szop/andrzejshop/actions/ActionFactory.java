package pl.szop.andrzejshop.actions;

public class ActionFactory {

    public static Action getAction(String actionName){
        switch (actionName){
            case AddToCartAction.NAME:
                return new AddToCartAction();
            case AddToFavoritesAction.NAME:
                return new AddToFavoritesAction();
            case RemoveFromFavoritesAction.NAME:
                return new RemoveFromFavoritesAction();
                default:
                    throw new IllegalArgumentException();
        }
    }
}
