package com.supermap.desktop.CtrlAction;

import com.supermap.desktop.Interface.IBaseItem;
import com.supermap.desktop.Interface.IForm;
import com.supermap.desktop.implement.CtrlAction;

public class CtrlActionWindowsHorizontal extends CtrlAction {
	
	public CtrlActionWindowsHorizontal(IBaseItem caller, IForm formClass) {
		super (caller, formClass);
	}

	@Override
	public void run() {
		// 未实现
	}

	@Override
	public boolean enable() {
		return true;
	}
}
