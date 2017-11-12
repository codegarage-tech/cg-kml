package com.reversecoder.library.customview.animatedpaginglistview;

import java.util.ArrayList;
import java.util.HashSet;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.reversecoder.library.R;
import com.reversecoder.library.customview.effects.CardsEffect;
import com.reversecoder.library.customview.effects.CurlEffect;
import com.reversecoder.library.customview.effects.FadeEffect;
import com.reversecoder.library.customview.effects.FanEffect;
import com.reversecoder.library.customview.effects.FlipEffect;
import com.reversecoder.library.customview.effects.FlyEffect;
import com.reversecoder.library.customview.effects.GrowEffect;
import com.reversecoder.library.customview.effects.HelixEffect;
import com.reversecoder.library.customview.effects.ReverseFlyEffect;
import com.reversecoder.library.customview.effects.SlideInEffect;
import com.reversecoder.library.customview.effects.StandardEffect;
import com.reversecoder.library.customview.effects.TiltEffect;
import com.reversecoder.library.customview.effects.TwirlEffect;
import com.reversecoder.library.customview.effects.WaveEffect;
import com.reversecoder.library.customview.effects.ZipperEffect;
import com.reversecoder.library.customview.utils.CustomEffect;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class PagingListView extends ListView {
	public static final int STANDARD = 0;
	public static final int GROW = 1;
	public static final int CARDS = 2;
	public static final int CURL = 3;
	public static final int WAVE = 4;
	public static final int FLIP = 5;
	public static final int FLY = 6;
	public static final int REVERSE_FLY = 7;
	public static final int HELIX = 8;
	public static final int FAN = 9;
	public static final int TILT = 10;
	public static final int ZIPPER = 11;
	public static final int FADE = 12;
	public static final int TWIRL = 13;
	public static final int SLIDE_IN = 14;
	public static final int DURATION = 600;
	public static final int OPAQUE = 255, TRANSPARENT = 0;
	private CustomEffect mTransitionEffect = null;
	private boolean mIsScrolling = false;
	private int mFirstVisibleItem = -1;
	public int mLastVisibleItem = -1;
	private int mPreviousFirstVisibleItem = 0;
	private long mPreviousEventTime = 0;
	private double mSpeed = 0;
	private int mMaxVelocity = 0;
	public static final int MAX_VELOCITY_OFF = 0;
	private boolean mOnlyAnimateNewItems;
	private boolean mOnlyAnimateOnFling;
	private boolean mIsFlingEvent;
	private boolean mSimulateGridWithList;
	private HashSet<Integer> mAlreadyAnimatedItems;

	public interface Pagingable {
		void onLoadMoreItems();
	}

	private boolean mIsLoading;
	private boolean mHasMoreItems;
	private Pagingable mPagingableListener;
	public LoadingView mLoadinView;
	private OnScrollListener onScrollListener;

	public PagingListView(Context context) {
		super(context);
		init(context, null);
	}

	public PagingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public PagingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public boolean isLoading() {
		return this.mIsLoading;
	}

	public void setIsLoading(boolean isLoading) {
		this.mIsLoading = isLoading;
	}

	public void setPagingableListener(Pagingable pagingableListener) {
		this.mPagingableListener = pagingableListener;
	}

	public void setHasMoreItems(boolean hasMoreItems) {
		this.mHasMoreItems = hasMoreItems;
		if (!this.mHasMoreItems) {
			removeFooterView(mLoadinView);
		} else if (findViewById(R.id.loading_view) == null) {
			addFooterView(mLoadinView);
			ListAdapter adapter = ((HeaderViewListAdapter) getAdapter())
					.getWrappedAdapter();
			setAdapter(adapter);
		}
	}

	public boolean hasMoreItems() {
		return this.mHasMoreItems;
	}

	public void onFinishLoading(boolean hasMoreItems,
			ArrayList<? extends Object> newItems) {
		setHasMoreItems(hasMoreItems);
		setIsLoading(false);
		if (newItems != null && newItems.size() > 0) {
			ListAdapter adapter = ((HeaderViewListAdapter) getAdapter())
					.getWrappedAdapter();
			if (adapter instanceof PagingListViewBaseAdapter) {
				((PagingListViewBaseAdapter) adapter).addMoreItems(newItems);
			}
		}
	}

	private void init(Context context, AttributeSet attrs) {

		mAlreadyAnimatedItems = new HashSet<Integer>();
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.CustomListView);
		int transitionEffect = a.getInteger(R.styleable.CustomListView_effect,
				STANDARD);
		int maxVelocity = a.getInteger(R.styleable.CustomListView_max_velocity,
				MAX_VELOCITY_OFF);
		mOnlyAnimateNewItems = a.getBoolean(
				R.styleable.CustomListView_only_animate_new_items, false);
		mOnlyAnimateOnFling = a.getBoolean(
				R.styleable.CustomListView_max_velocity, false);
		mSimulateGridWithList = a.getBoolean(
				R.styleable.CustomListView_simulate_grid_with_list, false);
		a.recycle();
		setTransitionEffect(transitionEffect);
		setMaxAnimationVelocity(maxVelocity);
		mIsLoading = false;
		mLoadinView = new LoadingView(getContext());
		addFooterView(mLoadinView);
		super.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					mIsScrolling = false;
					mIsFlingEvent = false;
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					mIsFlingEvent = true;
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					mIsScrolling = true;
					mIsFlingEvent = false;
					break;
				default:
					break;
				}
				notifyAdditionalOnScrollStateChangedListener(view, scrollState);
				// Dispatch to child OnScrollListener
				// if (onScrollListener != null) {
				// onScrollListener.onScrollStateChanged(view, scrollState);
				// }
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				boolean shouldAnimateItems = (mFirstVisibleItem != -1 && mLastVisibleItem != -1);
				int lastVisibleItem = firstVisibleItem + visibleItemCount - 1;
				if (mIsScrolling && shouldAnimateItems) {
					setVelocity(firstVisibleItem, totalItemCount);
					int indexAfterFirst = 0;
					while (firstVisibleItem + indexAfterFirst < mFirstVisibleItem) {
						View item = view.getChildAt(indexAfterFirst);
						doEffect(item, firstVisibleItem + indexAfterFirst, -1);
						indexAfterFirst++;
					}

					int indexBeforeLast = 0;
					while (lastVisibleItem - indexBeforeLast > mLastVisibleItem) {
						View item = view.getChildAt(lastVisibleItem
								- firstVisibleItem - indexBeforeLast);
						doEffect(item, lastVisibleItem - indexBeforeLast, 1);
						indexBeforeLast++;
					}
				} else if (!shouldAnimateItems) {
					for (int i = firstVisibleItem; i < visibleItemCount; i++) {
						mAlreadyAnimatedItems.add(i);
					}
				}

				mFirstVisibleItem = firstVisibleItem;
				mLastVisibleItem = lastVisibleItem;

				notifyAdditionalOnScrollListener(view, firstVisibleItem,
						visibleItemCount, totalItemCount);
				// Dispatch to child OnScrollListener
				// if (onScrollListener != null) {
				// onScrollListener.onScroll(view, firstVisibleItem,
				// visibleItemCount, totalItemCount);
				// }
				int lastVisibleItemForPaging = firstVisibleItem
						+ visibleItemCount;
				if (!mIsLoading && mHasMoreItems
						&& (lastVisibleItemForPaging == totalItemCount)) {
					if (mPagingableListener != null) {
						mIsLoading = true;
						mPagingableListener.onLoadMoreItems();
					}

				}
			}
		});
	}

	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		onScrollListener = listener;
	}

	public void setTransitionEffect(int transitionEffect) {

		switch (transitionEffect) {
		case STANDARD:
			setTransitionEffect(new StandardEffect());
			break;
		case GROW:
			setTransitionEffect(new GrowEffect());
			break;
		case CARDS:
			setTransitionEffect(new CardsEffect());
			break;
		case CURL:
			setTransitionEffect(new CurlEffect());
			break;
		case WAVE:
			setTransitionEffect(new WaveEffect());
			break;
		case FLIP:
			setTransitionEffect(new FlipEffect());
			break;
		case FLY:
			setTransitionEffect(new FlyEffect());
			break;
		case REVERSE_FLY:
			setTransitionEffect(new ReverseFlyEffect());
			break;
		case HELIX:
			setTransitionEffect(new HelixEffect());
			break;
		case FAN:
			setTransitionEffect(new FanEffect());
			break;
		case TILT:
			setTransitionEffect(new TiltEffect());
			break;
		case ZIPPER:
			setTransitionEffect(new ZipperEffect());
			break;
		case FADE:
			setTransitionEffect(new FadeEffect());
			break;
		case TWIRL:
			setTransitionEffect(new TwirlEffect());
			break;
		case SLIDE_IN:
			setTransitionEffect(new SlideInEffect());
			break;
		default:
			break;
		}
	}

	public void setTransitionEffect(CustomEffect transitionEffect) {
		mTransitionEffect = transitionEffect;
	}

	public void setShouldOnlyAnimateNewItems(boolean onlyAnimateNew) {
		mOnlyAnimateNewItems = onlyAnimateNew;
	}

	public void setShouldOnlyAnimateFling(boolean onlyFling) {
		mOnlyAnimateOnFling = onlyFling;
	}

	public void setMaxAnimationVelocity(int itemsPerSecond) {
		mMaxVelocity = itemsPerSecond;
	}

	public void setSimulateGridWithList(boolean simulateGridWithList) {
		mSimulateGridWithList = simulateGridWithList;
		setClipChildren(!simulateGridWithList);
	}

	/**
	 * Should be called in onScroll to keep take of current Velocity.
	 * 
	 * @param firstVisibleItem
	 *            The index of the first visible item in the ListView.
	 */
	private void setVelocity(int firstVisibleItem, int totalItemCount) {
		if (mMaxVelocity > MAX_VELOCITY_OFF
				&& mPreviousFirstVisibleItem != firstVisibleItem) {
			long currTime = System.currentTimeMillis();
			long timeToScrollOneItem = currTime - mPreviousEventTime;
			if (timeToScrollOneItem < 1) {
				double newSpeed = ((1.0d / timeToScrollOneItem) * 1000);
				// We need to normalize velocity so different size item don't
				// give largely different velocities.
				if (newSpeed < (0.9f * mSpeed)) {
					mSpeed *= 0.9f;
				} else if (newSpeed > (1.1f * mSpeed)) {
					mSpeed *= 1.1f;
				} else {
					mSpeed = newSpeed;
				}
			} else {
				mSpeed = ((1.0d / timeToScrollOneItem) * 1000);
			}

			mPreviousFirstVisibleItem = firstVisibleItem;
			mPreviousEventTime = currTime;
		}
	}

	/**
	 * 
	 * @return Returns the current Velocity of the ListView's scrolling in items
	 *         per second.
	 */
	private double getVelocity() {
		return mSpeed;
	}

	/**
	 * Initializes the item view and triggers the animation.
	 * 
	 * @param item
	 *            The view to be animated.
	 * @param position
	 *            The index of the view in the list.
	 * @param scrollDirection
	 *            Positive number indicating scrolling down, or negative number
	 *            indicating scrolling up.
	 */
	private void doEffect(View item, int position, int scrollDirection) {
		if (mIsScrolling) {
			if (mOnlyAnimateNewItems
					&& mAlreadyAnimatedItems.contains(position))
				return;

			if (mOnlyAnimateOnFling && !mIsFlingEvent)
				return;

			if (mMaxVelocity > MAX_VELOCITY_OFF && mMaxVelocity < getVelocity())
				return;

			if (mSimulateGridWithList) {
				ViewGroup itemRow = (ViewGroup) item;
				for (int i = 0; i < itemRow.getChildCount(); i++)
					doEffectImpl(itemRow.getChildAt(i), position,
							scrollDirection);
			} else {
				doEffectImpl(item, position, scrollDirection);
			}

			mAlreadyAnimatedItems.add(position);
		}
	}

	private void doEffectImpl(View item, int position, int scrollDirection) {
		ViewPropertyAnimator animator = ViewPropertyAnimator
				.animate(item).setDuration(DURATION)
				.setInterpolator(new AccelerateDecelerateInterpolator());

		scrollDirection = scrollDirection > 0 ? 1 : -1;
		mTransitionEffect.initView(item, position, scrollDirection);
		mTransitionEffect.setupAnimation(item, position, scrollDirection,
				animator);
		animator.start();
	}

	/**
	 * Notifies the OnScrollListener of an onScroll event, since JazzyListView
	 * is the primary listener for onScroll events.
	 */
	private void notifyAdditionalOnScrollListener(AbsListView view,
			int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (onScrollListener != null) {
			onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	/**
	 * Notifies the OnScrollListener of an onScrollStateChanged event, since
	 * JazzyListView is the primary listener for onScrollStateChanged events.
	 */
	private void notifyAdditionalOnScrollStateChangedListener(AbsListView view,
			int scrollState) {
		if (onScrollListener != null) {
			onScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

}
