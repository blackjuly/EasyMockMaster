package cn.whdreamblog.mockhelper.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.whdreamblog.mockhelper.R;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 14:54
 * desc : The class is used for show titleBar
 */
public class MockTitleBar extends RelativeLayout {

    private TextView tvCenterText;
    private ImageView ivLeftBtn;
    private TextView tvRightBtn;
    private TextView cancelBtn;
    private TitleBarLeftBtnOnClickListener leftBtnClickListener;
    private TitleBarRightBtnOnClickListener rightBtnClickListener;
    private LinearLayout llSearchContent;
    private RelativeLayout rlNormalContent;
    private View titleBottomLine;
    private String searchViewHint;
    private MockAutoCompleteTextView tvSearchBox;
    private boolean isSearchView = false;
    private List<String> dataList;

    public MockTitleBar(@NonNull Context context) {
        this(context, null);
    }

    public MockTitleBar(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MockTitleBar(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.mock_view_title_bar, this);
        initView();
        initAttr(context, attrs);
        initListener();
        setAdjustTransparentStatusBar(true);
    }

    /**
     * 初始化自定义属性
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void initAttr(@NonNull Context context, AttributeSet attrs) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.MockTitleBar);
        String centerText = typeArray.getString(R.styleable.MockTitleBar_titleName);
        String rightText = typeArray.getString(R.styleable.MockTitleBar_rightText);
        int rightDrawable = typeArray.getResourceId(R.styleable.MockTitleBar_rightDrawable, 0);
        int leftDrawable = typeArray.getResourceId(R.styleable.MockTitleBar_leftDrawable, R.drawable.lib_base_icon_arrow_left_white);
        int titleBackground = typeArray.getResourceId(R.styleable.MockTitleBar_titleBackground, 0);
        boolean isLeftBtnVisibility = typeArray.getBoolean(R.styleable.MockTitleBar_leftBtnIsVisibility, true);
        boolean isRightBtnVisibility = typeArray.getBoolean(R.styleable.MockTitleBar_rightBtnIsVisibility, true);
        boolean bottomLineVisibility = typeArray.getBoolean(R.styleable.MockTitleBar_bottomLineVisibility, true);
        isSearchView = typeArray.getBoolean(R.styleable.MockTitleBar_isSearchView, false);
        searchViewHint = typeArray.getString(R.styleable.MockTitleBar_searchViewHint);

        if (!TextUtils.isEmpty(searchViewHint)) {//设置搜索框hint
            tvSearchBox.setHint(searchViewHint);
        }
        if (isSearchView) {//判断为 搜索框状态 隐藏通常布局
            rlNormalContent.setVisibility(GONE);
            llSearchContent.setVisibility(VISIBLE);
            return;
        }
        if (!TextUtils.isEmpty(centerText)) {
            tvCenterText.setText(centerText);
        }
        if (!TextUtils.isEmpty(rightText)) {
            tvRightBtn.setText(rightText);
        }
        if (rightDrawable != 0) {
            tvRightBtn.setBackgroundResource(rightDrawable);
            tvRightBtn.setTextColor(getResources().getColor(R.color.white));
            tvRightBtn.setPadding(dip2px(15), dip2px(4), dip2px(15), dip2px(4));
        }
        if (leftDrawable != 0) {
            ivLeftBtn.setImageResource(leftDrawable);
        }
        if (!isLeftBtnVisibility) {
            ivLeftBtn.setVisibility(GONE);
        }
        if (!bottomLineVisibility) {
            titleBottomLine.setVisibility(GONE);
        }
        if (!isRightBtnVisibility) {
            tvRightBtn.setVisibility(GONE);
        }
        if (titleBackground != 0) {
            rlNormalContent.setBackgroundColor(getResources().getColor(titleBackground));
            if (getResources().getColor(titleBackground) != getResources().getColor(R.color.white)) {
                return;
            }
            tvCenterText.setTextColor(getResources().getColor(R.color.ehi_ui_black_v2));
        }

        typeArray.recycle();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        if (isSearchView) {
            cancelBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tvSearchBox.getWindowToken(), 0);
                    closePage();
                }
            });
            return;
        }
        initLeftBtnListener();
        initRightBtnListener();
    }

    private void initLeftBtnListener() {
        if (leftBtnClickListener != null) {
            ivLeftBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftBtnClickListener.onTitleBarLeftBtnClick(v);
                }
            });
        } else {
            ivLeftBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {//默认点击返回，销毁掉当前界面
                    closePage();
                }
            });
        }
    }

    private void initRightBtnListener() {
        if (rightBtnClickListener != null) {
            tvRightBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightBtnClickListener.onTitleBarRightBtnClick(v);
                }
            });
        }
    }

    /**
     * 初始化 searchBox的适配器
     */
    private void initAdapter() {
        if (!isSearchView) {
            return;
        }
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        ArrayAdapter<String> arrayAdapter;
        if (tvSearchBox.getAdapter() != null) {//判断适配器不为空，通知数据更新
            arrayAdapter = (ArrayAdapter<String>) tvSearchBox.getAdapter();
            arrayAdapter.clear();
            arrayAdapter.addAll(dataList);
            arrayAdapter.notifyDataSetChanged();
            return;
        }
        arrayAdapter = new ArrayAdapter<String>(
                getContext()
                , R.layout.mock_view_title_bar_search_result
                , R.id.tv_list_menu_item
                , dataList
        );
        tvSearchBox.setAdapter(
                arrayAdapter
        );
    }

