package com.reversecoder.kml.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reversecoder.library.customview.bang.SmallBang;
import com.reversecoder.library.customview.bang.SmallBangListener;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;
import com.reversecoder.kml.R;
import com.reversecoder.kml.adapter.ViewPagerAdapter;
import com.reversecoder.kml.fragment.CategoriesFragment;
import com.reversecoder.kml.fragment.DownloadFragment;
import com.reversecoder.kml.fragment.MovieDetailsFragment;
import com.reversecoder.kml.fragment.MoviesFragment;
import com.reversecoder.kml.fragment.ReviewFragment;
import com.reversecoder.kml.fragment.TrailerFragment;
import com.reversecoder.kml.guillotinemenu.GuillotineAnimation;
import com.reversecoder.kml.guillotinemenu.ParentActivity;
import com.reversecoder.kml.imageslider.ImageSlider;
import com.reversecoder.kml.model.Movies;
import com.reversecoder.kml.model.SliderModel;
import com.reversecoder.kml.persistentsearchview.SampleSuggestionsBuilder;
import com.reversecoder.kml.persistentsearchview.SearchResult;
import com.reversecoder.kml.persistentsearchview.SearchResultAdapter;
import com.reversecoder.kml.persistentsearchview.SimpleAnimationListener;
import com.reversecoder.kml.util.AllConstants;
import com.reversecoder.kml.util.CropSquareTransformation;
import com.slider.library.Animations.DescriptionAnimation;
import com.slider.library.SliderLayout;
import com.squareup.picasso.Picasso;

import org.cryse.widget.persistentsearch.DefaultVoiceRecognizerDelegate;
import org.cryse.widget.persistentsearch.LogoView;
import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.VoiceRecognitionDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeActivity extends ParentActivity implements AppBarLayout.OnOffsetChangedListener {

    private Fragment fragmentMovies;
    private Fragment fragmentCategories;
    private Fragment fragmentMovieDetail;
    private Fragment fragmentTrailer;
    private Fragment fragmentReview;
    private Fragment fragmentDownload;

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1023;
    private PersistentSearchView mSearchView, mMenuTitleView;
    private View mSearchTintView;
    private SearchResultAdapter mResultAdapter;
    private RecyclerView mRecyclerView;
    public LayoutInflater inflater;
    View gMenuLayout;
    FrameLayout baseLayout;
    private AppBarLayout appBarLayout;
    GuillotineAnimation gAnimation;
    public static final long RIPPLE_DURATION = 250;
    ImageView button_mic;
    LogoView logoview;
    ViewPager homeViewPager, movieDetailViewPager;
    ViewPagerAdapter homeViewPagerAdapter, movieDetailViewPagerAdapter;
    TabLayout homeTabLayout, movieDetailTabLayout;
    // image slider
    private SliderLayout mDemoSlider;
    ArrayList<SliderModel> myData = new ArrayList<SliderModel>();
    SliderModel dataModel;
    Bitmap bitmapImg = null;

    //draggable view
    LinearLayout fanArtImageView;
    ImageView draggableImageView;
    DraggableView draggableView;
    private static final int DELAY_MILLIS = 10;
    private boolean isFirstTimeDragged = true;
    Movies mMovies;
    TextView tvMdName, tvMdType, tvMdReleaseDate, tvMdDuration, tvMdImdbRating, tvMdImdbLink, tvMdDirector, tvMdStars, tvMdPlotSummary, tvMdSynopsis;
    ImageView btnMdLike;
    SmallBang mSmallBang;
    //boom menu
    private boolean isInit = false;
    //    private BoomMenuButton boomMenuButton;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initHomeUI();
        initHomeAction(savedInstanceState);
        initImageSliderData();
        initImageSlider();
    }

    public MoviesFragment getMovieFragmentObject() {
        FragmentManager fm = getSupportFragmentManager();
        MoviesFragment fragment = (MoviesFragment) fm.findFragmentByTag(AllConstants.FRAGMENT_TAG_MOVIE);
        return fragment;
    }

    public Fragment getCurrentVisibleFragmentObject(String fragmentTag) {
        FragmentManager fm = this.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(fragmentTag);
        return fragment;
    }

    public void initHomeUI() {
        mContext = HomeActivity.this;
        //menu
        inflater = LayoutInflater.from(getParentContext());
        baseLayout = (FrameLayout) findViewById(R.id.root);
        gMenuLayout = LayoutInflater.from(this).inflate(R.layout.menu_view, null);
        baseLayout.addView(gMenuLayout);

        //searchview
        mSearchView = (PersistentSearchView) findViewById(R.id.searchview);
        mSearchTintView = findViewById(R.id.view_search_tint);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_search_result);
        mMenuTitleView = (PersistentSearchView) gMenuLayout.findViewById(R.id.searchview);
        button_mic = (ImageView) gMenuLayout.findViewById(R.id.button_mic);
        button_mic.setVisibility(View.INVISIBLE);
        logoview = (LogoView) gMenuLayout.findViewById(R.id.logoview);
        logoview.setEnabled(false);
        ((EditText) gMenuLayout.findViewById(org.cryse.widget.persistentsearch.R.id.edittext_search)).setVisibility(View.INVISIBLE);

        // screen view
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        homeViewPager = (ViewPager) findViewById(R.id.viewpager);
        homeTabLayout = (TabLayout) findViewById(R.id.tabs);


        //draggable view
        initDraggableViewUI();

        //boom menu
