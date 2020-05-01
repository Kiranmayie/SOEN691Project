package datamining.visitors;

public class ActionEntityHandler {
	
	public int actionAbort=0;
	public int actionContinue=0;
	public int actionDefault=0;
	public int actionEmpty=0;
	public int actionLog=0;
	public int actionMethod=0;
	public int actionNestedTry=0;
	public int actionReturn=0;
	public int actionThrowCurrent=0;
	public int actionThrowNew=0;
	public int actionThrowWrap=0;
	public int actionTODO=0;
	
	public void setActionMethod(int actionMethod) {
		this.actionMethod = actionMethod;
	}
	public int getActionNestedTry() {
		return actionNestedTry;
	}
	
	public int getActionContinue() {
		return actionContinue;
	}
	public void setActionContinue(int actionContinue) {
		this.actionContinue = actionContinue;
	}
	
	public int getActionEmpty() {
		return actionEmpty;
	}
	public void setActionEmpty(int actionEmpty) {
		this.actionEmpty = actionEmpty;
	}
	public int getActionThrowWrap() {
		return actionThrowWrap;
	}
	public void setActionThrowWrap(int actionThrowWrap) {
		this.actionThrowWrap = actionThrowWrap;
	}

	public int getActionLog() {
		return actionLog;
	}
	public void setActionLog(int actionLog) {
		this.actionLog = actionLog;
	}
	public int getActionAbort() {
		return actionAbort;
	}
	public void setActionAbort(int actionAbort) {
		this.actionAbort = actionAbort;
	}
	
	public int getActionMethod() {
		return actionMethod;
	}
	
	public void setActionNestedTry(int actionNestedTry) {
		this.actionNestedTry = actionNestedTry;
	}
	public int getActionReturn() {
		return actionReturn;
	}
	public void setActionReturn(int actionReturn) {
		this.actionReturn = actionReturn;
	}
	public int getActionThrowCurrent() {
		return actionThrowCurrent;
	}
	public void setActionThrowCurrent(int actionThrowCurrent) {
		this.actionThrowCurrent = actionThrowCurrent;
	}
	public int getActionThrowNew() {
		return actionThrowNew;
	}
	public void setActionThrowNew(int actionThrowNew) {
		this.actionThrowNew = actionThrowNew;
	}
	public int getActionTODO() {
		return actionTODO;
	}
	public void setActionTODO(int actionTODO) {
		this.actionTODO = actionTODO;
	}
	
	public int getActionDefault() {
		return actionDefault;
	}
	public void setActionDefault(int actionDefault) {
		this.actionDefault = actionDefault;
	}
}
