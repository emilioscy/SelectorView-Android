package selectorexample.androidapp.com.selectorviewexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.emcy.selector.ObjectSelectorBuilder;
import com.emcy.selector.OnObjectSelectorListener;
import com.emcy.selector.OnValuesSelectorListener;
import com.emcy.selector.SelectorActivityAttributes;
import com.emcy.selector.SelectorBDFragmentAttributes;
import com.emcy.selector.ValuesSelectorBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnValuesSelectorListener, OnObjectSelectorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSingleObjectActivity(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(Dummy.class)
                .setSelectedObject(3)
                .startActivityForResult(this, getSelectorAttrsForActivity());
    }

    public void onSingleObjectActivitySearch(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(Dummy.class)
                .setSelectedObject(3)
                .startActivityForResult(this, getSelectorAttrsForActivitySearch());
    }

    public void onMultipleObjectActivity(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(Dummy.class)
                .multipleSelection()
                .setSelectedObjects(getSelectedObjects())
                .startActivityForResult(this, getSelectorAttrsForActivity());
    }

    public void onMultipleObjectActivitySearch(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(Dummy.class)
                .multipleSelection()
                .setSelectedObjects(getSelectedObjects())
                .startActivityForResult(this, getSelectorAttrsForActivitySearch());
    }

    public void onSingleValueActivity(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .setSelectedValue("Value 3")
                .startActivityForResult(this, getSelectorAttrsForActivity());
    }

    public void onSingleValueActivitySearch(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .setSelectedValue("Value 3")
                .startActivityForResult(this, getSelectorAttrsForActivitySearch());
    }

    public void onMultipleValuesActivity(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .multipleSelection()
                .setSelectedValues(getSelectedValues())
                .startActivityForResult(this, getSelectorAttrsForActivity());
    }

    public void onMultipleValuesActivitySearch(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .multipleSelection()
                .setSelectedValues(getSelectedValues())
                .startActivityForResult(this, getSelectorAttrsForActivitySearch());
    }


    public void onSingleObjectFragment(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(Dummy.class)
                .setSelectedObject(3)
                .showBottomDialogFragment(this, getSelectorAttrsForBottom());
    }

    public void onSingleObjectFragmentSearch(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(Dummy.class)
                .setSelectedObject(3)
                .showBottomDialogFragment(this, getSelectorAttrsForBottomSearch());
    }

    public void onMultipleObjectFragment(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(Dummy.class)
                .multipleSelection()
                .setSelectedObjects(getSelectedObjects())
                .showBottomDialogFragment(this, getSelectorAttrsForBottom());
    }

    public void onMultipleObjectFragmentSearch(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(Dummy.class)
                .multipleSelection()
                .setSelectedObjects(getSelectedObjects())
                .showBottomDialogFragment(this, getSelectorAttrsForBottomSearch());
    }

    public void onSingleValueFragment(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .setSelectedValue("Value 3")
                .showBottomDialogFragment(this, getSelectorAttrsForBottom());
    }

    public void onSingleValueFragmentSearch(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .setSelectedValue("Value 3")
                .showBottomDialogFragment(this, getSelectorAttrsForBottomSearch());
    }

    public void onMultipleValuesFragment(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .multipleSelection()
                .setSelectedValues(getSelectedValues())
                .showBottomDialogFragment(this, getSelectorAttrsForBottom());
    }

    public void onMultipleValuesFragmentSearch(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .multipleSelection()
                .setSelectedValues(getSelectedValues())
                .showBottomDialogFragment(this, getSelectorAttrsForBottomSearch());
    }

    public void onSingleObjectSelectorView(View view) {
        Intent intent = new Intent(this, SelectorViewTest.class);
        intent.putExtra("type", 0);
        startActivity(intent);
    }

    public void onSingleObjectSelectorViewSearch(View view) {
        Intent intent = new Intent(this, SelectorViewTest.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    public void onMultipleObjectSelectorView(View view) {
        Intent intent = new Intent(this, SelectorViewTest.class);
        intent.putExtra("type", 2);
        startActivity(intent);
    }

    public void onMultipleObjectSelectorViewSearch(View view) {
        Intent intent = new Intent(this, SelectorViewTest.class);
        intent.putExtra("type", 3);
        startActivity(intent);
    }

    public void onSingleValueSelectorView(View view) {
        Intent intent = new Intent(this, SelectorViewTest.class);
        intent.putExtra("type", 4);
        startActivity(intent);
    }

    public void onSingleValueSelectorViewSearch(View view) {
        Intent intent = new Intent(this, SelectorViewTest.class);
        intent.putExtra("type", 5);
        startActivity(intent);
    }

    public void onMultipleValuesSelectorView(View view) {
        Intent intent = new Intent(this, SelectorViewTest.class);
        intent.putExtra("type", 6);
        startActivity(intent);
    }

    public void onMultipleValuesSelectorViewSearch(View view) {
        Intent intent = new Intent(this, SelectorViewTest.class);
        intent.putExtra("type", 7);
        startActivity(intent);
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

    private SelectorActivityAttributes getSelectorAttrsForActivity() {
        return SelectorActivityAttributes.instance()
                .setTextNormalColor(R.color.black)
                .setNormalTickColor(R.color.black)
                .setBackArrowColor(R.color.blue)
                .setToolbarColor(R.color.blue)
                .setListItemBackgroundColor(R.color.pink)
                .setBackArrowColor(R.color.pink)
                .enableSelectedItemsCount()
                .setTextMarginStartPercent(0.04f)
                .setTickMarginEndPercent(0.96f);
    }

    private SelectorActivityAttributes getSelectorAttrsForActivitySearch() {
        return SelectorActivityAttributes.instance()
                .setTextNormalColor(R.color.black)
                .setNormalTickColor(R.color.black)
                .setBackArrowColor(R.color.blue)
                .enableSelectedItemsCount()
                .setEnableSearchView(true)
                .setSearchCornerRadius(R.dimen.searchViewCornerRadius)
                .setSearchBackgroundColor(R.color.searchViewColor)
                .setTextMarginStartPercent(0.04f)
                .setTickMarginEndPercent(0.96f);
    }

    private SelectorBDFragmentAttributes getSelectorAttrsForBottom() {
        return SelectorBDFragmentAttributes.instance()
                .setTextNormalColor(R.color.black)
                .setNormalTickColor(R.color.black)
                .setDoneButtonTextColor(R.color.pink)
                .setListItemBackgroundColor(R.color.blue)
                .enableSelectedItemsCount()
                .setToolbarColor(R.color.colorAccent)
                .setTextMarginStartPercent(0.04f)
                .setTickMarginEndPercent(0.96f);
    }

    private SelectorBDFragmentAttributes getSelectorAttrsForBottomSearch() {
        return SelectorBDFragmentAttributes.instance()
                .setTextNormalColor(R.color.black)
                .setNormalTickColor(R.color.black)
                .enableSelectedItemsCount()
                .setEnableSearchView(true)
                .setTextMarginStartPercent(0.04f)
                .setTickMarginEndPercent(0.96f);
    }

    private List<String> getDisplayedValues() {
        List<String> array = new ArrayList<>();
        for (int x = 0; x < 4; x++) {
            array.add("Value " + x);
        }
        return array;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ValuesSelectorBuilder.with(this).registerActivityResult(requestCode, resultCode, data);
        ObjectSelectorBuilder.with(this).registerActivityResult(requestCode, resultCode, data);
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
