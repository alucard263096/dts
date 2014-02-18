package com.example.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dao.tb_Defect_imageDAO;
import com.example.dao.tb_window_imageDAO;
import com.example.dts.ImageActivity;
import com.example.dts.ImageEditActivity;
import com.example.dts.ImageFloorPlanEditActivity;
import com.example.dts.ImageSketchEditActivity;
import com.example.dts.R;
import com.example.objects.defect_imageObj;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;
import com.example.utils.FormatUtil;

public class ViewFlowAdapter extends BaseAdapter {

	
	private int image_table=0;
	
	private ImageActivity mContext;
	private List<List<defect_imageObj>> list = new ArrayList<List<defect_imageObj>>();
	private FormatUtil formatUtil = new FormatUtil();

	public ViewFlowAdapter(ImageActivity mContext,
			List<List<defect_imageObj>> list,int _image_table) {
		this.image_table=_image_table;
		this.list = list;
		this.mContext = mContext;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View root = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			root = inflater.inflate(R.layout.image_item, null);
		} else {
			root = convertView;
		}

		final List<defect_imageObj> defectList = list.get(position);

		((RelativeLayout) root.findViewById(R.id.RelativeLayout1))
				.removeAllViews();
		((RelativeLayout) root.findViewById(R.id.RelativeLayout2))
				.removeAllViews();
		((RelativeLayout) root.findViewById(R.id.RelativeLayout3))
				.removeAllViews();
		((RelativeLayout) root.findViewById(R.id.RelativeLayout4))
				.removeAllViews();
		((RelativeLayout) root.findViewById(R.id.RelativeLayout5))
				.removeAllViews();
		for (int i = 0; i < defectList.size(); i++) {
			final int imagePosition = i;
			RelativeLayout rl = new RelativeLayout(root.getContext());
			if (i == 0) {
				rl = (RelativeLayout) root.findViewById(R.id.RelativeLayout1);
			}
			if (i == 1) {
				rl = (RelativeLayout) root.findViewById(R.id.RelativeLayout2);
			}
			if (i == 2) {
				rl = (RelativeLayout) root.findViewById(R.id.RelativeLayout3);
			}
			if (i == 3) {
				rl = (RelativeLayout) root.findViewById(R.id.RelativeLayout4);
			}
			if (i == 4) {
				rl = (RelativeLayout) root.findViewById(R.id.RelativeLayout5);
			}
			ImageView item1 = new ImageView(root.getContext());
			item1.setImageBitmap(FormatUtil.decodeSampledBitmapFromByteArray(defectList.get(i).getPic(), 610, 540));
			
			//item1.setImageBitmap(FormatUtil.zoomBitmap(formatUtil.byteToBitmap(defectList.get(i).getPic()), 610, 540));

			item1.setId(Integer.parseInt(String.valueOf(position + 1)
					+ String.valueOf(i)));
			// RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
			// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			// item1.setLayoutParams(lp);

			// item1.setLayoutParams(new LayoutParams(739, 509));
			item1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					final int defectImageId = defectList.get(imagePosition)
							.getDefectImageId();
					final int imageType = defectList.get(imagePosition)
							.getImageType();
					final byte[] pic = defectList.get(imagePosition).getPic();
					final View itemView = v;
					Activity imageA = (Activity) mContext;
					ImageView bigImageView = (ImageView) imageA
							.findViewById(R.id.big_imageView);

					if (defectImageId <= 0) {
						bigImageView.setImageDrawable(null);
						if (imageType == CommonEnum.ImageType.DefectImage
								.getIndex()) {
							showPicturePicker(mContext, itemView);
						} else if (imageType == CommonEnum.ImageType.FloorPlan
								.getIndex()) {
							ActionItem newItem = new ActionItem(1,
									"New Floor Plan");
							final QuickAction quickAction = new QuickAction(
									mContext, QuickAction.HORIZONTAL);

							// add action items into QuickAction
							quickAction.addActionItem(newItem);
							quickAction
									.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
										@Override
										public void onItemClick(
												QuickAction source, int pos,
												int actionId) {
											if (actionId == 1) {
												Intent intent = new Intent();
												intent.setClass(
														mContext,
														ImageFloorPlanEditActivity.class);
												intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
												Bundle bundle = new Bundle();
												bundle = mContext
														.getBundleForAdapter();
												bundle.putInt(
														"KEY_DEFECT_IMAGE_ID",
														defectList
																.get(imagePosition)
																.getDefectImageId());
												intent.putExtras(bundle);
												mContext.startActivity(intent);
											}
										}
									});

							quickAction.show(v);
						} else {
							ActionItem newItem = new ActionItem(1, "New Sketch");
							final QuickAction quickAction = new QuickAction(
									mContext, QuickAction.HORIZONTAL);

							// add action items into QuickAction
							quickAction.addActionItem(newItem);
							quickAction
									.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
										@Override
										public void onItemClick(
												QuickAction source, int pos,
												int actionId) {
											if (actionId == 1) {
												Intent intent = new Intent();
												intent.setClass(
														mContext,
														ImageSketchEditActivity.class);
												intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
												Bundle bundle = new Bundle();
												bundle = mContext
														.getBundleForAdapter();
												bundle.putInt(
														"KEY_DEFECT_IMAGE_ID",
														defectList
																.get(imagePosition)
																.getDefectImageId());
												intent.putExtras(bundle);
												mContext.startActivity(intent);
											}
										}
									});

							quickAction.show(v);
						}
					} else {
						// bigImageView.setVisibility(View.VISIBLE);
						bigImageView.setImageDrawable(formatUtil
								.byteToDrawable(pic));
						ActionItem editItem = new ActionItem(1, "Edit");

						ActionItem deleteItem = new ActionItem(3, "Delete");

						// create QuickAction. Use QuickAction.VERTICAL or
						// QuickAction.HORIZONTAL param to define layout
						// orientation
						final QuickAction quickAction = new QuickAction(
								mContext, QuickAction.HORIZONTAL);

						// add action items into QuickAction
						quickAction.addActionItem(editItem);
						// if(imageType==CommonEnum.ImageType.DefectImage.getIndex()){
						// ActionItem replaceItem = new ActionItem(2,
						// "Replace");
						// quickAction.addActionItem(replaceItem);
						// }
						quickAction.addActionItem(deleteItem);

						// Set listener for action item clicked
						quickAction
								.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
									@Override
									public void onItemClick(QuickAction source,
											int pos, int actionId) {
										if (actionId == 1) {
											Intent intent = new Intent();
											if (imageType == CommonEnum.ImageType.DefectImage
													.getIndex()) {
												intent.setClass(mContext,
														ImageEditActivity.class);
												intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
												Bundle bundle = new Bundle();
												bundle = mContext
														.getBundleForAdapter();
												bundle.putInt(
														"KEY_DEFECT_IMAGE_ID",
														defectList
																.get(imagePosition)
																.getDefectImageId());
												intent.putExtras(bundle);
												
												mContext.startActivityForResult(intent, 1);
											} else if (imageType == CommonEnum.ImageType.FloorPlan
													.getIndex()) {
												intent.setClass(
														mContext,
														ImageFloorPlanEditActivity.class);
												intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
												Bundle bundle = new Bundle();
												bundle = mContext
														.getBundleForAdapter();
												bundle.putInt(
														"KEY_DEFECT_IMAGE_ID",
														defectList
																.get(imagePosition)
																.getDefectImageId());
												intent.putExtras(bundle);
												mContext.startActivity(intent);
											} else {
												intent.setClass(
														mContext,
														ImageSketchEditActivity.class);
												intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
												Bundle bundle = new Bundle();
												bundle = mContext
														.getBundleForAdapter();
												bundle.putInt(
														"KEY_DEFECT_IMAGE_ID",
														defectList
																.get(imagePosition)
																.getDefectImageId());
												intent.putExtras(bundle);
												mContext.startActivity(intent);
											}

										} else if (actionId == 2) {
											showPicturePicker(mContext,
													itemView);
										} else if (actionId == 3) {
											defectList.get(imagePosition)
													.setPic(null);
											Activity imageA = (Activity) mContext;
											ImageView bigImageView = (ImageView) imageA
													.findViewById(R.id.big_imageView);
											bigImageView.setImageDrawable(null);
											ImageView smallImageView = (ImageView) itemView;
											Resources res = mContext
													.getResources();

											//smallImageView.setImageBitmap(FormatUtil.zoomBitmap(BitmapFactory.decodeResource(res,R.drawable.p_01),610, 540));
											
											smallImageView.setImageBitmap(FormatUtil.decodeSampledBitmapFromResource(res, R.drawable.p_01, 610, 540));
											
											deleteDefectImage(defectList.get(
													imagePosition)
													.getDefectImageId());
											defectList.get(imagePosition)
													.setDefectImageId(0);
										}
									}
								});

						quickAction.show(v);
					}
				}

			});
			rl.addView(item1);
		}

		return root;
	}

	public void showPicturePicker(Context context, final View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Photo From");
		builder.setNegativeButton("Cancel", null);
		builder.setItems(new String[] { "Camera", "Photo Album" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							getImageFromCamera(v);
							break;

						case 1:
							getImageFromPhotoAlbum(v);
							break;

						default:
							break;
						}
					}
				});
		builder.create().show();
	}

	private void getImageFromPhotoAlbum(View v) {
		Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
		openAlbumIntent.setType("image/*");

		/*
		 * 鍋炵requestCode銊〧romPhoto鑵攏um + defectList arrayList鑵攊ndex +
		 * defectList鑵攊ndex銊�绋涱Δ羁殭餆簮鏅炵摎顪藉矄鐪堫锟藉矄鐪堬仱鐪曟懐鍏氳湂鐪堫敯defectList
		 */
		int id = Integer.parseInt(String
				.valueOf(CommonEnum.PictureType.FromPhoto.getIndex())
				+ String.valueOf(v.getId()));
		Activity imageA = (Activity) mContext;
		imageA.startActivityForResult(openAlbumIntent, id);

	}

	private void getImageFromCamera(View v) {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "dts_image.jpg"));
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		int id = Integer.parseInt(String
				.valueOf(CommonEnum.PictureType.FromCamera.getIndex())
				+ String.valueOf(v.getId()));
		Activity imageA = (Activity) mContext;
		imageA.startActivityForResult(openCameraIntent, id);
	}

	private void deleteDefectImage(int defectImageId) {
		DBUtil util = DBFactory.GetUserDB(mContext);
		util.open();
		if(image_table==0){
			tb_Defect_imageDAO dao = new tb_Defect_imageDAO();
			dao.DeleteDefectImage(util, defectImageId);
		}else{
			tb_window_imageDAO dao = new tb_window_imageDAO();
			dao.DeleteDefectImage(util, defectImageId);
		}
		util.close();
	}

}
