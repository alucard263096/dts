package com.example.dts;

import com.example.common.ColorPickerDialog;
import com.example.common.CommonEnum;
import com.example.dao.tb_DefectDAO;
import com.example.dao.tb_Defect_imageDAO;
import com.example.objects.defectObj;
import com.example.objects.defect_imageObj;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;
import com.example.utils.FormatUtil;
import com.lee.demo.interfaces.ISketchPadCallback;
import com.lee.demo.view.SketchPadView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageSketchEditActivity extends Activity implements
		View.OnClickListener, ISketchPadCallback {

	private SketchPadView m_sketchPad = null;
	private FormatUtil formatUtil = new FormatUtil();
	private ColorPickerDialog dialog;
	Context context;
	private int currentColor = Color.BLACK;
	private defect_imageObj previousImageObj;
	private defect_imageObj nextImageObj;

	private Button m_penBtn = null;
	private Button m_eraserBtn = null;
	private Button m_previousBtn = null;
	private Button m_undoBtn = null;
	private Button m_redoBtn = null;
	private Button m_nextBtn = null;
	private Button m_pencolorBtn = null;
	private Button m_pensizeBtn = null;
	private RelativeLayout m_layout = null;
	private int flatId;
	private int siteId;
	private int areaId;
	private int flatTypeId;
	private int defectTypeId;
	private int roundNo;
	private int defectImageId;
	private String menu[];
	private String title;
	private String defectName;
	private TextView view_title;
	private int elementTypeId;
	private int elementId;
	private int defectId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_image_edit);

		getBundleData();
		findViews();
		init();
		setListensers();

		setPenColor(currentColor, false);
		setErase(true);

		setButtonEnabled(m_redoBtn, false);
		setButtonEnabled(m_undoBtn, false);

	}

	private void setListensers() {
		m_previousBtn.setOnClickListener(this);
		m_nextBtn.setOnClickListener(this);
		m_undoBtn.setOnClickListener(this);
		m_redoBtn.setOnClickListener(this);
		m_pencolorBtn.setOnClickListener(this);
		m_eraserBtn.setOnClickListener(this);
		m_layout.setOnClickListener(this);

		m_previousBtn.setOnTouchListener(TouchDark);
		m_undoBtn.setOnTouchListener(TouchDark);
		m_redoBtn.setOnTouchListener(TouchDark);
		m_nextBtn.setOnTouchListener(TouchDark);
		m_pencolorBtn.setOnTouchListener(TouchDark);

		m_sketchPad.setCallback(this);

	}

	private void getBundleData() {
		Bundle bundle = this.getIntent().getExtras();
		siteId = bundle.getInt("KEY_SITE_ID");
		flatId = bundle.getInt("KEY_FLAT_ID");
		flatTypeId = bundle.getInt("KEY_FLAT_TYPE_ID");
		areaId = bundle.getInt("KEY_AREA_ID");
		roundNo = bundle.getInt("KEY_ROUND_NO");
		elementTypeId = bundle.getInt("KEY_ELEMENT_TYPE_ID");
		menu = bundle.getStringArray("KEY_MENU");
		defectName = bundle.getString("KEY_DEFECT_NAME");
		title = bundle.getString("KEY_TITLE");
		elementId = bundle.getInt("KEY_ELEMENT_ID");
		defectId = bundle.getInt("KEY_DEFECT_ID");
		defectTypeId = bundle.getInt("KEY_DEFECT_TYPE_ID");
		defectImageId = bundle.getInt("KEY_DEFECT_IMAGE_ID");
	}

	private void findViews() {
		view_title = (TextView) findViewById(R.id.txtTitle);
		m_previousBtn = (Button) this.findViewById(R.id.btnPrevious);
		m_undoBtn = (Button) this.findViewById(R.id.btnUndo);
		m_redoBtn = (Button) this.findViewById(R.id.btnRedo);
		m_nextBtn = (Button) this.findViewById(R.id.btnNext);
		m_pencolorBtn = (Button) this.findViewById(R.id.btnColor);
		m_eraserBtn = (Button) this.findViewById(R.id.btnErase);
		m_layout = (RelativeLayout) this.findViewById(R.id.relativeLayout3);
		m_sketchPad = (SketchPadView) this.findViewById(R.id.sketchpad);
	}

	private void init() {
		view_title.setText(title + defectName + " ( Sketch )");
		DBUtil util = DBFactory.GetUserDB(ImageSketchEditActivity.this);
		util.open();
		if (defectImageId <= 0) {
			Resources res = getResources();
			m_sketchPad.setForeBitmap(BitmapFactory.decodeResource(res,
					R.drawable.edit_image_bg));
			defectImageId = saveDefectImage(formatUtil.bitmapToBytes(m_sketchPad
					.getForeBitmap()));
		} else {
			tb_Defect_imageDAO dao = new tb_Defect_imageDAO();
			defect_imageObj imageObj = dao.getDefectImage(util, defectImageId);
			m_sketchPad
					.setForeBitmap(formatUtil.byteToBitmap(imageObj.getPic()));
		}

		setPreviousImage();
		setNextImage();

		util.close();
		// Resources res = getResources();

	}

	private void setPreviousImage() {
		DBUtil util = DBFactory.GetUserDB(ImageSketchEditActivity.this);
		util.open();
		tb_Defect_imageDAO dao = new tb_Defect_imageDAO();
		previousImageObj = dao.getPreviousDefectImage(util, defectImageId,CommonEnum.ImageType.Skecth.getIndex());
		if (previousImageObj == null) {
			setButtonEnabled(m_previousBtn, false);
		} else {
			setButtonEnabled(m_previousBtn, true);
		}
		util.close();
	}

	private void setNextImage() {
		DBUtil util = DBFactory.GetUserDB(ImageSketchEditActivity.this);
		util.open();
		tb_Defect_imageDAO dao = new tb_Defect_imageDAO();
		nextImageObj = dao.getNextDefectImage(util, defectImageId,CommonEnum.ImageType.Skecth.getIndex());
		if (nextImageObj == null) {
			setButtonEnabled(m_nextBtn, false);
		} else {
			setButtonEnabled(m_nextBtn, true);
		}
		util.close();
	}

	public void setButtonEnabled(Button button, boolean isEnable) {
		button.setEnabled(isEnable);
		if (isEnable) {
			button.setAlpha(1);
		} else {
			button.setAlpha(0.5f);
		}
	}

	private int saveDefectImage(byte[] pic) {
		int result = -1;
		defectId = getDefectId();
		defect_imageObj dImageObj = new defect_imageObj();
		dImageObj.setUserId(1);
		dImageObj.setCreatedUserId(1);
		dImageObj.setUpdatedUserId(1);
		dImageObj.setDefectId(defectId);
		dImageObj.setDefectImageId(defectImageId);
		dImageObj.setImageType(CommonEnum.ImageType.Skecth.getIndex());
		dImageObj.setPic(pic);
		dImageObj.setStatus(0);
		DBUtil util = DBFactory.GetUserDB(ImageSketchEditActivity.this);
		util.open();
		tb_Defect_imageDAO dao = new tb_Defect_imageDAO();
		if (defectImageId <= 0) {
			result = dao.addDefectImage(util, dImageObj);
		} else {
			result = dao.updateDefectImage(util, dImageObj);
		}
		util.close();
		return result;
	}

	private int getDefectId() {
		if (defectId <= 0) {
			defectObj defect = new defectObj();
			defect.setDefectId(defectId);
			defect.setUserId(1);
			defect.setDefectType(CommonEnum.DefectType.Defect.getIndex());
			defect.setSitetId(siteId);
			defect.setFlatId(flatId);
			defect.setAreaId(areaId);
			defect.setElementTypeId(elementTypeId);
			defect.setElementId(elementId);
			defect.setDefectTypeId(defectTypeId);
			defect.setRoundNo(roundNo);
			defect.setDetailDesc("");
			defect.setScheduledCompletionDate(null);
			defect.setActualCompletionDate(null);
			defect.setDefectFormRef(1);
			defect.setFormReceivedDate(null);
			defect.setDesc("");
			defect.setStatus(0);
			defect.setCreatedUserId(1);
			defect.setUpdatedUserId(1);
			DBUtil util = DBFactory.GetUserDB(ImageSketchEditActivity.this);
			util.open();
			tb_DefectDAO dao = new tb_DefectDAO();
			int result = dao.addDefect(util, defect);
			util.close();
			return result;
		} else {
			return defectId;
		}
	}

	public void setPenColor(int color, boolean enable) {
		Bitmap newb = Bitmap.createBitmap(42, 42, Config.ARGB_8888);
		Canvas cv = new Canvas(newb);
		/*
		 * if (!enable) {
		 * cv.drawBitmap(BitmapFactory.decodeResource(getResources(),
		 * R.drawable.icon_bg2), 0, 0, null); }
		 */
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setAntiAlias(true);
		cv.drawCircle(21, 21, 20, paint);
		if (!enable) {
			paint.setColor(Color.parseColor("#ff4500"));
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(2.5f);
			cv.drawRect(0, 0, 42, 42, paint);// 长方形
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();
		m_layout.setBackground(formatUtil.byteToDrawable(formatUtil
				.bitmapToBytes(newb)));
		newb.recycle();

		m_sketchPad.setStrokeColor(color);
	}

	public void setErase(boolean enable) {
		Bitmap newb = Bitmap.createBitmap(42, 42, Config.ARGB_8888);
		Canvas cv = new Canvas(newb);
		//cv.drawBitmap(FormatUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_erase), 42, 42), 0, 0, null);
		cv.drawBitmap(FormatUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.icon_erase, 42, 42), 0, 0, null);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		if (!enable) {
			paint.setColor(Color.parseColor("#ff4500"));
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(2.5f);
			cv.drawRect(0, 0, 42, 42, paint);// 长方形
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();
		m_eraserBtn.setBackground(formatUtil.byteToDrawable(FormatUtil.bitmapToBytes(newb)));
		newb.recycle();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnPrevious:
			onPreviousClick(v);
			break;
		case R.id.btnUndo:
			onUndoClick(v);
			break;
		case R.id.btnRedo:
			onRedoClick(v);
			break;
		case R.id.btnNext:
			onNextClick(v);
			break;
		case R.id.btnColor:
			onPenColorClick(v);
			break;
		case R.id.btnErase:
			onEraseClick(v);
			break;
		case R.id.relativeLayout3:
			onPenClick(v);
			break;
		/*
		 * case R.id.btnPen: onPenClick(v); break;
		 * 
		 * case R.id.btnEraser: onEraseClick(v); break;
		 */
		}
	}

	protected void onPreviousClick(View v) {
		if (m_sketchPad.canUndo()) {
			saveDefectImage(formatUtil.bitmapToBytes(m_sketchPad
					.getForeBitmap()));
		}
		m_sketchPad.setForeBitmap(formatUtil.byteToBitmap(previousImageObj
				.getPic()));
		defectImageId = previousImageObj.getDefectImageId();
		setPreviousImage();
		setNextImage();
		// m_sketchPad.clearAllStrokes();
		setButtonEnabled(m_redoBtn, false);
		setButtonEnabled(m_undoBtn, false);
	}

	protected void onNextClick(View v) {
		if (m_sketchPad.canUndo()) {
			saveDefectImage(formatUtil.bitmapToBytes(m_sketchPad
					.getForeBitmap()));
		}
		m_sketchPad
				.setForeBitmap(formatUtil.byteToBitmap(nextImageObj.getPic()));
		defectImageId = nextImageObj.getDefectImageId();
		setPreviousImage();
		setNextImage();
		// m_sketchPad.clearAllStrokes();
		setButtonEnabled(m_redoBtn, false);
		setButtonEnabled(m_undoBtn, false);
	}

	protected void onPenClick(View v) {
		m_sketchPad.setStrokeType(SketchPadView.STROKE_PEN);
		setPenColor(currentColor, false);
		// setButtonEnabled(m_eraserBtn, true);
		setErase(true);
	}

	protected void onEraseClick(View v) {
		m_sketchPad.setStrokeType(SketchPadView.STROKE_ERASER);
		setPenColor(currentColor, true);
		// setButtonEnabled(m_eraserBtn, false);
		setErase(false);
	}

	protected void onResetClick(View v) {
		m_sketchPad.clearAllStrokes();

		// setButtonEnabled(m_resetBtn, false);
		setButtonEnabled(m_redoBtn, false);
		setButtonEnabled(m_undoBtn, false);
	}

	protected void onUndoClick(View v) {
		m_sketchPad.undo();
		setButtonEnabled(m_undoBtn, m_sketchPad.canUndo());
		setButtonEnabled(m_redoBtn, m_sketchPad.canRedo());
	}

	protected void onRedoClick(View v) {
		m_sketchPad.redo();
		setButtonEnabled(m_undoBtn, m_sketchPad.canUndo());
		setButtonEnabled(m_redoBtn, m_sketchPad.canRedo());
	}

	protected void onPenSizeClick(View v) {
	}

	protected void onPenColorClick(View v) {
		dialog = new ColorPickerDialog(ImageSketchEditActivity.this,
				currentColor, getResources().getString(
						R.string.sketchpadtool_title),
				new ColorPickerDialog.OnColorChangedListener() {
					@Override
					public void colorChanged(int color) {
						setPenColor(color, m_sketchPad.isErase());
						currentColor = color;
					}
				});
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		dialog.getWindow().setAttributes(lp);
		lp.alpha = 0.8f;// 透明度，黑暗度为lp.dimAmount=1.0f;
		dialog.show();
	}

	@Override
	public void onDestroy(SketchPadView obj) {
	}

	@Override
	public void onTouchDown(SketchPadView obj, MotionEvent event) {
		// setButtonEnabled(m_resetBtn, true);
		setButtonEnabled(m_undoBtn, true);
	}

	@Override
	public void onTouchUp(SketchPadView obj, MotionEvent event) {
	}

	public static final OnTouchListener TouchDark = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setScaleX(1.2f);
				v.setScaleY(1.2f);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.setScaleX(1f);
				v.setScaleY(1f);
			}
			return false;
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			goBack();
			return true;

		}
		return false;
	}

	public void goBack() {
		if (m_sketchPad.canUndo()) {
			saveDefectImage(formatUtil.bitmapToBytes(m_sketchPad
					.getForeBitmap()));
		}
//		Intent intent = new Intent();
//		intent.setClass(ImageSketchEditActivity.this, ImageActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		Bundle bundle = new Bundle();
//		bundle.putInt("KEY_FLAT_ID", flatId);
//		bundle.putInt("KEY_SITE_ID", siteId);
//		bundle.putInt("KEY_FLAT_TYPE_ID", flatTypeId);
//		bundle.putInt("KEY_AREA_ID", areaId);
//		bundle.putInt("KEY_ROUND_NO", roundNo);
//		bundle.putString("KEY_TITLE", title);
//		bundle.putInt("KEY_DEFECT_ID", defectId);
//		bundle.putInt("KEY_ELEMENT_TYPE_ID", elementTypeId);
//		bundle.putStringArray("KEY_MENU", menu);
//		bundle.putInt("KEY_ELEMENT_ID", elementId);
//		bundle.putString("KEY_DEFECT_NAME", defectName);
//		bundle.putInt("KEY_DEFECT_TYPE_ID", defectTypeId);
//		bundle.putInt("KEY_IMAGE_TYPE",
//				CommonEnum.ImageType.Skecth.getIndex());
//		intent.putExtras(bundle);
//		startActivity(intent);
		ImageSketchEditActivity.this.finish();
	}

}
