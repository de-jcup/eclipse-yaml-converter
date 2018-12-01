package de.jcup.yamlconverter.popup.actions;

import java.io.File;
import java.util.Iterator;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import de.jcup.yamlconverter.Activator;
import de.jcup.yamlconverter.YamlConverter;

public abstract class AbstractConverterAction implements IObjectActionDelegate{

	private Shell shell;
	private IStructuredSelection ss;
	protected YamlConverter converter;

	public AbstractConverterAction() {
		converter = Activator.getDefault().getConverter();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IObjectActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
	
		Iterator<?> it = ss.iterator();
		while (it.hasNext()){
			Object obj = it.next();
			if (obj instanceof IFile){
				 IFile file = (IFile) obj;
				 File javaFile = file.getLocation().toFile();
				 try {
					File newFile = convert(javaFile);
					
					/* refresh inside workspace, so new generated file because visible */
					file.getParent().refreshLocal(IFile.DEPTH_ONE, null);
					
					IFileStore fileStore =  EFS.getLocalFileSystem().getStore(new Path(newFile.getAbsolutePath()));
					
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage();
					IDE.openEditorOnFileStore(page, fileStore);
				} catch (Exception e) {
					Activator.getDefault().getLog().log(new Status(IStatus.ERROR,Activator.PLUGIN_ID,getClass().getSimpleName()+":Failed to convert:"+javaFile, e));
				}
			}
		}
	}

	protected abstract File convert(File javaFile) throws Exception;

	public void selectionChanged(IAction action, ISelection selection) {
		if (! (selection instanceof IStructuredSelection)){
			return;
		}
		this.ss = (IStructuredSelection) selection;
		
	}

}