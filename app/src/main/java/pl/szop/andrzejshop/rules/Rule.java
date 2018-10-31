package pl.szop.andrzejshop.rules;

public interface Rule {

    boolean check(Object... objects);
    boolean isNegative();
}
