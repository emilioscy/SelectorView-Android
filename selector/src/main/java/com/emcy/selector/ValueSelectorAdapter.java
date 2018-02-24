package com.emcy.selector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The recyclerView adapter for the ValueSelector
 */
class ValueSelectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<String> items;
    private List<String> itemsCopy;
    private String selectedValue;
    private int lastSelectedPosition = -1;
    private List<String> selectedValues;
    private OnValuesSelectorListener listener;
    private SelectorAttributes selectorAttributes;
    private boolean isMultipleSelection;
    private AsyncTask<Void, Void, List<String>> searchAsync;

    void setSelectedValue(String value) {
        this.selectedValue = value;
        notifyDataSetChanged();
    }

    void setSelectedValues(List<String> selectedValues) {
        isMultipleSelection = true;
        this.selectedValues = selectedValues;
        notifyDataSetChanged();
    }

    ValueSelectorAdapter(List<String> items, OnValuesSelectorListener listener, String selectedValue, SelectorAttributes selectorAttributes, Context context) {
        this.items = items;
        this.listener = listener;
        this.selectedValue = selectedValue;
        this.selectorAttributes = selectorAttributes;
        this.context = context;
        this.itemsCopy = new ArrayList<>();
        this.itemsCopy.addAll(items);
    }

    ValueSelectorAdapter(List<String> items, OnValuesSelectorListener listener, List<String> selectedValues, SelectorAttributes selectorAttributes, Context context) {
        isMultipleSelection = true;
        this.items = items;
        this.listener = listener;
        this.selectorAttributes = selectorAttributes;
        this.context = context;
        if (selectedValues == null) {
            this.selectedValues = new ArrayList<>();
        } else {
            this.selectedValues = selectedValues;
        }
        this.itemsCopy = new ArrayList<>();
        this.itemsCopy.addAll(items);
    }

    List<String> getSelectedValues() {
        return selectedValues;
    }

    void refresh(List<String> newList) {
        items.clear();
        items.addAll(newList);
        notifyDataSetChanged();
        itemsCopy = new ArrayList<>();
        itemsCopy.addAll(newList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selector, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String model = items.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.bindData(model);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void clearSelection() {
        selectedValues.clear();
        notifyDataSetChanged();
        if (listener != null) listener.onValuesUpdated(new ArrayList<String>());
    }

    private void setSearchedItems(List<String> searchedItems) {
        items.clear();
        items.addAll(searchedItems);
        notifyDataSetChanged();
    }

    void getFilterAsync(final String query) {
        if (searchAsync != null) {
            searchAsync.cancel(true);
        }
        searchAsync = new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                List<String> searchedItems = new ArrayList<>();
                for (String value : itemsCopy) {
                    if (value.toLowerCase().contains(query)) {
                        searchedItems.add(value);
                    }
                }
                return searchedItems;
            }

            @Override
            protected void onPostExecute(List<String> values) {
                super.onPostExecute(values);
                setSearchedItems(values);
            }
        }.executeOnExecutor(new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()));
    }

    void cancelAsync() {
        if (searchAsync != null) {
            searchAsync.cancel(true);
        }
    }

    void onSearchClosed() {
        items.clear();
        items.addAll(itemsCopy);
        notifyDataSetChanged();
    }

    void setCoyItems(ArrayList<String> values) {
        itemsCopy = new ArrayList<>();
        itemsCopy.addAll(values);
    }

    List<String> getItemsCopy() {
        return itemsCopy != null ? itemsCopy : null;
    }


    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;
        View selectable, backgroundView, separatorView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            selectable = itemView.findViewById(R.id.selectable);
            backgroundView = itemView.findViewById(R.id.backgroundView);
            separatorView = itemView.findViewById(R.id.separatorView);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                String currentClickedModel = items.get(getAdapterPosition());
                if (isMultipleSelection) {
                    if (selectedValues.contains(currentClickedModel)) {
                        selectedValues.remove(currentClickedModel);
                    } else {
                        selectedValues.add(currentClickedModel);
                    }
                    notifyItemChanged(getAdapterPosition());
                    listener.onValuesUpdated(selectedValues);
                } else {
                    selectedValue = currentClickedModel;
                    if (lastSelectedPosition != -1) {
                        notifyItemChanged(lastSelectedPosition);
                    }
                    lastSelectedPosition = getAdapterPosition();
                    notifyItemChanged(getAdapterPosition());
                    listener.onValueClick(selectedValue);
                    listener = null;
                }
            }

        }

        void bindData(String value) {
            if (value != null) {
                setAttrs();
                textView.setText(value);
                if (isMultipleSelection) {
                    if (selectedValues.contains(value)) {
                        imageView.setVisibility(View.VISIBLE);
                        lastSelectedPosition = getAdapterPosition();
                        setSelected();
                    } else {
                        imageView.setVisibility(View.GONE);
                        setUnselected();
                    }
                } else {
                    if (value.equals(selectedValue)) {
                        imageView.setVisibility(View.VISIBLE);
                        lastSelectedPosition = getAdapterPosition();
                        setSelected();
                    } else {
                        imageView.setVisibility(View.GONE);
                        setUnselected();
                    }
                }
                selectable.setOnClickListener(this);
            } else {
                items = new ArrayList<>();
                notifyDataSetChanged();
                Toast.makeText(context, context.getString(R.string.abstract_class_not_properly_implemented), Toast.LENGTH_SHORT).show();
            }
        }

        private void setUnselected() {
            if (selectorAttributes != null && selectorAttributes.getTextNormalColor() != -1) {
                textView.setTextColor(getColor(selectorAttributes.getTextNormalColor()));
            } else {
                textView.setTextColor(getColor(R.color.colorPrimary));
            }
        }

        private void setSelected() {
            if (selectorAttributes != null) {
                if (selectorAttributes.getTextSelectedColor() != -1) {
                    getColor(selectorAttributes.getTextSelectedColor());
                    textView.setTextColor(getColor(selectorAttributes.getTextSelectedColor()));
                } else {
                    textView.setTextColor(getColor(R.color.colorPrimary));
                }
                if (selectorAttributes.getTickDrawable() != -1) {
                    imageView.setImageResource(selectorAttributes.getTickDrawable());
                } else if (selectorAttributes.getNormalTickColor() != -1) {
                    imageView.setColorFilter(getColor(selectorAttributes.getNormalTickColor()), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }

        void setAttrs() {
            if (getAdapterPosition() == 0) {
                separatorView.setVisibility(View.GONE);
            } else {
                separatorView.setVisibility(View.VISIBLE);
            }
            if (selectorAttributes != null) {
                ConstraintLayout constrainLayout = itemView.findViewById(R.id.constrain);
                ViewGroup.LayoutParams layoutParams = constrainLayout.getLayoutParams();
                setConstrainsSet(selectorAttributes);
                if (selectorAttributes.getListItemHeight() != -1) {
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(layoutParams.width,
                            context.getResources().getDimensionPixelSize(selectorAttributes.getListItemHeight()));
                    itemView.setLayoutParams(params);
                }
                if (selectorAttributes.getTextSize() != -1) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            context.getResources().getDimensionPixelSize(selectorAttributes.getTextSize()));
                }
                if (selectorAttributes.getListItemBackgroundColor() != -1) {
                    backgroundView.setBackgroundColor(getColor(selectorAttributes.getListItemBackgroundColor()));
                }
                if (selectorAttributes.getListItemSeparatorColor() != -1) {
                    separatorView.setBackgroundColor(getColor(selectorAttributes.getListItemSeparatorColor()));
                }
                if (selectorAttributes.getListItemSeparatorHeight() != -1) {
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            context.getResources().getDimensionPixelSize(selectorAttributes.getListItemSeparatorHeight()));
                    separatorView.setLayoutParams(params);
                }
            }
        }

        private void setConstrainsSet(SelectorAttributes selectorAttributes) {
            try {
                if (selectorAttributes.getTextMarginStartPercent() != 0) {
                    ConstraintSet textConstraintSet = new ConstraintSet();
                    ConstraintLayout constraintLayout = itemView.findViewById(R.id.constrain);
                    textConstraintSet.clone(constraintLayout);
                    textConstraintSet.setGuidelinePercent(R.id.textPercentGuideLine, selectorAttributes.getTextMarginStartPercent());
                    textConstraintSet.applyTo(constraintLayout);
                }
                if (selectorAttributes.getTickMarginEndPercent() != 0) {
                    ConstraintSet tickConstraintSet = new ConstraintSet();
                    tickConstraintSet.clone((ConstraintLayout) itemView.findViewById(R.id.constrain));
                    tickConstraintSet.setGuidelinePercent(R.id.tickPercentGuideLine, selectorAttributes.getTickMarginEndPercent());
                    tickConstraintSet.applyTo((ConstraintLayout) itemView.findViewById(R.id.constrain));
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        private int getColor(int colorRes) {
            try {
                return ContextCompat.getColor(context, colorRes);
            } catch (Resources.NotFoundException e) {
                return 0;
            }
        }
    }
}

