package org.eclipse.ui.examples.multipageeditor;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
 
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.jface.action.IAction;

/**
 * Manages the installation/deinstallation of global actions for multi-page editors.
 * Responsible for the redirection of global actions to the active editor.
 * Multi-page contributor replaces the contributors for the individual editors in the multi-page editor.
 */
public class MultiPageContributor extends MultiPageEditorActionBarContributor {
	private IEditorPart activeEditorPart;
/**
 * Creates a multi-page contributor.
 */
public MultiPageContributor() {
	super();
}
/**
 * Returns the action registed with the given text editor.
 * @return IAction or null if editor is null.
 */
protected IAction getAction(ITextEditor editor, String actionID) {
	return (editor == null ? null : editor.getAction(actionID));
}
/* (non-JavaDoc)
 * Method declared in AbstractMultiPageEditorActionBarContributor.
 */
 
public void setActivePage(IEditorPart part) {
	if (activeEditorPart == part)
		return;

	activeEditorPart = part;

	IActionBars actionBars = getActionBars();
	if (actionBars != null) {

		ITextEditor editor = (part instanceof ITextEditor) ? (ITextEditor) part : null;

		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.DELETE, getAction(editor, ITextEditorActionConstants.DELETE));
		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.UNDO, getAction(editor, ITextEditorActionConstants.UNDO));
		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.REDO, getAction(editor, ITextEditorActionConstants.REDO));
		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.CUT, getAction(editor, ITextEditorActionConstants.CUT));
		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.COPY, getAction(editor, ITextEditorActionConstants.COPY));
		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.PASTE, getAction(editor, ITextEditorActionConstants.PASTE));
		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.SELECT_ALL, getAction(editor, ITextEditorActionConstants.SELECT_ALL));
		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.FIND, getAction(editor, ITextEditorActionConstants.FIND));
		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.BOOKMARK, getAction(editor, ITextEditorActionConstants.BOOKMARK));
		actionBars.updateActionBars();
	}
}
}
