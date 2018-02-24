package com.emcy.selector;

public class SelectorBuilder {

//    public static ValuesSelector withValues(OnValuesSelectorListener listener) {
//        return ValuesSelector.instance(listener);
//    }
//
//    public static ObjectSelector withObject(OnObjectSelectorListener listener) {
//        return ObjectSelector.instance(listener);
//    }
//
//    public static class ValuesSelector {
//
//        private static ValuesSelector instance;
//        private OnValuesSelectorListener listener;
//        private static final int REQUEST_CODE = 55;
//        private List<String> displayedValues;
//        private List<String> selectedValues;
//        private String selectedValue;
//        private boolean multipleSelection;
//        private SelectorActivityAttributes activityAttrs;
//        private SelectorBDFragmentAttributes bottomAttrs;
//
//        ValuesSelector(OnValuesSelectorListener listener) {
//            this.listener = listener;
//        }
//
//        private static ValuesSelector instance(OnValuesSelectorListener listener) {
//            return instance = new ValuesSelector(listener);
//        }
//
//        public ValuesSelector setDisplayValues(List<String> values) {
//            this.displayedValues = values;
//            return instance;
//        }
//
//        public ValuesSelector setDisplayValues(String... values) {
//            this.displayedValues = Arrays.asList(values);
//            return instance;
//        }
//
//        public ValuesSelector multipleSelection() {
//            this.multipleSelection = true;
//            return instance;
//        }
//
//        public ValuesSelector setSelectedValue(String value) {
//            if (multipleSelection) {
//                this.selectedValues = Collections.singletonList(value);
//            } else {
//                this.selectedValue = value;
//            }
//            return instance;
//        }
//
//        public ValuesSelector setSelectedValues(List<String> values) {
//            if (!multipleSelection) {
//                throw new IllegalStateException("You cannot set multiple selected values  when " +
//                        "multipleSelection is false");
//            }
//            this.selectedValues = values;
//            return instance;
//        }
//
//        public ValuesSelector setSelectedValues(String... selectedValues) {
//            if (!multipleSelection) {
//                throw new IllegalStateException("You cannot set multiple selected values  when " +
//                        "multipleSelection is false");
//            }
//            this.selectedValues = Arrays.asList(selectedValues);
//            return instance;
//        }
//
//        private void checkIntentSize(Activity activity, Intent intent) {
//            final Parcel testParcel = Parcel.obtain();
//            try {
//                testParcel.writeParcelable(intent, 0);
//                if ((testParcel.dataSize() / 1000) > 500) {
//                    handleLargeData(activity, displayedValues, true);
//                } else {
//                    activity.startActivityForResult(intent, REQUEST_CODE);
//                }
//            } finally {
//                testParcel.recycle();
//            }
//        }
//
//        private void checkIntentSize(Fragment fragment, Intent intent) {
//            final Parcel testParcel = Parcel.obtain();
//            try {
//                testParcel.writeParcelable(intent, 0);
//                if ((testParcel.dataSize() / 1000) > 500) {
//                    handleLargeData(fragment.getActivity(), displayedValues, false);
//                } else {
//                    fragment.getActivity().startActivityForResult(intent, REQUEST_CODE);
//                }
//            } finally {
//                testParcel.recycle();
//            }
//        }
//
//        private void handleLargeData(Activity activity, List<String> displayedValues, boolean b) {
//            Intent nIntent = new Intent(activity, SelectorActivity.class);
//            setIntentData(nIntent, b);
//            nIntent.putExtra(Constants.LARGE_DATA, true);
//            saveValuesInSharedPrefs(activity, displayedValues, nIntent);
//        }
//
//        private void saveValuesInSharedPrefs(final Activity activity, final List<String> displayedValues, final Intent intent) {
//
//            ProgressBar progressBar = new ProgressBar(activity);
//            final AlertDialog show = new AlertDialog.Builder(activity)
//                    .setTitle(R.string.please_wait)
//                    .setCancelable(false)
//                    .setView(progressBar)
//                    .show();
//
//            new AsyncTask<Void, Void, Boolean>() {
//                @Override
//                protected Boolean doInBackground(Void... voids) {
//                    JSONArray jsonArray = new JSONArray();
//                    for (int x = 0; x < displayedValues.size(); x++) {
//                        try {
//                            jsonArray.put(x, displayedValues.get(x));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            return false;
//                        }
//                    }
//                    activity.getSharedPreferences(Constants.SELECTOR_SHARED_PREFS, Context.MODE_PRIVATE)
//                            .edit().putString(DISPLAYED_VALUES, jsonArray.toString())
//                            .apply();
//                    return true;
//                }
//
//                @Override
//                protected void onPostExecute(Boolean aBoolean) {
//                    super.onPostExecute(aBoolean);
//                    show.cancel();
//                    if (aBoolean) {
//                        activity.startActivityForResult(intent, REQUEST_CODE);
//                    } else {
//                        Toast.makeText(activity, activity.getString(R.string.an_error_occurred), Toast.LENGTH_LONG).show();
//                    }
//                }
//            }.executeOnExecutor(new ThreadPoolExecutor(
//                    Runtime.getRuntime().availableProcessors(),
//                    Runtime.getRuntime().availableProcessors(),
//                    60,
//                    TimeUnit.SECONDS,
//                    new LinkedBlockingQueue<Runnable>()));
//        }
//
//        public void startActivityForResult(Activity activity, SelectorActivityAttributes attributes) {
//            if (displayedValues != null) {
//                this.activityAttrs = attributes;
//                Intent intent = new Intent(activity, SelectorActivity.class);
//                setIntentData(intent, true);
//                checkIntentSize(activity, intent);
//            } else {
//                Toast.makeText(activity, activity.getString(R.string.have_to_set_values), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        public void startActivityForResult(Fragment fragment, SelectorActivityAttributes attributes) {
//            if (displayedValues != null) {
//                this.activityAttrs = attributes;
//                Intent intent = new Intent(fragment.getActivity(), SelectorActivity.class);
//                setIntentData(intent, false);
//                checkIntentSize(fragment, intent);
//            } else {
//                Toast.makeText(fragment.getActivity(), fragment.getString(R.string.have_to_set_values), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        public void showBottomDialogFragment(FragmentActivity activity, SelectorBDFragmentAttributes attributes) {
//            if (displayedValues != null) {
//                this.bottomAttrs = attributes;
//                Bundle bundle = new Bundle();
//                setBundleData(bundle, true);
//                BottomSheetDialogFragment selectorSheet = new SelectorBottomSheet();
//                selectorSheet.setArguments(bundle);
//                selectorSheet.show(activity.getSupportFragmentManager(), selectorSheet.getTag());
//            } else {
//                Toast.makeText(activity, activity.getString(R.string.have_to_set_values), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        public void showBottomDialogFragment(Fragment fragment, SelectorBDFragmentAttributes attributes) {
//            if (displayedValues != null) {
//                this.bottomAttrs = attributes;
//                Bundle bundle = new Bundle();
//                setBundleData(bundle, false);
//                BottomSheetDialogFragment selectorSheet = new SelectorBottomSheet();
//                selectorSheet.setArguments(bundle);
//                selectorSheet.show(fragment.getActivity().getSupportFragmentManager(), selectorSheet.getTag());
//            } else {
//                Toast.makeText(fragment.getActivity(), fragment.getString(R.string.have_to_set_values), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        private void setIntentData(Intent intentData, boolean b) {
//            intentData.putExtra(IS_OBJECT_SELECTOR, false);
//            intentData.putExtra(DISPLAYED_VALUES, (Serializable) displayedValues);
//            intentData.putExtra(IS_FROM_ACTIVITY, b);
//            intentData.putExtra(IS_MULTIPLE_SELECTION, multipleSelection);
//            if (activityAttrs != null) {
//                intentData.putExtra(SELECTOR_ATTRS, activityAttrs);
//            }
//            if (multipleSelection) {
//                if (selectedValues == null) {
//                    selectedValues = new ArrayList<>();
//                }
//                intentData.putExtra(SELECTED_VALUES, (Serializable) selectedValues);
//            } else {
//                if (selectedValues != null)
//                    throw new IllegalStateException("You cannot select multiple values " +
//                            "when multipleSelection is false");
//                intentData.putExtra(SELECTED_VALUE, selectedValue);
//            }
//        }
//
//        private void setBundleData(Bundle bundle, boolean b) {
//            bundle.putBoolean(IS_OBJECT_SELECTOR, false);
//            bundle.putSerializable(DISPLAYED_VALUES, (Serializable) displayedValues);
//            bundle.putBoolean(IS_FROM_ACTIVITY, b);
//            bundle.putBoolean(IS_MULTIPLE_SELECTION, multipleSelection);
//            if (bottomAttrs != null) {
//                bundle.putParcelable(SELECTOR_ATTRS, bottomAttrs);
//            }
//            if (multipleSelection) {
//                if (selectedValues == null) {
//                    selectedValues = new ArrayList<>();
//                }
//                bundle.putSerializable(SELECTED_VALUES, (Serializable) selectedValues);
//            } else {
//                if (selectedValues != null)
//                    throw new IllegalStateException("You cannot select multiple values " +
//                            "when multipleSelection is false");
//                bundle.putString(SELECTED_VALUE, selectedValue);
//            }
//        }
//
//        @SuppressWarnings("unchecked")
//        public void registerActivityResult(int requestCode, int resultCode, Intent data) {
//            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//                boolean isMultipleSelection = data.getBooleanExtra(IS_MULTIPLE_SELECTION, false);
//                if (isMultipleSelection) {
//                    ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra(SELECTED_VALUES);
//                    if (listener != null) {
//                        listener.onValuesSelected(list);
//                    }
//                } else {
//                    if (listener != null)
//                        listener.onValueClick(data.getStringExtra(SELECTED_VALUE));
//                }
//            }
//        }
//    }
//
//    public static class ObjectSelector {
//        private static ObjectSelector instance;
//        private static final int REQUEST_CODE = 55;
//        private OnObjectSelectorListener listener;
//        private Class tClass;
//        private List<Long> selectedObjects;
//        private long selectedObject = -1;
//        private boolean multipleSelection;
//        private SelectorActivityAttributes activityAttrs;
//        private SelectorBDFragmentAttributes bottomAttrs;
//
//        ObjectSelector(OnObjectSelectorListener listener) {
//            this.listener = listener;
//        }
//
//        private static ObjectSelector instance(OnObjectSelectorListener listener) {
//            return instance = new ObjectSelector(listener);
//        }
//
//        public ObjectSelector setClass(Class<? extends SelectorDataGetter> tClass) {
//            this.tClass = tClass;
//            return instance;
//        }
//
//        public ObjectSelector multipleSelection() {
//            this.multipleSelection = true;
//            return instance;
//        }
//
//        public ObjectSelector setSelectedObject(long selectedObject) {
//            if (multipleSelection) {
//                this.selectedObjects = new ArrayList<>();
//                this.selectedObjects.add(selectedObject);
//            } else {
//                this.selectedObject = selectedObject;
//            }
//            return instance;
//        }
//
//        public ObjectSelector setSelectedObjects(List<Long> selectedObjects) {
//            if (!multipleSelection) {
//                throw new IllegalStateException("You cannot set multiple selected objects  when" +
//                        "multipleSelection is false");
//            }
//            this.selectedObjects = selectedObjects;
//            return instance;
//        }
//
//        public ObjectSelector setSelectedValues(Long... selectedValues) {
//            if (!multipleSelection) {
//                throw new IllegalStateException("You cannot set multiple selected objects  when " +
//                        "multipleSelection is false");
//            }
//            this.selectedObjects = Arrays.asList(selectedValues);
//            return instance;
//        }
//
//        public void startActivityForResult(Activity activity, SelectorActivityAttributes attributes) {
//            this.activityAttrs = attributes;
//            Intent intent = new Intent(activity, SelectorActivity.class);
//            if (tClass != null) {
//                setIntentData(intent, true);
//                activity.startActivityForResult(intent, REQUEST_CODE);
//            } else {
//                Toast.makeText(activity, activity.getString(R.string.object_class_null), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        public void startActivityForResult(Fragment fragment, SelectorActivityAttributes attributes) {
//            this.activityAttrs = attributes;
//            Intent intent = new Intent(fragment.getActivity(), SelectorActivity.class);
//            if (tClass != null) {
//                setIntentData(intent, false);
//                fragment.getActivity().startActivityForResult(intent, REQUEST_CODE);
//            } else {
//                Toast.makeText(fragment.getActivity(), fragment.getString(R.string.object_class_null), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        public void showBottomDialogFragment(FragmentActivity activity, SelectorBDFragmentAttributes attributes) {
//            if (tClass != null) {
//                Bundle bundle = new Bundle();
//                this.bottomAttrs = attributes;
//                setBundleData(bundle, true);
//                BottomSheetDialogFragment selectorSheet = new SelectorBottomSheet();
//                selectorSheet.setArguments(bundle);
//                selectorSheet.show(activity.getSupportFragmentManager(), selectorSheet.getTag());
//            } else {
//                Toast.makeText(activity, activity.getString(R.string.object_class_null), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        public void showBottomDialogFragment(Fragment fragment, SelectorBDFragmentAttributes attributes) {
//            if (tClass != null) {
//                Bundle bundle = new Bundle();
//                this.bottomAttrs = attributes;
//                setBundleData(bundle, false);
//                BottomSheetDialogFragment selectorSheet = new SelectorBottomSheet();
//                selectorSheet.setArguments(bundle);
//                selectorSheet.show(fragment.getActivity().getSupportFragmentManager(), selectorSheet.getTag());
//            } else {
//                Toast.makeText(fragment.getActivity(), fragment.getString(R.string.object_class_null), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        private void setIntentData(Intent intentData, boolean b) {
//            intentData.putExtra(IS_OBJECT_SELECTOR, true);
//            intentData.putExtra(MODEL_CLASS, tClass.getName());
//            intentData.putExtra(IS_FROM_ACTIVITY, b);
//            intentData.putExtra(IS_MULTIPLE_SELECTION, multipleSelection);
//            if (activityAttrs != null) {
//                intentData.putExtra(SELECTOR_ATTRS, activityAttrs);
//            }
//            if (multipleSelection) {
//                if (selectedObjects == null) {
//                    selectedObjects = new ArrayList<>();
//                }
//                intentData.putExtra(SELECTED_IDS, (Serializable) selectedObjects);
//            } else {
//                if (selectedObjects != null)
//                    throw new IllegalStateException("You cannot select multiple objects " +
//                            "when multipleSelection is false");
//                intentData.putExtra(SELECTED_ID, selectedObject);
//            }
//        }
//
//        private void setBundleData(Bundle bundle, boolean b) {
//            bundle.putBoolean(IS_OBJECT_SELECTOR, true);
//            bundle.putString(MODEL_CLASS, tClass.getName());
//            bundle.putBoolean(IS_FROM_ACTIVITY, b);
//            bundle.putBoolean(IS_MULTIPLE_SELECTION, multipleSelection);
//            if (bottomAttrs != null) {
//                bundle.putParcelable(SELECTOR_ATTRS, bottomAttrs);
//            }
//            if (multipleSelection) {
//                if (selectedObjects == null) {
//                    selectedObjects = new ArrayList<>();
//                }
//                bundle.putSerializable(SELECTED_IDS, (Serializable) selectedObjects);
//            } else {
//                if (selectedObjects != null)
//                    throw new IllegalStateException("You cannot select multiple objects " +
//                            "when multipleSelection is false");
//                bundle.putLong(SELECTED_ID, selectedObject);
//            }
//        }
//
//        @SuppressWarnings("unchecked")
//        public void registerActivityResult(int requestCode, int resultCode, Intent data) {
//            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//                boolean isMultipleSelection = data.getBooleanExtra(IS_MULTIPLE_SELECTION, false);
//                if (isMultipleSelection) {
//                    ArrayList<Long> list = (ArrayList<Long>) data.getSerializableExtra(SELECTED_IDS);
//                    if (listener != null) listener.onObjectsSelected(list);
//                } else {
//                    if (listener != null)
//                        listener.onObjectClick(data.getLongExtra(SELECTED_ID, -1));
//                }
//            }
//        }
//    }

}
