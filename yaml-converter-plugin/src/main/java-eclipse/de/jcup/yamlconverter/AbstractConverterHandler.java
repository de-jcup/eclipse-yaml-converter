package de.jcup.yamlconverter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

public abstract class AbstractConverterHandler extends AbstractHandler {
	protected YamlConverter converter;

	public AbstractConverterHandler() {
		converter = Activator.getDefault().getConverter();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (!(selection instanceof IStructuredSelection)) {
			return null;
		}
		IStructuredSelection ss = (IStructuredSelection) selection;

		Iterator<?> it = ss.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof IFile) {
				IFile file = (IFile) obj;
				File javaFile = file.getLocation().toFile();
				try {
					File newFile = convert(javaFile);

					/*
					 * refresh inside workspace, so new generated file because
					 * visible
					 */
					file.getParent().refreshLocal(IFile.DEPTH_ONE, null);

					IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(newFile.getAbsolutePath()));

					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IDE.openEditorOnFileStore(page, fileStore);
				} catch (Exception e) {
					Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							getClass().getSimpleName() + ":Failed to convert:" + javaFile, e));
				}
			}
		}

		return null;
	}

	protected abstract File convert(File javaFile) throws IOException;

}
