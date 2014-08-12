package com.beifengbpm.pd.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.beifengbpm.pd.datasource.DBControl;
import com.beifengbpm.pd.vo.WorkItem;
import com.beifengbpm.tools.Information;
import com.beifengbpm.tools.StringUtils;

public class MainFrame extends JFrame {

	private JToolBar jt;
	private JButton startButton;
	private JButton endButton;
	private JButton lineButton;
	private JButton shougongButton;
	private JButton tongbuButton;
	private JButton luyouButton;
	private JButton fileButton;
	private JPanel canvas;
	private JList components;
	private JButton saveflow = new JButton("保存流程");
	private Vector namevector = new Vector();
	private Vector itemvector = new Vector();
	public String flowname;
	public String flowfile;

	public MainFrame() {
		Information.FLOWMODELID = StringUtils.getkeys();
		this.setTitle("PROCESSDESIGNER");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(d.width, d.height - 30);
		init();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void init() {
		jt = new JToolBar();
		jt.setPreferredSize(new Dimension(0, 50));
		this.add(jt, BorderLayout.NORTH);
		startButton = new JButton("开始节点");
		endButton = new JButton("开始节点");
		lineButton = new JButton("连接线");
		shougongButton = new JButton("手工活动");
		tongbuButton = new JButton("同步活动");
		luyouButton = new JButton("路由");
		fileButton = new JButton("模型文件");
		jt.add(startButton);
		jt.add(endButton);
		jt.add(lineButton);
		jt.add(shougongButton);
		jt.add(tongbuButton);
		jt.add(luyouButton);
		jt.add(fileButton);
		jt.add(saveflow);
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				openStartFrame();
			}
		});
		endButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				openEndFrame();
			}
		});
		shougongButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				openShougongActivityFram();
			}
		});
		tongbuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openTongbuActivityFrame();
			}
		});
		lineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openLuyouFrame();
			}
		});
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSaveFrame();
			}
		});
		saveflow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				saveflow();
			}
		});
		canvas = new JPanel();
		canvas.setLayout(new BorderLayout());
		components = new JList();
		canvas.add(components);
		this.add(canvas, BorderLayout.CENTER);
	}

	public void setItemValue(WorkItem item) {
		itemvector.add(item);
		namevector.add(item.getWorkitemtype());
		components.setListData(namevector);
		if (item.getWorkitemtype() == 0) {
			startButton.setEnabled(false);
		} else if (item.getWorkitemtype() == 1) {
			endButton.setEnabled(false);
		}
	}

	public void setNameandFile(String name, String file) {
		this.flowname = name;
		this.flowfile = file;
	}

	private void openStartFrame() {
		new StartFrame(this);
	}

	private void openEndFrame() {
		new EndFrame(this);
	}

	private void openShougongActivityFrame() {
		new ShougongActivityFrame(this);
	}

	private void openTongbuActivityFrame() {
		new TongbuActivity(this);
	}

	private void openLineFrame() {
		new LineFrame(this, itemvector);
	}

	private void openLuyouFrame() {
		new LuyouFrame(this);
	}

	private void openSaveFrame() {
		new SaveFrame(this);
	}

	private void saveflow() {
		List<WorkItem> list = new ArrayList<WorkItem>();
		for (int i = 0; i < itemvector.size(); i++) {
			WorkItem item = (WorkItem) itemvector.get(i);
			if (item.getWorkitemtype() == 0) {
				for (int j = 0; j < itemvector.size(); j++) {
					WorkItem line = (WorkItem) itemvector.get(i);
					if (line.getWorkitemtype() == 2
							&& line.getFromId().equals(item.getWorkitemId())) {
						item.setToId("''" + line.getWorkitemId() + "''");
						list.add(item);
						break;
					}
				}
			} else if (item.getWorkitemtype() == 1) {
				for (int j = 0; j < itemvector.size(); j++) {
					WorkItem line = (WorkItem) itemvector.get(i);
					if (line.getWorkitemtype() == 2
							&& line.getToId().equals(item.getWorkitemId())) {
						item.setFormId("''" + line.getWorkitemId() + "''");
						list.add(item);
						break;
					}
				}
			} else if (item.getWorkitemtype() == 2) {
				String fromid = "''" + item.getFromId() + "''";
				String toid = "''" + item.getToId() + "''";
				item.setToId(toid);
				item.setFromId(fromid);
				list.add(item);
			} else if (item.getWorkitemtype() == 3
					|| item.getWorkitemtype() == 4
					|| item.getWorkitemtype() == 5) {
				for (int j = 0; j < itemvector.size(); j++) {
					WorkItem line = (WorkItem) itemvector.get(i);
					if (line.getWorkitemtype() == 2
							&& line.getToId().equals(item.getWorkitemId())) {
						String fromid = item.getFromId();
						if (StringUtils.isNotEmpty(fromid)) {
							fromid += ",''" + line.getWorkitemId() + "''";
						} else {
							fromid = "''" + line.getWorkitemId() + "''";
						}
						item.setFromId(fromid);
					}
					if (line.getWorkitemtype() == 2
							&& line.getFromId().equals(item.getWorkitemId())) {
						String toid = item.getToId();
						if (StringUtils.isNotEmpty(toid)) {
							toid = ",''" + line.getWorkitemId() + "''";
						} else {
							toid = "''" + line.getWorkitemId() + "''";
						}
						item.setToId(toid);
					}
				}
				list.add(item);
			}
		}
		DBControl db = null;

		String modelsql = "insert into FLOWMODEL values('"
				+ Information.FLOWMODELID + "','" + flowname + "','" + flowfile
				+ "')";
		System.out.println(modelsql);
		db = new DBControl();
		db.setData(modelsql);
		db.close();
		for (WorkItem item : list) {
			db = new DBControl();
			String fromId = item.getFromId() == null ? "" : item.getFromId();
			String toid = item.getToId() == null ? "" : item.getToId();
			String condition = item.getCondition() == null ? "" : item
					.getCondition();
			String workitemuserid = item.getWorkitemuserId() == null ? ""
					: item.getWorkitemuserId();
			String formid = item.getFormId() == null ? "" : item.getFormId();
			String sql = "insert into WORKITEM values('" + item.getWorkitemId()
					+ "','" + Information.FLOWMODELID + "','"
					+ item.getWorkitemname() + "'," + item.getWorkitemtype()
					+ ",'" + fromId + "','" + toid + "','" + condition + "','"
					+ workitemuserid + "','" + formid + "',null)";
			System.out.println(sql);
			db.setData(sql);
			db.close();
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}