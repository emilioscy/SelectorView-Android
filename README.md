# **SelectorView**

## **Description**

This library was developed for fast and easy build of a list of attributes for selection.

The main idea is to pass the values that you want to display to the user and get a callback with the selected attributes.

There many are options to use, like pre-selected attributes, multiple selection, search through the attributes and customise the whole appearance. 

## **Installation**

In your root build.gradle file add jitpack:

```
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

In your app's project build.gradle file add dependency:
```
dependencies {
    compile 'com.github.emilioscy:SelectorView-Android:v0.1.5'
}
```

## **Main Functionalities**
 1. ObjectSelectorBuilder
 2. ValuesSelectorBuilder
 3. SelectorView
 
### 1. ObjectSelectorBuilder
![Activity](https://github.com/emilioscy/SelectorView-Android/blob/master/object_selector_activity.gif)

With this functionality, you can start activity for result and show the objects you want like this :

```
public void startActivityForResult(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(MyObject.class)
                .multipleSelection()
                .setSelectedObjects(getSelectedObjects())
                .startActivityForResult(this, getSelectorAttrsForActivitySearch());
}
```

The first argument ```.with(this)``` is callback of class `OnObjectSelectorListener` which implements three functions.

```
   @Override
    public void onObjectClick(long id) {
    }

    @Override
    public void onObjectsSelected(List<Long> selectedIds) {
    }

    @Override
    public void onObjectsUpdated(List<Long> selectedIds) {
    }
```

The second argument is the object class (MyObject) for this case, which must extend the abstract class `SelectorDataGetter<T>`, where T is the MyObject. The class implements three methods as you can see below. 
1) getDisplayName()
2) getModelId()
3) getData()

The first one is the variable which is going to be displayed on the adapter. The second is the unique id of the object, 
which is going to return as a result at the end, or even pre select specific objects. The getData methods get as argument a list of type `T`, in this case `List<MyObject>` which are the data of the adapter. This list can be loaded from the database usually and it is executed asynchronously (for this example i created dummy data).

_Note: Empty constructor is needed._

```
public class MyObject extends SelectorDataGetter<MyObject> {

    String name;
    long id;

    public MyObject() {
    }

    public MyObject(String s, long x) {
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
    public List<MyObject> getData() {
        return createData();
    }

    private List<MyObject> createData() {
        List<MyObject> list = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            list.add(new MyObject(("Model : " + x), x));
        }
        return list;
    }
}
```

Next we have `.multipleSelection()`. This indicates that the user has the option to select multiple objects. If there are preselected objects then call `.setSelectedObjects(x)`, where x may be `long selectedObject` or `List<Long> selectedObjects` or `Long... selectedValues`. You cannot set multiple preselected objects without using `.multipleSelection()` but you can use it vise versa.

Then we have to call startActivityForResult to show the selector passing as first argument the instance of the activity or fragment . If you want to customise the selector the use the builder `SelectorActivityAttributes` as a second argument like the example below, otherwise pass null. Also override `OnActivityResult` result as below :

```
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ObjectSelectorBuilder.with(this).registerActivityResult(requestCode, resultCode, data);
    }
```

```
private SelectorActivityAttributes getSelectorAttrsForActivity() {
        return SelectorActivityAttributes.instance()
                .setTextNormalColor(R.color.black)
                .setNormalTickColor(R.color.black)
                .setBackArrowColor(R.color.blue)
                .enableSelectedItemsCount()
                .setTextMarginStartPercent(0.04f)
                .setTickMarginEndPercent(0.96f);
    }