    public void setTvSearchBoxHint(String hint) {
        tvSearchBox.setHint(hint);
    }

    /**
     * 为搜索框指定item点击事件
     *
     * @param onItemClickListener item设置点击事件
     */
    public void setSearchBoxItemListener(AdapterView.OnItemClickListener onItemClickListener) {
        tvSearchBox.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 监听搜索文字字数该改变
     *
     * @param textWatcher 监听器
     */
    public void setSearchBoxTextWatcher(TextWatcher textWatcher) {
        tvSearchBox.addTextChangedListener(textWatcher);
    }

    /**
     * 初始化view
     */
    private void initView() {
        tvCenterText = (TextView) findViewById(R.id.tv_title_bar_center_text);
        ivLeftBtn = (ImageView) findViewById(R.id.iv_title_bar_left_btn);
        tvRightBtn = (TextView) findViewById(R.id.tv_title_bar_right_btn);
        llSearchContent = (LinearLayout) findViewById(R.id.title_bar_search_content);
        rlNormalContent = (RelativeLayout) findViewById(R.id.title_bar_normal_content);
        cancelBtn = (TextView) findViewById(R.id.tv_title_bar_cancel_btn);
        tvSearchBox = (MockAutoCompleteTextView) findViewById(R.id.ac_tv_search_box);
        titleBottomLine = findViewById(R.id.title_bottom_line);
    }

    public TitleBarLeftBtnOnClickListener getLeftBtnClickListener() {
        return leftBtnClickListener;
    }

    public void setLeftBtnClickListener(TitleBarLeftBtnOnClickListener leftBtnClickListener) {
        this.leftBtnClickListener = leftBtnClickListener;
        initLeftBtnListener();
    }

    public TitleBarRightBtnOnClickListener getRightBtnClickListener() {
        return rightBtnClickListener;
    }

    public void setRightBtnClickListener(TitleBarRightBtnOnClickListener rightBtnClickListener) {
        this.rightBtnClickListener = rightBtnClickListener;
        initRightBtnListener();
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
        initAdapter();
    }

    public void setDataList(String... dataList) {
        this.dataList = new ArrayList<>(Arrays.asList(dataList));
        initAdapter();
    }

    public interface TitleBarLeftBtnOnClickListener {
        void onTitleBarLeftBtnClick(View v);
    }

    public interface TitleBarRightBtnOnClickListener {
        void onTitleBarRightBtnClick(View v);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        tvCenterText.setText(title);
    }

    /**
     * 关闭页面
     */
    public void closePage() {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            activity.onBackPressed();
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setTvCenterText(TextView tvCenterText) {
        this.tvCenterText = tvCenterText;
    }

    public void setIvLeftBtn(ImageView ivLeftBtn) {
        this.ivLeftBtn = ivLeftBtn;
    }

    public void setTvRightBtn(TextView tvRightBtn) {
        this.tvRightBtn = tvRightBtn;
    }

    public void setCancelBtn(TextView cancelBtn) {
        this.cancelBtn = cancelBtn;
    }

    public TextView getTvRightBtn() {
        return tvRightBtn;
    }

    public void setTvRightBtnVisibility(boolean visibility) {
        if (tvRightBtn == null) {
            return;
        }
        if (visibility) {
            tvRightBtn.setVisibility(VISIBLE);
            return;
        }
        tvRightBtn.setVisibility(GONE);
    }

    public void setTitleBottomLineVisibility(int visibility) {
        if (titleBottomLine == null) {
            return;
        }
        titleBottomLine.setVisibility(visibility);

    }

    public void setAdjustTransparentStatusBar(boolean isAdjust) {
        rlNormalContent.setPadding(
                0,
                (isAdjust && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        ? ScreenUtil.getStatusBarHeight(getContext()) : 0,
                0,
                0
        );

    }
}