//        boomMenuButton = (BoomMenuButton) findViewById(R.id.boom);
    }

    public void initHomeAction(Bundle savedInstanceState) {
//        view pager
        if (homeViewPager != null) {
            initializeHomeFragments(savedInstanceState);
            setupHomeViewPager(homeViewPager);
            homeTabLayout.setupWithViewPager(homeViewPager);
        }
        //menu
        gAnimation = new GuillotineAnimation.GuillotineBuilder(gMenuLayout, (View) mMenuTitleView.findViewById(R.id.button_home),
                (View) mSearchView.findViewById(R.id.button_home)).setStartDelay(RIPPLE_DURATION).setActionBarViewForAnimation(mSearchView)
                .setClosedOnStart(true).build();

        //
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mResultAdapter = new SearchResultAdapter(new ArrayList<SearchResult>());
        mRecyclerView.setAdapter(mResultAdapter);
        VoiceRecognitionDelegate delegate = new DefaultVoiceRecognizerDelegate(this, VOICE_RECOGNITION_REQUEST_CODE);
        if (delegate.isVoiceRecognitionAvailable()) {
            mSearchView.setVoiceRecognitionDelegate(delegate);
        }
        mSearchView.setHomeButtonListener(new PersistentSearchView.HomeButtonListener() {

            @Override
            public void onHomeButtonClick() {
                //Hamburger has been clicked
//                Toast.makeText(HomeActivity.this, "Menu click", Toast.LENGTH_LONG).show();
                gAnimation.open();
            }

        });

        mMenuTitleView.setHomeButtonListener(new PersistentSearchView.HomeButtonListener() {

            @Override
            public void onHomeButtonClick() {
                //Hamburger has been clicked
//                Toast.makeText(HomeActivity.this, "back click", Toast.LENGTH_LONG).show();
                gAnimation.close();

                mSearchView.setVisibility(View.VISIBLE);
            }

        });

        mSearchTintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.cancelEditing();
            }
        });
        mSearchView.setSuggestionBuilder(new SampleSuggestionsBuilder(this));
        mSearchView.setSearchListener(new PersistentSearchView.SearchListener() {

            @Override
            public void onSearchEditOpened() {
                //Use this to tint the screen
                mSearchTintView.setVisibility(View.VISIBLE);
                mSearchTintView
                        .animate()
                        .alpha(1.0f)
                        .setDuration(300)
                        .setListener(new SimpleAnimationListener())
                        .start();
            }

            @Override
            public void onSearchEditClosed() {
                mSearchTintView
                        .animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new SimpleAnimationListener() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mSearchTintView.setVisibility(View.GONE);
                            }
                        })
                        .start();
            }

            @Override
            public boolean onSearchEditBackPressed() {
                return false;
            }

            @Override
            public void onSearchExit() {
                mResultAdapter.clear();
                if (mRecyclerView.getVisibility() == View.VISIBLE) {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSearchTermChanged(String term) {

            }

            @Override
            public void onSearch(String string) {
                Toast.makeText(HomeActivity.this, string + " Searched", Toast.LENGTH_LONG).show();
                mRecyclerView.setVisibility(View.VISIBLE);
                fillResultToRecyclerView(string);
            }

            @Override
            public boolean onSuggestion(SearchItem searchItem) {
                Log.d("onSuggestion", searchItem.getTitle());
                return false;
            }

            @Override
            public void onSearchCleared() {
                //Called when the clear button is clicked
            }

        });

        //draggable view
        initDraggableViewAction();
    }

    private void initDraggableViewUI() {
        fanArtImageView = (LinearLayout) findViewById(R.id.iv_fan_art);
        draggableView = (DraggableView) findViewById(R.id.draggable_view);
        draggableImageView = (ImageView) findViewById(R.id.draggable_image_view);
        movieDetailTabLayout = (TabLayout) findViewById(R.id.md_tab);
        movieDetailViewPager = (ViewPager) findViewById(R.id.md_viewpager);

        draggableView.setVisibility(View.GONE);
        draggableView.setClickToMaximizeEnabled(true);
        draggableView.setClickToMinimizeEnabled(false);

        tvMdName = (TextView) findViewById(R.id.md_name);
        tvMdType = (TextView) findViewById(R.id.md_type);
        tvMdReleaseDate = (TextView) findViewById(R.id.md_release_date);
        tvMdDuration = (TextView) findViewById(R.id.md_duration);
        tvMdImdbRating = (TextView) findViewById(R.id.md_imdb_rating);
        tvMdImdbLink = (TextView) findViewById(R.id.md_imdb_link);
//        tvMdDirector=(TextView)findViewById(R.id.md_director);
//        tvMdStars=(TextView)findViewById(R.id.md_stars);
//        tvMdPlotSummary=(TextView)findViewById(R.id.md_plot_summary);
//        tvMdSynopsis=(TextView)findViewById(R.id.md_synopsis);
        btnMdLike = (ImageView) findViewById(R.id.btn_md_like);
    }

    private void initDraggableViewAction() {
        if (movieDetailViewPager != null) {
            setupMovieDetailViewPager(movieDetailViewPager);
            movieDetailTabLayout.setupWithViewPager(movieDetailViewPager);
        }
        fanArtImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Movie detail", Toast.LENGTH_LONG).show();
            }
        });

        draggableView.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                updateDraggableViewWhenMaximize();
            }

            @Override
            public void onMinimized() {
//                updateActionBarTitle();
                updateDraggableViewWhenMinimized();
            }

            @Override
            public void onClosedToLeft() {
//                resetActionBarTitle();
                updateDraggableViewWhenMinimized();
            }

            @Override
            public void onClosedToRight() {
//                resetActionBarTitle();
                updateDraggableViewWhenMinimized();
            }
        });
    }

    private void updateDraggableViewWhenMaximize() {
        Picasso.with(HomeActivity.this).load(mMovies.getPosterUrl()).transform(new CropSquareTransformation()).into(draggableImageView);
//        boomMenuButton.setVisibility(View.VISIBLE);

        tvMdName.setText(mMovies.getMovieName());
        tvMdType.setText(mMovies.getMovieType());
        tvMdReleaseDate.setText("Release: " + mMovies.getReleaseDate());
        tvMdDuration.setText("Duration: " + mMovies.getDuration());
        tvMdImdbRating.setText(mMovies.getImdbRating());
        tvMdImdbLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mMovies.getImdbLink()));
                startActivity(intent);
            }
        });
