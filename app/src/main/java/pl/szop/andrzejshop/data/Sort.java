package pl.szop.andrzejshop.data;

import java.util.Objects;

public class Sort {
    private String name;
    private boolean desc;

    public Sort(String name, boolean desc){
        this.name = name;
        this.desc = desc;
    }

    public String getName(){
        return name;
    }

    public boolean isDesc(){
        return desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sort sort = (Sort) o;
        return desc == sort.desc &&
                Objects.equals(name, sort.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, desc);
    }
}
