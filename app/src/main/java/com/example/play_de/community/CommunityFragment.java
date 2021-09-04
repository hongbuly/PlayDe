package com.example.play_de.community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.play_de.chat.ChatActivity;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.R;
import com.example.play_de.main.OnClickRemoveListener;
import com.example.play_de.main.OnClickReportListener;
import com.example.play_de.profile.ProfileActivity;
import com.hedgehog.ratingbar.RatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommunityFragment extends Fragment implements OnBackPressedListener, OnClickReportListener, OnClickRemoveListener {
    private Context context;
    private MainActivity main;
    private View view;
    private ImageButton back, plusBtn, userBtn;
    private EditText filterEdit;

    private RelativeLayout community_view01;
    private Button recommendBtn, meetBtn, questionBtn, newsBtn;
    private int whiteColor, greyColor;
    private int setBtn = 0;

    private RelativeLayout write_view;
    private TextView bulletin;
    private EditText write_editText;
    private Button write_btn;
    private View blur;
    private RelativeLayout bulletin_view;
    private ImageView x;
    private TextView recommend, meet, question, news;
    private int selected_bulletin = -1;
    private String[] selected_tag = {"추천해요", "만나요", "질문있어요", "최근 소식"};

    private SwipeRefreshLayout swipeRefreshCommunity;
    private CommunityRecyclerAdapter communityRecyclerAdapter;

    private RelativeLayout community_view02;
    private ImageView image;
    private TextView name, content, heart, comment, read, time, tag;

    private int comment_id; //댓글 id
    private int community_position; //community adapter 에서 position
    private int board_id; //데이터 베이스에서 커뮤니티 id
    private SwipeRefreshLayout swipeRefreshComment;
    private CommunityCommentAdapter comment_adapter;
    private EditText msg_edit;
    private ImageView sendBtn;
    private boolean second_comment = false;

    private LinearLayout profile_view;
    private int profile_position;
    private ImageView profile_image;
    private TextView profile_name, send_message;
    private RatingBar heart_rating;
    private ImageButton blockBtn;

    private CommunityProfileFavoriteAdapter heart_adapter;
    private CommunityProfileFavoriteAdapter store_adapter;

    private LinearLayout report_layout01;
    private TextView[] _report_reason = new TextView[8];

    private RelativeLayout report_layout02;
    private boolean is_no_see = false;
    private TextView report_reason, report_no_see;
    private Button report_btn01;

    private RelativeLayout report_layout03;
    private EditText report_edit;
    private Button report_btn02;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_community, container, false);

        initialSetUp();
        eventListener();

        return view;
    }

    private void initialSetUp() {
        community_view01 = view.findViewById(R.id.community_view01);
        write_view = view.findViewById(R.id.write_view);
        community_view02 = view.findViewById(R.id.community_view02);
        profile_view = view.findViewById(R.id.profile_view);
        report_layout01 = view.findViewById(R.id.report_layout01);
        report_layout02 = view.findViewById(R.id.report_layout02);
        report_layout03 = view.findViewById(R.id.report_layout03);

        back = view.findViewById(R.id.backBtn);
        plusBtn = view.findViewById(R.id.plusBtn);
        userBtn = view.findViewById(R.id.userBtn);

        filterEdit = view.findViewById(R.id.filterEdit);

        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        content = view.findViewById(R.id.content);
        heart = view.findViewById(R.id.heart);
        comment = view.findViewById(R.id.comment);
        read = view.findViewById(R.id.read);
        time = view.findViewById(R.id.time);
        tag = view.findViewById(R.id.tag);

        recommendBtn = view.findViewById(R.id.recommendBtn);
        meetBtn = view.findViewById(R.id.meetBtn);
        questionBtn = view.findViewById(R.id.questionBtn);
        newsBtn = view.findViewById(R.id.newsBtn);

        whiteColor = ContextCompat.getColor(context, R.color.White);
        greyColor = ContextCompat.getColor(context, R.color.LineGrey);

        communityRecyclerAdapter = new CommunityRecyclerAdapter();
        RecyclerView community_recyclerView = view.findViewById(R.id.community_recycler);
        RecyclerView.LayoutManager communityLayoutManager = new LinearLayoutManager(getActivity());
        community_recyclerView.setLayoutManager(communityLayoutManager);
        community_recyclerView.setAdapter(communityRecyclerAdapter);

        swipeRefreshCommunity = view.findViewById(R.id.swipe_community);
        swipeRefreshComment = view.findViewById(R.id.swipe_comment);

        bulletin = view.findViewById(R.id.bulletin);
        write_editText = view.findViewById(R.id.write_editText);
        write_btn = view.findViewById(R.id.write_btn);
        blur = view.findViewById(R.id.blur);
        bulletin_view = view.findViewById(R.id.bulletin_view);
        x = view.findViewById(R.id.x);
        recommend = view.findViewById(R.id.recommend);
        meet = view.findViewById(R.id.meet);
        question = view.findViewById(R.id.question);
        news = view.findViewById(R.id.news);

        msg_edit = view.findViewById(R.id.msg_edit);
        sendBtn = view.findViewById(R.id.sendBtn);

        profile_image = view.findViewById(R.id.profile_image);
        profile_name = view.findViewById(R.id.profile_name);
        send_message = view.findViewById(R.id.send_message);
        blockBtn = view.findViewById(R.id.blockBtn);
        heart_rating = view.findViewById(R.id.heart_rating);

        comment_adapter = new CommunityCommentAdapter();
        RecyclerView comment_recyclerView = view.findViewById(R.id.comment_recycler);
        RecyclerView.LayoutManager commentLayoutManager = new LinearLayoutManager(getActivity());
        comment_recyclerView.setLayoutManager(commentLayoutManager);
        comment_recyclerView.setAdapter(comment_adapter);

        heart_adapter = new CommunityProfileFavoriteAdapter();
        RecyclerView heart_recyclerView = view.findViewById(R.id.heart_recycler);
        RecyclerView.LayoutManager heartLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        heart_recyclerView.setLayoutManager(heartLayoutManager);
        heart_recyclerView.setAdapter(heart_adapter);
        addHeartRecyclerView();

        store_adapter = new CommunityProfileFavoriteAdapter();
        RecyclerView store_recyclerView = view.findViewById(R.id.store_recycler);
        RecyclerView.LayoutManager storeLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        store_recyclerView.setLayoutManager(storeLayoutManager);
        store_recyclerView.setAdapter(store_adapter);
        addStoreRecyclerView();

        _report_reason[0] = view.findViewById(R.id.report_reason01);
        _report_reason[1] = view.findViewById(R.id.report_reason02);
        _report_reason[2] = view.findViewById(R.id.report_reason03);
        _report_reason[3] = view.findViewById(R.id.report_reason04);
        _report_reason[4] = view.findViewById(R.id.report_reason05);
        _report_reason[5] = view.findViewById(R.id.report_reason06);
        _report_reason[6] = view.findViewById(R.id.report_reason07);
        _report_reason[7] = view.findViewById(R.id.report_reason08);

        report_reason = view.findViewById(R.id.report_reason);
        report_no_see = view.findViewById(R.id.report_no_see);
        report_btn01 = view.findViewById(R.id.report_btn01);

        report_edit = view.findViewById(R.id.report_edit);
        report_btn02 = view.findViewById(R.id.report_btn02);
    }

    @SuppressLint("SetTextI18n")
    private void eventListener() {
        //뒤로 가기 버튼
        back.setOnClickListener(v -> backView());

        //글 올리는 오른쪽 하단 플러스 버튼
        plusBtn.setOnClickListener(v -> {
            community_view01.setVisibility(View.GONE);
            write_view.setVisibility(View.VISIBLE);
        });

        //오른쪽 상단 프로필 버튼
        userBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileActivity.class);
            startActivity(intent);
        });

        //editText 검색
        filterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력되기 전에
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //텍스트가 변경 될때마다
            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력된 후
            }
        });

        //#추천해요 버튼
        recommendBtn.setOnClickListener(v -> {
            changeBtn(0);
        });

        //#만나요 버튼
        meetBtn.setOnClickListener(v -> {
            changeBtn(1);
        });

        //#질문 있어요 버튼
        questionBtn.setOnClickListener(v -> {
            changeBtn(2);
        });

        //#최근 소식 버튼
        newsBtn.setOnClickListener(v -> {
            changeBtn(3);
        });

        communityRecyclerAdapter.setOnItemClickListener((component, position) -> {
            if (component == 1) {
                //image 클릭, chat 만들기
                if (communityRecyclerAdapter.getData(position).uid != Integer.parseInt(MainActivity.userId)) {
                    Intent intent = new Intent(requireContext(), ChatActivity.class);
                    intent.putExtra("destinationName", communityRecyclerAdapter.getData(position).name);
                    intent.putExtra("destinationUid", communityRecyclerAdapter.getData(position).uid);
                    startActivity(intent);
                }
            } else if (component == 2) {
                //공감하기 버튼 클릭
                board_id = communityRecyclerAdapter.getData(position).write_id;
                communityRecyclerAdapter.setHeart(position);
                communityRecyclerAdapter.notifyDataSetChanged();
                clickHeart();
            } else if (component == 3) {
                // 댓글 달기
                community_view01.setVisibility(View.GONE);
                community_view02.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(communityRecyclerAdapter.getData(position).image)
                        .apply(new RequestOptions().circleCrop())
                        .into(image);
                name.setText(communityRecyclerAdapter.getData(position).name);
                content.setText(communityRecyclerAdapter.getData(position).comment);
                read.setText(Integer.toString(communityRecyclerAdapter.getData(position).visit));
                time.setText(communityRecyclerAdapter.getData(position).time);
                heart.setText("공감 " + communityRecyclerAdapter.getData(position).like);
                comment.setText("댓글 " + communityRecyclerAdapter.getData(position).comment_cnt);
                tag.setText(communityRecyclerAdapter.getData(position).tag);

                board_id = communityRecyclerAdapter.getData(position).write_id;
                community_position = position;
                refreshComment(board_id);
            } else if (component == 4) {
                //three dot 클릭, 삭제
                board_id = communityRecyclerAdapter.getData(position).write_id;
                main.showBlur_remove(true);
            }
        });
        refreshCommunityWrite();

        swipeRefreshCommunity.setOnRefreshListener(() -> {
            refreshCommunityWrite();
            swipeRefreshCommunity.setRefreshing(false);
        });

        swipeRefreshComment.setOnRefreshListener(() -> {
            refreshComment(board_id);
            swipeRefreshComment.setRefreshing(false);
        });

        //게시판 선택 다이얼로그
        bulletin.setOnClickListener(v -> {
            blur.setVisibility(View.VISIBLE);
            bulletin_view.setVisibility(View.VISIBLE);
        });

        blur.setOnClickListener(v -> {
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        x.setOnClickListener(v -> {
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        //다이얼로그
        recommend.setOnClickListener(v -> {
            selected_bulletin = 0;
            bulletin.setText("#추천해요");
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        meet.setOnClickListener(v -> {
            selected_bulletin = 1;
            bulletin.setText("#만나요");
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        question.setOnClickListener(v -> {
            selected_bulletin = 2;
            bulletin.setText("#질문있어요");
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        news.setOnClickListener(v -> {
            selected_bulletin = 3;
            bulletin.setText("#최신 소식");
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        //글 올리기 버튼
        write_btn.setOnClickListener(v -> {
            if (selected_bulletin == -1) {
                Toast.makeText(context, "게시판을 선택해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                write_community();
                selected_bulletin = -1;
                bulletin.setText("게시판 선택");
            }
        });

        comment_adapter.setOnItemClickListener((component, position) -> {
            if (component == 0) {
                //이미지 클릭 -> 프로필 보여주기
                community_view02.setVisibility(View.GONE);
                profile_view.setVisibility(View.VISIBLE);
                if (comment_adapter.getData(position).image.equals("")) {
                    profile_image.setImageResource(R.drawable.circle_grey);
                } else {
                    Uri uri = Uri.parse(comment_adapter.getData(position).image);
                    Glide.with(profile_image.getContext())
                            .load(uri)
                            .apply(new RequestOptions().circleCrop())
                            .into(profile_image);
                }
                profile_name.setText(comment_adapter.getData(position).name);
                profile_position = position;
                //하단에 하트와 가게도 set 하도록 연결할 것.
            } else if (component == 1) {
                //답글쓰기, msg_edit 포커스 주기.
                msg_edit.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                second_comment = true;
                comment_id = comment_adapter.getData(position).write_id;
            } else if (component == 2) {
                //신고하기
                main.showBlur_report(true);
            }
        });

        //커뮤니티 상세보기 하트 버튼
        heart.setOnClickListener(v -> {
            communityRecyclerAdapter.setHeart(community_position);
            communityRecyclerAdapter.notifyDataSetChanged();
            clickHeart();
        });

        sendBtn.setOnClickListener(v -> {
                    write_comment();
                    refreshComment(board_id);
                }
        );

        //프로필 상세보기에서
        send_message.setOnClickListener(v -> {
            //메시지 보내기.
            if (communityRecyclerAdapter.getData(profile_position).uid != Integer.parseInt(MainActivity.userId)) {
                Intent intent = new Intent(requireContext(), ChatActivity.class);
                intent.putExtra("destinationName", communityRecyclerAdapter.getData(profile_position).name);
                intent.putExtra("destinationUid", communityRecyclerAdapter.getData(profile_position).uid);
                startActivity(intent);
            }
        });

        blockBtn.setOnClickListener(v -> {
            //차단하기.
            onClickReport();
        });

        //User Model 에 heart 갯수 추가하기.
        heart_rating.setStar(3);

        for (int i = 0; i < 7; i++) {
            int finalI = i;
            _report_reason[i].setOnClickListener(v -> {
                report_layout01.setVisibility(View.GONE);
                report_layout02.setVisibility(View.VISIBLE);
                report_reason.setText(_report_reason[finalI].getText().toString());
            });
        }

        _report_reason[7].setOnClickListener(v -> {
            report_layout01.setVisibility(View.GONE);
            report_layout03.setVisibility(View.VISIBLE);
        });

        report_no_see.setOnClickListener(v -> {
            if (is_no_see) {
                report_no_see.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_corner_grey, 0, 0, 0);
                is_no_see = false;
            } else {
                report_no_see.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_gradient, 0, 0, 0);
                is_no_see = true;
            }
        });

        report_btn01.setOnClickListener(v -> {
            Toast.makeText(context, "신고되었습니다.", Toast.LENGTH_SHORT).show();
            backView();
        });

        report_btn02.setOnClickListener(v -> {
            Toast.makeText(context, "신고되었습니다.", Toast.LENGTH_SHORT).show();
            backView();
        });
    }

    private void changeBtn(int setBtn) {
        if (setBtn == 0) {
            recommendBtn.setTextColor(whiteColor);
            recommendBtn.setBackgroundResource(R.drawable.round_red20);
        } else if (setBtn == 1) {
            meetBtn.setTextColor(whiteColor);
            meetBtn.setBackgroundResource(R.drawable.round_red20);
        } else if (setBtn == 2) {
            questionBtn.setTextColor(whiteColor);
            questionBtn.setBackgroundResource(R.drawable.round_red20);
        } else if (setBtn == 3) {
            newsBtn.setTextColor(whiteColor);
            newsBtn.setBackgroundResource(R.drawable.round_red20);
        }

        if (this.setBtn == 0) {
            recommendBtn.setTextColor(greyColor);
            recommendBtn.setBackgroundResource(R.drawable.round_corner_line20);
        } else if (this.setBtn == 1) {
            meetBtn.setTextColor(greyColor);
            meetBtn.setBackgroundResource(R.drawable.round_corner_line20);
        } else if (this.setBtn == 2) {
            questionBtn.setTextColor(greyColor);
            questionBtn.setBackgroundResource(R.drawable.round_corner_line20);
        } else if (this.setBtn == 3) {
            newsBtn.setTextColor(greyColor);
            newsBtn.setBackgroundResource(R.drawable.round_corner_line20);
        }

        this.setBtn = setBtn;
        refreshCommunityWrite();
    }

    private void write_community() {
        //글 올리기
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("/community/board/upload?user_id=");
        urlStr.append(MainActivity.userId);
        urlStr.append("&content=");
        urlStr.append(write_editText.getText().toString());
        urlStr.append("&tag=");
        urlStr.append(selected_tag[selected_bulletin]);
        Log.e("writeCommunity", urlStr.toString());
        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean act = jsonObject.getBoolean("act");
                        if (act) {
                            Toast.makeText(context, "업로드되었습니다.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "업로드를 실패했습니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("write_community", "예외 발생");
                    }
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("communityWrite", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
        refreshCommunityWrite();
        backView();
    }

    private void clickHeart() {
        //공감하기 버튼
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("/community/board/like?user_id=");
        urlStr.append(MainActivity.userId);
        urlStr.append("&board_id=");
        urlStr.append(board_id);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean act = jsonObject.getBoolean("act");
                        if (act)
                            Log.e("clickHeart", "true");
                        else
                            Log.e("clickHeart", "false");
                        Log.e("clickHeart", "업로드");
                    } catch (Exception e) {
                        Log.e("clickHeart", "예외 발생");
                    }
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("clickHeart", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void write_comment() {
        //댓글 쓰기
        if (second_comment) {
            //대댓글 쓰기
            StringBuilder urlStr = new StringBuilder();
            urlStr.append(MainActivity.mainUrl);
            urlStr.append("/community/reply/upload?user_id=");
            urlStr.append(MainActivity.userId);
            urlStr.append("&comment_id=");
            urlStr.append(comment_id);
            urlStr.append("&content=");
            urlStr.append(msg_edit.getText().toString());
            msg_edit.setText("");
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    urlStr.toString(),
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean act = jsonObject.getBoolean("act");
                            if (act) {
                                Toast.makeText(context, "업로드되었습니다.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "업로드를 실패했습니다.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("write_comment", "예외 발생");
                        }
                    },
                    error -> {
                        Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                        Log.e("writeComment", "에러 발생");
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    return new HashMap<>();
                }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue = Volley.newRequestQueue(context);
            AppHelper.requestQueue.add(request);
            second_comment = false;
        } else {
            StringBuilder urlStr = new StringBuilder();
            urlStr.append(MainActivity.mainUrl);
            urlStr.append("/community/comment/upload?user_id=");
            urlStr.append(MainActivity.userId);
            urlStr.append("&board_id=");
            urlStr.append(board_id);
            urlStr.append("&content=");
            urlStr.append(msg_edit.getText().toString());
            msg_edit.setText("");
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    urlStr.toString(),
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean act = jsonObject.getBoolean("act");
                            if (act) {
                                Toast.makeText(context, "업로드되었습니다.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "업로드를 실패했습니다.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("write_comment", "예외 발생");
                        }
                    },
                    error -> {
                        Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                        Log.e("writeComment", "에러 발생");
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    return new HashMap<>();
                }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue = Volley.newRequestQueue(context);
            AppHelper.requestQueue.add(request);
        }
    }

    private void refreshCommunityWrite() {
        //커뮤니티 글 새로고침
        communityRecyclerAdapter.initialSetUp();
        communityRecyclerAdapter.notifyDataSetChanged();
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("/community/get?user_id=");
        urlStr.append(MainActivity.userId);
        urlStr.append("&range=1,50&tag=");
        urlStr.append(selected_tag[setBtn]);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr.toString(),
                response -> {
                    Log.e("communityJSONParse", response);
                    communityJSONParse(response);
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("communityRefresh", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void refreshComment(int write_id) {
        //커뮤니티 글 상세보기
        comment_adapter.initialSetUp();
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("/community/board/");
        urlStr.append(write_id);
        urlStr.append("?user_id=");
        urlStr.append(MainActivity.userId);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr.toString(),
                this::commentJSONParse,
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("commentRefresh", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void addCommunityRecyclerView(int write_id, String image, String name, String comment, int uid, int like, boolean my_like, String time, int visit, String tag, int comment_cnt) {
        CommunityItem item = new CommunityItem();
        item.write_id = write_id;
        if (!image.equals(""))
            item.image = image;
        item.name = name;
        item.comment = comment;
        item.uid = uid;
        item.like = like;
        item.my_like = my_like;
        int hour = Integer.parseInt(time.substring(11, 13));
        if (hour == 12) {
            item.time = "오후 " + time.substring(11, 16);
        } else if (hour > 12) {
            item.time = "오후 " + (Integer.parseInt(time.substring(11, 13)) - 12) + time.substring(13, 16);
        } else {
            item.time = "오전 " + time.substring(11, 16);
        }
        item.comment_cnt = comment_cnt;
        item.visit = visit;
        item.tag = "#" + tag;
        communityRecyclerAdapter.addItem(item);
        communityRecyclerAdapter.notifyDataSetChanged();
    }

    private void addCommentRecyclerView(int comment_id, String content, boolean second_comment, int id, String name, String profile) {
        CommunityItem item = new CommunityItem();
        item.write_id = comment_id;
        item.image = Integer.toString(R.drawable.circle_grey);
        item.name = name;
        item.second_comment = second_comment;
        item.comment = content;
        item.uid = id;
        item.image = profile;
        comment_adapter.addItem(item);
        comment_adapter.notifyDataSetChanged();
    }

    private void addHeartRecyclerView() {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.image = R.drawable.rumicube;
        item.name = "루미큐브";
        heart_adapter.addItem(item);

        item.image = R.drawable.cluedo;
        item.name = "클루";
        heart_adapter.addItem(item);

        item.image = R.drawable.ticket_to_ride;
        item.name = "티켓투라이드";
        heart_adapter.addItem(item);

        item.image = R.drawable.uno;
        item.name = "우노";
        heart_adapter.addItem(item);

        item.image = R.drawable.diamond;
        item.name = "다이아몬드";
        heart_adapter.addItem(item);

        heart_adapter.notifyDataSetChanged();
    }

    private void addStoreRecyclerView() {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.image = R.drawable.rumicube;
        item.name = "루미큐브";
        store_adapter.addItem(item);

        item.image = R.drawable.cluedo;
        item.name = "클루";
        store_adapter.addItem(item);

        item.image = R.drawable.ticket_to_ride;
        item.name = "티켓투라이드";
        store_adapter.addItem(item);

        item.image = R.drawable.uno;
        item.name = "우노";
        store_adapter.addItem(item);

        item.image = R.drawable.diamond;
        item.name = "다이아몬드";
        store_adapter.addItem(item);

        store_adapter.notifyDataSetChanged();
    }

    //글 목록 불러오기
    @SuppressLint("SetTextI18n")
    private void communityJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String meta = jsonObject.getString("meta");
            JSONObject subJsonObject01 = new JSONObject(meta);
            int count = subJsonObject01.getInt("count");
            filterEdit.setHint("전체 " + count);

            String community = jsonObject.getString("community");
            JSONArray jsonArray = new JSONArray(community);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                String board = subJsonObject.getString("board");
                JSONObject subJsonObject2 = new JSONObject(board);
                int write_id = subJsonObject2.getInt("id"); //커뮤니티 글 id
                String content = subJsonObject2.getString("content");
                int like = subJsonObject2.getInt("like");
                boolean my_like = subJsonObject2.getBoolean("my_like");
                String time = subJsonObject2.getString("created_at");
                int visit = subJsonObject2.getInt("visit");
                String tag = subJsonObject2.getString("tag");
                int comment_cnt = subJsonObject2.getInt("comment_cnt");

                String writer = subJsonObject.getString("writer");
                JSONObject subJsonObject3 = new JSONObject(writer);
                String nickname = subJsonObject3.getString("nickname");
                String profile = subJsonObject3.getString("profile");
                int id = subJsonObject3.getInt("id");
                addCommunityRecyclerView(write_id, profile, nickname, content, id, like, my_like, time, visit, tag, comment_cnt);
            }
        } catch (Exception e) {
            Log.e("communityJSONParse", "예외 발생");
        }
    }

    //커뮤니티 글 상세보기
    @SuppressLint("SetTextI18n")
    private void commentJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject subJsonObject01 = new JSONObject(jsonObject.getString("board"));
            int like = subJsonObject01.getInt("like");
            heart.setText("공감 " + like);

            String comment = jsonObject.getString("comment");
            JSONArray jsonArray = new JSONArray(comment);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                int comment_id = subJsonObject.getInt("id");
                String content = subJsonObject.getString("content");
                boolean second_comment = subJsonObject.getBoolean("reply");

                JSONObject subJsonObject2 = subJsonObject.getJSONObject("writer");
                int id = subJsonObject2.getInt("id");
                String name = subJsonObject2.getString("nickname");
                String profile = subJsonObject2.getString("profile");

                addCommentRecyclerView(comment_id, content, second_comment, id, name, profile);
            }
        } catch (Exception e) {
            Log.e("commentJSONParse", "예외 발생");
        }
    }

    @Override
    public void onBackPressed() {
        backView();
    }

    private void backView() {
        if (report_layout03.getVisibility() == View.VISIBLE || report_layout02.getVisibility() == View.VISIBLE || report_layout01.getVisibility() == View.VISIBLE) {
            report_layout03.setVisibility(View.GONE);
            report_layout02.setVisibility(View.GONE);
            report_layout01.setVisibility(View.GONE);
            community_view02.setVisibility(View.VISIBLE);
        } else if (profile_view.getVisibility() == View.VISIBLE) {
            profile_view.setVisibility(View.GONE);
            community_view02.setVisibility(View.VISIBLE);
        } else if (community_view02.getVisibility() == View.VISIBLE) {
            community_view02.setVisibility(View.GONE);
            community_view01.setVisibility(View.VISIBLE);
        } else if (write_view.getVisibility() == View.VISIBLE) {
            write_view.setVisibility(View.GONE);
            community_view01.setVisibility(View.VISIBLE);
        } else
            main.onBackTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 2);
        main.setOnClickReportListener(this);
        main.setOnClickRemoveListener(this);
    }

    @Override
    public void onClickReport() {
        //신고하기 버튼
        community_view02.setVisibility(View.GONE);
        profile_view.setVisibility(View.GONE);
        report_layout01.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickRemove() {
        //커뮤니티 글 삭제 버튼
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("/community/board/delete?user_id=");
        urlStr.append(MainActivity.userId);
        urlStr.append("&board_id=");
        urlStr.append(board_id);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean act = jsonObject.getBoolean("act");
                        if (act) {
                            Toast.makeText(context, "삭제되었습니다..", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "삭제를 실패했습니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("remove_community", "예외 발생");
                    }
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("remove_community", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
        refreshCommunityWrite();
    }
}
