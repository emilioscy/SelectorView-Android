package selectorexample.androidapp.com.selectorviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.emcy.selector.OnObjectSelectorListener;
import com.emcy.selector.OnValuesSelectorListener;
import com.emcy.selector.SelectorView;

import java.util.ArrayList;
import java.util.List;

public class SelectorViewTest extends AppCompatActivity implements OnObjectSelectorListener, OnValuesSelectorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_view_test);
        setType(getIntent().getIntExtra("type", 0));
    }

    private void setType(int type) {
        switch (type){
            case 0:
                typeOne();
                break;
            case 1:
                typeTwo();
                break;
            case 2:
                typeThree();
                break;
            case 3:
                typeFour();
                break;
            case 4:
                typeFive();
                break;
            case 5:
                typeSix();
                break;
            case 6:
                typeSeven();
                break;
            case 7:
                typeEight();
                break;
        }
    }

    private void typeEight() {
        SelectorView selector = (SelectorView) findViewById(R.id.selector8);
        selector.setVisibility(View.VISIBLE);
        selector.setOnValueSelectorListener(this);
        selector.setDisplayedValues(getDisplayedValues());
        selector.setSelectedValue("Value 5");
    }

    private void typeSeven() {
        SelectorView selector = (SelectorView) findViewById(R.id.selector7);
        selector.setVisibility(View.VISIBLE);
        selector.setOnValueSelectorListener(this);
        selector.setDisplayedValues(getDisplayedValues());
        selector.setSelectedValue("Value 5");
    }

    private void typeSix() {
        SelectorView selector = (SelectorView) findViewById(R.id.selector6);
        selector.setVisibility(View.VISIBLE);
        selector.setOnValueSelectorListener(this);
        selector.setDisplayedValues(getDisplayedValues());
        selector.setSelectedValue("Value 5");
    }

    private void typeFive() {
        SelectorView selector = (SelectorView) findViewById(R.id.selector5);
        selector.setVisibility(View.VISIBLE);
        selector.setOnValueSelectorListener(this);
        selector.setDisplayedValues(getDisplayedValues());
        selector.setSelectedValue("Value 5");
    }

    private void typeFour() {
        SelectorView selector = (SelectorView) findViewById(R.id.selector4);
        selector.setVisibility(View.VISIBLE);
        selector.setOnObjectSelectorListener(this);
        selector.setSelectedObject(3);
    }

    private void typeThree() {
        SelectorView selector = (SelectorView) findViewById(R.id.selector3);
        selector.setVisibility(View.VISIBLE);
        selector.setOnObjectSelectorListener(this);
        selector.setSelectedObjects(getSelectedObjects());
    }

    private void typeTwo() {
        SelectorView selector = (SelectorView) findViewById(R.id.selector2);
        selector.setVisibility(View.VISIBLE);
        selector.setOnObjectSelectorListener(this);
        selector.setSelectedObject(3);
    }

    private void typeOne() {
        SelectorView selector = (SelectorView) findViewById(R.id.selector1);
        selector.setVisibility(View.VISIBLE);
        selector.setOnObjectSelectorListener(this);
        selector.setSelectedObject(3);
    }

    private List<String> getDisplayedValues() {
        List<String> array = new ArrayList<>();
        for (int x = 0; x < 4; x++) {
            array.add("Value " + x);
        }
        return array;
    }

    private List<Long> getSelectedObjects() {
        List<Long> selectedObjects = new ArrayList<>();
        selectedObjects.add(2L);
        selectedObjects.add(4L);
        return selectedObjects;
    }

    private List<String> getSelectedValues() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Value 2");
        stringList.add("Value 4");
        return stringList;
    }

    @Override
    public void onValueClick(String value) {
        if (value != null)
            Toast.makeText(this, " " + value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValuesSelected(List<String> selectedValues) {
        if (selectedValues != null && selectedValues.size() != 0)
            Toast.makeText(this, " " + selectedValues, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValuesUpdated(List<String> selectedValues) {
        if (selectedValues != null && selectedValues.size() != 0)
            Toast.makeText(this, " " + selectedValues, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onObjectClick(long id) {
        if (id != -1)
            Toast.makeText(this, " " + id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onObjectsSelected(List<Long> selectedIds) {
        if (selectedIds != null && selectedIds.size() != 0)
            Toast.makeText(this, " " + selectedIds, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onObjectsUpdated(List<Long> selectedIds) {
        if (selectedIds != null && selectedIds.size() != 0)
            Toast.makeText(this, " " + selectedIds, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