```

This are all the attributes that can be used to customise the selector.
**Table 1**

| Attribute         | Type                | Description  |
| :-------------:     |:-------------:      | :-----:       |
| setActivityTheme  |int @StyleRes            |Activity for result theme|
| setToolbarColor   |int @ColorRes            | ToolBar color        |
| setTitleText     | int @StringRes           | Title       |
| setTitleTextSize  | int @DimenRes           | Title text size       |
| setTitleTextColor     | int @ColorRes           | Title text color      |
| setTitleFont | String fontPath | Title font
| setDoneButtonText     | int @StringRes           | Done button text       |
| setDoneButtonTextSize     | int @DimenRes           | Done button text size       |
| setDoneButtonTextColor     | int @ColorRes           | Done button text color       |
| setDoneDrawable     | int @DrawableRes           | Uses drawable instead of done button|
| setClearButtonText     | int @StringRes           | Clear button text       |
| setClearButtonTextSize     | int @DimenRes           | Clear button text size       |
| setClearButtonTextColor     | int @ColorRes           | Clear button text color       |
| setClearDrawable     | int @DrawableRes           | Uses drawable instead of clear button|
| setBackArrowColor     | int @ColorRes           | Home button tint color       |
|setActionButtonsFont | String fontPath | Clear and Done buttons font
| enableSelectedItemsCount     |            | Enables the selected objects count        |

You can also edit the list item of the adapter using the following attributes :

**Table 2**

| Attribute         | Type                | Description  |
| :-------------:    |:-------------:      | :-----:       |
| setListItemHeight  |int @DimenRes            |RecyclerView item height|
| setTextNormalColor   |int @ColorRes            | Normal text color       |
| setTextSelectedColor     | int @ColorRes           | Selected text color       |
| textSize  | int @DimenRes           | Text size       |
| setTextMarginStartPercent     | float margin           |The text margin start as percentage of it's parent|
| setTextFont | String fontPath | The list item text font|
| setListItemBackgroundColor     | int @ColorRes           | RecyclerView item color      |
| setNormalTickColor     | int @ColorRes           | The color of the selected object drawable (tick) |
| setTickDrawable     | int @DrawableRes           | Selected object drawable       |
| setTickMarginEndPercent     | float margin          | The tick margin end as percentage of it's parent|
| setListItemSeparatorColor     | int @ColorRes           | The separator color      |
| setListItemSeparatorHeight     | int @DimenRes           | Separator height       |
| setEnableSearchView     | boolean showSearchView          | Enables searchView|
| setSearchDrawable     | int @DrawableRes           | Search icon|
| setSearchCancelDrawable     | int @DrawableRes           | Search cancel icon      |
| setSearchHintColor     |     int @ColorRes       | searchView hint color|
| setSearchHintText     |     int @StringRes       | searchView hint|
| setSearchTextColor     |     int @ColorRes       | searchView text color|
| setSearchBackgroundColor     |     int @ColorRes       | searchView background color|
| setSearchSpacerBackgroundColor     |     int @ColorRes       | spacer color between toolbar and adapter|
| setSearchCornerRadius     |     int @DimenRes       | searchView cornerRadius|
| setSearchWidth     |     int @DimenRes       | searchView width|
| setSearchHeight     |     int @DimenRes       | searchView height|
| setSearchMargins     |int @DimenRes,int @DimenRes,int @DimenRes,int @DimenRes| searchView margin left, top, right, bottom|
| setSearchFont | String fontPath | searchView text font|

You can also show the same data with the same builder in `BottomSheetDialogFragment` using :

```
public void showBottomFragment(View view) {
        ObjectSelectorBuilder.with(this)
                .setClass(MyObject.class)
                .multipleSelection()
                .setSelectedObjects(getSelectedObjects())
                .showBottomDialogFragment(this, getSelectorAttrsForBottom());
}

private SelectorBDFragmentAttributes getSelectorAttrsForBottom() {
        return SelectorBDFragmentAttributes.instance()
                .setTextNormalColor(R.color.black)
                .setNormalTickColor(R.color.black)
                .enableSelectedItemsCount()
                .setTextMarginStartPercent(0.04f)
                .setTickMarginEndPercent(0.96f);
}
```
_Note: Do not create the builder in `onCreate()` method of the activity or fragment if `savedInstanceState` is not null, or the fragment will be inflated every time the method gets called._

The attributes for the fragment `SelectorBDFragmentAttributes` are almost the same as **Table1** (except the `homeButton`) and the **Table2**.

![Fragment](https://github.com/emilioscy/SelectorView-Android/blob/master/object_selector_fragment.gif)

### 2. ValuesSelectorBuilder

This functionality is very smiliar to the first one except that now we pass string values instead of object.
The callback is of type `OnValuesSelectorListener`.

```
public void showActivityForResult(View view) {
        ValuesSelectorBuilder.with(this)
                .setDisplayValues(getDisplayedValues())
                .multipleSelection()
                .setSelectedValues(getSelectedValues())
                .startActivityForResult(this, getSelectorAttrsForActivity());
}

