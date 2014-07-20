package org.samsu.youdaofanyi.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class OpenDialogHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		QueryDialog dialog = new QueryDialog(Display.getCurrent().getActiveShell());
		dialog.open();
		return null;
	}

}
