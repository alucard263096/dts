package com.example.dts;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.common.global_info;
import com.example.dao.tb_UserDAO;
import com.example.objects.StaticVar;
import com.example.socketthread.LoginGet;
import com.example.utils.BaseDB;
import com.example.utils.DBFactory;
import com.example.utils.ToolUtil;

public class LoginActivity extends Activity {

	private EditText txtServer;
//	private EditText txtServerPort;

//	private ArrayAdapter adapterServer;
	private EditText txtUserID;
	private EditText txtPassword;
	private Button btnLogin;
	private Button btnExit;
	protected static final int MENU_ABOUT = Menu.FIRST;
	protected static final int MENU_Quit = Menu.FIRST + 1;
	private global_info app;

	public static final String PREFS_NAME = "DTS_PREF";
	private static final String PREF_USERNAME = "DTS_USERNAME";
	private static final String PREF_SERVER = "DTS_SERVER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Retrive [Username] and [Server] from stored preference
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		String Stored_Username = pref.getString(PREF_USERNAME, null);
		String Stored_Server = pref.getString(PREF_SERVER, null);

		findViews(Stored_Server, Stored_Username);
		setListensers();
		initHandle();
	}

	// Call once for delete all databse
	public void deleteAllDatabase() {

		File dbf = new File("/data/data/com.example.dts/databases/");
		File[] dbfs = dbf.listFiles();
		for (int i = 0; i < dbfs.length; i++) {
			File f = dbfs[i];
			f.delete();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ABOUT, 0, getString(R.string.about_label));
		menu.add(0, MENU_Quit, 0, getString(R.string.quit_label));
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case MENU_ABOUT:
			finish();
			break;
		case MENU_Quit:
			QuitApp();
			break;
		}
		return true;
	}

	private void findViews(String StoredServer, String StoredUsername) {
		txtServer = (EditText) findViewById(R.id.spServer);
		// txtServerPort = (EditText) findViewById(R.id.spServerPort);
		txtUserID = (EditText) findViewById(R.id.txtUserID);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnExit = (Button) findViewById(R.id.btnExit);

		// Restored [Server], [Username]
		if (StoredServer != null && StoredUsername != null) {
			txtServer.setText(StoredServer);
			txtUserID.setText(StoredUsername);
		}
	}

	private class SpinnerXMLSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}
	}

	private void setListensers() {
		btnLogin.setOnClickListener(loginSys);
		btnExit.setOnClickListener(exitSys);
	}

	private Button.OnClickListener exitSys = new Button.OnClickListener() {
		public void onClick(View v) {
			QuitApp();
		}
	};
	Handler mThirdHandler;

	private void initHandle() {
		// TODO Auto-generated method stub
		this.mThirdHandler = new Handler(this.getMainLooper()) {
			@Override
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				LoginGet ts = new LoginGet(LoginActivity.this, txtUserID
						.getText().toString(), ToolUtil.encryption(txtPassword
						.getText().toString().toLowerCase()));
				ts.start();
			};
		};
	}

	public void Login() {

		BaseDB util = DBFactory.GetBaseDB(LoginActivity.this);
		if (util.CheckDbExists() == false) {
			// util.DeleteDB();
			// util.CreateDBOnAsset();
			openErrorDialog(R.string._1000004);
			return;
		}
		util.open();
		tb_UserDAO dao = new tb_UserDAO();
		List<String> user = dao.getUserByLoginName(util, txtUserID.getText()
				.toString());
		util.close();
		if (user.size() <= 0) {
			openErrorDialog(R.string._1000003);
		} else {
			if (user.get(2)
					.toString()
					.toLowerCase()
					.equals(ToolUtil.encryption(txtPassword.getText()
							.toString().toLowerCase()))) {
				app = ((global_info) getApplicationContext());
				app.setUserId(Integer.parseInt(user.get(0)));
				app.setLoginName(user.get(1).toString());
				app.setUserName(user.get(3).toString());
				app.setCompanyName(user.get(4).toString());
				app.setUserRole(Integer.parseInt(user.get(5)));
				app.setUserContactNo(user.get(6).toString());
				app.setUserEmail(user.get(7).toString());
				app.setUserStatus(Integer.parseInt(user.get(10)));

				StaticVar.USERID = user.get(0).toString();

				// Toast.makeText(getApplicationContext(),
				// "login success.",Toast.LENGTH_SHORT).show();

				// UserDB db=new
				// UserDB(LoginActivity.this,String.valueOf(StaticVar.USERID));
				// db.DeleteDB();
				// db=new
				// UserDB(LoginActivity.this,String.valueOf(StaticVar.USERID));
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent, 1);
				// startActivity(intent);
				// LoginActivity.this.finish();
			} else {
				Log.i("a1", user.get(2).toString());
				Log.i("a2",
						ToolUtil.encryption(txtPassword.getText().toString()));
				openErrorDialog(R.string._1000003);
			}
		}
	}

	private Button.OnClickListener loginSys = new Button.OnClickListener() {
		public void onClick(View v) {
			if (ValidLogin()) {
				// Set Server name
				StaticVar.ServerName = txtServer.getText().toString();
				// StaticVar.ServerPort=Integer.valueOf(txtServerPort.getText().toString());

				// Store [Username] and [server]
				SharedPreferences pref = getSharedPreferences(PREFS_NAME,
						MODE_PRIVATE);
				pref.edit()
						.putString(PREF_USERNAME,
								txtUserID.getText().toString()).commit();
				pref.edit()
						.putString(PREF_SERVER, txtServer.getText().toString())
						.commit();

				mThirdHandler.sendEmptyMessage(0);
			}
		}
	};

	private boolean ValidLogin() {
		boolean valid = true;

		// Validate [Username], [Password], [Server] only
		if (txtUserID.getText().toString() == null
				|| txtUserID.getText().toString().trim().length() == 0) {
			openErrorDialog(R.string._1000001);
			valid = false;
		} else if (txtPassword.getText().toString() == null
				|| txtPassword.getText().toString().trim().length() == 0) {
			openErrorDialog(R.string._1000001);
			valid = false;
		} else if (txtServer.getText().toString() == null
				|| txtServer.getText().toString().trim().length() == 0) {
			openErrorDialog(R.string._1000002);
			valid = false;
		}
		// else if(txtServerPort.getText().toString()==null ||
		// txtServerPort.getText().toString().trim().length()==0){
		// openErrorDialog(R.string._10000021);
		// valid=false;
		// }

		// String selectString = spServer.getSelectedItem().toString();
		// if(selectString.equals(null) || selectString.trim().length()<=0){
		// openErrorDialog(R.string._1000002);
		// valid=false;
		// }

		return valid;
	}

	private void openErrorDialog(int msg) {
		AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
				.setTitle(R.string.msg_title).setMessage(msg)
				.setNegativeButton(R.string.ok_label, null).show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			QuitApp();
			return true;
		}
		return false;
	}

	public void QuitApp() {
		new AlertDialog.Builder(LoginActivity.this)
				.setTitle(R.string.msg_title)
				.setMessage("You sure quit?")
				.setPositiveButton("Sure",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								int nPid = android.os.Process.myPid();
								android.os.Process.killProcess(nPid);
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).show();

	}

	public boolean checkValidScreen() {
		boolean valid = true;
		Display display = this.getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		if (width != 1280 || height != 800) {
			// valid=false;
		}

		return valid;
	}

}
