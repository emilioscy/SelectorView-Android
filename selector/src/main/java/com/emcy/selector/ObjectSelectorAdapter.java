package com.emcy.selector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * The recyclerView adapter for the ObjectSelector
 */
class ObjectSelectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<SelectorDataGetter> items;
    private List<SelectorDataGetter> itemsCopy;
    private long selectedId;
    private int lastSelectedPosition = -1;
    private List<Long> selectedIds;
    private OnObjectSelectorListener listener;
    private SelectorAttributes selectorAttributes;
    private boolean isMultipleSelection;
    private AsyncTask<Void, Void, List<SelectorDataGetter>> searchAsync;

    void setSelectedObject(long id) {
        this.selectedId = id;
        notifyDataSetChanged();
    }

    void setSelectedObjects(List<Long> selectedIds) {
        isMultipleSelection = true;
        this.selectedIds = new ArrayList<>();
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }

    ObjectSelectorAdapter(OnObjectSelectorListener listener, long selectedId, SelectorAttributes selectorAttributes, Context context) {
        items = new ArrayList<>();
        this.listener = listener;
        this.selectedId = selectedId;
        this.selectorAttributes = selectorAttributes;
        this.context = context;
        this.itemsCopy = new ArrayList<>();
    }

    ObjectSelectorAdapter(OnObjectSelectorListener listener, List<Long> selectedIds, SelectorAttributes selectorAttributes, Context context) {
        isMultipleSelection = true;
        items = new ArrayList<>();
        this.listener = listener;
        this.selectorAttributes = selectorAttributes;
        this.context = context;
        if (selectedIds == null) {
            this.selectedIds = new ArrayList<>();
        } else {
            this.selectedIds = selectedIds;
        }
        this.itemsCopy = new ArrayList<>();
    }

    List<Long> getSelectedIds() {
        return selectedIds;
    }

    void refresh(List<SelectorDataGetter> newList) {
        items.clear();
        items.addAll(newList);
        notifyDataSetChanged();
        itemsCopy = newList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selector, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SelectorDataGetter model = items.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.bindData(model);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void clearSelection() {
        selectedIds.clear();
        notifyDataSetChanged();
        if (listener != null) listener.onObjectsUpdated(new ArrayList<Long>());
    }

    private void setSearchedItems(List<SelectorDataGetter> searchedItems) {
        items.clear();
        items.addAll(searchedItems);
        notifyDataSetChanged();
    }

    void getFilterAsync(final String query) {
        if (searchAsync != null) {
            searchAsync.cancel(true);
        }
        searchAsync = new AsyncTask<Void, Void, List<SelectorDataGetter>>() {
            @Override
            protected List<SelectorDataGetter> doInBackground(Void... voids) {
                List<SelectorDataGetter> searchedItems = new ArrayList<>();
                for (SelectorDataGetter model : itemsCopy) {
                    if (model.getDisplayName().toLowerCase().contains(query)) {
                        searchedItems.add(model);
                    }
                }
                return searchedItems;
            }

            @Override
            protected void onPostExecute(List<SelectorDataGetter> selectorDataGetters) {
                super.onPostExecute(selectorDataGetters);
                setSearchedItems(selectorDataGetters);
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
                Long currentClickedModel = items.get(getAdapterPosition()).getModelId();
                if (isMultipleSelection) {
                    if (selectedIds.contains(currentClickedModel)) {
                        selectedIds.remove(currentClickedModel);
                    } else {
                        selectedIds.add(currentClickedModel);
                    }
                    notifyItemChanged(getAdapterPosition());
                    listener.onObjectsUpdated(selectedIds);
                } else {
                    selectedId = currentClickedModel;
                    if (lastSelectedPosition != -1) {
                        notifyItemChanged(lastSelectedPosition);
                    }
                    lastSelectedPosition = getAdapterPosition();
                    notifyItemChanged(getAdapterPosition());
                    listener.onObjectClick(selectedId);
                    listener = null;
                }
            }

        }

        void bindData(SelectorDataGetter model) {
            if (model != null && model.getDisplayName() != null && model.getModelId() != null) {
                setAttrs();
                textView.setText(model.getDisplayName());
                if (isMultipleSelection) {
                    if (selectedIds.contains(model.getModelId())) {
                        imageView.setVisibility(View.VISIBLE);
                        lastSelectedPosition = getAdapterPosition();
                        setSelected();
                    } else {
                        imageView.setVisibility(View.GONE);
                        setUnselected();
                    }
                } else {
                    if (model.getModelId() == selectedId) {
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
                itemView.findViewById(R.id.constrain);
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
                Log.d("testFont","Searchview " + selectorAttributes.getTextFont());
                Helper.setTypeFace(context, textView, selectorAttributes.getTextFont());
            }
        }

        private void setConstrainsSet(SelectorAttributes selectorAttributes) {
            try {
                if (selectorAttributes.getTextMarginStartPercent() != 0) {
                    ConstraintSet textConstraintSet = new ConstraintSet();
                    ConstraintLayout constrainLayout = itemView.findViewById(R.id.constrain);
                    textConstraintSet.clone(constrainLayout);
                    textConstraintSet.setGuidelinePercent(R.id.textPercentGuideLine, selectorAttributes.getTextMarginStartPercent());
                    textConstraintSet.applyTo(constrainLayout);
                }
                if (selectorAttributes.getTickMarginEndPercent() != 0) {
                    ConstraintSet tickConstraintSet = new ConstraintSet();
                    ConstraintLayout constrainLayout = itemView.findViewById(R.id.constrain);
                    tickConstraintSet.clone(constrainLayout);
                    tickConstraintSet.setGuidelinePercent(R.id.tickPercentGuideLine, selectorAttributes.getTickMarginEndPercent());
                    tickConstraintSet.applyTo(constrainLayout);
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
