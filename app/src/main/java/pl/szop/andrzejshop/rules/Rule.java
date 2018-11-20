package pl.szop.andrzejshop.rules;

public interface Rule {
    // TODO rename
    public enum Action{
        VISIBLE,
        CHECKING
    }

    Action getAction();
    boolean check(Object... objects);
    boolean isNegative();

}
