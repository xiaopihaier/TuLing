package com.xiaopihaier;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONObject;

public class TuLing {
	static final int WIDTH = 500;
	static final int HEIGHT = 300;
	// 得到显示器屏幕的宽高
	public static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String hllo_begin = null;
		TextField text_user;
		TextArea text_tulin;
		JButton button;
		hllo_begin = "你好，我是图灵机器人，我的创造人是“背书包的小屁孩儿”，欢迎和我聊天*w*";
		// 界面
		JFrame jf = new JFrame("智能聊天机器人");// 标题
		jf.setSize(WIDTH, HEIGHT);
		JPanel panel = new JPanel();
		jf.setContentPane(panel);
		jf.setBounds((width - WIDTH) / 2, (height - HEIGHT) / 2, WIDTH, HEIGHT);// 使窗口居中
		button = new JButton("发送");
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");// 设置日期格式
		text_tulin = new TextArea();
		text_tulin.setText(df.format(new Date()) + "\n" + "豆豆：" + hllo_begin);
		text_user = new TextField();
		panel.setLayout(new FlowLayout());
		panel.add(text_tulin);
		text_tulin.setPreferredSize(new Dimension(450, 200));

		panel.add(text_user);
		text_user.setPreferredSize(new Dimension(350, 50));
		jf.addWindowListener(new WindowAdapter() {// 使text_user获取焦点
			public void windowOpened(WindowEvent e) {
				text_user.requestFocus();
			}
		});

		panel.add(button);
		button.setPreferredSize(new Dimension(100, 25));
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 后台逻辑
				// 申请图灵机器人后获取
				String APIKEY = "d5ab0be9da584b41928c6467b68a2630";
				String INFO;
				try {
					INFO = URLEncoder.encode(text_user.getText(), "utf-8");

					String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
					URL getUrl = new URL(getURL);
					HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
					connection.connect();
					// 取得输入流，并使用Reader读取
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(connection.getInputStream(), "utf-8"));
					StringBuffer sb = new StringBuffer();
					String line = "";
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					reader.close();
					// 断开连接
					connection.disconnect();
					String menu = sb.toString();
					JSONObject jsonObject = new JSONObject(menu);
					String text = jsonObject.getString("text");
					text_tulin.setText(df.format(new Date()) + "\n" + "豆豆:" + text);
					text_user.setText("");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

}
