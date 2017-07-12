/*******************************************************************************
 * Copyright (c) 2017 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Lucas Bullen (Red Hat Inc.) - initial implementation
 *******************************************************************************/
package org.eclipse.ui.genericeditor.tests;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Test;

import org.eclipse.swt.widgets.Display;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.tests.util.DisplayHelper;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.genericeditor.ExtensionBasedTextEditor;
import org.eclipse.ui.part.FileEditorInput;

import org.eclipse.ui.texteditor.IDocumentProvider;

public class ReconcilerTest extends AbstratGenericEditorTest {

	protected ExtensionBasedTextEditor secondEditor;

	@Test
	public void testReconciler() throws Exception {
		performTestOnEditor();
	}

	@Test
	public void testMultipleEditors() throws Exception {
		IProject secondProject = ResourcesPlugin.getWorkspace().getRoot().getProject(getClass().getName() + System.currentTimeMillis());
		secondProject.create(null);
		secondProject.open(null);
		IFile secondFile = secondProject.getFile("foo.txt");
		secondFile.create(new ByteArrayInputStream("bar 'bar'".getBytes()), true, null);
		secondEditor = (ExtensionBasedTextEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().openEditor(new FileEditorInput(secondFile), "org.eclipse.ui.genericeditor.GenericEditor");
		performTestOnEditor();
	}

	private void performTestOnEditor() throws Exception {
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument doc = dp.getDocument(editor.getEditorInput());

		doc.set("foo");

		new DisplayHelper() {
			@Override
			protected boolean condition() {
					try {
						return !doc.get(0, doc.getLineLength(0)).contains("foo");
					} catch (BadLocationException e) {
						return false;
					}
			}
		}.waitForCondition(Display.getDefault().getActiveShell().getDisplay(), 2000);
		Assert.assertTrue("file was not affected by reconciler", doc.get().contains("BAR"));
	}

}