//        tvMdImdbLink.setText(mMovies.getImdbLink());
//        tvMdDirector.setText(mMovies.getDirector());
//        tvMdStars.setText(mMovies.getStars());
//        tvMdPlotSummary.setText(mMovies.getPlotSummary());
//        tvMdSynopsis.setText(mMovies.getSynopsis());

        if (mSmallBang == null) {
            mSmallBang = getBang(mContext);
        }
        btnMdLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMovies.isLiked()) {
                    btnMdLike.setBackgroundResource(R.drawable.heart_grey);
                    mMovies.setLiked(false);
                } else {
                    btnMdLike.setBackgroundResource(R.drawable.heart_red_fill);
                    mMovies.setLiked(true);
                }
                mSmallBang.bang(v);
                mSmallBang.setmListener(new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {
                    }

                    @Override
                    public void onAnimationEnd() {
                    }
                });
            }
        });
    }

    public Movies getSelectedMovie() {
        return mMovies;
    }

    private void updateDraggableViewWhenMinimized() {
//        boomMenuButton.setVisibility(View.INVISIBLE);
    }

    public void openDraggableView(Movies movies) {
        isFirstTimeDragged = false;
        mMovies = movies;

        if (draggableView != null) {
            draggableView.setVisibility(View.VISIBLE);
            draggableView.maximize();
        }
    }

