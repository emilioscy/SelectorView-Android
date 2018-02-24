package com.emcy.selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.emcy.selector.Constants.IS_FROM_ACTIVITY;
import static com.emcy.selector.Constants.IS_MULTIPLE_SELECTION;
import static com.emcy.selector.Constants.IS_OBJECT_SELECTOR;
import static com.emcy.selector.Constants.MODEL_CLASS;
import static com.emcy.selector.Constants.SELECTED_ID;
import static com.emcy.selector.Constants.SELECTED_IDS;
import static com.emcy.selector.Constants.SELECTOR_ATTRS;

/**
 * Static builder for showing object in recycler adapter.
 * Pass SelectorActivityAttributes or SelectorBDFragmentAttributes on
 * startActivityForResult or showBottomDialogFragment respectively to customise the views
 */

public class ObjectSelectorBuilder {

    private static ObjectSelectorBuilder instance;
    private static final int REQUEST_CODE = 55;
    private OnObjectSelectorListener listener;
    private Class tClass;
    private List<Long> selectedObjects;
    private long selectedObject = -1;
    private boolean multipleSelection;
    private SelectorActivityAttributes activityAttrs;
    private SelectorBDFragmentAttributes bottomAttrs;

    ObjectSelectorBuilder(OnObjectSelectorListener listener) {
        this.listener = listener;
    }

    public static ObjectSelectorBuilder with(OnObjectSelectorListener listener) {
        return instance = new ObjectSelectorBuilder(listener);
    }

    public ObjectSelectorBuilder setClass(Class<? extends SelectorDataGetter> tClass) {
        this.tClass = tClass;
        return instance;
    }

    public ObjectSelectorBuilder multipleSelection() {
        this.multipleSelection = true;
        return instance;
    }

    public ObjectSelectorBuilder setSelectedObject(long selectedObject) {
        if (multipleSelection) {
            this.selectedObjects = new ArrayList<>();
            this.selectedObjects.add(selectedObject);
        } else {
            this.selectedObject = selectedObject;
        }
        return instance;
    }

    public ObjectSelectorBuilder setSelectedObjects(List<Long> selectedObjects) {
        if (!multipleSelection) {
            throw new IllegalStateException("You cannot set multiple selected objects  when" +
                    "multipleSelection is false");
        }
        this.selectedObjects = selectedObjects;
        return instance;
    }

    public ObjectSelectorBuilder setSelectedValues(Long... selectedValues) {
        if (!multipleSelection) {
            throw new IllegalStateException("You cannot set multiple selected objects  when " +
                    "multipleSelection is false");
        }
        this.selectedObjects = Arrays.asList(selectedValues);
        return instance;
    }

    public void startActivityForResult(Activity activity, SelectorActivityAttributes attributes) {
        this.activityAttrs = attributes;
        Intent intent = new Intent(activity, SelectorActivity.class);
        if (tClass != null) {
            setIntentData(intent, true);
            activity.startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(activity, activity.getString(R.string.object_class_null), Toast.LENGTH_SHORT).show();
        }
    }

    public void startActivityForResult(Fragment fragment, SelectorActivityAttributes attributes) {
        this.activityAttrs = attributes;
        Intent intent = new Intent(fragment.getActivity(), SelectorActivity.class);
        if (tClass != null) {
            setIntentData(intent, false);
            fragment.getActivity().startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(fragment.getActivity(), fragment.getString(R.string.object_class_null), Toast.LENGTH_SHORT).show();
        }
    }

    public void showBottomDialogFragment(FragmentActivity activity, SelectorBDFragmentAttributes attributes) {
        if (tClass != null) {
            Bundle bundle = new Bundle();
            this.bottomAttrs = attributes;
            setBundleData(bundle, true);
            BottomSheetDialogFragment selectorSheet = new SelectorBottomSheet();
            selectorSheet.setArguments(bundle);
            selectorSheet.show(activity.getSupportFragmentManager(), selectorSheet.getTag());
        } else {
            Toast.makeText(activity, activity.getString(R.string.object_class_null), Toast.LENGTH_SHORT).show();
        }
    }

    public void showBottomDialogFragment(Fragment fragment, SelectorBDFragmentAttributes attributes) {
        if (tClass != null) {
            Bundle bundle = new Bundle();
            this.bottomAttrs = attributes;
            setBundleData(bundle, false);
            BottomSheetDialogFragment selectorSheet = new SelectorBottomSheet();
            selectorSheet.setArguments(bundle);
            selectorSheet.show(fragment.getActivity().getSupportFragmentManager(), selectorSheet.getTag());
        } else {
            Toast.makeText(fragment.getActivity(), fragment.getString(R.string.object_class_null), Toast.LENGTH_SHORT).show();
        }
    }

    private void setIntentData(Intent intentData, boolean b) {
        intentData.putExtra(IS_OBJECT_SELECTOR, true);
        intentData.putExtra(MODEL_CLASS, tClass.getName());
        intentData.putExtra(IS_FROM_ACTIVITY, b);
        intentData.putExtra(IS_MULTIPLE_SELECTION, multipleSelection);
        if (activityAttrs != null) {
            intentData.putExtra(SELECTOR_ATTRS, activityAttrs);
        }
        if (multipleSelection) {
            if (selectedObjects == null) {
                selectedObjects = new ArrayList<>();
            }
            intentData.putExtra(SELECTED_IDS, (Serializable) selectedObjects);
        } else {
            if (selectedObjects != null)
                throw new IllegalStateException("You cannot select multiple objects " +
                        "when multipleSelection is false");
            intentData.putExtra(SELECTED_ID, selectedObject);
        }
    }

    private void setBundleData(Bundle bundle, boolean b) {
        bundle.putBoolean(IS_OBJECT_SELECTOR, true);
        bundle.putString(MODEL_CLASS, tClass.getName());
        bundle.putBoolean(IS_FROM_ACTIVITY, b);
        bundle.putBoolean(IS_MULTIPLE_SELECTION, multipleSelection);
        if (bottomAttrs != null) {
            bundle.putParcelable(SELECTOR_ATTRS, bottomAttrs);
        }
        if (multipleSelection) {
            if (selectedObjects == null) {
                selectedObjects = new ArrayList<>();
            }
            bundle.putSerializable(SELECTED_IDS, (Serializable) selectedObjects);
        } else {
            if (selectedObjects != null)
                throw new IllegalStateException("You cannot select multiple objects " +
                        "when multipleSelection is false");
            bundle.putLong(SELECTED_ID, selectedObject);
        }
    }

    @SuppressWarnings("unchecked")
    public void registerActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            boolean isMultipleSelection = data.getBooleanExtra(IS_MULTIPLE_SELECTION, false);
            if (isMultipleSelection) {
                ArrayList<Long> list = (ArrayList<Long>) data.getSerializableExtra(SELECTED_IDS);
                if (listener != null) listener.onObjectsSelected(list);
            } else {
                if (listener != null)
                    listener.onObjectClick(data.getLongExtra(SELECTED_ID, -1));
            }
        }
    }
    
}
