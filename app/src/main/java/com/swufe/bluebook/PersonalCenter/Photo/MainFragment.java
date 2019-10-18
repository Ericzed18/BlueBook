package com.swufe.bluebook.PersonalCenter.Photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.Backstage.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.swufe.bluebook.Moment.MomentActivity;
import com.swufe.bluebook.PersonalCenter.Photo.CityList.MyPlace;
import com.swufe.bluebook.PersonalCenter.Photo.CityList.MyPlaceAdapter;
import com.swufe.bluebook.R;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 版权所有：XXX有限公司
 *
 * MainFragment
 *
 * @author zhou.wenkai ,Created on 2016-5-5 10:25:49
 * 		   Major Function：<b>MainFragment</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class MainFragment extends PictureSelectFragment {

    @Bind(R.id.touxiang)
    ImageView mPictureIv;

    @Bind(R.id.nicname)
    TextView nicname;

    @Bind(R.id.citygridview)
    GridView citygridview;


    private List<MyPlace> myPlacesList = new ArrayList<MyPlace>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_photo;
    }

    @Override
    public void initViews(View view) {
        Picasso.with(getContext()).load((String) BmobUser.getObjectByKey("headPortrait")).into(mPictureIv);
        nicname.setText((String)BmobUser.getObjectByKey("nickname"));

        initMyPlaces(); // 初始化数据
        MyPlaceAdapter adapter = new MyPlaceAdapter(getContext(),
                R.layout.item_place,myPlacesList);
        citygridview.setAdapter(adapter);

    }


    @Override
    public void initEvents() {
        final User user= new User();

        // 设置图片点击监听
        mPictureIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                mPictureIv.setImageBitmap(bitmap);

                String filePath = fileUri.getEncodedPath();
                String imagePath = Uri.decode(filePath);

                final BmobFile bmobFile = new BmobFile(new File(imagePath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                            Log.i(TAG, "done: "+bmobFile.getFileUrl());
                            user.setHeadPortrait(bmobFile.getFileUrl());
                            BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                            user.update(bmobUser.getObjectId(),new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){

                                    }else{

                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        //设置修改昵称监听
        nicname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),UpdateNicnameActivity.class);
                intent.putExtra("nicname",nicname.getText());
                startActivityForResult(intent,2);
            }
        });

        citygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //对整个item设置点击事件
                MyPlace places=myPlacesList.get(position);
                Intent intent = new Intent(getActivity(), MomentActivity.class);
                startActivity(intent);
                //点击城市，跳转到对应城市的卡片列表，传递城市名和日期两个参数
//                Intent intent=new Intent(getContext(),MyPlaceCardActivity.class);
//                intent.putExtra("city",place.getCityName());
//                intent.putExtra("time",place.getDays());
//                Log.i(TAG, "onItemClick: city="+place.getCityName());
//                Log.i(TAG, "onItemClick: time="+place.getDays());
//
//                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==2){
            User user= new User();
            String nic=data.getStringExtra("nicname_new");
            Log.i(TAG, "onActivityResult: nic="+nic);
            nicname.setText(nic);
            user.setNickname(nic);
            BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
            user.update(bmobUser.getObjectId(),new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){

                    }else{

                    }
                }
            });
        }
    }


    private void initMyPlaces(){
        //从后台获取所有数据
        MyPlace places1=new MyPlace("成都","726","http://bmob-cdn-21576.b0.upaiyun.com/2018/09/23/c97d5653404e72108020f2c8cc79a1e7.jpg");
        myPlacesList.add(places1);
        MyPlace places2=new MyPlace("北京","321","http://bmob-cdn-21576.b0.upaiyun.com/2018/09/23/acb1cbe240669ec580c90d39d5ba6355.png");
        myPlacesList.add(places2);
        MyPlace places3=new MyPlace("厦门","412","http://bmob-cdn-21576.b0.upaiyun.com/2018/09/23/ef8d477c4090dbf780702c1049422be3.jpg");
        myPlacesList.add(places3);
        MyPlace places4=new MyPlace("重庆","545","http://bmob-cdn-21576.b0.upaiyun.com/2018/09/23/6e6d2cfc4095aed1805360a9ba116f3f.jpg");
        myPlacesList.add(places4);
        MyPlace places5=new MyPlace("南京","63","http://bmob-cdn-21576.b0.upaiyun.com/2018/09/23/89484294402771f98063625731f90d1f.jpg");
        myPlacesList.add(places5);
        MyPlace places6=new MyPlace("西安","154","http://bmob-cdn-21576.b0.upaiyun.com/2018/09/23/6f79221840c5305080605c178e5e6569.jpg");
        myPlacesList.add(places6);
    }
}

