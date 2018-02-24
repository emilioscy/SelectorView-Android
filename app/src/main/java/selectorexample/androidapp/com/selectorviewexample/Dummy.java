package selectorexample.androidapp.com.selectorviewexample;

import com.emcy.selector.SelectorDataGetter;

import java.util.ArrayList;
import java.util.List;

public class Dummy extends SelectorDataGetter<Dummy> {

    String name;
    long id;

    public Dummy() {
    }

    public Dummy(String s, long x) {
        this.id = x;
        this.name = s;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public Long getModelId() {
        return id;
    }

    @Override
    public List<Dummy> getData() {
        return createData();
    }

    private List<Dummy> createData() {
        List<Dummy> list = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            list.add(new Dummy(("MyName is : " + x), x));
        }
        return list;
    }
}
