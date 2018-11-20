package pl.szop.andrzejshop.rules;

public class RulesFactory {

    public static Rule getRule(String ruleName, boolean negative){
        switch (ruleName){
            case BoughtRule.NAME:
                return new BoughtRule(negative);
            case InCartRule.NAME:
                return new InCartRule(negative);
            case FavoritesRule.NAME:
                return new FavoritesRule();
                default:
                    throw new IllegalArgumentException();
        }
    }
}
