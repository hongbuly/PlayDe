package com.example.play_de.community;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import com.example.play_de.main.OnClickUrlListener;
import com.example.play_de.profile.ProfileActivity;
import com.hedgehog.ratingbar.RatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommunityFragment extends Fragment implements OnBackPressedListener, OnClickReportListener, OnClickRemoveListener, OnClickUrlListener {
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
    private Button write_btn, moment_write_btn;
    private TextView moment_write_num;
    private View blur;
    private RelativeLayout bulletin_view;
    private ImageView x;
    private TextView recommend, meet, question, news;
    private int selected_bulletin = -1;
    private String[] selected_tag = {"????????????", "?????????", "???????????????", "?????? ??????"};

    private RelativeLayout storage;
    private CommunityStorageAdapter storage_adapter;

    private SwipeRefreshLayout swipeRefreshCommunity;
    private CommunityRecyclerAdapter communityRecyclerAdapter;

    private RelativeLayout community_view02;
    private ImageView image;
    private TextView name, content, heart, comment, read, time, tag;

    private int comment_id; //?????? id
    private int community_position; //community adapter ?????? position
    private int board_id; //????????? ??????????????? ???????????? id
    private SwipeRefreshLayout swipeRefreshComment;
    private CommunityCommentAdapter comment_adapter;
    private EditText msg_edit;
    private ImageView sendBtn;
    private boolean second_comment = false;
    private String block_uid;
    private int isCommunity = 0; //0:??? 1:?????? 2: ?????????

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
        storage = view.findViewById(R.id.storage);
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
        moment_write_btn = view.findViewById(R.id.moment_write_btn);
        moment_write_num = view.findViewById(R.id.moment_write_num);
        blur = view.findViewById(R.id.blur);
        bulletin_view = view.findViewById(R.id.bulletin_view);
        x = view.findViewById(R.id.x);
        recommend = view.findViewById(R.id.recommend);
        meet = view.findViewById(R.id.meet);
        question = view.findViewById(R.id.question);
        news = view.findViewById(R.id.news);

        storage_adapter = new CommunityStorageAdapter();
        RecyclerView storage_recyclerView = view.findViewById(R.id.storage_recycler);
        RecyclerView.LayoutManager storageLayoutManager = new LinearLayoutManager(getActivity());
        storage_recyclerView.setLayoutManager(storageLayoutManager);
        storage_recyclerView.setAdapter(storage_adapter);

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

        store_adapter = new CommunityProfileFavoriteAdapter();
        RecyclerView store_recyclerView = view.findViewById(R.id.store_recycler);
        RecyclerView.LayoutManager storeLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        store_recyclerView.setLayoutManager(storeLayoutManager);
        store_recyclerView.setAdapter(store_adapter);

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
        //?????? ?????? ??????
        back.setOnClickListener(v -> backView());

        //??? ????????? ????????? ?????? ????????? ??????
        plusBtn.setOnClickListener(v -> {
            setStorage();
            community_view01.setVisibility(View.GONE);
            write_view.setVisibility(View.VISIBLE);
        });

        //????????? ?????? ????????? ??????
        userBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileActivity.class);
            startActivity(intent);
        });

        //editText ??????
        filterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //???????????? ??????
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //???????????? ?????? ????????????
            }

            @Override
            public void afterTextChanged(Editable s) {
                //????????? ???
            }
        });

        //#???????????? ??????
        recommendBtn.setOnClickListener(v -> {
            changeBtn(0);
        });

        //#????????? ??????
        meetBtn.setOnClickListener(v -> {
            changeBtn(1);
        });

        //#?????? ????????? ??????
        questionBtn.setOnClickListener(v -> {
            changeBtn(2);
        });

        //#?????? ?????? ??????
        newsBtn.setOnClickListener(v -> {
            changeBtn(3);
        });

        communityRecyclerAdapter.setOnItemClickListener((component, position) -> {
            if (component == 1) {
                //image ??????, chat ?????????
                if (communityRecyclerAdapter.getData(position).uid != Integer.parseInt(MainActivity.userId)) {
                    Intent intent = new Intent(requireContext(), ChatActivity.class);
                    intent.putExtra("destinationName", communityRecyclerAdapter.getData(position).name);
                    intent.putExtra("destinationUid", Integer.toString(communityRecyclerAdapter.getData(position).uid));
                    intent.putExtra("destinationImage", communityRecyclerAdapter.getData(position).image);
                    startActivity(intent);
                }
            } else if (component == 2) {
                //???????????? ?????? ??????
                board_id = communityRecyclerAdapter.getData(position).write_id;
                communityRecyclerAdapter.setHeart(position);
                communityRecyclerAdapter.notifyDataSetChanged();
                clickHeart();
            } else if (component == 3) {
                // ?????? ??????
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
                heart.setText("?????? " + communityRecyclerAdapter.getData(position).like);
                comment.setText("?????? " + communityRecyclerAdapter.getData(position).comment_cnt);
                tag.setText(communityRecyclerAdapter.getData(position).tag);

                board_id = communityRecyclerAdapter.getData(position).write_id;
                community_position = position;
                refreshComment(board_id);
            } else if (component == 4) {
                //three dot ??????, ??????
                board_id = communityRecyclerAdapter.getData(position).write_id;
                main.showBlur_remove(true);

                //????????????
                main.showBlur_report(true);
                block_uid = Integer.toString(communityRecyclerAdapter.getData(position).uid);

                main.showBlur_url(true);

                isCommunity = 0;
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

        //????????? ?????? ???????????????
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

        //???????????????
        recommend.setOnClickListener(v -> {
            selected_bulletin = 0;
            bulletin.setText("#????????????");
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        meet.setOnClickListener(v -> {
            selected_bulletin = 1;
            bulletin.setText("#?????????");
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        question.setOnClickListener(v -> {
            selected_bulletin = 2;
            bulletin.setText("#???????????????");
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        news.setOnClickListener(v -> {
            selected_bulletin = 3;
            bulletin.setText("#?????? ??????");
            blur.setVisibility(View.GONE);
            bulletin_view.setVisibility(View.GONE);
        });

        //??? ????????? ??????
        write_btn.setOnClickListener(v -> {
            if (selected_bulletin == -1) {
                Toast.makeText(context, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            } else {
                write_community();
                selected_bulletin = -1;
                bulletin.setText("????????? ??????");
            }
        });

        moment_write_btn.setOnClickListener(v -> {
            //????????????
            addStorage();
        });

        moment_write_num.setOnClickListener(v -> {
            //???????????? ??????
            write_view.setVisibility(View.GONE);
            storage.setVisibility(View.VISIBLE);
            setStorage();
        });

        storage_adapter.setOnItemClickListener(position -> {
            //????????? ???????????? ????????? ???????????? write_editText ??? set ??????.
            write_editText.setText(storage_adapter.getData(position).content);
            storage.setVisibility(View.GONE);
            write_view.setVisibility(View.VISIBLE);
        });

        comment_adapter.setOnItemClickListener((component, position) -> {
            if (component == 0) {
                //????????? ?????? -> ????????? ????????????
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
                //????????? ????????? ????????? set ????????? ????????? ???.
                setUserInformation(comment_adapter.getData(position).uid);
            } else if (component == 1) {
                //????????????, msg_edit ????????? ??????.
                msg_edit.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                second_comment = true;
                comment_id = comment_adapter.getData(position).write_id;
            } else if (component == 2) {
                //three dot ??????, ??????
                if (comment_adapter.getData(position).second_comment)
                    isCommunity = 2; //?????????
                else
                    isCommunity = 1; //??????

                main.showBlur_remove(true);

                //????????????
                main.showBlur_report(true);
                block_uid = Integer.toString(comment_adapter.getData(position).uid);

                main.showBlur_url(true);
            }
        });

        //???????????? ???????????? ?????? ??????
        heart.setOnClickListener(v -> {
            communityRecyclerAdapter.setHeart(community_position);
            communityRecyclerAdapter.notifyDataSetChanged();
            clickHeart();
        });

        //????????? ??????
        sendBtn.setOnClickListener(v -> {
                    write_comment();
                    refreshComment(board_id);
                }
        );

        //????????? ??????????????????
        send_message.setOnClickListener(v -> {
            //????????? ?????????.
            if (communityRecyclerAdapter.getData(profile_position).uid != Integer.parseInt(MainActivity.userId)) {
                Intent intent = new Intent(requireContext(), ChatActivity.class);
                intent.putExtra("destinationName", communityRecyclerAdapter.getData(profile_position).name);
                intent.putExtra("destinationUid", Integer.toString(communityRecyclerAdapter.getData(profile_position).uid));
                intent.putExtra("destinationImage", communityRecyclerAdapter.getData(profile_position).image);
                startActivity(intent);
            }
        });

        blockBtn.setOnClickListener(v -> {
            //????????????.
            onClickReport();
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
            blocking();
        });

        report_btn02.setOnClickListener(v -> {
            blocking();
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

    private void addStorage() {
        //?????? ????????????
        String urlStr = MainActivity.mainUrl
                + "community/board/upload";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean act = jsonObject.getBoolean("act");
                        if (act) {
                            Toast.makeText(context, "???????????????????????????.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("addStorage", "?????? ??????");
                    }
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("addStorage", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                body.put("content", write_editText.getText().toString());
                body.put("temp", "True");
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
        write_editText.setText(null);
        backView();
    }

    private void setStorage() {
        //?????? ????????? ?????? ????????????
        storage_adapter.initialSetUp();
        String urlStr = MainActivity.mainUrl
                + "community/temp/get";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String meta = jsonObject.getString("meta");
                        JSONObject sub_jsonObject = new JSONObject(meta);
                        String count = Integer.toString(sub_jsonObject.getInt("count"));
                        moment_write_num.setText(count);

                        String community = jsonObject.getString("community");
                        JSONArray jsonArray = new JSONArray(community);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject subJsonObject = jsonArray.getJSONObject(i);
                            int id = subJsonObject.getInt("id");
                            String content = subJsonObject.getString("content");
                            String tag = subJsonObject.getString("tag");
                            //?????? ???????????? ??????!!
                            String time = subJsonObject.getString("created_at");
                            addStorageRecyclerView(id, content, tag, time);
                        }
                    } catch (Exception e) {
                        Log.e("setStorage", "?????? ??????");
                    }
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("setStorage", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                body.put("range", "1, 30");
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void blocking() {
        //????????? ????????????
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("user/block/add");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean act = jsonObject.getBoolean("act");
                        if (act) {
                            Toast.makeText(context, "?????????????????????.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("block_user", "?????? ??????");
                    }
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("block_user", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                body.put("his_id", block_uid);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
        backView();
    }

    private void setUserInformation(int uid) {
        //????????? ?????? ???????????? ????????????
        setFavCafe(Integer.toString(uid));
        setFavGame(Integer.toString(uid));
        setHeart(Integer.toString(uid));
    }

    private void setFavCafe(String uid) {
        //??? ?????? ????????????
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("cafe/fav");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String cafe = jsonObject.getString("cafe");
                        JSONArray jsonArray = new JSONArray(cafe);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject subJsonObject = jsonArray.getJSONObject(i);
                            int id = subJsonObject.getInt("id");
                            String name = subJsonObject.getString("name");
                            String profile = subJsonObject.getString("profile");
                            addStoreRecyclerView(id, name, profile);
                        }
                    } catch (Exception e) {
                        Log.e("setFavCafe", "?????? ??????");
                    }
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("setFavCafe", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", uid);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void setFavGame(String uid) {
        //???????????? ??????????????? ????????????
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("game/wish");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String games = jsonObject.getString("games");
                        JSONArray jsonArray = new JSONArray(games);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject subJsonObject = jsonArray.getJSONObject(i);
                            int id = subJsonObject.getInt("id");
                            String name = subJsonObject.getString("kor_name");
                            String profile = subJsonObject.getString("profile_img");
                            addHeartRecyclerView(id, name, profile);
                        }
                    } catch (Exception e) {
                        Log.e("setFavGame", "?????? ??????");
                    }
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("setFavGame", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", uid);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void setHeart(String uid) {
        //????????? ????????? ????????????
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("user/profile");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int score = jsonObject.getInt("score");
                        heart_rating.setStar(((float) score) / 2);
                    } catch (Exception e) {
                        Log.e("setHeart", "?????? ??????");
                    }
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("setHeart", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", uid);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void write_community() {
        //??? ?????????
        String urlStr = MainActivity.mainUrl
                + "community/board/upload";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean act = jsonObject.getBoolean("act");
                        if (act) {
                            Toast.makeText(context, "????????????????????????.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("write_community", "?????? ??????");
                    }
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("communityWrite", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                body.put("content", write_editText.getText().toString());
                body.put("tag", selected_tag[selected_bulletin]);
                body.put("temp", "");
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
        refreshCommunityWrite();
        write_editText.setText("");
        backView();
    }

    private void clickHeart() {
        //???????????? ??????
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("community/board/like");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean act = jsonObject.getBoolean("act");
                        if (act)
                            Log.e("clickHeart", "true");
                        else
                            Log.e("clickHeart", "false");
                        Log.e("clickHeart", "?????????");
                    } catch (Exception e) {
                        Log.e("clickHeart", "?????? ??????");
                    }
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("clickHeart", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                body.put("board_id", Integer.toString(board_id));
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void write_comment() {
        //?????? ??????
        if (second_comment) {
            //????????? ??????
            StringBuilder urlStr = new StringBuilder();
            urlStr.append(MainActivity.mainUrl);
            urlStr.append("community/reply/upload");
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    urlStr.toString(),
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean act = jsonObject.getBoolean("act");
                            if (act) {
                                Toast.makeText(context, "????????????????????????.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("write_comment", "?????? ??????");
                        }
                    },
                    error -> {
                        Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        Log.e("writeComment", "?????? ??????");
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> body = new HashMap<>();
                    body.put("user_id", MainActivity.userId);
                    body.put("comment_id", Integer.toString(comment_id));
                    body.put("content", msg_edit.getText().toString());
                    return body;
                }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue = Volley.newRequestQueue(context);
            AppHelper.requestQueue.add(request);
            second_comment = false;
            msg_edit.setText("");
        } else {
            StringBuilder urlStr = new StringBuilder();
            urlStr.append(MainActivity.mainUrl);
            urlStr.append("community/comment/upload");
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    urlStr.toString(),
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean act = jsonObject.getBoolean("act");
                            if (act) {
                                Toast.makeText(context, "????????????????????????.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("write_comment", "?????? ??????");
                        }
                    },
                    error -> {
                        Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        Log.e("writeComment", "?????? ??????");
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> body = new HashMap<>();
                    body.put("user_id", MainActivity.userId);
                    body.put("board_id", Integer.toString(board_id));
                    body.put("content", msg_edit.getText().toString());
                    return body;
                }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue = Volley.newRequestQueue(context);
            AppHelper.requestQueue.add(request);
            msg_edit.setText("");
        }
    }

    private void refreshCommunityWrite() {
        //???????????? ??? ????????????
        communityRecyclerAdapter.initialSetUp();
        communityRecyclerAdapter.notifyDataSetChanged();
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("community/get");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    Log.e("communityJSONParse", response);
                    communityJSONParse(response);
                },
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("communityRefresh", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                body.put("range", "1, 50");
                body.put("tag", selected_tag[setBtn]);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void refreshComment(int write_id) {
        //???????????? ??? ????????????
        comment_adapter.initialSetUp();
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("community/board/");
        urlStr.append(write_id);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                this::commentJSONParse,
                error -> {
                    Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("commentRefresh", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                return body;
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
        item.time = time;
        item.comment_cnt = comment_cnt;
        item.visit = visit;
        item.tag = "#" + tag;
        communityRecyclerAdapter.addItem(item);
        communityRecyclerAdapter.notifyDataSetChanged();
    }

    private void addCommentRecyclerView(int comment_id, String content, String time, boolean second_comment, int id, String name, String profile) {
        CommunityItem item = new CommunityItem();
        item.write_id = comment_id;
        item.image = Integer.toString(R.drawable.circle_grey);
        item.name = name;
        item.second_comment = second_comment;
        item.comment = content;
        item.time = time;
        item.uid = id;
        item.image = profile;
        comment_adapter.addItem(item);
        comment_adapter.notifyDataSetChanged();
    }

    private void addHeartRecyclerView(int id, String name, String image) {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.id = id;
        item.image = image;
        item.name = name;
        heart_adapter.addItem(item);
        heart_adapter.notifyDataSetChanged();
    }

    private void addStoreRecyclerView(int id, String name, String image) {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.id = id;
        item.image = image;
        item.name = name;
        store_adapter.addItem(item);
        store_adapter.notifyDataSetChanged();
    }

    private void addStorageRecyclerView(int id, String content, String tag, String time) {
        CommunityStorage item = new CommunityStorage();
        item.id = id;
        item.content = content;
        item.time = time;
        storage_adapter.addItem(item);
        storage_adapter.notifyDataSetChanged();
    }

    //??? ?????? ????????????
    @SuppressLint("SetTextI18n")
    private void communityJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String meta = jsonObject.getString("meta");
            JSONObject subJsonObject01 = new JSONObject(meta);
            int count = subJsonObject01.getInt("count");
            filterEdit.setHint("?????? " + count);

            String community = jsonObject.getString("community");
            JSONArray jsonArray = new JSONArray(community);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                String board = subJsonObject.getString("board");
                JSONObject subJsonObject2 = new JSONObject(board);
                int write_id = subJsonObject2.getInt("id"); //???????????? ??? id
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
            Log.e("communityJSONParse", "?????? ??????");
        }
    }

    //???????????? ??? ????????????
    @SuppressLint("SetTextI18n")
    private void commentJSONParse(String response) {
        Log.e("commentJSONParse", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject subJsonObject01 = new JSONObject(jsonObject.getString("board"));
            int like = subJsonObject01.getInt("like");
            heart.setText("?????? " + like);

            String comment = jsonObject.getString("comment");
            JSONArray jsonArray = new JSONArray(comment);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                int comment_id = subJsonObject.getInt("id");
                String content = subJsonObject.getString("content");
                String time = subJsonObject.getString("created_at");
                boolean second_comment = subJsonObject.getBoolean("reply");

                JSONObject subJsonObject2 = subJsonObject.getJSONObject("writer");
                int id = subJsonObject2.getInt("id");
                String name = subJsonObject2.getString("nickname");
                String profile = subJsonObject2.getString("profile");

                addCommentRecyclerView(comment_id, content, time, second_comment, id, name, profile);
            }
        } catch (Exception e) {
            Log.e("commentJSONParse", "?????? ??????");
        }
    }

    @Override
    public void onBackPressed() {
        backView();
    }

    private void backView() {
        if (storage.getVisibility() == View.VISIBLE) {
            storage.setVisibility(View.GONE);
            write_view.setVisibility(View.VISIBLE);
        } else if (report_layout03.getVisibility() == View.VISIBLE || report_layout02.getVisibility() == View.VISIBLE || report_layout01.getVisibility() == View.VISIBLE) {
            report_layout03.setVisibility(View.GONE);
            report_layout02.setVisibility(View.GONE);
            report_layout01.setVisibility(View.GONE);
            community_view01.setVisibility(View.VISIBLE);
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
        main.setOnClickUrlListener(this);
    }

    @Override
    public void onClickReport() {
        //???????????? ??????
        community_view01.setVisibility(View.GONE);
        community_view02.setVisibility(View.GONE);
        profile_view.setVisibility(View.GONE);
        report_layout01.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickRemove() {
        if (isCommunity == 0) {
            //???????????? ??? ?????? ??????
            StringBuilder urlStr = new StringBuilder();
            urlStr.append(MainActivity.mainUrl);
            urlStr.append("community/board/delete");
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    urlStr.toString(),
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean act = jsonObject.getBoolean("act");
                            if (act) {
                                Toast.makeText(context, "?????????????????????.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("remove_community", "?????? ??????");
                        }
                    },
                    error -> {
                        Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        Log.e("remove_community", "?????? ??????");
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> body = new HashMap<>();
                    body.put("user_id", MainActivity.userId);
                    body.put("board_id", Integer.toString(board_id));
                    return body;
                }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue = Volley.newRequestQueue(context);
            AppHelper.requestQueue.add(request);
            refreshCommunityWrite();
        } else if (isCommunity == 1){
            //???????????? ?????? ?????? ??????
            StringBuilder urlStr = new StringBuilder();
            urlStr.append(MainActivity.mainUrl);
            urlStr.append("community/comment/delete");
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    urlStr.toString(),
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean act = jsonObject.getBoolean("act");
                            if (act) {
                                Toast.makeText(context, "?????????????????????.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("remove_comment", "?????? ??????");
                        }
                    },
                    error -> {
                        Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        Log.e("remove_comment", "?????? ??????");
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> body = new HashMap<>();
                    body.put("user_id", MainActivity.userId);
                    body.put("comment_id", Integer.toString(comment_id));
                    return body;
                }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue = Volley.newRequestQueue(context);
            AppHelper.requestQueue.add(request);
            refreshComment(board_id);
        } else {
            //???????????? ????????? ?????? ??????
            StringBuilder urlStr = new StringBuilder();
            urlStr.append(MainActivity.mainUrl);
            urlStr.append("community/reply/delete");
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    urlStr.toString(),
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean act = jsonObject.getBoolean("act");
                            if (act) {
                                Toast.makeText(context, "?????????????????????.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("remove_reply", "?????? ??????");
                        }
                    },
                    error -> {
                        Toast.makeText(context, "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        Log.e("remove_reply", "?????? ??????");
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> body = new HashMap<>();
                    body.put("user_id", MainActivity.userId);
                    body.put("reply_id", Integer.toString(comment_id));
                    return body;
                }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue = Volley.newRequestQueue(context);
            AppHelper.requestQueue.add(request);
            refreshComment(board_id);
        }
    }

    @Override
    public void onClickUrl() {
        //url ??????
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", "https://played.page.link/NLtk");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "??????????????? URL??? ?????????????????????.", Toast.LENGTH_SHORT).show();
    }
}
