package com.example.play_de.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play_de.chat.ChatActivity;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.R;
import com.example.play_de.main.OnClickReportListener;
import com.example.play_de.profile.ProfileActivity;
import com.hedgehog.ratingbar.RatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommunityFragment extends Fragment implements OnBackPressedListener, OnClickReportListener {
    private Context context;
    private StringBuilder urlStr;
    private MainActivity main;
    private View view;
    private ImageButton back, plusBtn, userBtn;
    private EditText filterEdit;

    private LinearLayout community_view01;
    private CommunityTagAdapter communityTagAdapter;

    private LinearLayout write_view;
    private EditText write_editText;
    private Button write_btn;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CommunityRecyclerAdapter communityRecyclerAdapter;

    private RelativeLayout community_view02;
    private ImageView image;
    private TextView name, level, content, heart;

    private int board_id;
    private CommunityCommentAdapter comment_adapter;
    private EditText msg_edit;
    private ImageView sendBtn;

    private LinearLayout profile_view;
    private ImageView profile_image;
    private TextView profile_name, profile_level, send_message;
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
        level = view.findViewById(R.id.level);
        content = view.findViewById(R.id.content);
        heart = view.findViewById(R.id.heart);

        communityTagAdapter = new CommunityTagAdapter();
        RecyclerView tag_recyclerView = view.findViewById(R.id.tag_recycler);
        RecyclerView.LayoutManager tagLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        tag_recyclerView.setLayoutManager(tagLayoutManager);
        tag_recyclerView.setAdapter(communityTagAdapter);
        addTagRecyclerView();

        communityRecyclerAdapter = new CommunityRecyclerAdapter();
        RecyclerView community_recyclerView = view.findViewById(R.id.community_recycler);
        RecyclerView.LayoutManager communityLayoutManager = new LinearLayoutManager(getActivity());
        community_recyclerView.setLayoutManager(communityLayoutManager);
        community_recyclerView.setAdapter(communityRecyclerAdapter);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_community);

        write_editText = view.findViewById(R.id.write_editText);
        write_btn = view.findViewById(R.id.write_btn);

        msg_edit = view.findViewById(R.id.msg_edit);
        sendBtn = view.findViewById(R.id.sendBtn);

        profile_image = view.findViewById(R.id.profile_image);
        profile_name = view.findViewById(R.id.profile_name);
        profile_level = view.findViewById(R.id.profile_level);
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

    private void eventListener() {
        back.setOnClickListener(v -> backView());

        plusBtn.setOnClickListener(v -> {
            community_view01.setVisibility(View.GONE);
            write_view.setVisibility(View.VISIBLE);
        });

        userBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileActivity.class);
            startActivity(intent);
        });

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

        heart.setOnClickListener(v -> {
            //공감하기 갯수 올리기
        });

        communityRecyclerAdapter.setOnItemClickListener((component, position) -> {
            if (component == 1) {
                //image 클릭, chat 만들기
                Intent intent = new Intent(requireContext(), ChatActivity.class);
                intent.putExtra("destinationName", communityRecyclerAdapter.getData(position).name);
                intent.putExtra("destinationUid", communityRecyclerAdapter.getData(position).uid);
                startActivity(intent);
            } else if (component == 2) {
                //공감하기 버튼 클릭
            } else if (component == 3) {
                // 댓글 달기
                community_view01.setVisibility(View.GONE);
                community_view02.setVisibility(View.VISIBLE);
//                Glide.with(requireContext())
//                        .load(communityRecyclerAdapter.getData(position).image)
//                        .apply(new RequestOptions().circleCrop())
//                        .into(image);
                image.setImageResource(Integer.parseInt(communityRecyclerAdapter.getData(position).image));
                name.setText(communityRecyclerAdapter.getData(position).name);
                level.setText(communityRecyclerAdapter.getData(position).level);
                content.setText(communityRecyclerAdapter.getData(position).comment);

                board_id = communityRecyclerAdapter.getData(position).write_id;
                refreshComment(board_id);
            }
        });
        refreshCommunityWrite();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            refreshCommunityWrite();
            mSwipeRefreshLayout.setRefreshing(false);
        });

        write_btn.setOnClickListener(v -> {
            write_community();
        });

        sendBtn.setOnClickListener(v -> {
            write_comment();
        });

        send_message.setOnClickListener(v -> {
            //메시지 보내기.
        });

        blockBtn.setOnClickListener(v -> {
            //차단하기.
        });

        //User Model 에 heart 갯수 추가하기.
        heart_rating.setStar(3);

        comment_adapter.setOnItemClickListener((component, position) -> {
            if (component == 0) {
                community_view02.setVisibility(View.GONE);
                profile_view.setVisibility(View.VISIBLE);
                profile_image.setImageResource(Integer.parseInt(comment_adapter.getData(position).image));
                profile_name.setText(comment_adapter.getData(position).name);
                profile_level.setText(comment_adapter.getData(position).level);
                //하단에 하트와 가게도 set 하도록 연결할 것.
            } else if (component == 1) {
                //답글쓰기
            } else if (component == 2) {
                //신고하기
                main.showBlur_report(true);
            }
        });

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

    private void write_community() {
        //글 올리기
        urlStr = new StringBuilder();
        urlStr.append("https://playde-server-pzovl.run.goorm.io/community/board/upload?user_id=");
        urlStr.append(MainActivity.userId);
        urlStr.append("&content=");
        urlStr.append(write_editText.getText().toString());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    if (response.equals("SUCCESS")) {
                        Toast.makeText(context, "업로드 완료", Toast.LENGTH_SHORT).show();
                    } else if (response.equals("FAIL")) {
                        Toast.makeText(context, "업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("communityWrite", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
        backView();
    }

    private void write_comment() {
        //댓글 쓰기
        urlStr = new StringBuilder();
        urlStr.append("https://playde-server-pzovl.run.goorm.io/community/comment/upload?user_id=");
        urlStr.append(MainActivity.userId);
        urlStr.append("&board_id=");
        urlStr.append(Integer.toString(board_id));
        urlStr.append("&content=");
        urlStr.append(msg_edit.getText().toString());
        msg_edit.setText("");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    if (response.equals("SUCCESS")) {
                        Toast.makeText(context, "업로드 완료", Toast.LENGTH_SHORT).show();
                    } else if (response.equals("FAIL")) {
                        Toast.makeText(context, "업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("sendComment", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
        refreshComment(board_id);
    }

    private void refreshCommunityWrite() {
        //커뮤니티 글 새로고침
        communityRecyclerAdapter.initialSetUp();
        urlStr = new StringBuilder();
        urlStr.append("https://playde-server-pzovl.run.goorm.io/community/get?user_id=");
        urlStr.append(MainActivity.userId);
        urlStr.append("&range=1,10&top_id=1");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    Log.e("JSONParse", response);
                    communityJSONParse(response);
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("communityRefresh", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void refreshComment(int write_id) {
        //커뮤니티 글 상세보기
        comment_adapter.initialSetUp();
        urlStr = new StringBuilder();
        urlStr.append("https://playde-server-pzovl.run.goorm.io/community/board/");
        urlStr.append(Integer.toString(write_id));
        urlStr.append("?user_id=");
        urlStr.append(MainActivity.userId);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    commentJSONParse(response);
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("commentRefresh", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void addCommunityRecyclerView(int write_id, String image, String name, String comment, int uid) {
        CommunityItem item = new CommunityItem();
        item.write_id = write_id;
        if (image.equals(""))
            item.image = Integer.toString(R.drawable.circle_grey);
        else
            item.image = image;
        item.name = name;
        item.level = "보드게임러버";
        item.comment = comment;
        item.uid = uid;
        communityRecyclerAdapter.addItem(item);
        communityRecyclerAdapter.notifyDataSetChanged();
    }

    private void addCommentRecyclerView(int comment_id, String content, int id, String name) {
        CommunityItem item = new CommunityItem();
        item.write_id = comment_id;
        item.image = Integer.toString(R.drawable.circle_grey);
        item.name = name;
        item.level = "보드게임러버";
        item.comment = content;
        item.uid = id;
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

    private void addTagRecyclerView() {
        String item = "#추천해요";
        communityTagAdapter.addItem(item);
        item = "#만나요";
        communityTagAdapter.addItem(item);
        item = "#질문있어요";
        communityTagAdapter.addItem(item);
        item = "#보드게임 소식";
        communityTagAdapter.addItem(item);
        item = "#일상";
        communityTagAdapter.addItem(item);
        item = "#수다";
        communityTagAdapter.addItem(item);
        communityTagAdapter.notifyDataSetChanged();
    }

    private void communityJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String community = jsonObject.getString("community");
            JSONArray jsonArray = new JSONArray(community);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                String board = subJsonObject.getString("board");
                JSONObject subJsonObject2 = new JSONObject(board);
                int write_id = subJsonObject2.getInt("id");
                String content = subJsonObject2.getString("content");

                String writer = subJsonObject.getString("writer");
                JSONObject subJsonObject3 = new JSONObject(writer);
                String nickname = subJsonObject3.getString("nickname");
                String profile = subJsonObject3.getString("profile");
                int id = subJsonObject3.getInt("id");
                addCommunityRecyclerView(write_id, profile, nickname, content, id);
            }
        } catch (Exception e) {
            Log.e("communityJSONParse", "예외 발생");
        }
    }

    private void commentJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String comment = jsonObject.getString("comment");
            JSONArray jsonArray = new JSONArray(comment);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                int comment_id = subJsonObject.getInt("id");
                String content = subJsonObject.getString("content");

                JSONObject subJsonObject2 = subJsonObject.getJSONObject("writer");
                int id = subJsonObject2.getInt("id");
                String name = subJsonObject2.getString("nickname");

                addCommentRecyclerView(comment_id, content, id, name);
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
    }

    @Override
    public void onClickReport() {
        //신고하기 버튼
        community_view02.setVisibility(View.GONE);
        report_layout01.setVisibility(View.VISIBLE);
    }
}
