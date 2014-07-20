package org.samsu.youdaofanyi.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jdom.Document;
import org.samsu.youdaofanyi.Activator;
import org.samsu.youdaofanyi.httpclient.HttpClientUtil;
import org.samsu.youdaofanyi.model.ResultModel;

public class QueryDialog extends Dialog{
	
	private Text queryText;
	private Text resultTextText;
	private Button queryButton;
	
    private Group infoGroup;

	protected QueryDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createContents(Composite parent) {
		  Shell shell = this.getShell(); 
		  shell.setSize(400, 300);
		  Monitor primary = shell.getMonitor(); 
		  Rectangle bounds = primary.getBounds(); 
		  Rectangle rect = shell.getBounds(); 
		  int x = bounds.x + (bounds.width - rect.width) / 2; 
		  int y = bounds.y + (bounds.height - rect.height) / 2 - 50;
		  shell.setText("翻译小助手");
		  shell.setLocation (x, y);
		  shell.setImage(Activator.getImageDescriptor("/icon/menu.png").createImage());
		  /*布局开始*/
		  Composite composite = new Composite(parent, SWT.NONE);
		  GridLayout layout = new GridLayout(3, false);
		  layout.marginBottom = 10;
		  layout.marginTop = 10;
		  layout.marginLeft = 10;
		  layout.marginRight = -30;
		  layout.horizontalSpacing = 30;
		  layout.verticalSpacing = 0;
		  composite.setLayout(layout);
		  composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  /*headerComposite...*/
		  Composite headerComposite = new Composite(composite, SWT.NONE);
		  headerComposite.setLayout(new GridLayout(3, false));
		  headerComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  new Label(headerComposite, SWT.NONE).setText("请输入:");
		  queryText = new Text(headerComposite, SWT.BORDER | SWT.MULTI);
		  queryText.setText(DialogUtil.getSelecedTextFromEditor());//设置选中的文字
		  queryText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  queryButton = new Button(headerComposite, SWT.NONE);
		  queryButton.setText("查询");
		  //给Button添加事件
		  addListenerToButton();
		  //******************************//
		  //***GROUP START***//
		  Composite infoComposite = new Composite(parent, SWT.NONE);
		  infoComposite.setLayout(new GridLayout(1, true));
		  infoComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  infoGroup = new Group(infoComposite, SWT.NONE);
		  infoGroup.setText("查询结果");
		  GridLayout groupLayout = new GridLayout(1, false);
		  groupLayout.marginBottom = 5;
		  groupLayout.marginTop = 5;
		  groupLayout.marginLeft = 10;
		  groupLayout.marginRight = 10;
		  infoGroup.setLayout(groupLayout);
		  infoGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  infoGroup.pack();
		  resultTextText = new Text(infoGroup, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		  resultTextText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  resultTextText.setText("" + System.getProperty("line.separator") + 
		  		""+ System.getProperty("line.separator") + 
		  		""+ System.getProperty("line.separator") + 
		  		""+ System.getProperty("line.separator") + 
		  		""+ System.getProperty("line.separator") + 
		  		""+ System.getProperty("line.separator") + 
		  		""+ System.getProperty("line.separator") + 
		  		""+ System.getProperty("line.separator") + 
//		  		""+ System.getProperty("line.separator") + 
		  		"");
		  return super.createContents(parent);
	}

	@Override
	protected Button createButton(Composite parent, int id,
	        String label, boolean defaultButton) {
	    if (id == IDialogConstants.CANCEL_ID || id == IDialogConstants.OK_ID) {
	    	return null;
	    }
	    return super.createButton(parent, id, label, defaultButton);
	}
	
	public void addListenerToButton(){
		queryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String qtext = queryText.getText();
				if(qtext.isEmpty()){
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "提示", "请输入或选择查询");
				} else {
					Document doc = HttpClientUtil.getDocumentByQuery(qtext);
					if(doc != null){
						ResultModel rm = HttpClientUtil.convertDocToModel(doc);
						resultTextText.setText(ResultModel.getFormattedDisplatString(rm));
					}
				}
				super.mouseDown(e);
			}
		});
	}
}