private List<String> getSelectedValues() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Value 2");
        stringList.add("Value 4");
        return stringList;
}

private List<String> getDisplayedValues() {
        List<String> array = new ArrayList<>();
        for (int x = 0; x < 200; x++) {
            array.add("Value " + x);
        }
        return array;
}

@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ValuesSelectorBuilder.with(this).registerActivityResult(requestCode, resultCode, data);
}

```

### 3. SelectorView

The selectorView includes the RecyclerViewAdapter and the SearchView. You can use objects or values as well, preselect values, setMultipleSelection, selectionCounter and customise the layout using the Table2 attributes inside xml.

```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:selector="http://schemas.android.com/apk/res-auto">

    <com.emcy.selector.SelectorView
         android:id="@+id/selectorView"
         android:layout_width="250dp"
         android:layout_height="400dp"
         selector:isMultipleSelection="true"
         selector:enableSearchView="true"
         selector:searchDrawable="@drawable/ic_search_icon"
         selector:searchCancelDrawable="@drawable/ic_clear_search"
         selector:searchHintColor="@color/colorPrimary"
         selector:searchHintText="@string/search_hint"
         selector:searchTextColor="@color/colorPrimary"
         selector:searchBackgroundColor="@color/searchViewColor"
         selector:searchSpacerBackgroundColor="@color/white"
         selector:searchCornerRadius="@dimen/searchViewCornerRadius"
         selector:objectClass="@string/full_package_class_name"
         selector:listItemHeight="@dimen/list_item_height"
         selector:textNormalColor="@color/colorPrimary"
         selector:textSelectedColor="@color/colorPrimary"
         selector:textSize="@dimen/text_size"
         selector:textMarginStartPercent="0.04"
         selector:listItemBackgroundColor="@color/white"
         selector:normalTickColor="@color/black"
         selector:tickDrawable="@drawable/ic_done_blue"
         selector:tickMarginEndPercent="0.96"
         selector:listItemSeparatorColor="@color/colorPrimary"
         selector:listItemSeparatorHeight="@dimen/separator_height"
         selector:textFont="fonts/Roboto-BoldItalic.ttf"
         selector:searchTextFont="fonts/Roboto-ThinItalic.ttf"
         app:layout_constraintLeft_toLeftOf="parent"
         app:layout_constraintRight_toRightOf="parent"
         app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>

```

Then init the searchView :

```
private void initSearchView() {
        SelectorView selector = (SelectorView) findViewById(R.id.selectorView);
        selector.setOnObjectSelectorListener(this);
        selector.setSelectedObject(3);
}
```

Result :

![SelectorView](https://github.com/emilioscy/SelectorView-Android/blob/master/selector_view_example.png)

If you want to use with string values then replace the line: 

```
selector:objectClass="@string/full_package_class_name"
```

with :
```
selector:withValues="true"
```

and then initialise it in the class:

```
 private void initSearchView() {
        SelectorView selector = (SelectorView) findViewById(R.id.selectorView);
        selector.setOnValueSelectorListener(this);
        selector.setDisplayedValues(getDisplayedValues());
        selector.setSelectedValue("Value 5");
}

private List<String> getDisplayedValues() {
        List<String> array = new ArrayList<>();
        for (int x = 0; x < 200; x++) {
            array.add("Value " + x);
        }
        return array;
}
```

Other methods of SearchView:

| Method         | Argument|Description  |
| :-------------:     |:-------------:      | :-----:|
| clearSelection()  ||clear the selected objects or values|
| getSelected()     | |returns the selected objects or values with the callback|
| setQuery()    |String query |set manually search query       |
| setClass()    |int @StringRes |set manually the object class       |











