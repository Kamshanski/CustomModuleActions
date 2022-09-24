package common

import com.intellij.ide.ui.laf.darcula.DarculaLaf
import java.awt.Container
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.LookAndFeel
import javax.swing.UIManager
import javax.swing.WindowConstants


inline fun runTestFrame(laf: LookAndFeel? = DarculaLaf(), action: JFrame.() -> Container) {
	UIManager.setLookAndFeel(laf)

	val frame = JFrame("Title")

	frame.layout = GridLayout(1, 1)
	frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
	frame.setSize(700, 700)

//    val scroll = JBScrollPane()
//    frame.contentPane = scroll
//    scroll.setViewportView(action())

	frame.add(action(frame))

	frame.isVisible = true
}