//    BoomMenuButton.AnimatorListener animator=new BoomMenuButton.AnimatorListener() {
//        @Override
//        public void toShow() {
//
//        }
//
//        @Override
//        public void showing(float fraction) {
//
//        }
//
//        @Override
//        public void showed() {
//
//        }
//
//        @Override
//        public void toHide() {
//
//        }
//
//        @Override
//        public void hiding(float fraction) {
//
//        }
//
//        @Override
//        public void hided() {
//
//        }
//    };

    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        if (!isInit) {
//            initBoom();
//        }
//        isInit = true;
//    }
    private String[] Colors = {
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};

    public int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

//    private void initBoom() {
//        int number = 5;
//
//        Drawable[] drawables = new Drawable[number];
//        int[] drawablesResource = new int[]{
//                R.drawable.mark,
//                R.drawable.refresh,
//                R.drawable.copy,
//                R.drawable.heart,
//                R.drawable.info,
//                R.drawable.like,
//                R.drawable.record,
//                R.drawable.search,
//                R.drawable.settings
//        };
//        for (int i = 0; i < number; i++)
//            drawables[i] = ContextCompat.getDrawable(mContext, drawablesResource[i]);
//
//        String[] STRINGS = new String[]{
//                "Mark",
//                "Refresh",
//                "Copy",
//                "Heart",
//                "Info",
//                "Like",
//                "Record",
//                "Search",
//                "Settings"
//        };
//        String[] strings = new String[number];
//        for (int i = 0; i < number; i++)
//            strings[i] = STRINGS[i];
//
//        int[][] colors = new int[number][2];
//        for (int i = 0; i < number; i++) {
//            colors[i][1] = GetRandomColor();
//            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
//        }
//
//        ButtonType buttonType = ButtonType.CIRCLE;
//
//        // Now with Builder, you can init BMB more convenient
//        new BoomMenuButton.Builder()
//                .subButtons(drawables, colors, strings)
//                .button(buttonType)
//                .boom(BoomType.HORIZONTAL_THROW)
//                .place(PlaceType.CIRCLE_5_4)
//                .boomButtonShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
//                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
//                .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
//                    @Override
//                    public void onClick(int buttonIndex) {
//                        if (buttonIndex == 0) {
//                            Intent intentLogin=new Intent(mContext,LoginActivity.class);
//                            startActivity(intentLogin);
//                            Toast.makeText(HomeActivity.this, "Heart", Toast.LENGTH_SHORT).show();
//                        } else if (buttonIndex == 1) {
//                            Intent intentRegistration=new Intent(mContext,WaveSwipeRefreshLayoutActivity.class);
//                            startActivity(intentRegistration);
//                            Toast.makeText(HomeActivity.this, "Java", Toast.LENGTH_SHORT).show();
//                        } else if (buttonIndex == 2) {
//                            Intent intentDownload=new Intent(mContext,DownloadActivity.class);
//                            startActivity(intentDownload);
//                            Toast.makeText(HomeActivity.this, "Github", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .animator(animator)
//                .init(boomMenuButton);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragmentMovies != null) {
            getSupportFragmentManager().putFragment(outState, AllConstants.FRAGMENT_TAG_MOVIE, fragmentMovies);
        }
        if (fragmentCategories != null) {
            getSupportFragmentManager().putFragment(outState, AllConstants.FRAGMENT_TAG_CATEGORY, fragmentCategories);
        }
    }

    private void initializeHomeFragments(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Fragment movieFragment = getSupportFragmentManager().getFragment(savedInstanceState,
                    AllConstants.FRAGMENT_TAG_MOVIE);

            if (movieFragment != null && movieFragment instanceof MoviesFragment) {
                fragmentMovies = (MoviesFragment) movieFragment;
            } else {
                fragmentMovies = MoviesFragment.newInstance();
            }

            Fragment categoryFragment = getSupportFragmentManager().getFragment(savedInstanceState,
                    AllConstants.FRAGMENT_TAG_CATEGORY);

            if (categoryFragment != null && categoryFragment instanceof CategoriesFragment) {
                fragmentCategories = (CategoriesFragment) categoryFragment;
            } else {
                fragmentCategories = CategoriesFragment.newInstance();
            }
        } else {
            fragmentMovies = MoviesFragment.newInstance();
            fragmentCategories = CategoriesFragment.newInstance();
        }
    }

    private void setupHomeViewPager(ViewPager viewPager) {
        homeViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeViewPagerAdapter.addFragment(fragmentMovies, AllConstants.FRAGMENT_TTTLE_MOVIE);
        homeViewPagerAdapter.addFragment(fragmentCategories, AllConstants.FRAGMENT_TTTLE_CATEGORY);
        viewPager.setAdapter(homeViewPagerAdapter);
    }

    private void setupMovieDetailViewPager(ViewPager viewPager) {
        fragmentMovieDetail = MovieDetailsFragment.newInstance();
        fragmentTrailer = TrailerFragment.newInstance();
        fragmentReview = ReviewFragment.newInstance();
        fragmentDownload = DownloadFragment.newInstance();

        movieDetailViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        movieDetailViewPagerAdapter.addFragment(fragmentMovieDetail, AllConstants.FRAGMENT_TTTLE_MOVIE_DETAIL);
//        movieDetailViewPagerAdapter.addFragment(fragmentTrailer, AllConstants.FRAGMENT_TTTLE_TRAILER);
//        movieDetailViewPagerAdapter.addFragment(fragmentReview, AllConstants.FRAGMENT_TTTLE_REVIEW);
        movieDetailViewPagerAdapter.addFragment(fragmentDownload, AllConstants.FRAGMENT_TTTLE_DOWNLOAD);
        viewPager.setAdapter(movieDetailViewPagerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mSearchView.populateEditText(matches);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fillResultToRecyclerView(String query) {
        List<SearchResult> newResults = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SearchResult result = new SearchResult(query, query + Integer.toString(i), "");
            newResults.add(result);
        }
        mResultAdapter.replaceWith(newResults);
    }

    private void initImageSliderData() {
        myData.clear();

//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.lee_min_ho_2);
        dataModel = new SliderModel(
                "Lee Min Ho 1",
                bitmapImg, "http://loop.co.id/uploads/article/media_upload/1048/cov-leeminho.jpg");
        myData.add(dataModel);

//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.kim_yoon_seok_1);
        dataModel = new SliderModel(
                "Kim Yoon-seok 1",
                bitmapImg, "http://mn.kobiz.or.kr/cheditor/attach/HRuoHasjYbGDjIQefKzS.jpg");
        myData.add(dataModel);

//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.kim_yoon_seok_2);
        dataModel = new SliderModel(
                "Kim Yoon-seok 2",
                bitmapImg, "http://1.fwcdn.pl/ph/90/70/679070/472243_1.1.jpg");
        myData.add(dataModel);

//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.won_bin_1);
        dataModel = new SliderModel(
                "Won Bin 1",
                bitmapImg, "http://images.kdramastars.com/data/thumbs/full/210980/600/0/0/0/won-bin.jpg");
        myData.add(dataModel);

//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.won_bin_2);
        dataModel = new SliderModel(
                "Won Bin 2",
                bitmapImg, "http://0.soompi.io/wp-content/uploads/2012/11/Won_bin-interview-1.jpg");
        myData.add(dataModel);


//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.banner_c);
//        dataModel = new SliderModel(
//                "Banner 3",
//                bitmapImg, "");
//        myData.add(dataModel);
//
//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.banner_d);
//        dataModel = new SliderModel(
//                "Banner 4",
//                bitmapImg, "");
//        myData.add(dataModel);
//
//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.banner_e);
//        dataModel = new SliderModel(
//                "Banner 5",
//                bitmapImg, "");
//        myData.add(dataModel);
//
//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.banner_f);
//        dataModel = new SliderModel(
//                "Banner 6",
//                bitmapImg, "");
//        myData.add(dataModel);
//
//        bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.banner_h);
//        dataModel = new SliderModel(
//                "Banner 7",
//                bitmapImg, "");
//        myData.add(dataModel);

    }

    private void initImageSlider() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        for (SliderModel name : myData) {
            ImageSlider textSliderView = new ImageSlider(HomeActivity.this, name);
            // initialize a SliderLayout
            // textSliderView.description(name.getmTitle())
            // .image(name.getmImage())
            // .setScaleType(BaseSliderView.ScaleType.Fit);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider
                .setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
    }

    public void startImageSlider() {
        if (mDemoSlider != null) {
            mDemoSlider.startAutoCycle();
        }
    }

    public void resumeImageSlider() {
        if (mDemoSlider != null) {
            mDemoSlider.startAutoCycle();
        }
    }

    public void stopImageSlider() {
        if (mDemoSlider != null) {
            mDemoSlider.stopAutoCycle();
        }
    }

    public void onResume() {
//        refresh();
        super.onResume();
        startImageSlider();
    }

    public void onPause() {
        super.onPause();
        stopImageSlider();
    }

    public void onDestroy() {
        super.onDestroy();
//        Picasso.with(Context).cancelRequest(Target);
    }


    @Override
    public void onBackPressed() {
        if (mSearchView.isSearching()) {
            Log.d("backpress", "isSearching()");
            mSearchView.closeSearch();
        } else if (mRecyclerView.getVisibility() == View.VISIBLE) {
            Log.d("backpress", "getVisibility()");
            mResultAdapter.clear();
            mRecyclerView.setVisibility(View.GONE);
        } else if (gAnimation.isOpened()) {
            Log.d("backpress", "isOpened()");
            gAnimation.close();
            mSearchView.setVisibility(View.VISIBLE);
        }
//        else if (boomMenuButton.isOpen()) {
//            Log.d("backpress", "dismiss()");
//            boomMenuButton.dismiss();
//            return;
//        }
        else if (draggableView.isMaximized() && !isFirstTimeDragged) {
            Log.d("backpress", "isMaximized()");
            draggableView.minimize();
        } else {
            Log.d("backpress", "else");
            super.onBackPressed();
        }
    }

    ///////////////////////////////////////
    // SWIPE TO REFRESH FIX
    ///////////////////////////////////////

    int index = 0;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        index = i;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Fragment pageFragment = homeViewPagerAdapter.getItem(homeViewPager.getCurrentItem());
                if (pageFragment != null && pageFragment instanceof MoviesFragment) {
                    MoviesFragment moviesFragment = (MoviesFragment) pageFragment;
                    if (index == 0) {
                        moviesFragment.setSwipeToRefreshEnabled(true);
                    } else {
                        moviesFragment.setSwipeToRefreshEnabled(false);
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onStart() {
        super.onStart();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onStop() {
        appBarLayout.removeOnOffsetChangedListener(this);
        super.onStop();
    }

    /******************************
     * bang view
     ******************************/
    public SmallBang getBang(Context mContext) {
        SmallBang mSmallBang = SmallBang.attach2Window((HomeActivity) mContext);
        return mSmallBang;
    }
}
