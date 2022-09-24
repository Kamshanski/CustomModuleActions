package dev.itssho.module.component.idea.action.idea

import com.intellij.CommonBundle
import com.intellij.history.LocalHistory
import com.intellij.ide.IdeBundle
import com.intellij.ide.actions.CreateElementActionBase
import com.intellij.ide.util.DirectoryUtil
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.UnknownFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.registry.Registry
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.util.IncorrectOperationException
import dev.itssho.module.component.idea.common.IdeaException
import java.awt.Component
import java.io.File
import java.util.StringTokenizer

// Исходник: com.intellij.ide.actions.CreateDirectoryOrPackageHandler
class MyCreateDirectoryOrPackageHandler(
	private val project: Project,
	private val directory: PsiDirectory,
	private var isDirectory: Boolean,
	private val delimiters: String,
	private val dialogParent: Component? = null,
) : InputValidatorEx {

	var createdDirectory: PsiDirectory? = null
		private set
	private var errorText: String? = null
	var error: IdeaException? = null
		private set

	override fun checkInput(inputString: String?): Boolean {
		val tokenizer = StringTokenizer(inputString, delimiters)
		var vFile: VirtualFile? = directory.virtualFile
		var firstToken = true
		while (tokenizer.hasMoreTokens()) {
			val token = tokenizer.nextToken()
			if (!tokenizer.hasMoreTokens() && (token == "." || token == "..")) {
				errorText = IdeBundle.message("error.invalid.directory.name", token)
				error = NotValidDirectoryName(errorText)
				return false
			}
			if (vFile != null) {
				if (firstToken && "~" == token) {
					val userHomeDir = VfsUtil.getUserHomeDir()
					if (userHomeDir == null) {
						errorText = IdeBundle.message("error.user.home.directory.not.found")
						error = UserHomeDirectoryNotFound(errorText)
						return false
					}
					vFile = userHomeDir
				} else if (".." == token) {
					val parent = vFile.parent
					if (parent == null) {
						errorText = IdeBundle.message("error.invalid.directory", vFile.presentableUrl + File.separatorChar + "..")
						error = NotValidDirectory(errorText)
						return false
					}
					vFile = parent
				} else if ("." != token) {
					val child = vFile.findChild(token)
					if (child != null) {
						if (!child.isDirectory) {
							errorText = IdeBundle.message("error.file.with.name.already.exists", token)
							error = AlreadyExistsException(errorText)
							return false
						} else if (!tokenizer.hasMoreTokens()) {
							errorText = IdeBundle.message("error.directory.with.name.already.exists", token)
							error = AlreadyExistsException(errorText)
							return false
						}
					}
					vFile = child
				}
			}
			if (FileTypeManager.getInstance().isFileIgnored(token)) {
				errorText = if (isDirectory) IdeBundle.message("warning.create.directory.with.ignored.name", token) else IdeBundle.message("warning.create.package.with.ignored.name", token)
				error = PsiCreationException(errorText)
				return true
			}
			if (!isDirectory && token.isNotEmpty() && !PsiDirectoryFactory.getInstance(project).isValidPackageName(token)) {
				errorText = IdeBundle.message("error.invalid.java.package.name")
				error = PsiCreationException(errorText)
				return true
			}
			firstToken = false
		}
		errorText = null
		error = null

		return true
	}

	override fun getErrorText(inputString: String?): String? {
		return errorText
	}

	override fun canClose(subDirName: String): Boolean {
		if (subDirName.isEmpty()) {
			showErrorDialog(IdeBundle.message("error.name.should.be.specified"))
			return false
		}
		val multiCreation = StringUtil.containsAnyChar(subDirName, delimiters)
		if (!multiCreation) {
			try {
				directory.checkCreateSubdirectory(subDirName)
			} catch (ex: IncorrectOperationException) {
				showErrorDialog(CreateElementActionBase.filterMessage(ex.message))
				return false
			}
		}
		if (hasSameNameFile(subDirName)) {
			throw IdeaException("There is a file with the same name '$subDirName'. Select another name for file or directory")
		}

		doCreateElement(subDirName)
		return createdDirectory != null
	}

	private fun hasSameNameFile(subDirName: String): Boolean {
		if (StringUtil.countChars(subDirName, '.') == 1 && Registry.`is`("ide.suggest.file.when.creating.filename.like.directory")) {
			val fileType = findFileTypeBoundToName(subDirName)
			return fileType != null
		} else {
			return false
		}
	}

	private fun findFileTypeBoundToName(name: String?): FileType? {
		val fileType = FileTypeManager.getInstance().getFileTypeByFileName(name!!)
		return if (fileType is UnknownFileType) null else fileType
	}

	private fun doCreateElement(subDirName: String) {
		val commandKey = if (isDirectory) {
			IdeBundle.message("command.create.directory")
		} else {
			IdeBundle.message("command.create.package")
		}
		invokeAndWaitForWriteCommand(project, commandKey) {
			val dirPath = directory.virtualFile.presentableUrl
			val actionName = IdeBundle.message("progress.creating.directory", dirPath, File.separator, subDirName)
			val action = LocalHistory.getInstance().startAction(actionName)
			try {
				createDirectories(subDirName)
			} catch (ex: IncorrectOperationException) {
				ApplicationManager.getApplication().invokeLater { showErrorDialog(CreateElementActionBase.filterMessage(ex.message)) }
			} finally {
				action.finish()
			}
		}
	}

	private fun showErrorDialog(message: String) {
		val title = CommonBundle.getErrorTitle()
		val icon = Messages.getErrorIcon()
		if (dialogParent != null) {
			Messages.showMessageDialog(dialogParent, message, title, icon)
		} else {
			Messages.showMessageDialog(project, message, title, icon)
		}
	}

	private fun createDirectories(subDirName: String?) {
		createdDirectory = DirectoryUtil.createSubdirectories(subDirName, directory, delimiters)
	}
}
/* Вид, в котором файл был скопипасчен из иходников

// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.ide.actions;

import com.intellij.CommonBundle;
import com.intellij.history.LocalHistory;
import com.intellij.history.LocalHistoryAction;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.util.DirectoryUtil;
import com.intellij.lang.LangBundle;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.UnknownFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.StringTokenizer;

public class CreateDirectoryOrPackageHandler implements InputValidatorEx {
  @Nullable private final Project project;
  @NotNull private final PsiDirectory myDirectory;
  private final boolean isDirectory;
  @Nullable private PsiFileSystemItem createdDirectory = null;
  @NotNull private final String myDelimiters;
  @Nullable private final Component myDialogParent;
  private String myErrorText;

  public CreateDirectoryOrPackageHandler(@Nullable Project project,
                                         @NotNull PsiDirectory directory,
                                         boolean isDirectory,
                                         @NotNull final String delimiters) {
    this(project, directory, isDirectory, delimiters, null);
  }

  public CreateDirectoryOrPackageHandler(@Nullable Project project,
                                         @NotNull PsiDirectory directory,
                                         boolean isDirectory,
                                         @NotNull final String delimiters,
                                         @Nullable Component dialogParent) {
    project = project;
    myDirectory = directory;
    isDirectory = isDirectory;
    myDelimiters = delimiters;
    myDialogParent = dialogParent;
  }

  @Override
  public boolean checkInput(String inputString) {
    final StringTokenizer tokenizer = new StringTokenizer(inputString, myDelimiters);
    VirtualFile vFile = myDirectory.getVirtualFile();
    boolean firstToken = true;
    while (tokenizer.hasMoreTokens()) {
      final String token = tokenizer.nextToken();
      if (!tokenizer.hasMoreTokens() && (token.equals(".") || token.equals(".."))) {
        myErrorText = IdeBundle.message("error.invalid.directory.name", token);
        return false;
      }
      if (vFile != null) {
        if (firstToken && "~".equals(token)) {
          final VirtualFile userHomeDir = VfsUtil.getUserHomeDir();
          if (userHomeDir == null) {
            myErrorText = IdeBundle.message("error.user.home.directory.not.found");
            return false;
          }
          vFile = userHomeDir;
        }
        else if ("..".equals(token)) {
          final VirtualFile parent = vFile.getParent();
          if (parent == null) {
            myErrorText = IdeBundle.message("error.invalid.directory", vFile.getPresentableUrl() + File.separatorChar + "..");
            return false;
          }
          vFile = parent;
        }
        else if (!".".equals(token)){
          final VirtualFile child = vFile.findChild(token);
          if (child != null) {
            if (!child.isDirectory()) {
              myErrorText = IdeBundle.message("error.file.with.name.already.exists", token);
              return false;
            }
            else if (!tokenizer.hasMoreTokens()) {
              myErrorText = IdeBundle.message("error.directory.with.name.already.exists", token);
              return false;
            }
          }
          vFile = child;
        }
      }
      if (FileTypeManager.getInstance().isFileIgnored(token)) {
        myErrorText = isDirectory ? IdeBundle.message("warning.create.directory.with.ignored.name", token)
                                    : IdeBundle.message("warning.create.package.with.ignored.name", token);
        return true;
      }
      if (!isDirectory && token.length() > 0 && !PsiDirectoryFactory.getInstance(project).isValidPackageName(token)) {
        myErrorText = IdeBundle.message("error.invalid.java.package.name");
        return true;
      }
      firstToken = false;
    }
    myErrorText = null;
    return true;
  }

  @Override
  public String getErrorText(String inputString) {
    return myErrorText;
  }

  @Override
  public boolean canClose(final String subDirName) {

    if (subDirName.length() == 0) {
      showErrorDialog(IdeBundle.message("error.name.should.be.specified"));
      return false;
    }

    final boolean multiCreation = StringUtil.containsAnyChar(subDirName, myDelimiters);
    if (!multiCreation) {
      try {
        myDirectory.checkCreateSubdirectory(subDirName);
      }
      catch (IncorrectOperationException ex) {
        showErrorDialog(CreateElementActionBase.filterMessage(ex.getMessage()));
        return false;
      }
    }

    final Boolean createFile = suggestCreatingFileInstead(subDirName);
    if (createFile == null) {
      return false;
    }

    doCreateElement(subDirName, createFile);

    return createdDirectory != null;
  }

  @Nullable
  private Boolean suggestCreatingFileInstead(String subDirName) {
    Boolean createFile = false;
    if (StringUtil.countChars(subDirName, '.') == 1 && Registry.is("ide.suggest.file.when.creating.filename.like.directory")) {
      FileType fileType = findFileTypeBoundToName(subDirName);
      if (fileType != null) {
        String message = LangBundle.message("dialog.message.name.you.entered", subDirName);
        int ec = Messages.showYesNoCancelDialog(project, message,
                                                LangBundle.message("dialog.title.file.name.detected"),
                                                LangBundle.message("button.yes.create.file"),
                                                LangBundle.message("button.no.create", isDirectory ?
                                                                                       LangBundle.message("button.no.create.directory") :
                                                                                       LangBundle.message("button.no.create.package")),
                                                CommonBundle.getCancelButtonText(),
                                                fileType.getIcon());
        if (ec == Messages.CANCEL) {
          createFile = null;
        }
        if (ec == Messages.YES) {
          createFile = true;
        }
      }
    }
    return createFile;
  }

  @Nullable
  public static FileType findFileTypeBoundToName(String name) {
    FileType fileType = FileTypeManager.getInstance().getFileTypeByFileName(name);
    return fileType instanceof UnknownFileType ? null : fileType;
  }

  private void doCreateElement(final String subDirName, final boolean createFile) {
    Runnable command = () -> {
      final Runnable run = () -> {
        String dirPath = myDirectory.getVirtualFile().getPresentableUrl();
        String actionName = IdeBundle.message("progress.creating.directory", dirPath, File.separator, subDirName);
        LocalHistoryAction action = LocalHistory.getInstance().startAction(actionName);
        try {
          if (createFile) {
            CreateFileAction.MkDirs mkdirs = new CreateFileAction.MkDirs(subDirName, myDirectory);
            createdDirectory = mkdirs.directory.createFile(mkdirs.newName);
          } else {
            createDirectories(subDirName);
          }
        }
        catch (final IncorrectOperationException ex) {
          ApplicationManager.getApplication().invokeLater(() -> showErrorDialog(CreateElementActionBase.filterMessage(ex.getMessage())));
        }
        finally {
          action.finish();
        }
      };
      ApplicationManager.getApplication().runWriteAction(run);
    };
    CommandProcessor.getInstance().executeCommand(project, command, createFile ? IdeBundle.message("command.create.file")
                                                                                 : isDirectory
                                                                      ? IdeBundle.message("command.create.directory")
                                                                      : IdeBundle.message("command.create.package"), null);
  }

  private void showErrorDialog(String message) {
    String title = CommonBundle.getErrorTitle();
    Icon icon = Messages.getErrorIcon();
    if (myDialogParent != null) {
      Messages.showMessageDialog(myDialogParent, message, title, icon);
    }
    else {
      Messages.showMessageDialog(project, message, title, icon);
    }
  }

  protected void createDirectories(String subDirName) {
    createdDirectory = DirectoryUtil.createSubdirectories(subDirName, myDirectory, myDelimiters);
  }

  @Nullable
  public PsiFileSystemItem getCreatedElement() {
    return createdDirectory;
  }
}

 */